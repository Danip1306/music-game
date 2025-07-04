package com.example.musicgame.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

sealed class DashboardNavigationEvent {
    // Eliminamos NavigateToLevels si ya no lo necesitamos
    // object NavigateToLevels : DashboardNavigationEvent()
    object NavigateToTests : DashboardNavigationEvent()
}

class DashboardViewModel : ViewModel() {

    private val _navigationEvents = Channel<DashboardNavigationEvent>()
    val navigationEvents = _navigationEvents.receiveAsFlow()

    // Eliminamos esta función
    // fun onNavigateToLevelsClicked() {
    //     viewModelScope.launch {
    //         _navigationEvents.send(DashboardNavigationEvent.NavigateToLevels)
    //     }
    // }

    fun onNavigateToTestsClicked() {
        viewModelScope.launch {
            _navigationEvents.send(DashboardNavigationEvent.NavigateToTests)
        }
    }
}