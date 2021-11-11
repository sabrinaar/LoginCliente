package com.sabrina.logincliente.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.sabrina.logincliente.R
import com.sabrina.logincliente.data.DataSourceCliente
import com.sabrina.logincliente.domain.RepoClienteImpl
import com.sabrina.logincliente.utils.DatePickerFragment
import com.sabrina.logincliente.valueobject.Resource
import com.sabrina.logincliente.viewmodels.ViewModelCliente
import com.sabrina.logincliente.viewmodels.ViewModelFactoryCliente
import kotlinx.android.synthetic.main.fragment_creacion_cliente.*
import kotlinx.android.synthetic.main.fragment_main.*


class FragmentCreacionCliente : Fragment() {

    //se le asigna este view model al fragment
    private val viewModel by viewModels<ViewModelCliente> {
        ViewModelFactoryCliente(
            RepoClienteImpl(
                DataSourceCliente()
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
        return inflater.inflate(R.layout.fragment_creacion_cliente, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //get data

        fecha_nacimiento.setOnClickListener{
            showDatePickerDialog()
        }

        button_crear_cliente.setOnClickListener{
            viewModel.crearCliente(nombre.text.toString(), apellido.text.toString(), edad.text.toString(), fecha_nacimiento.text.toString()).observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Resource.Loading -> {
                        progressBar_add_cliente.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        progressBar_add_cliente.visibility = View.GONE
                        showAlert("","Cliente creado con éxito!")
                    }
                    is Resource.Failure -> {
                        progressBar_add_cliente.visibility = View.GONE
                        showAlert("Error","Se ha producido un error al crear el cliente")
                    }
                }
            })
        }

    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(childFragmentManager, "datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        fecha_nacimiento.setText("$day/$month/$year")
    }

    private fun showAlert(titulo:String,mensaje:String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(titulo)
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}