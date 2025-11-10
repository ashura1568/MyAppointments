package com.example.myappointments.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myappointments.PreferenceHelper
import com.example.myappointments.PreferenceHelper.get
import com.example.myappointments.PreferenceHelper.set
import com.example.myappointments.R
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {



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

        if(preferences["session",false])
            goToMenuActivity()

        val buttonLogin: Button = findViewById(R.id.btnLogin)

        buttonLogin.setOnClickListener {
            createSessionPreference()
            goToMenuActivity()
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

    private fun createSessionPreference() {
        /* Preferencias sin kotlin para guardar sesiones temporales en el dispositivo
        val preferences = getSharedPreferences("general", Context.MODE_PRIVATE)
        val editor=preferences.edit()
        editor.putBoolean("session",true)
        editor.apply()*/
        val preferences = PreferenceHelper.defaultPrefs(this)
        preferences["session"]=true
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


