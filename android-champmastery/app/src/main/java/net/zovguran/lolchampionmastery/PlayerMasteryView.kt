package net.zovguran.lolchampionmastery

import android.net.Uri.encode
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_player_mastery_view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import net.zovguran.lolchampionmastery.data.MasteryDatabase
import net.zovguran.lolchampionmastery.data.MasteryRecord
import net.zovguran.lolchampionmastery.data.MasteryRecordRepository
import org.json.JSONArray
import org.json.JSONObject

private const val API_KEY = "api_key"

class PlayerMasteryView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_mastery_view)

        val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        textView_summonerLevel.text = getString(R.string.player_level, "0")

        val summonername = intent.getStringExtra("summonername")

        val apiBase: String = getString(R.string.api_summoner_base)
        val apiKey: String = prefs.getString(API_KEY, null) ?: ""
        val url: String =
            "${apiBase}${encode(
                summonername,
                "application/x-www-form-urlencoded"
            )}?api_key=$apiKey"

        textView_temp.text = "Loading...\n$url"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response -> playerLoaded(response) },
            Response.ErrorListener { textView_summonername.setText("Error!") }
        )
        Volley.newRequestQueue(this).add(jsonObjectRequest)
    }

    private fun playerLoaded(json: JSONObject)
    {
        textView_temp.text = json.toString()
        val profileIconId: String = json.getString("profileIconId")
        val summonerLevel: String = getString(R.string.player_level, json.getString("summonerLevel"))
        val summonername: String = json.getString("name")
        val profileIconBase: String = getString(R.string.api_static_icons_root)
        val imageURL : String = "$profileIconBase$profileIconId.png"
        Picasso.get().load(imageURL).into(imageView_profilePicture)
        textView_summonername.text = summonername
        textView_summonerLevel.text = summonerLevel

        val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val summonerId: String = json.getString("id")
        val apiBase: String = getString(R.string.api_mastery_base)
        val apiKey: String = prefs.getString(API_KEY, null) ?: ""
        val url = "$apiBase$summonerId?api_key=$apiKey"
        val jsonObjectRequest = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener {response ->
                masteriesLoaded(response, summonerId, summonername)
                // textView_temp.text = "${this.textView_temp.text}\n\n${response.toString()}"
            },
            Response.ErrorListener {
                textView_temp.text = "${textView_temp.text}\n\n$url\n\n${it.toString()}"
            }
        )
        Volley.newRequestQueue(this).add(jsonObjectRequest)
    }

    private fun masteriesLoaded(json: JSONArray, summonerId: String, summonername: String) {
        val masteryDatabaseDao = MasteryDatabase.getDatabase(application).masteryDatabaseDao()
        val repository: MasteryRecordRepository = MasteryRecordRepository(masteryDatabaseDao)

        repository.deleteMasteryRecordBySummonerId(summonerId)

        for (i in 0 until json.length()) {
            val obj = json.getJSONObject(i)
            val championId = getIdFromKey(this, obj.getInt("championId").toString())
            val championLevel = obj.getInt("championLevel")
            val championPoints = obj.getInt("championPoints")
            //val summonerId = obj.getString("summonerId")
            //textView_temp.text = "${textView_temp.text}\n$championId: $championPoints"

            repository.insertMasteryRecord(MasteryRecord(
                    summonerName = summonername,
                    summonerId = summonerId,
                    championId = championId,
                    championPoints = championPoints,
                    championLevel = championLevel
                ))
        }

        val masteryScore = repository.getMasteryScoreBySummonerId(summonerId)
        textView_temp.text = masteryScore.joinToString(separator="\n")
    }
}