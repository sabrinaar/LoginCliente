package com.sabrina.logincliente.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.sabrina.logincliente.domain.RepoCliente
import com.sabrina.logincliente.valueobject.Resource
import kotlinx.coroutines.Dispatchers

class ViewModelCliente (private val repoC: RepoCliente) : ViewModel() {

    fun crearCliente(fieldpath: Uri, id_regalo: String, id_usuario: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(repoC.crearCliente(fieldpath, id_regalo, id_usuario))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }

}