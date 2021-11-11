package com.sabrina.logincliente.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.sabrina.logincliente.domain.RepoCliente
import com.sabrina.logincliente.valueobject.Resource
import kotlinx.coroutines.Dispatchers

class ViewModelCliente (private val repoC: RepoCliente) : ViewModel() {

    fun crearCliente(nombre:String, apellido:String, edad:String, fecha_nac:String) =
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(repoC.crearCliente(nombre, apellido, edad, fecha_nac))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }

}