package com.example.myappointments.model

data class Appointment (
    /*val id: Int,
    val description: String,
    val type: String,
    val status: String,

    @SerializedName("scheduled_date") val scheduledDate: String,
    @SerializedName("scheduled_time_12") val scheduledTime: String,
    @SerializedName("created_at") val createdAt: String,

    val specialty: Specialty,
    val doctor: Doctor*/
    val id: Int,
    val doctorName: String,
    val scheduledDate: String,
    val scheduledTime: String

)