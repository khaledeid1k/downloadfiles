package com.example.downloadfiles.ui

import android.Manifest
import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat


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
                    android.Manifest.permission.READ_CONTACTS -> {
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
                    multiplePermissionResultLauncher.launch(
                        arrayOf(permission)
                    )
                },
                onGoToAppSettingsClick ={}
            )
        }

}