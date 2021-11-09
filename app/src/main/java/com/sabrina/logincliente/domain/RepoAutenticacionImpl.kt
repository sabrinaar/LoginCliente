package com.sabrina.logincliente.domain

import com.google.firebase.auth.PhoneAuthCredential
import com.sabrina.logincliente.data.DataSourceAutenticacion
import com.sabrina.logincliente.valueobject.Resource

class RepoAutenticacionImpl (private val dataSourceAut: DataSourceAutenticacion) : RepoAutenticacion{
    override suspend fun autenticar_facebook(token:String) : Resource<Boolean> {
        return dataSourceAut.autenticacionFacebook(token)
    }

    override suspend fun autenticar_telefono(credential: PhoneAuthCredential): Resource<Boolean> {
        return dataSourceAut.autenticacionTelefono(credential)
    }


}