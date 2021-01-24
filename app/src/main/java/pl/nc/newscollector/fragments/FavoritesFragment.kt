package pl.nc.newscollector.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_articles.*
import kotlinx.android.synthetic.main.fragment_favorites.*
import pl.nc.newscollector.R
import pl.nc.newscollector.adapters.ArticleAdapter
import pl.nc.newscollector.adapters.FavoritesAdapter
import pl.nc.newscollector.models.Article

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val articleAdapter = FavoritesAdapter(ArrayList())
        rvFavorites.setHasFixedSize(true)
        rvFavorites.adapter = articleAdapter
        getArticles()
    }

    private fun getArticles() {

        val preferences = requireActivity().getSharedPreferences("SAVED_ARTICLES", Context.MODE_PRIVATE)
        val articleAdapter = rvFavorites.adapter as FavoritesAdapter

        val articlesAsString = preferences.getString("SAVED", null)
        val articlesList = articlesAsString?.let {
            ArrayList(Gson().fromJson(it, Array<Article>::class.java).toList())
        } ?: arrayListOf()

        articlesList.forEach {
            articleAdapter.addArticle(it)
        }
    }

}