package com.sabrina.logincliente.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.*
import com.sabrina.logincliente.R
import com.sabrina.logincliente.data.DataSourceAutenticacion
import com.sabrina.logincliente.domain.RepoAutenticacionImpl
import com.sabrina.logincliente.valueobject.Resource
import com.sabrina.logincliente.viewmodels.ViewModelAutenticacion
import com.sabrina.logincliente.viewmodels.ViewModelFactoryAutenticacion
import kotlinx.android.synthetic.main.fragment_verificacion.*

class FragmentVerificacion : Fragment() {

    lateinit var storedVerificationId: String


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
        requireArguments().let {
            // uid = it.getString("uid")!!
            storedVerificationId= it.getString("verificationId")!!
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verificacion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_verificar.setOnClickListener{
            var otp=id_otp.text.toString()
            if(!otp.isEmpty()){
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp)
                autenticacion_telefono_firebase(credential)
            }else{
                Toast.makeText(requireContext(),"Ingrese codigo de verificación",Toast.LENGTH_SHORT).show()
            }
        }

    }
    fun onNavigateToCreacionCliente() {
        findNavController().navigate(R.id.fragmentCreacionCliente)
    }

    fun autenticacion_telefono_firebase(credential: PhoneAuthCredential) {
        viewModel.autenticarTelefono(credential).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                      progressBarVerif.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    progressBarVerif.visibility = View.GONE
                    onNavigateToCreacionCliente()
                }
                is Resource.Failure -> {
                    progressBarVerif.visibility = View.GONE
                    showAlert()
                }
            }
        })
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}