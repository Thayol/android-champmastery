package net.zovguran.lolchampionmastery

import android.net.Uri.encode
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_player_mastery_view.*
import org.json.JSONObject

class PlayerMasteryView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_mastery_view)

        textView_summonerLevel.text = getString(R.string.player_level, "0")

        val summonername = intent.getStringExtra("summonername")

        val api_base: String = "https://eun1.api.riotgames.com/lol/summoner/v4/summoners/by-name/"
        val apiKey: String = "KEY HERE"
        val url: String =
            "$api_base${encode(summonername, "application/x-www-form-urlencoded")}?api_key=$apiKey"

        textView_temp.text = "Loading...\n$url"

        /*
        val textView = textView_temp

        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { textView.text = it },
            Response.ErrorListener { textView.text = "Error!" })

        queue.add(stringRequest)
        */

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response -> playerLoaded(response) },
            Response.ErrorListener { }
        )
        Volley.newRequestQueue(this).add(jsonObjectRequest)
    }

    private fun playerLoaded(json: JSONObject)
    {
        textView_temp.text = json.toString()
        val profileIconId: String = json.getString("profileIconId")
        val summonerLevel: String = getString(R.string.player_level, json.getString("summonerLevel"))
        val summonername: String = json.getString("name")
        val imageURL : String = "http://ddragon.leagueoflegends.com/cdn/10.12.1/img/profileicon/$profileIconId.png"
        Picasso.get().load(imageURL).into(imageView_profilePicture)
        textView_summonername.text = summonername
        textView_summonerLevel.text = summonerLevel
    }
}