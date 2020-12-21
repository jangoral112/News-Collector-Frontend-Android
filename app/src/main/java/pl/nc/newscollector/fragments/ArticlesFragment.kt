package pl.nc.newscollector.fragments
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.fragment_articles.*
import pl.nc.newscollector.R
import pl.nc.newscollector.adapters.ArticleAdapter
import pl.nc.newscollector.models.Article
import android.widget.Toast.makeText as toast

class ArticlesFragment(endpoints: Map<String, String>) : Fragment(R.layout.fragment_articles) {

    private val getArticlesURL = endpoints["ARTICLES_GET"] ?:
                        error(message = "ARTICLES_GET => url is not set")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val articleAdapter = ArticleAdapter(ArrayList())
        rvArticle.setHasFixedSize(true)
        rvArticle.adapter = articleAdapter
        getArticles()
    }

    private fun getArticles() {
        val articleAdapter = rvArticle.adapter as ArticleAdapter
        getArticlesURL.httpGet().responseObject(Article.Deserializer()) { _, _, result ->
            when (result) {
                is Result.Success -> {
                    val (articles, _) = result
                    articles?.forEach { a ->
                        articleAdapter.addArticle(a)
                    }
                    activity?.runOnUiThread {
                        toast(activity, "SUCCESS: (${articles?.size}) Articles fetched", Toast.LENGTH_SHORT).show()
                    }
                }
                is Result.Failure -> {
                    activity?.runOnUiThread {
                        toast(activity, "ERROR: Fetching articles", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}