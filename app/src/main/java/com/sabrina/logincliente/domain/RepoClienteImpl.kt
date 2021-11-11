package com.sabrina.logincliente.domain

import com.sabrina.logincliente.data.DataSourceCliente
import com.sabrina.logincliente.valueobject.Resource

class RepoClienteImpl (private val dataSourceCliente: DataSourceCliente) : RepoCliente{
    override suspend fun crearCliente(nombre:String, apellido:String, edad:String, fecha_nac:String) : Resource<Boolean> {
        return dataSourceCliente.crearCliente(nombre, apellido, edad, fecha_nac)
    }
}