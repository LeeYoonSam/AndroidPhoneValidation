package com.ys.phoneinfo.extension

import android.content.Context

inline fun Context.getIdentifierHeight(identifierName: String): Int {
    var size = 0
    val resourceId = resources.getIdentifier(identifierName, "dimen", "android")
    if (resourceId > 0) {
        size = resources.getDimensionPixelSize(resourceId)
    }

    return size
}