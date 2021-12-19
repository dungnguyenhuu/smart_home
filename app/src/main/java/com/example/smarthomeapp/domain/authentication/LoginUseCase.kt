package com.example.smarthomeapp.domain.authentication

import com.example.smarthomeapp.R
import com.example.smarthomeapp.base.domain.usecase.SingleUseCase
import com.example.smarthomeapp.data.pojo.authentication.LoginRequest
import com.example.smarthomeapp.data.pojo.authentication.LoginResponse
import com.example.smarthomeapp.data.repository.ClientRepository
import com.example.smarthomeapp.module.credential.CredentialManager
import com.example.smarthomeapp.util.validation.InvalidFieldInputException
import com.example.smarthomeapp.util.validation.ValidationCode
import com.example.smarthomeapp.util.validation.isEmail
import com.example.smarthomeapp.util.validation.isValidPassword
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class LoginUseCase @Inject constructor() : SingleUseCase<LoginResponse, LoginRequest>() {

    @Inject
    lateinit var repository: ClientRepository

    @Inject
    lateinit var credentialManager: CredentialManager

    override fun create(params: LoginRequest?): Single<out LoginResponse> {
        return params?.let {
            Single.create<LoginRequest> { emitter ->
                if (!it.email.isEmail()) {
                    emitter.onError(
                        InvalidFieldInputException(
                            ValidationCode.ERROR_EMAIL_INVALID,
                            R.string.error_email_invalid
                        )
                    )
                } else if (!it.password.isValidPassword()) {
                    emitter.onError(
                        InvalidFieldInputException(
                            ValidationCode.ERROR_PASSWORD_INVALID,
                            R.string.error_password_invalid
                        )
                    )
                } else {
                    emitter.onSuccess(params)
                }
            }
                .flatMap {
                    repository.login(it).subscribeOn(Schedulers.io())
                }
                .doOnSuccess {
                    credentialManager.saveAuthToken(it.accessToken)
                }
        } ?: errorParamsSingle()

    }

}