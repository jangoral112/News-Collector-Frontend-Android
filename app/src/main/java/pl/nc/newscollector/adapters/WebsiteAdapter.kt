package pl.nc.newscollector.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import pl.nc.newscollector.R
import pl.nc.newscollector.models.Website
import java.util.function.BiFunction

class WebsiteAdapter(var websiteList: ArrayList<Website>) : RecyclerView.Adapter<WebsiteAdapter.WebsiteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebsiteViewHolder {
        return WebsiteViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.single_website,
                        parent,
                        false
                )
        )
    }

    override fun getItemCount(): Int = websiteList.size

    override fun onBindViewHolder(holder: WebsiteViewHolder, position: Int) {
        val website = websiteList[position]
        holder.apply {
            tvWebsiteName.text = website.name
            rvFeeds.adapter = FeedAdapter(website.feeds)
            rvFeeds.visibility = if (website.isExpanded) View.VISIBLE else View.GONE
        }
    }

    fun getSubscribedFeeds(): MutableMap<String, ArrayList<String>> {
        val subscribedFeeds = mutableMapOf<String, ArrayList<String>>()
        websiteList.forEach { website ->
            website.feeds.forEach { feed ->
                if (feed.isChecked)
                    subscribedFeeds.getOrPut(website.name, ::arrayListOf) += feed.name
            }
        }
        return subscribedFeeds;
    }


    inner class WebsiteViewHolder(websiteView: View) : RecyclerView.ViewHolder(websiteView) {
        val tvWebsiteName: TextView = websiteView.findViewById(R.id.tvWebsiteName)
        var rvFeeds: RecyclerView = websiteView.findViewById(R.id.rvFeeds)

        init {
            tvWebsiteName.setOnClickListener {
                websiteList[adapterPosition].apply {
                    isExpanded = !isExpanded
                    notifyItemChanged(adapterPosition)
                }
            }
        }
    }
}
