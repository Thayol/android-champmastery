package net.zovguran.lolchampionmastery

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

var champidMap: Map<String, String>? = null

fun getIdFromKey(context: Context, key: String) : String {
    if (champidMap == null)
    {
        // from https://bezkoder.com/kotlin-android-read-json-file-assets-gson/
        var jsonString: String
        try {
            jsonString = context.assets.open("champid.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            jsonString = ""
        }
        val gson = Gson()
        val mapType = object : TypeToken<Map<String, String>>() {}.type
        champidMap = gson.fromJson(jsonString, object : TypeToken<Map<String, Any>>() {}.type);
    }

    return champidMap?.get(key) ?: "ERROR"
}