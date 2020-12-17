package pl.nc.newscollector.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.nc.newscollector.R
import pl.nc.newscollector.models.Website

class WebsiteAdapter(var websiteList: ArrayList<Website>) : RecyclerView.Adapter<WebsiteAdapter.WebsiteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebsiteViewHolder {
        return WebsiteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.single_website, parent, false))
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

    inner class WebsiteViewHolder(websiteView: View) : RecyclerView.ViewHolder(websiteView) {
        val tvWebsiteName = websiteView.findViewById<TextView>(R.id.tvWebsiteName)
        var rvFeeds = websiteView.findViewById<RecyclerView>(R.id.rvFeeds)

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
