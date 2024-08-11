package com.example.downloadfiles

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor (): ViewModel() {
    private var counter = 0
    val map = mutableStateMapOf<Int,Int>()

    private val _state = MutableStateFlow(mutableStateMapOf<Int,Int>())
    val state = _state.asStateFlow()
    fun addOne() {
        counter++
        _state.update {
            it[0] = counter
            it
        }
    }



    val visiblePermissionDialogQueue = mutableStateListOf<String>()
    val updateNotificationProcess = MutableStateFlow(0)

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

}