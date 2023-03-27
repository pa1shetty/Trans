package com.example.trans.screens.setup_screen.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trans.data.database.AppDatabase
import com.example.trans.data.datastore.DataStoreRepository
import com.example.trans.network.Enums.NavigateTo
import com.example.trans.network.Enums.NavigateTo.*
import com.example.trans.network.Enums.RegStatus
import com.example.trans.screens.setup_screen.utils.states.ConfigSetupState
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
    private val database: AppDatabase,
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
    private val userId = dataStoreRepository.userId
    private val isUpdateNeeded = appUpdate.isUpdateNeeded()
    private val isLoggInSkipped = dataStoreRepository.isLoggInSkipped
    private val isLoginMandatory = fireBaseConfigRepository.isLoginMandatory

    suspend fun navigate(): NavigateTo {
        if (isUpdateNeeded == MANDATORY_UPDATE) return UPDATE_SCREEN
        if (isLoggedIn.first() && getRegStatus() == RegStatus.REG.status
        ) return HOME_SCREEN
        if (getRegStatus() == RegStatus.FIREBASE_REG.status
        ) return REG_SCREEN
        if (isLoginMandatory) return LOGIN_SCREEN
        return if (isLoggInSkipped.first()) HOME_SCREEN else LOGIN_SCREEN
    }


    private suspend fun getRegStatus(): Int {
        return if (isLoggedIn.first()) {
            database.getUserDetailsDao()
                .getUser(usrId = userId.first()).regStatus
        } else {
            RegStatus.UN_REG.status
        }
    }
}