package pl.nc.newscollector

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Secure
import android.util.Log
import android.widget.Toast
import androidx.core.content.edit
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpPost
import kotlinx.android.synthetic.main.activity_start.*
import android.provider.Settings.Secure.getString as getAndroidID

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        setAuthKey()
        initialize()
        btnStart.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    @SuppressLint("HardwareIds")
    private fun setAuthKey() {
        val preferences = getPreferences(Context.MODE_PRIVATE)
        val authKey = preferences.getString("AUTH_KEY", null)
        if (authKey == null) {
            preferences.edit {
                putString("AUTH_KEY", getAndroidID(contentResolver, Secure.ANDROID_ID))
                apply()
            }
        }
    }

    private fun initialize() {
        var endpoint = "https://g2alb3a3q6.execute-api.us-east-1.amazonaws.com/test/users"
        val preferences = getPreferences(Context.MODE_PRIVATE)
        preferences.getString("AUTH_KEY", null)?.let { authKey ->
            endpoint += "?authKey=$authKey"
            endpoint.httpPost().responseString { request, response, result ->
                println(response)
                println(result)
            }
        } ?: Toast.makeText(this, "Authentication problem", Toast.LENGTH_LONG).show()
    }

}