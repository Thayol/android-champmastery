package net.zovguran.lolchampionmastery

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

var champidMap: Map<String, String>? = null
var champnameMap: Map<String, String>? = null

fun getIdFromKey(context: Context, key: String) : String {
    if (champidMap == null) // lazy load
    {
        // from https://bezkoder.com/kotlin-android-read-json-file-assets-gson/
        var jsonString: String = ""
        try {
            jsonString = context.assets.open("champid.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
        }
        val gson = Gson()
        // val mapType = object : TypeToken<Map<String, String>>() {}.type // the source included this, but i didn't need it
        champidMap = gson.fromJson(jsonString, object : TypeToken<Map<String, Any>>() {}.type);
    }

    return champidMap?.get(key) ?: "ERROR"
}
fun getIdFromKeyIfLoaded(key: String) : String {
    return champidMap?.get(key) ?: key
}
fun loadIdFromKey(context: Context) { // just a wrapper for untidy code
    getNameFromId(context, "")
}

fun getNameFromId(context: Context, id: String) : String {
    if (champnameMap == null) // lazy load
    {
        var jsonString: String = ""
        try {
            jsonString = context.assets.open("champname.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
        }
        val gson = Gson()
        champnameMap = gson.fromJson(jsonString, object : TypeToken<Map<String, Any>>() {}.type);
    }

    return champnameMap?.get(id) ?: "ERROR"
}
fun getNameFromIdIfLoaded(id: String) : String {
    return champnameMap?.get(id) ?: id
}
fun loadNameFromId(context: Context) { // another wrapper
    getNameFromId(context, "")
}