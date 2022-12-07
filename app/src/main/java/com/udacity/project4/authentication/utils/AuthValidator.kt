package com.udacity.project4.authentication.utils

import android.util.Patterns

class AuthValidator {

    companion object{

        fun isEmailValid(email: String?): Boolean{
            return (!email.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches())
        }

        fun isPasswordValid(password: String?): Boolean{
            return !password.isNullOrEmpty() && password.length > 6
        }
    }
}