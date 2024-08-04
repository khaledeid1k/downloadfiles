package com.example.downloadfiles

import android.app.Activity
import androidx.compose.runtime.compositionLocalOf

object SharedDataHolder {
    val LocalActivity = compositionLocalOf<Activity> { error("no activity") }
    var baseViewModel: BaseViewModel? = null
}
