package pl.nc.newscollector.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import pl.nc.newscollector.R
import pl.nc.newscollector.models.Article

class ArticleAdapter(var articlesList: ArrayList<Article>) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.single_article, parent, false))
    }

    override fun getItemCount() = articlesList.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articlesList[position]
        holder.tvArticleDate.text = article.date
        holder.tvArticleDescription.text = article.description
        holder.tvArticleKeywords.text = article.keywords
        holder.tvArticleTitle.text = article.title
        holder.clExpandable.visibility = if (article.isExpanded) View.VISIBLE else View.GONE
    }

    inner class ArticleViewHolder(articleView: View) : RecyclerView.ViewHolder(articleView) {

        val tvArticleTitle: TextView = articleView.findViewById<TextView>(R.id.tvArticleTitle)
        val tvArticleDescription: TextView = articleView.findViewById<TextView>(R.id.tvArticleDescription)
        val tvArticleDate: TextView = articleView.findViewById<TextView>(R.id.tvArticleDate)
        val tvArticleKeywords: TextView = articleView.findViewById<TextView>(R.id.tvArticleKeyword)
        val btnOpenArticle: Button = articleView.findViewById<Button>(R.id.btnOpenArticle)
        val clExpandable: ConstraintLayout = articleView.findViewById<ConstraintLayout>(R.id.clExpandable)

        init {
            tvArticleTitle.setOnClickListener {
                articlesList[adapterPosition].apply {
                    isExpanded = !isExpanded
                    notifyItemChanged(adapterPosition)
                }
            }


            btnOpenArticle.setOnClickListener {
                itemView.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(articlesList[adapterPosition].link)))
            }
        }
    }
}