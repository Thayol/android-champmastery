package net.zovguran.lolchampionmastery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_first.*
import java.util.logging.Logger


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_search.setOnClickListener {
            searchSummoner()
        }

        button_settings.setOnClickListener {
            openSettings()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                openSettings()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun searchSummoner() {
        val summonername : String = editText_search.text.toString()
        val intent = Intent(activity, PlayerMasteryView::class.java).apply {
            putExtra("summonername", summonername)
        }
        startActivity(intent)
    }

    private fun openSettings() {
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }
}