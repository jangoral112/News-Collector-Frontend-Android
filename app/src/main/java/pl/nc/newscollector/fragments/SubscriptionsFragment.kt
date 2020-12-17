package pl.nc.newscollector.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_subscriptions.*
import kotlinx.android.synthetic.main.single_website.*
import pl.nc.newscollector.R
import pl.nc.newscollector.adapters.WebsiteAdapter
import pl.nc.newscollector.models.Feed
import pl.nc.newscollector.models.Website

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
        websiteList.add(Website(feedsList2, "CNN"))
        websiteList.add(Website(feedsList3, "CNN"))
        websiteList.add(Website(feedsList4, "CNN"))
        websiteList.add(Website(feedsList5, "CNN"))
        websiteList.add(Website(feedsList6, "CNN"))

        val websiteAdapter = WebsiteAdapter(websiteList)
        rvSubscriptions.adapter = websiteAdapter

        btnSubmitSubscriptions.setOnClickListener {
            activity?.runOnUiThread {
                Toast.makeText(activity, "Subscriptions have been updated", Toast.LENGTH_SHORT).show()
            }
        }
    }
}