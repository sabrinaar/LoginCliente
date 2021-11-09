package com.sabrina.logincliente.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.sabrina.logincliente.R
import kotlinx.android.synthetic.main.fragment_main.*
import android.content.Intent
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.sabrina.logincliente.data.DataSourceAutenticacion
import com.sabrina.logincliente.domain.RepoAutenticacionImpl
import com.sabrina.logincliente.valueobject.Resource
import com.sabrina.logincliente.viewmodels.ViewModelAutenticacion
import com.sabrina.logincliente.viewmodels.ViewModelFactoryAutenticacion
import java.util.concurrent.TimeUnit


class FragmentMain : Fragment() {

    private val callbackManager = CallbackManager.Factory.create()
    lateinit var storedVerificationId:String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks


    //se le asigna este view model al fragment
    private val viewModel by viewModels<ViewModelAutenticacion> {
        ViewModelFactoryAutenticacion(
            RepoAutenticacionImpl(
                DataSourceAutenticacion()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d("TAG", "onVerificationCompleted:$credential")
                autenticacion_telefono_firebase(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("TAG", "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("TAG", "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token

            }
        }








        button_telefono.setOnClickListener{
            val number="+543585499240"
            sendVerificationcode(number)

        }

        button_facebook.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult?) {
                        result?.let {
                            val token = it.accessToken
                            autenticacion_facebook_firebase(token.token)
                        }
                    }

                    override fun onCancel() {
                    }

                    override fun onError(error: FacebookException?) {
                        showAlert()
                    }

                })


        }


    }

    fun autenticacion_facebook_firebase(token: String) {
        viewModel.autenticarFacebook(token).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    progressBarMain.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    progressBarMain.visibility = View.GONE
                    onNavigateToCreacionCliente()

                }
                is Resource.Failure -> {
                    progressBarMain.visibility = View.GONE
                    showAlert()
                }
            }
        })
    }

    fun autenticacion_telefono_firebase(credential: PhoneAuthCredential) {
        viewModel.autenticarTelefono(credential).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    progressBarMain.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    progressBarMain.visibility = View.GONE
                    onNavigateToCreacionCliente()

                }
                is Resource.Failure -> {
                    progressBarMain.visibility = View.GONE
                    showAlert()
                }
            }
        })
    }


    fun onNavigateToCreacionCliente() {
        findNavController().navigate(R.id.fragmentCreacionCliente)
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun sendVerificationcode(number: String) {
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(number)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this.requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

}