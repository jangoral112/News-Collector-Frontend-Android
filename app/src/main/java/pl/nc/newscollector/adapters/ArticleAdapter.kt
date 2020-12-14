package pl.nc.newscollector.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.nc.newscollector.R
import pl.nc.newscollector.models.Article

class ArticleAdapter(val articlesList: ArrayList<Article>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ArticleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.single_article, parent, false))
    }

    override fun getItemCount() = articlesList.size

    inner class ArticleViewHolder(articleView: View) : RecyclerView.ViewHolder(articleView)
}