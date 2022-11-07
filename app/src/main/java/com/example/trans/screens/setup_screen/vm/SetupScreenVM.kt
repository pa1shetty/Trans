package com.example.trans.screens.setup_screen.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trans.data.datastore.DataStoreRepository
import com.example.trans.screens.setup_screen.utils.states.ConfigSetupState
import com.example.trans.screens.setup_screen.vm.SetupScreenVM.NavigateTo.*
import com.example.trans.utillity.AppUpdate
import com.example.trans.utillity.AppUpdate.UpdateType.MANDATORY_UPDATE
import com.example.trans.utillity.firebase.FireBaseConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetupScreenVM @Inject constructor(
    dataStoreRepository: DataStoreRepository,
    appUpdate: AppUpdate,
    private val fireBaseConfigRepository: FireBaseConfigRepository
) : ViewModel() {
    private val _configSetUpState = MutableSharedFlow<ConfigSetupState>(replay = 0)
    val configSetUpState: SharedFlow<ConfigSetupState> = _configSetUpState

    init {
        setUpConfigFireBase()
    }

    private fun setUpConfigFireBase() {
        viewModelScope.launch(Dispatchers.IO) {
            _configSetUpState.emit(ConfigSetupState.LOADING)
            fireBaseConfigRepository.fetchAndActivate().addOnCompleteListener {
                viewModelScope.launch(Dispatchers.IO) {
                    _configSetUpState.emit(ConfigSetupState.DONE)
                }
            }
        }
    }


    private val isLoggedIn = dataStoreRepository.isLoggedIn

    private val isUpdateNeeded = appUpdate.isUpdateNeeded()
    private val isLoggInSkipped = dataStoreRepository.isLoggInSkipped
    private val isLoginMandatory = fireBaseConfigRepository.isLoginMandatory

    suspend fun navigate(): NavigateTo {
        if (isUpdateNeeded == MANDATORY_UPDATE) return UPDATE_SCREEN
        if (isLoggedIn.first()) return HOME_SCREEN
        if (isLoginMandatory) return LOGIN_SCREEN
        return if (isLoggInSkipped.first()) HOME_SCREEN else LOGIN_SCREEN
    }


    enum class NavigateTo {
        LOGIN_SCREEN,
        HOME_SCREEN,
        UPDATE_SCREEN
    }
}