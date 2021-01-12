package pl.nc.newscollector.adapters

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_keywords.view.*
import pl.nc.newscollector.R
import pl.nc.newscollector.models.Keyword

class KeywordAdapter (
        private val keywords: ArrayList<Keyword>
) : RecyclerView.Adapter<KeywordAdapter.KeywordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordViewHolder {
        return KeywordViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.single_keyword,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: KeywordViewHolder, position: Int) {
        var keyword = keywords[position]
        holder.apply {
            tvKeywordValue.text = keyword.value
            cbKeywordChecked.isChecked = keyword.isChecked
            toggleStrikeThrough(tvKeywordValue, keyword.isChecked)
            cbKeywordChecked.setOnCheckedChangeListener { _, isChecked ->
                keyword.isChecked = !keyword.isChecked
                toggleStrikeThrough(tvKeywordValue, isChecked) }
        }
    }

    inner class KeywordViewHolder(keywordView :View) : RecyclerView.ViewHolder(keywordView) {
        val tvKeywordValue: TextView = keywordView.findViewById(R.id.tvKeywordValue)
        val cbKeywordChecked: CheckBox = keywordView.findViewById(R.id.cbKeywordChecked)
    }



    fun addKeyword(keyword: Keyword) {
        keywords.add(keyword)
        notifyItemInserted(keywords.size-1)
    }

    fun deleteSelectedKeywords() {
        keywords.removeAll {
            it.isChecked
        }
        notifyDataSetChanged()
    }

    private fun toggleStrikeThrough(tvKeywordValue: TextView, isChecked: Boolean) {
        if(isChecked) tvKeywordValue.paintFlags = tvKeywordValue.paintFlags or STRIKE_THRU_TEXT_FLAG
        else tvKeywordValue.paintFlags = tvKeywordValue.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
    }

    override fun getItemCount(): Int {
        return keywords.size
    }
}