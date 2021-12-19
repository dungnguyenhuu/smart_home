package com.example.smarthomeapp.base.scene

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smarthomeapp.base.dialog.ConfirmBuilder
import dagger.android.support.DaggerFragment
import timber.log.Timber
import java.lang.ref.WeakReference

abstract class BaseFragment : DaggerFragment(), BaseContract.Scene {

    private var baseActivityWeakReference: WeakReference<BaseActivity<*, *>?>? = null

    @CallSuper
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is BaseActivity<*, *>) {
            baseActivityWeakReference =
                WeakReference(activity as BaseActivity<*, *>?)
        }
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        baseActivityWeakReference = null
    }

    protected fun getBaseActivity(): BaseActivity<*, *>? {
        return baseActivityWeakReference.let { it ->
            if (it?.get() == null) {
                (activity as? BaseActivity<*, *>)?.also {
                    baseActivityWeakReference = WeakReference(it)
                }
            } else it.get()
        }
    }

    override fun getSceneArguments() = arguments

    override fun getSceneResources() = resources

    override fun showLoading(loadingType: Int) {
        getBaseActivity()?.showLoading(loadingType)
    }

    override fun hideLoading(loadingType: Int) {
        getBaseActivity()?.hideLoading(loadingType)
    }

    override fun showMessage(builder: ConfirmBuilder) {
        getBaseActivity()?.showMessage(builder)
    }

    override fun toast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun toast(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onApplicationFailure(errorCode: Int, message: String?) {
        getBaseActivity()?.onApplicationFailure(errorCode, message)
    }

    override fun onNoNetworkConnection() {
        getBaseActivity()?.onNoNetworkConnection()
    }

    override fun requestSceneBackward() {
    }

    override fun requestSceneFinish() {
    }

    protected fun <T : ViewDataBinding> inflateViewDataBinding(
        inflater: LayoutInflater,
        @LayoutRes layoutResId: Int,
        container: ViewGroup?
    ): T {
        return DataBindingUtil.inflate(inflater, layoutResId, container, false)
    }

    protected open fun onSceneInit() {}

    protected open fun onSceneReady() {}
}

@Suppress("unused")
abstract class BaseVmFragment<SCENE : BaseContract.Scene, VIEWMODEL : BaseContract.ViewModel<SCENE>> :
    BaseFragment() {

    protected var viewBinding: ViewDataBinding? = null
        private set
    protected val viewModel: VIEWMODEL by lazy {
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onSceneInit()
        return onCreateViewDataBinding(inflater, container).apply {
            lifecycleOwner = this@BaseVmFragment
        }.also {
            viewBinding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupComponents()
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        viewModel.run {
            onViewModelDestroy()
            onDetachFromScene()
        }
    }

    open fun reloadData() {
    }

    /**
     * Create content viewbinding
     *
     * @return the content viewbinding
     */
    protected abstract fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewDataBinding

    /**
     * Define the viewmodel class for this scene. It will be used for inject viewmodel from [ViewModelProvider]
     *
     * @return viewmodel class name
     */
    protected abstract fun getViewModelClass(): Class<out ViewModel>

    /**
     * Define view-model variables to bind viewmodel with viewbinding
     *
     * @return viewmodel variables id (BR.vm,... or [BinderConst.NOT_BINDING])
     */
    protected abstract fun getViewModelVariableId(): Int

    protected abstract fun <VM : ViewModel> createViewModel(vmClass: Class<VM>): VM

    private fun setupComponents() {
        viewModel.run {
            @Suppress("UNCHECKED_CAST")
            onAttachToScene(this@BaseVmFragment as SCENE)
            onAttachLifecycle(this@BaseVmFragment)
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

    @Suppress("UNCHECKED_CAST")
    private fun initViewModel(): VIEWMODEL =
        getViewModelClass().let {
            createViewModel(it) as VIEWMODEL
        }
}