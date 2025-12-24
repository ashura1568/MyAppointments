package com.example.myappointments.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myappointments.PreferenceHelper
import com.example.myappointments.PreferenceHelper.set
import com.example.myappointments.PreferenceHelper.get
import com.example.myappointments.R
import com.example.myappointments.io.ApiService
import com.example.myappointments.model.User
import com.example.myappointments.util.toast
import com.google.android.material.snackbar.Snackbar
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

    private val authHeader by lazy {
        val jwt = preferences["jwt", ""]
        "Bearer $jwt"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        setOnClickListeners()


    }

    private fun setOnClickListeners() {

        val btnProfile: Button = findViewById(R.id.btnProfile)

        btnProfile.setOnClickListener {
            editProfile()
        }

        val buttonCreateCita: Button = findViewById(R.id.btnCreateAppointment)

        buttonCreateCita.setOnClickListener {
            //val intent = Intent(this, CreateAppointmentActivity::class.java)
            //startActivity(intent)
            createAppointment(it)
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

    private fun editProfile() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
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

    private fun createAppointment(view: View) {
        val call = apiService.getUser(authHeader)
        call.enqueue(object: Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                toast(t.localizedMessage)
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    val phoneLength = user?.phone?.length ?: 0

                    if (phoneLength >= 6) {
                        val intent = Intent(this@MenuActivity, CreateAppointmentActivity::class.java)
                        startActivity(intent)
                    } else {
                        Snackbar.make(view, R.string.you_need_a_phone, Snackbar.LENGTH_LONG).show()
                    }
                }
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