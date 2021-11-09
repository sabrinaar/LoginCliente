package com.sabrina.logincliente.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sabrina.logincliente.domain.RepoAutenticacion
import com.sabrina.logincliente.domain.RepoCliente

class ViewModelFactoryAutenticacion (private val repoA: RepoAutenticacion): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RepoAutenticacion::class.java).newInstance(repoA)
    }
}