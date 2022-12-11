package com.udacity.project4.authentication.signup.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.udacity.project4.authentication.data.UserCredentials
import com.udacity.project4.authentication.signup.domain.SignupViewModel
import com.udacity.project4.base.BaseFragment
import com.udacity.project4.base.hideKeyboard
import com.udacity.project4.databinding.FragmentSignupBinding
import com.udacity.project4.locationreminders.RemindersActivity

class SignupFragment : BaseFragment() {

    lateinit var binding: FragmentSignupBinding

    override val _viewModel: SignupViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        binding =  FragmentSignupBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = _viewModel


        _viewModel.signUpEvent.observe(viewLifecycleOwner, Observer {
            binding.btnSignup.hideKeyboard()
            proceedToSignup(it)
        })

        return binding.root
    }

    private fun proceedToSignup(userCredentials: UserCredentials){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(userCredentials.email, userCredentials.password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    navigateToRemindersActivity()
                } else {
                    task.exception?.localizedMessage.let {
                        _viewModel.showSnackBar.value = "$it"
                    }
                }
            }
    }

    private fun navigateToRemindersActivity(){
        Intent(requireActivity(), RemindersActivity::class.java).apply {
            startActivity(this)
            requireActivity().finish()
        }
    }
}