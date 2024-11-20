package com.denior.parasol.location

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class FusedLocationService(
    private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient
) : LocationService {

    override suspend fun getCurrentLocation(): Location? {
        return suspendCancellableCoroutine { continuation ->
            if (ActivityCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                continuation.resumeWithException(SecurityException("Permission denied"))
                return@suspendCancellableCoroutine
            }

            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                continuation.resume(location)
            }.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
        }
    }
}
