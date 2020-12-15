package pl.nc.newscollector.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_articles.*
import pl.nc.newscollector.R
import pl.nc.newscollector.adapters.ArticleAdapter
import pl.nc.newscollector.models.Article

class ArticlesFragment : Fragment(R.layout.fragment_articles) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sampleData = getSampleData()
        val articleAdapter = ArticleAdapter(sampleData)
        rvArticle.adapter = articleAdapter
    }


    private fun getSampleData(): ArrayList<Article> {
        var article = ArrayList<Article>()
        article.add(Article("Kradzieże na ulicy Matejki", "12.12.12", "asbasbas Bla bla balb asbabasbasbas", "https://www.youtube.com/watch?v=hbMqd0XRN34&t=616s", "covid", "CNN", "Europe"))
        article.add(Article("Kotlin lang hahaha", "15.12.12", "Bla bla balb asbabasbasbas", "https://discuss.kotlinlang.org/t/ternary-operator/2116/2", "covid", "Onet", "Polacy"))
        article.add(Article("Pepper fajna hahaha stronga", "12.12.12", "Bla bla balb asbabasbasbas", "https://www.pepper.pl/", "covid", "WP", "PiS"))
        return article
    }
}