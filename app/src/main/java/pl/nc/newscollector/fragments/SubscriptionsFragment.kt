package pl.nc.newscollector.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_subscriptions.*
import pl.nc.newscollector.R
import pl.nc.newscollector.adapters.WebsiteAdapter
import pl.nc.newscollector.models.Feed
import pl.nc.newscollector.models.Website
import java.nio.charset.Charset


class SubscriptionsFragment : Fragment(R.layout.fragment_subscriptions) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // POBIERAMY
        val websiteList = arrayListOf<Website>()
        getAvailableSubscriptions(websiteList)
        loadSubscriptions(websiteList)
        val websiteAdapter = WebsiteAdapter(websiteList)
        rvSubscriptions.adapter = websiteAdapter



        btnSubmitSubscriptions.setOnClickListener {
            // ZAPISUJEMY
            saveSubscriptions(websiteList)

            // WYSYLANIE
            val subscribedFeeds = websiteAdapter.getSubscribedFeeds()
            val jsonString = Gson().toJson(subscribedFeeds).toString()
            configureEndPoint().httpPut().body(jsonString, Charset.forName("UTF-8")).responseString { request, response, result -> when(result) {
                is Result.Success -> activity?.runOnUiThread {
                    Toast.makeText(activity, "Subscriptions have been updated", Toast.LENGTH_SHORT).show()
                }
                is Result.Failure -> activity?.runOnUiThread {
                    Toast.makeText(activity, "Error when updating subscriptions", Toast.LENGTH_SHORT).show()
                } }
            }
        }
    }

    private fun getAvailableSubscriptions(websiteList: ArrayList<Website>) {
        val URL = "https://g2alb3a3q6.execute-api.us-east-1.amazonaws.com/test/feeds/list-all"
        URL.httpGet().responseString { request, response, result ->
            val jsonString = result.get()
            val availableSubscriptions = Gson().fromJson(jsonString, Map::class.java) as Map<String, ArrayList<String>>
            for(websiteName in availableSubscriptions.keys) {
                val feedsList = arrayListOf<Feed>()
                for (feedName in availableSubscriptions[websiteName]!!) {
                    feedsList.add(Feed(feedName))
                }
                val website = Website(feedsList, websiteName)
                websiteList.add(website)
            }

            requireActivity().runOnUiThread {
                Toast.makeText(activity, "Successfully downloaded subscriptions", Toast.LENGTH_SHORT).show()
            }
        }.join()
    }

    private fun saveSubscriptions(updatedSubscriptions: ArrayList<Website>) {
        val preferences = requireActivity().getSharedPreferences("SUBSCRIPTIONS", Context.MODE_PRIVATE)
        preferences.edit {
            clear()
            putString("WEBSITES", Gson().toJson(updatedSubscriptions).toString())
            apply()
        }
    }

    private fun loadSubscriptions(availableSubscriptions: ArrayList<Website>) {
        val preferences = requireActivity().getSharedPreferences("SUBSCRIPTIONS", Context.MODE_PRIVATE)
        val savedSubscriptions = preferences.getString("WEBSITES", null)?.let {
            val articleArray = Gson().fromJson(it, Array<Website>::class.java)
            val articlesList = arrayListOf<Website>()
            articlesList.addAll(articleArray)
            articlesList
        } ?: return

        for(savedWebsite: Website in savedSubscriptions) {
            availableSubscriptions.find { availableWebsite ->
                availableWebsite.name == savedWebsite.name
            }?.also { foundWebsite ->
                for (savedFeed: Feed in savedWebsite.feeds) {
                    foundWebsite.feeds.find { foundFeed ->
                        foundFeed.name == savedFeed.name && savedFeed.isChecked
                    }?.also { foundFeed -> foundFeed.isChecked = true
                    }
                }
            }
        }
    }

    fun configureEndPoint(): String {
       var endpoint = "https://g2alb3a3q6.execute-api.us-east-1.amazonaws.com/test/feeds"
        val prefs = requireActivity().getSharedPreferences("StartActivity", Context.MODE_PRIVATE)
        prefs.getString("AUTH_KEY", null)?.also{
            authKey -> endpoint += "?authKey=$authKey"
        }
        return endpoint
    }
}