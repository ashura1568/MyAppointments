package com.example.myappointments.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myappointments.PreferenceHelper
import com.example.myappointments.PreferenceHelper.set
import com.example.myappointments.PreferenceHelper.get
import com.example.myappointments.R
import com.example.myappointments.io.ApiService
import com.example.myappointments.util.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuActivity : AppCompatActivity() {

    private val apiService by lazy {
        ApiService.create()
    }
    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        val buttonCreateCita: Button = findViewById(R.id.btnCreateAppointment)

        buttonCreateCita.setOnClickListener {
            val intent = Intent(this, CreateAppointmentActivity::class.java)
            startActivity(intent)
        }

        val buttonMyAppointments: Button = findViewById(R.id.btnMyAppointments)

        buttonMyAppointments.setOnClickListener {
            val intent = Intent(this, AppointmentsActivity::class.java)
            startActivity(intent)
        }

        val buttonLogout: Button = findViewById(R.id.btnLogout)

        buttonLogout.setOnClickListener {
            performLogout()
            /*clearSessionPreference()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()*/
        }
    }

    private fun performLogout() {
        /*val call = apiService.postLogout(authHeader)
        call.enqueue(object: Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                toast(t.localizedMessage)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                clearSessionPreference()

                val intent = Intent(this@MenuActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })*/
        //val call = apiService.postLogout(authHeader)
        val jwt = preferences["jwt",""]
        val call = apiService.postLogout("Bearer $jwt")
        call.enqueue(object: Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                toast(t.localizedMessage)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                clearSessionPreference()

                val intent = Intent(this@MenuActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }



    private fun clearSessionPreference() {
        /* Preferencias sin kotlin para guardar sesiones temporales en el dispositivo
        val preferences = getSharedPreferences("general", Context.MODE_PRIVATE)
        val editor=preferences.edit()
        editor.putBoolean("session",false)
        editor.apply()*/

        //val preferences = PreferenceHelper.defaultPrefs(this)
        //preferences["session"] = false

        preferences["jwt"] = ""
    }
}