package net.zovguran.lolchampionmastery

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_second.*

private const val API_KEY = "api_key"

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = PreferenceManager.getDefaultSharedPreferences(context)

        button_save.setOnClickListener {
            saveData()
            closeSettings()
        }

        button_cancel.setOnClickListener {
            closeSettings()
        }

        val apiKey: String? = prefs.getString(API_KEY, null)
        if (apiKey != null)
        {
            editText_apiKey.hint = apiKey
        }


    }

    private fun saveData() {
        // only save if a new value has been entered
        if (editText_apiKey.text.toString() != "") {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val editor: SharedPreferences.Editor = prefs.edit()
            editor.putString(API_KEY, editText_apiKey.text.toString())
            editor.apply() // change .apply() to .commit() if it is too slow in the background...

            view?.let {
                Snackbar.make(it, "API key updated.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        } else {
            view?.let {
                Snackbar.make(it, "No API key was entered.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        }
    }

    private fun closeSettings() {
        findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
    }
}