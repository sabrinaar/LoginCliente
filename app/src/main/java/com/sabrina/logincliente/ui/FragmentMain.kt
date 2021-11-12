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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sabrina.logincliente.data.DataSourceAutenticacion
import com.sabrina.logincliente.domain.RepoAutenticacionImpl
import com.sabrina.logincliente.valueobject.Resource
import com.sabrina.logincliente.viewmodels.ViewModelAutenticacion
import com.sabrina.logincliente.viewmodels.ViewModelFactoryAutenticacion


class FragmentMain : Fragment() {

    private val callbackManager = CallbackManager.Factory.create()


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

        button_telefono.setOnClickListener{
            onNavigateToIngresarTel()
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

    fun onNavigateToIngresarTel() {
        findNavController().navigate(R.id.action_fragmentMain_to_fragmentIngresarTelefono)
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




}