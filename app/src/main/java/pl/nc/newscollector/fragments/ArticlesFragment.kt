package pl.nc.newscollector.fragments
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
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

    private val getArticlesURL = endpoints["ARTICLES_GET"] ?: error(message = "ARTICLES_GET => url is not set")

    fun vibratePhone() {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(200)
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val articleAdapter = ArticleAdapter(arrayListOf(), this)
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