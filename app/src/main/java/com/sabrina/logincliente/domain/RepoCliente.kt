package com.sabrina.logincliente.domain

import com.sabrina.logincliente.valueobject.Resource

interface RepoCliente {
    suspend fun crearCliente(nombre:String, apellido:String, edad:String, fecha_nac:String): Resource<Boolean>

}