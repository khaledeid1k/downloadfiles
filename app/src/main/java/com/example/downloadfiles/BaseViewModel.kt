package com.example.downloadfiles

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.downloadfiles.network.downloadFileR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BaseViewModel: ViewModel() {
    val visiblePermissionDialogQueue = mutableStateListOf<String>()
    private val _updateNotificationProcess = MutableStateFlow(0)
    val updateNotificationProcess = _updateNotificationProcess.asStateFlow()

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if(!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        }
    }

    fun download(){
        viewModelScope.launch(Dispatchers.IO) {
            downloadFileR(
                //   "https://images.pexels.com/photos/35537/child-children-girl-happy.jpg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                "https://server8.mp3quran.net/harthi/061.mp3",
                //  "https://server8.mp3quran.net/harthi/001.mp3",

                "asdasdadsasdadsdsaads",
            ) {
                _updateNotificationProcess.value = it
            }
        }
    }

}