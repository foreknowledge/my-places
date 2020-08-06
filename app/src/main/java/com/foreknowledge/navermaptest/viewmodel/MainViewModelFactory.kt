package com.foreknowledge.navermaptest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.foreknowledge.navermaptest.model.repository.NaverRepository

/**
 * Created by Yeji on 08,April,2020.
 */
@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val naverRepository: NaverRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = MapViewModel(naverRepository) as T
}