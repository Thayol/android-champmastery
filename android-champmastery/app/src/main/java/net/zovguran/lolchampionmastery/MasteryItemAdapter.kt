package net.zovguran.lolchampionmastery

import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_mastery.view.*
import net.zovguran.lolchampionmastery.data.MasteryRecord
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.coroutineContext

class MasteryItemAdapter(val context: Context, val masteryListFull: List<MasteryRecord>) :
    RecyclerView.Adapter<MasteryItemAdapter.MasteryItemViewHolder>(), Filterable {

    var masteryList: MutableList<MasteryRecord> = masteryListFull.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MasteryItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_mastery,
            parent,
            false
        )

        return MasteryItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MasteryItemViewHolder, position: Int) {
        val currentItem = masteryList[position]

        holder.championIcon.setImageResource(
            context.resources.getIdentifier(
                "champion_${currentItem.championId.toLowerCase(Locale.ROOT)}",
                "drawable",
                context.packageName
            )
        )
        holder.championName.text = getNameFromIdIfLoaded(currentItem.championId)
        holder.championPoints.text = currentItem.championPoints.toString()
        holder.masteryLevel.setImageResource(
            when (currentItem.championLevel) {
                1 -> R.drawable.mastery_lvl1
                2 -> R.drawable.mastery_lvl2
                3 -> R.drawable.mastery_lvl3
                4 -> R.drawable.mastery_lvl4
                5 -> R.drawable.mastery_lvl5
                6 -> R.drawable.mastery_lvl6
                7 -> R.drawable.mastery_lvl7
                else -> R.drawable.mastery_lvl0
            }
        )
    }

    override fun getItemCount() = masteryList.size

    class MasteryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val championIcon: ImageView = itemView.imageView_championIcon
        val championName: TextView = itemView.textView_championName
        val championPoints: TextView = itemView.textView_championPoints
        val masteryLevel: ImageView = itemView.imageView_masteryIcon
    }

    public override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList: MutableList<MasteryRecord> = mutableListOf()

                if (constraint.isNullOrBlank()) {
                    filteredList.addAll(masteryListFull)
                } else {
                    val filterPattern: String = constraint.toString().toLowerCase(Locale.ROOT)
                        .trim()
                        .replace(" ", "")
                        .replace("'", "")

                    masteryListFull.forEach {
                        if (getNameFromIdIfLoaded(it.championId).toLowerCase(Locale.ROOT)
                                .replace("'", "")
                                .replace(" ", "")
                                .contains(filterPattern)
                        ) {
                            filteredList.add(it)
                        }
                    }
                }

                return FilterResults().apply { values = filteredList.toList() }
            }

            @Suppress("UNCHECKED_CAST") // this makes the IDE actually work...
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null) {
                    masteryList.clear()
                    masteryList.addAll(results.values as List<MasteryRecord>)
                    notifyDataSetChanged()
                }
            }
        }
    }
}