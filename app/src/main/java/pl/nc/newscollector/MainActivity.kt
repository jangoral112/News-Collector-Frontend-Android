package pl.nc.newscollector

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import pl.nc.newscollector.fragments.ArticlesFragment
import pl.nc.newscollector.fragments.KeywordsFragment
import pl.nc.newscollector.fragments.FavoritesFragment
import pl.nc.newscollector.fragments.SubscriptionsFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val endpoints = getServerEndpoints()
        val articlesFragment = ArticlesFragment(endpoints)
        val subscriptionsFragment = SubscriptionsFragment(endpoints)
        val keywordsFragment = KeywordsFragment(endpoints)
        val settingsFragment = FavoritesFragment(endpoints)
        setFragment(articlesFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.miArticles -> setFragment(articlesFragment)
                R.id.miSubscriptions -> setFragment(subscriptionsFragment)
                R.id.miKeywords -> setFragment(keywordsFragment)
                R.id.miFavorites -> setFragment(settingsFragment)
            }
            true
        }
    }

    private fun getServerEndpoints(): Map<String, String> {
        val baseURL = "https://g2alb3a3q6.execute-api.us-east-1.amazonaws.com/test/"
        val authKeyParam = "?authKey=${getAuthKey()}"
        return mapOf(
                "SUBSCRIPTIONS_UPDATE" to "$baseURL/feeds$authKeyParam",
                "SUBSCRIPTIONS_GET" to "$baseURL/feeds/list-all",
                "ARTICLES_GET" to "$baseURL/news$authKeyParam",
                "KEYWORDS_GET" to "$baseURL/keywords/for-user$authKeyParam",
                "KEYWORDS_PUT" to "$baseURL/keywords/for-user$authKeyParam"
        )
    }

    private fun getAuthKey(): String {
        val sharedPreferences = getSharedPreferences("StartActivity", Context.MODE_PRIVATE)
        val authKey = sharedPreferences.getString("AUTH_KEY", null)
        return authKey ?: error(message = "AUTH_KEY => auth_key is not set")
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
    }

}