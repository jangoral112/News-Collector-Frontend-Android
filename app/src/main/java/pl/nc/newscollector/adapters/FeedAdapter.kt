package pl.nc.newscollector.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.nc.newscollector.R
import pl.nc.newscollector.models.Feed

class FeedAdapter(var feedList: ArrayList<Feed>) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.single_feed, parent, false))
    }

    override fun getItemCount(): Int = feedList.size

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val feed = feedList[position]
        holder.apply {
            tvFeedName.text = feed.name
            cbFeed.isChecked = feed.isChecked
            cbFeed.setOnCheckedChangeListener { _, isChecked ->
                feed.isChecked = !feed.isChecked
            }
        }
    }

    inner class FeedViewHolder(feedView: View) : RecyclerView.ViewHolder(feedView) {
        val tvFeedName: TextView = feedView.findViewById(R.id.tvFeedName)
        val cbFeed: CheckBox = feedView.findViewById(R.id.cbFeed)
    }

}
