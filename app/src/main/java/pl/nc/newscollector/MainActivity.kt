package pl.nc.newscollector

import android.os.Bundle
import android.provider.Settings
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import pl.nc.newscollector.fragments.ArticlesFragment
import pl.nc.newscollector.fragments.KeywordsFragment
import pl.nc.newscollector.fragments.SettingsFragment
import pl.nc.newscollector.fragments.SubscriptionsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val articlesFragment = ArticlesFragment()
        val subscriptionsFragment = SubscriptionsFragment()
        val keywordsFragment = KeywordsFragment()
        val settingsFragment = SettingsFragment()

        setFragment(articlesFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.miArticles -> setFragment(articlesFragment)
                R.id.miSubscriptions -> setFragment(subscriptionsFragment)
                R.id.miKeywords -> setFragment(keywordsFragment)
                R.id.miSettings -> setFragment(settingsFragment)
            }
            true
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
    }

}