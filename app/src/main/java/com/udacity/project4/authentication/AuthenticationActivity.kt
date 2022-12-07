package com.udacity.project4.authentication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.udacity.project4.R
import com.udacity.project4.databinding.ActivityAuthenticationBinding
import com.udacity.project4.locationreminders.RemindersActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * This class should be the starting point of the app, It asks the users to sign in / register, and redirects the
 * signed in users to the RemindersActivity.
 */
@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAuthenticationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (AuthUI.getInstance().auth.currentUser != null){
            navigateToRemindersActivity()
            return
        }

        binding = DataBindingUtil.setContentView(this,R.layout.activity_authentication)

    }

    private fun navigateToRemindersActivity(){
        Intent(this,RemindersActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

}
