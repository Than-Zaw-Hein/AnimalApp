package com.tzh.animal

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.tzh.animal.ui.navigation.MyNavHost
import com.tzh.animal.ui.theme.AnimalWallpaperTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAndRequestStoragePermissions()
        setContent {
            AnimalWallpaperTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MyNavHost(navController = rememberNavController())
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == storageRequestCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                // Your code for accessing storage here
            } else {
                // Permission denied
                // Handle the case where the user denies permission
            }
        }
    }

    val storageRequestCode = 101

    fun gotoSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.fromParts("package", packageName, null)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private val storagePermissions = arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.MANAGE_EXTERNAL_STORAGE
    )

    private fun checkAndRequestStoragePermissions() {
        val readGranted = ContextCompat.checkSelfPermission(
            this, android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        val writeGranted = ContextCompat.checkSelfPermission(
            this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        val manageGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.MANAGE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }

        if (!(readGranted && writeGranted && manageGranted)) {
            ActivityCompat.requestPermissions(
                this, storagePermissions, storageRequestCode
            )
            if (shouldShowPermissionRationale(storagePermissions)) {
                showPermissionRationaleDialog()
            }
        }
    }

    private fun shouldShowPermissionRationale(permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (shouldShowRequestPermissionRationale(permission)) {
                return true
            }
        }
        return false
    }

    private fun showPermissionRationaleDialog() {
        AlertDialog.Builder(this).setTitle("Permission Required")
            .setMessage("This app needs permission to access external storage for download image functionality.")
            .setPositiveButton("Grant Permission") { _, _ ->
                gotoSetting()
            }.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }
}