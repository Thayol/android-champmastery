package net.zovguran.lolchampionmastery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_player_mastery_view.*

class PlayerMasteryView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_mastery_view)

        textView_temp.text = intent.getStringExtra("summonername")
    }
}