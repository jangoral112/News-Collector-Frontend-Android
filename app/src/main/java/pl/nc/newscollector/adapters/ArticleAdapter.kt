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
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.edit
import androidx.recyclerview.widget.RecyclerView
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import pl.nc.newscollector.R
import pl.nc.newscollector.fragments.ArticlesFragment
import pl.nc.newscollector.models.Article


class ArticleAdapter(var articlesList: ArrayList<Article>, val fragment: ArticlesFragment) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.single_article, parent, false)
        )
    }

    override fun getItemCount() = articlesList.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articlesList[position]
        holder.tvArticleDate.text = article.date
        holder.tvArticleDescription.text = article.description
        holder.tvArticleKeywords.text = article.keywords
        holder.tvArticleTitle.text = article.title
        holder.tvArticleWebsiteName.text = article.websiteName
        holder.tvArticleFeedName.text = article.feedName
        holder.clExpandable.visibility = if (article.isExpanded) View.VISIBLE else View.GONE

        holder.tvArticleTitle.setOnLongClickListener {
            fragment.vibratePhone()
            val preferences = it.context.getSharedPreferences(
                "SAVED_ARTICLES",
                Context.MODE_PRIVATE
            )
            val savedArticles: String? = preferences.getString("SAVED", null)
            preferences.edit {
                val listOfArticles = savedArticles?.let {
                    val arrayList = ArrayList(
                        Gson().fromJson(it, Array<Article>::class.java).toList()
                    )
                    if (!arrayList.contains(article))
                        arrayList.add(article)
                    arrayList
                } ?: arrayListOf(article)
                listOfArticles.forEach {
                    Log.i("LIST", it.toString())
                }
                clear()
                putString("SAVED", Gson().toJson(listOfArticles).toString())
                apply()
            }
            fragment.activity?.runOnUiThread {
                Toast.makeText(fragment.activity, "SUCCESS: Added to favorites", Toast.LENGTH_SHORT)
                    .show()
            }
            true
        }
    }

    fun addArticle(article: Article) {
        articlesList.add(article)
        notifyItemInserted(articlesList.size - 1)
    }

    fun refreshArticles() {
        val currentTimeMillis = System.currentTimeMillis()
        fragment.refreshArticlesURL.httpGet().responseString { request, response, result ->
            when (result) {
                is Result.Success -> fragment.activity?.runOnUiThread {
                    Toast.makeText(
                        fragment.activity,
                        "SUCCESS: Started parsing new articles",
                        Toast.LENGTH_SHORT
                    ).show()
                }
//                is Result.Failure -> fragment.activity?.runOnUiThread {
//                    Toast.makeText(
//                        fragment.activity,
//                        "ERROR: Refreshing new articles",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
            }
        }
        val preferences = fragment.requireActivity().getSharedPreferences("FETCHING_ARTICLES", Context.MODE_PRIVATE)
        preferences.edit {
            clear()
            putLong("LAST_FETCH", currentTimeMillis)
            apply()
        }
        val length = this.itemCount
        articlesList.clear()
        notifyItemRangeRemoved(0, length)
        fragment.getArticles()
    }


    inner class ArticleViewHolder(articleView: View) : RecyclerView.ViewHolder(articleView) {

        val tvArticleTitle: TextView = articleView.findViewById(R.id.tvArticleTitle)
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