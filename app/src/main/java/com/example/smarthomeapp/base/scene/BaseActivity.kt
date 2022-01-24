package com.example.smarthomeapp.base.scene

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smarthomeapp.base.annotation.LoadingType
import com.example.smarthomeapp.base.dialog.ConfirmBuilder
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber
import java.lang.ref.WeakReference

abstract class BaseActivity<SCENE : BaseContract.Scene, VIEWMODEL : BaseContract.ViewModel<SCENE>> :
    DaggerAppCompatActivity(), BaseContract.Scene {

    companion object {
        private const val TAG_LOADING_DIALOG = "TAG_LOADING_DIALOG"
        private const val TAG_CONFIRM_DIALOG = "TAG_CONFIRM_DIALOG"
    }

    /**
     * Get content viewbinding, may be null in [.onSceneInit]
     *
     * @return the viewbinding
     */
    protected var viewBinding: ViewDataBinding? = null
        private set

    /**
     * Get viewmodel which currently attached to this scene, may be null in [.onSceneInit]
     *
     * @return the viewmodel
     */
    protected val viewModel by lazy {
        initViewModel()
    }
    private var loadingDialogWeakReference: WeakReference<DialogFragment?> = WeakReference(null)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onSceneInit()
        viewBinding = onCreateViewDataBinding().apply {
            lifecycleOwner = this@BaseActivity
        }
        viewModel.run {
            @Suppress("UNCHECKED_CAST")
            onAttachToScene(this@BaseActivity as SCENE)
            onAttachLifecycle(this@BaseActivity)
            getViewModelVariableId().let {
                if (it != BinderConst.NOT_BINDING) {
                    viewBinding?.setVariable(it, this)
                }
            }
            onViewModelCreated()
        }
        Timber.d("INIT s=${javaClass.simpleName} v=${viewBinding?.javaClass?.simpleName} vm=${viewModel.javaClass.simpleName}")
        viewBinding?.executePendingBindings()
        onSceneReady()
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        viewModel.run {
            onViewModelDestroy()
            onDetachLifecycle(this@BaseActivity)
            onDetachFromScene()
        }
    }

    open fun reloadData() {
    }

    override fun getSceneResources() = resources

    override fun getSceneArguments() = intent?.extras

    override fun showLoading(loadingType: Int) {
        if (loadingType != LoadingType.BLOCKING) {
            return
        }
        loadingDialogWeakReference.get().let {
            it ?: provideLoadingDialog().also {
                loadingDialogWeakReference = WeakReference(it)
            }
        }.run {
            if (!isVisible && !isAdded) {
                show(supportFragmentManager, TAG_LOADING_DIALOG)
            }
        }
    }

    override fun hideLoading(loadingType: Int) {
        if (loadingType != LoadingType.BLOCKING) {
            return
        }
        loadingDialogWeakReference.clear()
        BaseDialog.dismissIfShowing(this, TAG_LOADING_DIALOG)
    }

    override fun showMessage(builder: ConfirmBuilder) {
        BaseDialog.dismissIfShowing(this, TAG_CONFIRM_DIALOG)
        provideConfirmDialog(builder).show(supportFragmentManager, TAG_CONFIRM_DIALOG)
    }

    override fun onApplicationFailure(errorCode: Int, message: String?) {
    }

    override fun toast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun toast(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onNoNetworkConnection() {

    }

    override fun requestSceneBackward() {
        onBackPressed()
    }

    override fun requestSceneFinish() {
        finish()
    }

    /**
     * Create content viewbinding
     *
     * @return the content viewbinding
     */
    protected abstract fun onCreateViewDataBinding(): ViewDataBinding

    /**
     * Define the viewmodel class for this scene. It will be used for inject viewmodel from [ViewModelProvider]
     *
     * @return viewmodel class name
     */
    protected abstract fun getViewModelClass(): Class<out ViewModel>

    /**
     * Support function for [.setContentView] with create viewbinding from layout resource id
     *
     * @param layoutRes the layout resource id
     * @param <T>       type of generated binding class
     * @return the viewbinding
    </T> */
    protected fun <T : ViewDataBinding> setContentViewBinding(@LayoutRes layoutRes: Int): T {
        return DataBindingUtil.setContentView(this, layoutRes)
    }

    /**
     * Define view-model variables to bind viewmodel with viewbinding
     *
     * @return viewmodel variables id (BR.vm,... or [BinderConst.NOT_BINDING])
     */
    protected abstract fun getViewModelVariableId(): Int

    protected abstract fun provideLoadingDialog(): DialogFragment

    protected abstract fun provideConfirmDialog(builder: ConfirmBuilder): DialogFragment

    protected abstract fun <VM> createViewModel(vmClass: Class<VM>): VM where VM : ViewModel

    /**
     * Call when scene starts to initialize
     */
    protected open fun onSceneInit() {}

    /**
     * Call after scene finish it's initialization
     */
    protected open fun onSceneReady() {}

    @Suppress("UNCHECKED_CAST")
    private fun initViewModel(): VIEWMODEL =
        getViewModelClass().let {
            createViewModel(it) as VIEWMODEL
        }

}