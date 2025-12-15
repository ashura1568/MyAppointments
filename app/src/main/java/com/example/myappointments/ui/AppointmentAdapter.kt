package com.example.myappointments.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myappointments.R
import com.example.myappointments.model.Appointment
import androidx.transition.TransitionManager
import androidx.transition.AutoTransition

class AppointmentAdapter
    : RecyclerView.Adapter<AppointmentAdapter.ViewHolder>() {

    var appointments = ArrayList<Appointment>()

        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val tvAppointmentId = itemView.findViewById<TextView>(R.id.tvAppointmentId)
            val tvDoctorName = itemView.findViewById<TextView>(R.id.tvDoctorName)
            val tvScheduledDate = itemView.findViewById<TextView>(R.id.tvScheduledDate)
            val tvScheduledTime = itemView.findViewById<TextView>(R.id.tvScheduledTime)

            val tvSpecialty = itemView.findViewById<TextView>(R.id.tvSpecialty)
            val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
            val tvStatus = itemView.findViewById<TextView>(R.id.tvStatus)
            val tvType = itemView.findViewById<TextView>(R.id.tvType)
            val tvCreatedAt = itemView.findViewById<TextView>(R.id.tvType)

            val ibExpand = itemView.findViewById<ImageButton>(R.id.ibExpand)

            val linearLayoutDetails = itemView.findViewById<LinearLayout>(R.id.linearLayoutDetails)

            fun bind(appointment: Appointment)= with (itemView) {
                tvAppointmentId.text=context.getString(R.string.item_appointment_id, appointment.id)
                tvDoctorName.text=appointment.doctor.name
                tvScheduledDate.text=context.getString(R.string.item_appointment_date, appointment.scheduledDate)
                tvScheduledTime.text=context.getString(R.string.item_appointment_time, appointment.scheduledTime)

                tvSpecialty.text = appointment.specialty.name
                tvDescription.text = appointment.description
                tvStatus.text = appointment.status
                tvType.text = appointment.type
                tvCreatedAt.text = context.getString(R.string.item_appointment_created_at, appointment.createdAt)

                ibExpand.setOnClickListener {
                    TransitionManager.beginDelayedTransition(parent as ViewGroup, AutoTransition())

                    if (linearLayoutDetails.visibility == View.VISIBLE) {
                        linearLayoutDetails.visibility = View.GONE
                        ibExpand.setImageResource(R.drawable.ic_expand_more)
                    } else {
                        linearLayoutDetails.visibility = View.VISIBLE
                        ibExpand.setImageResource(R.drawable.ic_expand_less)
                    }
                }

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