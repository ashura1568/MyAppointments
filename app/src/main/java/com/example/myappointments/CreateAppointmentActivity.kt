package com.example.myappointments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

class CreateAppointmentActivity : AppCompatActivity() {

    private val selectedCalendar = Calendar.getInstance()

    private var selectedRadioBtn: RadioButton? = null
    val cvStep1: CardView = findViewById(R.id.cvStep1)
    val cvStep2: CardView = findViewById(R.id.cvStep2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_create_appointment)

        val buttonNext: Button = findViewById(R.id.btnNext)
        val buttonConfirm: Button = findViewById(R.id.btnConfirmAppointment)

        val spinnerespecialty: Spinner = findViewById(R.id.spinnerSpecialties)
        val doctorespecialty: Spinner = findViewById(R.id.spinnerDoctors)

        buttonNext.setOnClickListener {
            val etDescription: EditText = findViewById(R.id.etDescription)
            if (etDescription.text.toString().length < 3) {
                etDescription.error = getString(R.string.validate_appointment_description)
            } else {
                // continue to step 2
                cvStep1.visibility = View.GONE
                cvStep2.visibility = View.VISIBLE
            }
        }

        buttonConfirm.setOnClickListener {
            Toast.makeText(
                this,
                "Cita registrada correctamente",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }


        val specialtyOptions=arrayOf("Especialidad A","Especialidad B","Especialidad C")
        spinnerespecialty.adapter=
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, specialtyOptions)

        val doctorOptions=arrayOf("Doctor A","Doctor B","Doctor C")
        doctorespecialty.adapter=
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, doctorOptions)



    }

    @SuppressLint("StringFormatMatches")
    fun onClickScheduledDate(v: View?) {

        val year = selectedCalendar.get(Calendar.YEAR)
        val month = selectedCalendar.get(Calendar.MONTH)
        val dayOfMonth = selectedCalendar.get(Calendar.DAY_OF_MONTH)

        val scheduledDate: EditText = findViewById(R.id.etScheduledDate)

        val listener = DatePickerDialog.OnDateSetListener { datePicker, y, m, d ->
            // Toast.makeText(this, "$y-$m-$d", Toast.LENGTH_SHORT).show()
            selectedCalendar.set(y, m, d)

            scheduledDate.setText(
                resources.getString(
                    R.string.date_format,
                    y,
                    //(m+1).twoDigits(),
                    m.twoDigits(),
                    d.twoDigits()
                )
            )
            scheduledDate.error = null
        }

        // new dialog
        val datePickerDialog = DatePickerDialog(this, listener, year, month, dayOfMonth)

        // set limits
        val datePicker = datePickerDialog.datePicker
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        datePicker.minDate = calendar.timeInMillis // +1
        calendar.add(Calendar.DAY_OF_MONTH, 29)
        datePicker.maxDate = calendar.timeInMillis // +30

        // show dialog
        datePickerDialog.show()


    }

    private fun displayRadioButtons(){
        val radioGroupLeft: LinearLayout = findViewById(R.id.radioGroupLeft)
        val radioGroupRight: LinearLayout = findViewById(R.id.radioGroupRight)
        //radigrp.clearCheck()
        //radigrp.removeAllViews()
        selectedRadioBtn=null
        radioGroupLeft.removeAllViews()
        radioGroupRight.removeAllViews()



        val hours = arrayOf("3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM")
        var goToLeft = true

        hours.forEach {
            val radioButton = RadioButton(this)
            radioButton.id = View.generateViewId()
            radioButton.text = it
            //radigrp.addView(radioButton)

            radioButton.setOnClickListener { view ->
                selectedRadioBtn?.isChecked = false

                selectedRadioBtn = view as RadioButton?
                selectedRadioBtn?.isChecked = true
            }

            if (goToLeft)
                radioGroupLeft.addView(radioButton)
            else
                radioGroupRight.addView(radioButton)
            goToLeft = !goToLeft
        }


    }

    private fun Int.twoDigits()
            = if (this>=10) this.toString() else "0$this"

    @Deprecated("Deprecated in Java")
    @SuppressLint("GestureBackNavigation", "MissingSuperCall")
    override fun onBackPressed() {
        if(cvStep2.visibility == View.VISIBLE){
            cvStep2.visibility == View.GONE
            cvStep1.visibility == View.VISIBLE

        }else if(cvStep1.visibility == View.VISIBLE){
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.dialog_create_appointment_exit_title))
            builder.setMessage(getString(R.string.dialog_create_appointment_exit_message))
            builder.setPositiveButton(getString(R.string.dialog_create_appointment_exit_positive_btn)) { _, _ ->
                finish()
            }
            builder.setNegativeButton(getString(R.string.dialog_create_appointment_exit_negative_btn)) { dialog, _ ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }

    }

}