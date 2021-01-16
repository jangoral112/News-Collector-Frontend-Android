package pl.nc.newscollector.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.edit
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import pl.nc.newscollector.R
import pl.nc.newscollector.models.Article

class FavoritesAdapter(var articlesList: ArrayList<Article>) :
    RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.single_article, parent, false)
        )
    }

    override fun getItemCount() = articlesList.size

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val article = articlesList[position]
        holder.tvArticleDate.text = article.date
        holder.tvArticleDescription.text = article.description
        holder.tvArticleKeywords.text = article.keywords
        holder.tvArticleTitle.text = article.title
        holder.tvArticleWebsiteName.text = article.websiteName
        holder.tvArticleFeedName.text = article.feedName
        holder.clExpandable.visibility = if (article.isExpanded) View.VISIBLE else View.GONE
    }

    fun addArticle(article: Article) {
        articlesList.add(article)
        notifyItemInserted(articlesList.size - 1)
    }



    inner class FavoritesViewHolder(articleView: View) : RecyclerView.ViewHolder(articleView) {

        val tvArticleTitle: TextView = articleView.findViewById(R.id.tvWebsiteName)
        val tvArticleDescription: TextView = articleView.findViewById(R.id.tvArticleDescription)
        val tvArticleDate: TextView = articleView.findViewById(R.id.tvArticleDate)
        val tvArticleKeywords: TextView = articleView.findViewById(R.id.tvArticleKeyword)
        val tvArticleWebsiteName: TextView = articleView.findViewById(R.id.tvArticleWebsiteName)
        val tvArticleFeedName: TextView = articleView.findViewById(R.id.tvArticleFeedName)
        val clExpandable: ConstraintLayout = articleView.findViewById(R.id.clExpandable)
        private val btnOpenArticle: Button = articleView.findViewById(R.id.btnOpenArticle)

        init {

            tvArticleTitle.setOnClickListener {
                articlesList[adapterPosition].apply {
                    isExpanded = !isExpanded
                    notifyItemChanged(adapterPosition)
                }
            }
            btnOpenArticle.setOnClickListener {
                itemView.context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(articlesList[adapterPosition].link)
                    )
                )
            }
        }
    }
}