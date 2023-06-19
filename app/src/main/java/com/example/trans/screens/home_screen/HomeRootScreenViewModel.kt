package com.example.trans.screens.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trans.data.database.DataBaseRepository
import com.example.trans.data.datastore.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeRootScreenViewModel @Inject constructor(
    private val dataBaseRepository: DataBaseRepository,
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    private val _userName = MutableSharedFlow<String>()
    val userName = _userName.asSharedFlow()

    init {
        gerUserName()
    }
    private fun gerUserName() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            _userName.emit(dataBaseRepository.userName(dataStoreRepository.userId.first()))
        }
    }
}

