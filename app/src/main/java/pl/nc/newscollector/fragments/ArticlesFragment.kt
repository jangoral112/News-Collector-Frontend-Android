package pl.nc.newscollector.fragments
import android.content.Context
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

class ArticlesFragment : Fragment(R.layout.fragment_articles) {

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            val articleAdapter = ArticleAdapter(ArrayList())
            rvArticle.setHasFixedSize(true)
            rvArticle.adapter = articleAdapter
            getArticles()
        }


        fun configureEndPoint(): String {
            var endpoint = "https://g2alb3a3q6.execute-api.us-east-1.amazonaws.com/test/news"
            val prefs = requireActivity().getSharedPreferences("StartActivity", Context.MODE_PRIVATE)
            prefs.getString("AUTH_KEY", null)?.also{
                authKey -> endpoint += "?authKey=$authKey"
            }
            return endpoint
        }

        fun getArticles() {

            val articleAdapter = rvArticle.adapter as ArticleAdapter

            configureEndPoint().httpGet().responseObject(Article.Deserializer()) { _, response, result ->
                when (result) {
                    is Result.Success -> {
                        val (articles, err) = result
                        articles?.forEach { a ->
                            articleAdapter.addArticle(a)
                        }
                        activity?.runOnUiThread {
                            toast(activity, "Successfully downloaded ${articles?.size} articles", Toast.LENGTH_SHORT).show()
                        }
                    }
                    is Result.Failure -> {
                        activity?.runOnUiThread {
                            toast(activity, "Error when downloading articles", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }
        }
}