package com.example.myappointments.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myappointments.R
import com.example.myappointments.io.ApiService
import com.example.myappointments.model.Appointment
import com.example.myappointments.util.PreferenceHelper
import com.example.myappointments.util.PreferenceHelper.get
import com.example.myappointments.util.toast

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class AppointmentsActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    private val appointmentAdapter = AppointmentAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointments)

        /*val appointments = ArrayList<Appointment>()
        appointments.add(
            Appointment(1,"Medico Test","24/10/2025","3:00 PM")
        )
        appointments.add(
            Appointment(2,"Medico B","25/10/2025","4:00 PM")
        )
        appointments.add(
                Appointment(3,"Medico C","26/10/2025","5:00 PM")
                )*/

        loadAppointments()

        val recicleview: RecyclerView = findViewById(R.id.rvAppointments)

        recicleview.layoutManager = LinearLayoutManager(this)

        recicleview.adapter= appointmentAdapter

    }

    private fun loadAppointments() {
        val jwt = preferences["jwt", ""]

        val call = apiService.getAppointments("Bearer $jwt")

        call.enqueue(object: Callback<ArrayList<Appointment>> {
            override fun onFailure(call: Call<ArrayList<Appointment>>, t: Throwable) {
                toast(t.localizedMessage)
            }

            override fun onResponse(call: Call<ArrayList<Appointment>>, response: Response<ArrayList<Appointment>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        appointmentAdapter.appointments = it
                        appointmentAdapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}