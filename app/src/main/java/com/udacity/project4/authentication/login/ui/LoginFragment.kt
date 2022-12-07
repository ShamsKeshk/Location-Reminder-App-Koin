package com.udacity.project4.authentication.login.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.udacity.project4.authentication.login.domain.LoginViewModel
import com.udacity.project4.authentication.login.framewrok.LoginAuthManager
import com.udacity.project4.base.BaseFragment
import com.udacity.project4.databinding.FragmentLoginBinding
import com.udacity.project4.locationreminders.RemindersActivity

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : BaseFragment() {

    private val signInLauncher = registerFirebaseAuthContract()

    private val loginAuthManager = LoginAuthManager()

    private lateinit var binding: FragmentLoginBinding
    override val _viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View{

        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.btnLogin.setOnClickListener {
            loginAuthManager.lunchSignInLauncher(signInLauncher)
        }

        binding.btnSignup.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
        }

        return binding.root
    }


    private fun navigateToRemindersActivity(){
        Intent(requireActivity(), RemindersActivity::class.java).apply {
            startActivity(this)
            requireActivity().finish()
        }
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            navigateToRemindersActivity()
        } else {
            result.idpResponse?.error?.localizedMessage?.let {
                _viewModel.showSnackBar.value = it
            }
        }
    }

    private fun registerFirebaseAuthContract(): ActivityResultLauncher<Intent> {
        return this.registerForActivityResult(FirebaseAuthUIActivityResultContract()) { res ->
            this.onSignInResult(res)
        }
    }
}