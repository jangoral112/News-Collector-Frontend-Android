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
        var articles = ArrayList<Article>()
        articles.add(Article("Castro: USA celowo nie zapobiegły atakom terrorystów", "15.12.2020", "Stany Zjednoczone celowo nie zapobiegły atakom na swoim terytorium, żeby móc usprawiedliwić wojnę z terroryzmem - napisał kubański przywódca Fidel Castro w artykule, opublikowanym na łamach dziennika młodzieży komunistycznej \"Juventud Rebelde\".", "https://www.wp.pl/?service=wiadomosci.wp.pl&paid=6037087318049921&c=96&src01=f1e45", "Castro", "WP", "Wiadomości"))
        articles.add(Article("Jak nie dać się oszukać kupując choinkę i wybrać najlepszą", "14.12.2020", "Wielu z nas stoi przed wyborem choinki. Jeśli już decydujemy się na żywe drzewko, warto wiedzieć, jak wybrać to najlepsze, które nie zgubi igieł po kilku dniach. Czym się kierować wybierając sprzedawcę? Jak rozpoznać, czy uczciwie sprzedaje swoje drzewka i skąd mieć pewność, że choinka jest świeżo ścięta?", "https://www.onet.pl/styl-zycia/onetkobieta/jak-nie-dac-sie-oszukac-kupujac-choinke-i-wybrac-najlepsza/8m1ye1d,2b83378a", "Christmas", "Onet", "Styl Życia"))
        articles.add(Article("The incredible stories behind 5 of the world's most expensive watches", "15.12.2020", "A little over 30 years ago, Geneva's Antiquorum pioneered the modern watch auction with its \"Art of Patek Philippe\" sale. ", "https://edition.cnn.com/style/article/5-of-historys-most-expensive-watches/index.html", "Watch, Patek", "CNN", "Style"))
        return articles
    }
}