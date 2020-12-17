package pl.nc.newscollector.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.fragment_articles.*
import pl.nc.newscollector.R
import pl.nc.newscollector.adapters.ArticleAdapter
import pl.nc.newscollector.models.Article

class ArticlesFragment : Fragment(R.layout.fragment_articles) {

    private var URL = "https://3a1qblqh9a.execute-api.us-east-1.amazonaws.com/test/news?authKey=JakubReszka"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val articleAdapter = ArticleAdapter(ArrayList())
        rvArticle.setHasFixedSize(true)
        rvArticle.adapter = articleAdapter
        getArticles()
    }

    fun getArticles() {

        val articleAdapter = rvArticle.adapter as ArticleAdapter

        URL.httpGet().responseObject(Article.Deserializer()) { _, response, result ->
            when (result) {
                is Result.Success -> {
                    val (articles, err) = result
                    articles?.forEach { a ->
                        articleAdapter.addArticle(a)
                    }
                    activity?.runOnUiThread {
                        Toast.makeText(activity, "Successfully downloaded articles", Toast.LENGTH_SHORT).show()
                    }
                }
                is Result.Failure -> {
                    activity?.runOnUiThread {
                        Toast.makeText(activity, "Error when downloading articles", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }
}