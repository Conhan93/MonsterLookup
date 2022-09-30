package ViewModel.Main

import Model.Storage.ILocalStorage
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class MainViewModel(
    private val storage: ILocalStorage
) {

    var isDarkMode by mutableStateOf(false)
        private set
    val darkModeItemString: String
        get() = if (isDarkMode) "Light Mode" else "Dark Mode"

    fun onEvent(event: MainEvent) {
        when(event) {
            MainEvent.ClearCacheEvent -> storage.clear()
            MainEvent.SwitchLightModeEvent -> isDarkMode = isDarkMode.not()
            is MainEvent.ExitAppEvent -> { event.exitApp() }
        }
    }
}