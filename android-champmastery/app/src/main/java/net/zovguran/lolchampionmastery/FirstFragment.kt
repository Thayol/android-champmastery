package net.zovguran.lolchampionmastery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_first.*
import net.zovguran.lolchampionmastery.data.MasteryDatabase
import net.zovguran.lolchampionmastery.data.MasteryRecordRepository


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

        if (activity?.application != null) {
            val masteryDatabaseDao =
                MasteryDatabase.getDatabase(activity!!.application).masteryDatabaseDao()
            val repository: MasteryRecordRepository = MasteryRecordRepository(masteryDatabaseDao)

            val names = repository.getStoredSummonerNames()
            textView_storedNames.text = getString(R.string.offline_available_names, names.joinToString(separator = "\n"))
        }

        button_search.setOnClickListener {
            searchSummoner()
        }

        button_settings.setOnClickListener {
            openSettings()
        }

        button_offline_search.setOnClickListener {
            offlineSearchSummoner()
        }

        switch_offline.setOnClickListener {
            if (switch_offline.isChecked) {
                button_search.visibility = View.GONE
                button_offline_search.visibility = View.VISIBLE
                textView_storedNames.visibility = View.VISIBLE
            } else {
                button_search.visibility = View.VISIBLE
                button_offline_search.visibility = View.GONE
                textView_storedNames.visibility = View.GONE
            }
        }

        editText_search.setText(R.string.default_summonername)
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
        val summonername: String = editText_search.text.toString()
        val intent = Intent(activity, PlayerMasteryView::class.java).apply {
            putExtra("summonername", summonername)
        }
        startActivity(intent)
    }

    private fun offlineSearchSummoner() {
        val summonername: String = editText_search.text.toString()
        val intent = Intent(activity, PlayerMasteryView::class.java).apply {
            putExtra("summonername", summonername)
            putExtra("fromStorage", true)
        }
        startActivity(intent)
    }

    private fun openSettings() {
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }
}