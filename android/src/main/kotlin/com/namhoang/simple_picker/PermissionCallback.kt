package com.namhoang.simple_picker

interface PermissionCallback {
    fun onPermissionGranted()
    fun onPermissionDenied()
}