package pl.nc.newscollector.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_subscriptions.*
import pl.nc.newscollector.R
import pl.nc.newscollector.adapters.WebsiteAdapter
import pl.nc.newscollector.models.Feed
import pl.nc.newscollector.models.Website
import java.nio.charset.Charset


class SubscriptionsFragment(endpoints: Map<String, String>) : Fragment(R.layout.fragment_subscriptions) {

    private val updateSubscriptionsURL = endpoints["SUBSCRIPTIONS_UPDATE"]
            ?: error(message = "SUBSCRIPTIONS_UPDATE => url is not set")

    private val getSubscriptionsURL = endpoints["SUBSCRIPTIONS_GET"]
            ?: error(message = "SUBSCRIPTIONS_GET => url is not set")


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val websiteList = arrayListOf<Website>()
        getAvailableSubscriptions(websiteList)
        loadSubscriptions(websiteList)
        val websiteAdapter = WebsiteAdapter(websiteList)
        rvSubscriptions.adapter = websiteAdapter
        btnSubmitSubscriptions.setOnClickListener {
            saveSubscriptions(websiteList)
            val subscribedFeeds = websiteAdapter.getSubscribedFeeds()
            val jsonString = Gson().toJson(subscribedFeeds).toString()
            updateSubscriptionsURL.httpPut().body(jsonString, Charset.forName("UTF-8")).responseString { _, _, result ->
                when (result) {
                    is Result.Success -> activity?.runOnUiThread {
                        Toast.makeText(activity, "SUCCESS: Subscriptions updated", Toast.LENGTH_SHORT).show()
                    }
                    is Result.Failure -> activity?.runOnUiThread {
                        Toast.makeText(activity, "ERROR: Updating subscriptions", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun getAvailableSubscriptions(websiteList: ArrayList<Website>) {
        getSubscriptionsURL.httpGet().responseString { request, response, result ->
            when (result) {
                is Result.Success -> {
                    val jsonString = result.get()
                    val availableSubscriptions = Gson().fromJson(jsonString, Map::class.java)
                            as Map<String, ArrayList<String>>
                    for (websiteName in availableSubscriptions.keys) {
                        val feedsList = arrayListOf<Feed>()
                        for (feedName in availableSubscriptions[websiteName]!!) {
                            feedsList.add(Feed(feedName))
                        }
                        val website = Website(feedsList, websiteName)
                        websiteList.add(website)
                    }
                    activity?.runOnUiThread {
                        Toast.makeText(activity, "SUCCESS: Subscriptions fetched", Toast.LENGTH_SHORT).show()
                    }
                }
                is Result.Failure -> activity?.runOnUiThread {
                    Toast.makeText(activity, "ERROR: Fetching subscriptions", Toast.LENGTH_SHORT).show()
                }
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
            val articlesArray = Gson().fromJson(it, Array<Website>::class.java)
            val articlesList = arrayListOf<Website>()
            articlesList.addAll(articlesArray)
            articlesList
        } ?: return

        for (savedWebsite: Website in savedSubscriptions) {
            availableSubscriptions.find { availableWebsite ->
                availableWebsite.name == savedWebsite.name
            }?.also { foundWebsite ->
                for (savedFeed: Feed in savedWebsite.feeds) {
                    foundWebsite.feeds.find { foundFeed ->
                        foundFeed.name == savedFeed.name && savedFeed.isChecked
                    }?.also { foundFeed ->
                        foundFeed.isChecked = true
                    }
                }
            }
        }
    }
}