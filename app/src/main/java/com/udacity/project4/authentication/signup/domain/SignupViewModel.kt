package com.udacity.project4.authentication.signup.domain

import androidx.lifecycle.MutableLiveData
import com.udacity.project4.authentication.data.UserCredentials
import com.udacity.project4.authentication.utils.AuthValidator
import com.udacity.project4.base.BaseViewModel
import com.udacity.project4.base.NavigationCommand
import com.udacity.project4.utils.SingleLiveEvent


class SignupViewModel: BaseViewModel() {

    var isShouldValidateFields: MutableLiveData<Boolean> = MutableLiveData(false)

    var newUserData = MutableLiveData(UserCredentials())

    val signUpEvent = SingleLiveEvent<UserCredentials>()

    fun navigateToLogin(){
        navigationCommand.value = NavigationCommand.Back
    }

    @JvmOverloads
    fun isEmailValid(email: String? = newUserData.value!!.email): Boolean{
        return AuthValidator.isEmailValid(email)
    }

    @JvmOverloads
    fun isPasswordValid(password: String? = newUserData.value!!.password): Boolean{
        return AuthValidator.isPasswordValid(password)
    }

    fun setEmail(email: String?){
        email?.let {
            newUserData.value!!.email = it
        }
    }

    fun setPassword(password: String?){
        password?.let {
            newUserData.value!!.password = it
        }
    }

    fun onClickSignUp(){
        isShouldValidateFields.value = true

        if (isValidAuthInfo()){
            signUpEvent.value = newUserData.value
        }
    }

    @JvmOverloads
    fun isValidAuthInfo(userCredentials: UserCredentials = newUserData.value!!): Boolean{
        return isEmailValid(userCredentials.email) && isPasswordValid(userCredentials.password)
    }
}