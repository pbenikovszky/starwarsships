package com.pbenikovszky.starwarsships.utilities

import android.content.Context

class FileHelper {

    companion object {

        fun getTextFromResourceFile(context: Context, resourceID: Int): String {
            return context.resources.openRawResource(resourceID).use {
                it.bufferedReader().use { bufferedReader ->
                    bufferedReader.readText()
                }
            }
        }

    }
}