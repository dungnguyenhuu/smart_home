package com.example.smarthomeapp.domain.authentication

import com.example.smarthomeapp.R
import com.example.smarthomeapp.base.domain.usecase.SingleUseCase
import com.example.smarthomeapp.data.pojo.Response
import com.example.smarthomeapp.data.pojo.authentication.RegisterRequest
import com.example.smarthomeapp.data.repository.ClientRepository
import com.example.smarthomeapp.util.validation.*
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RegisterUseCase @Inject constructor() :
    SingleUseCase<Response, RegisterRequest>() {

    @Inject
    lateinit var repository: ClientRepository

    override fun create(params: RegisterRequest?): Single<out Response> {
        return params?.let {
            Single.defer {
                if (!it.email.isEmail()) {
                    Single.error(
                        InvalidFieldInputException(
                            ValidationCode.ERROR_EMAIL_INVALID,
                            R.string.error_email_invalid
                        )
                    )
                } else if (!it.password.isValidPassword()) {
                    Single.error(
                        InvalidFieldInputException(
                            ValidationCode.ERROR_PASSWORD_INVALID,
                            R.string.error_password_invalid
                        )
                    )
                } else if (!it.name.isValidFullName()) {
                    Single.error(
                        InvalidFieldInputException(
                            ValidationCode.ERROR_NAME_INVALID,
                            R.string.error_name_invalid
                        )
                    )
                } else {
                    repository.register(it)
                }
            }
        } ?: errorParamsSingle()

    }
}