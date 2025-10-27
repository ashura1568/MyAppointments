package com.example.myappointments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myappointments.PreferenceHelper.get
import com.example.myappointments.PreferenceHelper.set

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
}


