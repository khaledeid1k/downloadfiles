package com.example.downloadfiles

import android.Manifest
import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat

import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun  CheckPermissions(activity: Activity){
    val viewModel = viewModel<BaseViewModel>()
    val dialogQueue = viewModel.visiblePermissionDialogQueue
    dialogQueue
        .reversed()
        .forEach { permission ->
            PermissionDialog(
                permissionTextProvider = when (permission) {
                    Manifest.permission.CAMERA -> {
                        CameraPermissionTextProvider()
                    }
                    Manifest.permission.READ_CONTACTS -> {
                        RecordAudioPermissionTextProvider()
                    }
                    else -> return@forEach
                },
                isPermanentlyDeclined = !ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    permission
                ),
                onDismiss = viewModel::dismissDialog,
                onOkClick = {
                    viewModel.dismissDialog()

                },
                onGoToAppSettingsClick ={}
            )
        }

}