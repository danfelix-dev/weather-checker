package com.zshock.weatherchecker.helper

import android.content.Context
import com.zshock.weatherchecker.R

class ColorHelper {

    companion object {

        fun getMaterialColor(uniqueId: Long, context: Context): Int {
            val colors = context.resources.getIntArray(R.array.material_colors)
            return colors[uniqueId.toInt() % colors.count()]
        }

    }
}