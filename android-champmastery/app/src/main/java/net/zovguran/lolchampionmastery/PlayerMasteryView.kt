package net.zovguran.lolchampionmastery

import android.net.Uri.encode
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_player_mastery_view.*
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

        // uncomment the textView_temp parts and set visibility to visible to see very detailed messages on the UI

        textView_summonerLevel.text = getString(R.string.player_level, "0")

        // get extras from the intent launching the activity
        val summonername = intent.getStringExtra("summonername")
        val fromStorage = intent.getBooleanExtra("fromStorage", false)
        if (fromStorage) { // if in offline mode
            val repository: MasteryRecordRepository = MasteryRecordRepository(
                MasteryDatabase.getDatabase(application).masteryDatabaseDao()
            )
            val records = repository.getStoredMasteryScoresBySummonerName(summonername ?: "")
            textView_summonerLevel.text = getString(R.string.level_offline)
            if (records.isNotEmpty()) {
                textView_summonername.text = records.first().summonerName // for correct names
                setupRecycler(records)
            } else {
                textView_summonername.text = getString(R.string.fetch_error_1)
            }
        } else { // if in online mode
            val apiBase: String = getString(R.string.api_summoner_base)
            val apiKey: String = prefs.getString(API_KEY, null) ?: ""
            val url: String =
                "${apiBase}${encode(
                    summonername,
                    "application/x-www-form-urlencoded"
                )}?api_key=$apiKey"

            // textView_temp.text = "Loading...\n$url"

            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener { response -> playerLoaded(response) },
                Response.ErrorListener {
                    textView_summonername.text = getString(R.string.fetch_error_1)
                    textView_summonerLevel.text = getString(R.string.fetch_error_2)
                }
            )
            Volley.newRequestQueue(this).add(jsonObjectRequest)
        }
    }

    // async called when the API server responds with player data
    private fun playerLoaded(json: JSONObject) {
        // textView_temp.text = json.toString()
        val profileIconId: String = json.getString("profileIconId")
        val summonerLevel: String =
            getString(R.string.player_level, json.getString("summonerLevel"))
        val summonername: String = json.getString("name")
        val profileIconBase: String = getString(R.string.api_static_icons_root)
        val imageURL: String = "$profileIconBase$profileIconId.png"
        Glide.with(this).load(imageURL).into(imageView_profilePicture)

        textView_summonername.text = summonername
        textView_summonerLevel.text = summonerLevel

        val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val summonerId: String = json.getString("id")
        val apiBase: String = getString(R.string.api_mastery_base)
        val apiKey: String = prefs.getString(API_KEY, null) ?: ""
        val url = "$apiBase$summonerId?api_key=$apiKey"
        val jsonObjectRequest = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                masteriesLoaded(response, summonerId, summonername)
                // textView_temp.text = "${this.textView_temp.text}\n\n${response.toString()}"
            },
            Response.ErrorListener {
                // textView_temp.text = "${textView_temp.text}\n\n$url\n\n${it.toString()}"
            }
        )
        Volley.newRequestQueue(this).add(jsonObjectRequest)
    }

    // async called when the mastery information response arrived
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

            repository.insertMasteryRecord(
                MasteryRecord(
                    summonerName = summonername,
                    summonerId = summonerId,
                    championId = championId,
                    championPoints = championPoints,
                    championLevel = championLevel
                )
            )
        }

        val masteryScore = repository.getMasteryScoreBySummonerId(summonerId)
        setupRecycler(masteryScore)
        // textView_temp.text = masteryScore.joinToString(separator = "\n")
    }

    // factored out: both online and offline mode set up the recycler the same way
    private fun setupRecycler(masteryRecords: List<MasteryRecord>) {
        loadIdFromKey(applicationContext) // preload the json asset files
        loadNameFromId(applicationContext)

        val adapter = MasteryItemAdapter(applicationContext, masteryRecords)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String?) = false
        })
    }
}