package pl.nc.newscollector.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_keywords.*
import pl.nc.newscollector.R
import pl.nc.newscollector.adapters.KeywordAdapter
import pl.nc.newscollector.models.Keyword
import java.nio.charset.Charset

class KeywordsFragment(endpoints: Map<String, String>) : Fragment(R.layout.fragment_keywords) {

    private val getKeywordsURL = endpoints["KEYWORDS_GET"] ?: error(message = "KEYWORDS_GET => url is not set")

    private val putKeywordsURL = endpoints["KEYWORDS_PUT"] ?: error(message = "KEYWORDS_PUT => url is not set")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val keywordList = arrayListOf<Keyword>()
        getUserKeywords(keywordList)
        val keywordAdapter =  KeywordAdapter(keywordList)
        rvKeywords.adapter = keywordAdapter

        btnAddKeyword.setOnClickListener { // TODO wysylanie do bazki
                val keywordValue = etEnterKeyword.text.toString()
                if(keywordValue.isNotEmpty()) {
                    keywordAdapter.addKeyword(Keyword(keywordValue))
                    updateUserKeywords(keywordList)
                }
                etEnterKeyword.text.clear()
            }

        btnDeleteKeywords.setOnClickListener {
            keywordAdapter.deleteSelectedKeywords()
            updateUserKeywords(keywordList)
        }
    }

    private fun getUserKeywords(keywordList: ArrayList<Keyword>) {
        getKeywordsURL.httpGet().responseString{ request, response, result ->
            when(result) {
                is Result.Success -> {
                    val jsonString = result.get()
                    val userKeywords = Gson().fromJson(jsonString, ArrayList::class.java)
                            as ArrayList<String>

                    userKeywords.forEach{ keyword -> keywordList.add(Keyword(keyword)) }

                    activity?.runOnUiThread {
                        Toast.makeText(activity,"SUCCESS: Keywords fetched", Toast.LENGTH_SHORT).show()
                    }
                }
                is Result.Failure -> activity?.runOnUiThread {
                    Toast.makeText(activity, "ERROR: Fetching Keywords", Toast.LENGTH_SHORT).show()
                }
            }
        }.join()
    }

    private fun updateUserKeywords(keywordList: ArrayList<Keyword>) {
        val keywordsValues = keywordList.map{ it.value }.toList()
        val jsonString = Gson().toJson(keywordsValues).toString()
        putKeywordsURL.httpPut().body(jsonString, Charset.forName("UTF-8")).responseString { _, _, result ->
            when (result) {
                is Result.Success -> activity?.runOnUiThread {
                    Toast.makeText(activity, "SUCCESS: Keywords updated", Toast.LENGTH_SHORT).show()
                }
                is Result.Failure -> activity?.runOnUiThread {
                    Toast.makeText(activity, "ERROR: Updating keywords", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}