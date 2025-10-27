package com.example.myappointments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myappointments.PreferenceHelper.set

class MenuActivity : AppCompatActivity() {
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
            clearSessionPreference()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun clearSessionPreference() {
        /* Preferencias sin kotlin para guardar sesiones temporales en el dispositivo
        val preferences = getSharedPreferences("general", Context.MODE_PRIVATE)
        val editor=preferences.edit()
        editor.putBoolean("session",false)
        editor.apply()*/

        val preferences = PreferenceHelper.defaultPrefs(this)
        preferences["session"] = false
    }
}