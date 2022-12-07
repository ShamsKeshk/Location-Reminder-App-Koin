package com.udacity.project4.authentication.login.framewrok

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.firebase.ui.auth.AuthUI

class LoginAuthManager {

    // Choose authentication providers
    private val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build())

    // Create and launch sign-in intent
    private val signInIntent = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(providers)
        .build()


    fun lunchSignInLauncher(signInLauncher: ActivityResultLauncher<Intent>) {
        signInLauncher.launch(signInIntent)
    }
}