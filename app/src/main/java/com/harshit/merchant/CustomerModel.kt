package com.harshit.merchant

data class CustomerModel(
    var loanId: String = "",
    var imei: String = "",
    var name: String = "",
    var mobile: String = "",
    var email: String = "",
    var photoUrl: String = "",
    var remarks: String = "N/A",
    var syncDate: String = "",
    var status: String = "Unlocked",
    
    // Control Flags
    var isDeviceLocked: Boolean = false,
    var isYoutubeLocked: Boolean = false,
    var isCameraLocked: Boolean = false,
    var isWhatsappLocked: Boolean = false,
    var uninstallRequested: Boolean = false
)
