package com.example.smarthomeapp.injection.provider

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smarthomeapp.presentation.authentication.contract.AuthenticationViewModel
import com.example.smarthomeapp.presentation.authentication.login.contract.LoginViewModel
import com.example.smarthomeapp.presentation.authentication.register.contract.RegisterViewModel
import com.example.smarthomeapp.presentation.device.contract.DeviceViewModel
import com.example.smarthomeapp.presentation.main.contract.MainViewModel
import com.example.smarthomeapp.presentation.main.home.contract.HomeViewModel
import com.example.smarthomeapp.presentation.main.notification.contract.NotificationViewModel
import com.example.smarthomeapp.presentation.main.profile.contract.ProfileViewModel
import com.example.smarthomeapp.presentation.room_detail.contract.RoomDetailViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class ViewModelFactory @Inject constructor(private val provideMap: MutableMap<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return provideMap.get(modelClass as Class<*>)?.get() as T?
            ?: throw RuntimeException(modelClass.simpleName + " haven't been added bind-map, check IViewModelProvider")
    }
}

@Singleton
class InjectedAndroidViewModelFactory
@Inject constructor(
    application: Application,
    private val providerMap: MutableMap<Class<out AndroidViewModel>, @JvmSuppressWildcards Provider<AndroidViewModel>>
) : ViewModelProvider.AndroidViewModelFactory(application) {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (AndroidViewModel::class.java.isAssignableFrom(modelClass))
            return providerMap.get(modelClass as Class<*>)?.get() as T?
                ?: throw RuntimeException(modelClass.simpleName + " is not added to binds-map, check ViewModelModule")
        else
            return super.create(modelClass)
    }
}

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class AndroidViewModelKey(val value: KClass<out AndroidViewModel>)

@Module
abstract class IViewModelProvider {

    @Binds
    abstract fun bindViewModelProviderFactory(vmf: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    abstract fun bindAndroidViewModelProviderFactory(vmf: InjectedAndroidViewModelFactory): ViewModelProvider.AndroidViewModelFactory

    @AndroidViewModelKey(MainViewModel::class)
    @Binds
    @IntoMap
    abstract fun provideMainViewModel(vm: MainViewModel): AndroidViewModel

    @AndroidViewModelKey(HomeViewModel::class)
    @Binds
    @IntoMap
    abstract fun provideHomeViewModel(vm: HomeViewModel): AndroidViewModel

    @AndroidViewModelKey(NotificationViewModel::class)
    @Binds
    @IntoMap
    abstract fun provideNotificationViewModel(vm: NotificationViewModel): AndroidViewModel

    @AndroidViewModelKey(ProfileViewModel::class)
    @Binds
    @IntoMap
    abstract fun provideProfileViewModel(vm: ProfileViewModel): AndroidViewModel

    @AndroidViewModelKey(RoomDetailViewModel::class)
    @Binds
    @IntoMap
    abstract fun provideRoomDetailViewModel(vm: RoomDetailViewModel): AndroidViewModel

    @AndroidViewModelKey(DeviceViewModel::class)
    @Binds
    @IntoMap
    abstract fun provideDeviceViewModel(vm: DeviceViewModel): AndroidViewModel

    @AndroidViewModelKey(AuthenticationViewModel::class)
    @Binds
    @IntoMap
    abstract fun provideAuthenticationViewModel(vm: AuthenticationViewModel): AndroidViewModel

    @AndroidViewModelKey(LoginViewModel::class)
    @Binds
    @IntoMap
    abstract fun provideLoginViewModel(vm: LoginViewModel): AndroidViewModel

    @AndroidViewModelKey(RegisterViewModel::class)
    @Binds
    @IntoMap
    abstract fun provideRegisterViewModel(vm: RegisterViewModel): AndroidViewModel

}