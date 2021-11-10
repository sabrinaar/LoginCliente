package com.sabrina.logincliente.data

import com.google.firebase.auth.*
import com.sabrina.logincliente.valueobject.Resource
import kotlinx.coroutines.tasks.await

class DataSourceAutenticacion {


    suspend fun autenticacionFacebook(token: String): Resource<Boolean> {

        try {

            val credencial = FacebookAuthProvider.getCredential(token)
            FirebaseAuth.getInstance().signInWithCredential(credencial).await()
            return Resource.Success(true)
        } catch (e: Exception) {
            return Resource.Failure(e)
        }
    }

    //check si la credencial (codigo que ingresa el usuario) es correcta
    suspend fun autenticacionTelefono(credential: PhoneAuthCredential): Resource<Boolean> {

        try {
            FirebaseAuth.getInstance().signInWithCredential(credential).await()
            return Resource.Success(true)
        } catch (e: Exception) {
            return Resource.Failure(e)
        }
    }


}