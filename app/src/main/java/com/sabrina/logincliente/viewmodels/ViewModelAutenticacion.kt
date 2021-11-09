package com.sabrina.logincliente.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.firebase.auth.PhoneAuthCredential
import com.sabrina.logincliente.domain.RepoAutenticacion
import com.sabrina.logincliente.valueobject.Resource
import kotlinx.coroutines.Dispatchers

class ViewModelAutenticacion (private val repoA: RepoAutenticacion) : ViewModel() {

    fun autenticarFacebook(token: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(repoA.autenticar_facebook(token))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }

    fun autenticarTelefono(credential: PhoneAuthCredential) =
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(repoA.autenticar_telefono(credential))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }

}