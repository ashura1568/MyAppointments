package com.example.myappointments.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myappointments.R
import com.example.myappointments.model.Appointment

class AppointmentAdapter(val appointments: ArrayList<Appointment>)
    : RecyclerView.Adapter<AppointmentAdapter.ViewHolder>() {

        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val tvAppointmentId = itemView.findViewById<TextView>(R.id.tvAppointmentId)
            val tvDoctorName = itemView.findViewById<TextView>(R.id.tvDoctorName)
            val tvScheduledDate = itemView.findViewById<TextView>(R.id.tvScheduledDate)
            val tvScheduledTime = itemView.findViewById<TextView>(R.id.tvScheduledTime)
            fun bind(appointment: Appointment)= with (itemView) {
                tvAppointmentId.text=context.getString(R.string.item_appointment_id, appointment.id)
                tvDoctorName.text=appointment.doctorName
                tvScheduledDate.text=context.getString(R.string.item_appointment_date, appointment.scheduledDate)
                tvScheduledTime.text=context.getString(R.string.item_appointment_time, appointment.scheduledTime)

            }


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_appointment,
                parent,
                false
            ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.bind(appointment)


    }

    // Number of elements
    override fun getItemCount() = appointments.size

}