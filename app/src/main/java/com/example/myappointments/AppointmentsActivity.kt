package com.example.myappointments

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myappointments.model.Appointment

class AppointmentsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointments)

        val appointments = ArrayList<Appointment>()
        appointments.add(
            Appointment(1,"Medico Test","24/10/2025","3:00 PM")
        )
        appointments.add(
            Appointment(2,"Medico B","25/10/2025","4:00 PM")
        )
        appointments.add(
                Appointment(3,"Medico C","26/10/2025","5:00 PM")
                )


        val recicleview: RecyclerView = findViewById(R.id.rvAppointments)

        recicleview.layoutManager = LinearLayoutManager(this)

        recicleview.adapter= AppointmentAdapter(appointments)

    }
}