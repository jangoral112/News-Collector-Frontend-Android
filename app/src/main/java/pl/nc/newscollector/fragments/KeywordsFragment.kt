package pl.nc.newscollector.fragments

import androidx.fragment.app.Fragment
import pl.nc.newscollector.R

class KeywordsFragment(val endpoints: Map<String, String>) : Fragment(R.layout.fragment_keywords) {

    private val getKeywordsURL = endpoints["KEYWORDS_GET"] ?: error(message = "KEYWORDS_GET => url is not set")

    private val putKeywordsURL = endpoints["KEYWORDS_PUT"] ?: error(message = "KEYWORDS_PUT => url is not set")


}