package ViewModel.Main


sealed class MainEvent {
    object ClearCacheEvent: MainEvent()
    object SwitchLightModeEvent: MainEvent()
    data class ExitAppEvent(val exitApp: () -> Unit): MainEvent()
}
