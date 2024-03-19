package com.softanime.recipeapp.presentation.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.softanime.recipeapp.R
import com.softanime.recipeapp.data.models.register.BodyRegister
import com.softanime.recipeapp.databinding.FragmentRegisterBinding
import com.softanime.recipeapp.presentation.viewModels.RegisterViewModel
import com.softanime.recipeapp.utils.Constants.MY_API_KEY
import com.softanime.recipeapp.utils.NetworkRequest
import com.softanime.recipeapp.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    // Binding
    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    // ViewModel
    private val viewModel : RegisterViewModel by viewModels()
    // Body
    @Inject
    lateinit var body : BodyRegister

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate Views
        _binding = FragmentRegisterBinding.inflate(layoutInflater,container,false)
        //Init Views
        setupViews()
        return binding.root
    }

    private fun setupViews() {
        binding.apply {
            coverImg.load(R.drawable.register_logo)

            // Validate Email
            emailEdt.addTextChangedListener {
                if (it.toString().contains("@") && it.toString().contains(".")){
                    emailTxtLay.error = ""
                }else
                    emailTxtLay.error = getString(R.string.emailInvalidate)
            }

            btnRegister.setOnClickListener {
                val firstName = nameEdt.text.toString()
                val lastName = lastNameEdt.text.toString()
                val username = usernameEdt.text.toString()
                val email = emailEdt.text.toString()

                body.firstName = firstName
                body.lastName = lastName
                body.username = username
                body.email = email

                // Call Api
                viewModel.callRegisterApi(MY_API_KEY,body)

                CoroutineScope(Dispatchers.Main).launch {
                    viewModel.registerData.observe(viewLifecycleOwner){ state ->
                        when(state){
                            is NetworkRequest.LOADING ->{}
                            is NetworkRequest.SUCCESS ->{
                                root.showSnackBar(state.data!!.hash)
                            }
                            is NetworkRequest.ERROR ->{
                                root.showSnackBar(state.message!!)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}