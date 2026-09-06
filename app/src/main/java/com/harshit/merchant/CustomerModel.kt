package com.harshit.merchant

data class CustomerModel(
    var imei: String = "",
    var name: String = "",
    var mobile: String = "",
    var email: String = "",
    var photoUrl: String = "",
    var isDeviceLocked: Boolean = false,
    var isAppLocked: Boolean = false,
    var isCallLocked: Boolean = false,
    var isSimLocked: Boolean = false,
    var uninstallRequested: Boolean = false
)
