package com.example.myappointments.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.myappointments.R
import java.util.Calendar
import com.google.android.material.snackbar.Snackbar
import com.example.myappointments.io.ApiService
import com.example.myappointments.model.Specialty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class card_view_step_one : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_card_view_step_one)

    }
}