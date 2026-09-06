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
    
    // कंट्रोल्स
    var isDeviceLocked: Boolean = false,
    var isYoutubeLocked: Boolean = false,
    var isWhatsappLocked: Boolean = false,
    var isInstagramLocked: Boolean = false,
    var isPhonePeLocked: Boolean = false,
    var uninstallRequested: Boolean = false
)
