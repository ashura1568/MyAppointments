package com.example.myappointments.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myappointments.PreferenceHelper
import com.example.myappointments.PreferenceHelper.get
import com.example.myappointments.PreferenceHelper.set
import com.example.myappointments.R
import com.example.myappointments.io.ApiService
import com.example.myappointments.io.response.LoginResponse
import com.example.myappointments.util.toast
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val etEmail: EditText = findViewById(R.id.etEmail)
        // shared preferences
        // SQLite
        // files

        /* Preferencias sin kotlin para guardar sesiones temporales en el dispositivo

        val preferences = getSharedPreferences("general", Context.MODE_PRIVATE)
        val session=preferences.getBoolean("session",false)*/


        val preferences = PreferenceHelper.defaultPrefs(this)

        /*if(preferences["session",false])
            goToMenuActivity()*/

        if (preferences["jwt", ""].contains("."))
            goToMenuActivity()

        val buttonLogin: Button = findViewById(R.id.btnLogin)

        buttonLogin.setOnClickListener {
            //createSessionPreference()
            //goToMenuActivity()
            performLogin()
        }

        val textoregistro: TextView = findViewById(R.id.tvGoToRegister)

        textoregistro.setOnClickListener {
            //Toast.makeText(this, getString(R.string.please_fill_your_register_data), Toast.LENGTH_SHORT).show()
            Toast.makeText(
                this,
                getString(R.string.please_fill_your_register_data),
                Toast.LENGTH_SHORT
            ).show()

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


    }

    private fun performLogin() {
        /*val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        if (email.trim().isEmpty() || password.trim().isEmpty()) {
            toast(getString(R.string.error_empty_credentials))
            return
        }

        val call = apiService.postLogin(email, password)
        call.enqueue(object: Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                toast(t.localizedMessage)
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse == null) {
                        toast(getString(R.string.error_login_response))
                        return
                    }
                    if (loginResponse.success) {
                        createSessionPreference(loginResponse.jwt)
                        toast(getString(R.string.welcome_name, loginResponse.user.name))
                        goToMenuActivity(true)
                    } else {
                        toast(getString(R.string.error_invalid_credentials))
                    }
                } else {
                    toast(getString(R.string.error_login_response))
                }
            }
        })*/

        //val email = etEmail.text.toString()
        //val password = etPassword.text.toString()

        /*if (email.trim().isEmpty() || password.trim().isEmpty()) {
            toast(getString(R.string.error_empty_credentials))
            return
        }*/

        val etEmail: EditText = findViewById(R.id.etEmail)
        val etPassword: EditText = findViewById(R.id.etPassword)

        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        if (email.trim().isEmpty() || password.trim().isEmpty()) {
            toast(getString(R.string.error_empty_credentials))
            return
        }

        val call = apiService.postLogin(email, password)
        call.enqueue(object: Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                toast(t.localizedMessage)
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse == null) {
                        toast(getString(R.string.error_login_response))
                        return
                    }
                    if (loginResponse.success) {
                        createSessionPreference(loginResponse.jwt)
                        toast(getString(R.string.welcome_name, loginResponse.user.name))
                        //goToMenuActivity(true)
                        goToMenuActivity()
                    } else {
                        toast(getString(R.string.error_invalid_credentials))
                    }
                } else {
                    toast(getString(R.string.error_login_response))
                }
            }
        })


    }

    private fun createSessionPreference(jwt: String) {
        /* Preferencias sin kotlin para guardar sesiones temporales en el dispositivo
        val preferences = getSharedPreferences("general", Context.MODE_PRIVATE)
        val editor=preferences.edit()
        editor.putBoolean("session",true)
        editor.apply()*/
        val preferences = PreferenceHelper.defaultPrefs(this)
        //preferences["session"]=true
        preferences["jwt"] = jwt
    }

    private fun goToMenuActivity() {

        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("GestureBackNavigation", "MissingSuperCall")
    @Override
    override fun onBackPressed() {
        val mainLayout: LinearLayout = findViewById(R.id.mainLayout)
        val snackBar by lazy {
            Snackbar.make(mainLayout, R.string.press_back_again, Snackbar.LENGTH_SHORT)
        }

        if (snackBar.isShown)
            super.onBackPressed()
            //onBackPressedDispatcher.onBackPressed()
        else
            snackBar.show()
    }
}


