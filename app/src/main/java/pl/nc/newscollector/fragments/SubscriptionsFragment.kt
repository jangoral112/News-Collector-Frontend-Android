package pl.nc.newscollector.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpPost
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
        var websiteList = ArrayList<Website>()
        var feedsList = ArrayList<Feed>()
        feedsList.add(Feed("Pierwszy"))
        feedsList.add(Feed("Drugi"))
        feedsList.add(Feed("Trzeci"))

        var feedsList2 = ArrayList<Feed>()
        feedsList2.add(Feed("Pierwszy"))
        feedsList2.add(Feed("Drugi"))
        feedsList2.add(Feed("Trzeci"))
        feedsList2.add(Feed("Czwarty"))
        feedsList2.add(Feed("Piąty"))

        var feedsList3 = arrayListOf(Feed("Pierwszy"), Feed("Drugi"))


        var feedsList4 = ArrayList<Feed>()
        feedsList4.add(Feed("Pierwszy"))
        feedsList4.add(Feed("Drugi"))
        feedsList4.add(Feed("Trzeci"))
        feedsList4.add(Feed("Czwarty"))
        feedsList4.add(Feed("Piąty"))

        var feedsList5 = ArrayList<Feed>()
        feedsList5.add(Feed("Pierwszy"))
        feedsList5.add(Feed("Drugi"))
        feedsList5.add(Feed("Trzeci"))
        feedsList5.add(Feed("Czwarty"))
        feedsList5.add(Feed("Piąty"))


        var feedsList6 = ArrayList<Feed>()
        feedsList6.add(Feed("Pierwszy"))
        feedsList6.add(Feed("Drugi"))
        feedsList6.add(Feed("Trzeci"))
        feedsList6.add(Feed("Czwarty"))
        feedsList6.add(Feed("Piąty"))


        websiteList.add(Website(feedsList, "CNN"))
        websiteList.add(Website(feedsList2, "WP"))
        websiteList.add(Website(feedsList3, "ONET"))
        websiteList.add(Website(feedsList4, "BBC"))
        websiteList.add(Website(feedsList5, "FAKT"))
        websiteList.add(Website(feedsList6, "WYBORCZA"))

        val websiteAdapter = WebsiteAdapter(websiteList)
        rvSubscriptions.adapter = websiteAdapter

        btnSubmitSubscriptions.setOnClickListener {
            val subscribedFeeds = websiteAdapter.getSubscribedFeeds()
            val jsonString = Gson().toJson(subscribedFeeds).toString()
            val endPointForAssociatingUserWithFeeds = "https://webhook.site/051d58d9-4f5e-4afa-973b-2fff1e1c7749"
            endPointForAssociatingUserWithFeeds.httpPost().body(jsonString, Charset.forName("UTF-8")).responseString {
                request, response, result -> when(result) {
                is Result.Success ->  activity?.runOnUiThread {
                    Toast.makeText(activity, "Subscriptions have been updated", Toast.LENGTH_SHORT).show()
                }
                is Result.Failure -> activity?.runOnUiThread {
                    Toast.makeText(activity, "Error when updating subscriptions", Toast.LENGTH_SHORT).show()
                } }
            }
        }
    }
}