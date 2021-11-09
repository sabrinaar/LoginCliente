package com.sabrina.logincliente.domain

import com.google.firebase.auth.PhoneAuthCredential
import com.sabrina.logincliente.valueobject.Resource

interface RepoAutenticacion {
    suspend fun autenticar_facebook(token:String): Resource<Boolean>
    suspend fun autenticar_telefono(credential: PhoneAuthCredential): Resource<Boolean>


}