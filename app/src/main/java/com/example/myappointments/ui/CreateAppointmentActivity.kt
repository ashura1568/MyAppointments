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

class CreateAppointmentActivity : AppCompatActivity() {

    val apiService: ApiService by lazy {
        ApiService.create()
    }
    private var selectedTimeRadioBtn: RadioButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_create_appointment)

        val cvStep1: CardView = findViewById(R.id.cvStep1)
        val cvStep2: CardView = findViewById(R.id.cvStep2)
        val cvStep3: CardView = findViewById(R.id.cvStep3)


        val buttonNext: Button = findViewById(R.id.btnNext)
        val buttonConfirm: Button = findViewById(R.id.btnConfirmAppointment)
        val btnNext2: Button = findViewById(R.id.btnNext2)


        val doctorespecialty: Spinner = findViewById(R.id.spinnerDoctors)

        val etScheduledDate: EditText = findViewById(R.id.etScheduledDate)

        val createAppointmentLinearLayout: LinearLayout = findViewById(R.id.createAppointmentLinearLayout)

       //val selectedTimeRadioBtn: RadioButton? = null

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

        btnNext2.setOnClickListener {
            when {
                etScheduledDate.text.toString().isEmpty() ->
                    etScheduledDate.error = getString(R.string.validate_appointment_date)

                selectedTimeRadioBtn == null ->
                    Snackbar.make(createAppointmentLinearLayout,
                        R.string.validate_appointment_time, Snackbar.LENGTH_SHORT).show()

                else -> {
                    // continue to step 3
                    showAppointmentDataToConfirm()
                    cvStep2.visibility = View.GONE
                    cvStep3.visibility = View.VISIBLE
                }
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

        loadSpecialties()
        /*val spinnerespecialty: Spinner = findViewById(R.id.spinnerSpecialties)
        val specialtyOptions=arrayOf("Especialidad A","Especialidad B","Especialidad C")
        spinnerespecialty.adapter=
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, specialtyOptions)*/


        val doctorOptions=arrayOf("Doctor A","Doctor B","Doctor C")
        doctorespecialty.adapter=
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, doctorOptions)



    }

    private fun loadSpecialties() {



        val call = apiService.getSpecialties()
        call.enqueue(object: Callback<ArrayList<Specialty>> {
            override fun onFailure(call: Call<ArrayList<Specialty>>, t: Throwable) {
                Toast.makeText(this@CreateAppointmentActivity, "Ocurrio un problema al cargar las especialidades", Toast.LENGTH_SHORT).show()
                finish()
            }

            override fun onResponse(call: Call<ArrayList<Specialty>>, response: Response<ArrayList<Specialty>>) {
                if (response.isSuccessful) { // [200...300)
                    /*response.body()?.let {
                        val specialties = it.toMutableList()
                        spinnerespecialty.adapter = ArrayAdapter(this@CreateAppointmentActivity, android.R.layout.simple_list_item_1, specialties)
                    }*/
                    val specialties = response.body()
                    val specialtyOptions = ArrayList<String>()

                    specialties?.forEach {
                        specialtyOptions.add(it.name)
                    }
                    val spinnerespecialty: Spinner = findViewById(R.id.spinnerSpecialties)
                    spinnerespecialty.adapter = ArrayAdapter(this@CreateAppointmentActivity, android.R.layout.simple_list_item_1, specialtyOptions)
                }
            }
        })


        //val specialtyOptions=arrayOf("Especialidad A","Especialidad B","Especialidad C")
        /*spinnerespecialty.adapter=
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, specialtyOptions)*/
    }
    @SuppressLint("StringFormatMatches")
    fun onClickScheduledDate(v: View?) {

        val selectedCalendar = Calendar.getInstance()

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
            scheduledDate.error=null
            displayRadioButtons()
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
        //var selectedTimeRadioBtn: RadioButton? = null
        //radigrp.clearCheck()
        //radigrp.removeAllViews()
        selectedTimeRadioBtn=null
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
                selectedTimeRadioBtn?.isChecked = false

                selectedTimeRadioBtn = view as RadioButton?
                selectedTimeRadioBtn?.isChecked = true
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
        setContentView(R.layout.activity_create_appointment)

        val cvStep1: CardView = findViewById(R.id.cvStep1)
        val cvStep2: CardView = findViewById(R.id.cvStep2)
        val cvStep3: CardView = findViewById(R.id.cvStep3)

        when {
            cvStep3.visibility == View.VISIBLE -> {
                cvStep3.visibility = View.GONE
                cvStep2.visibility = View.VISIBLE

            }

            cvStep2.visibility == View.VISIBLE -> {
                cvStep2.visibility = View.GONE
                cvStep1.visibility = View.VISIBLE

            }

            cvStep1.visibility == View.VISIBLE -> {
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


    private fun showAppointmentDataToConfirm() {
        val tvConfirmDescription: TextView = findViewById(R.id.tvConfirmDescription)
        val tvConfirmSpecialty: TextView = findViewById(R.id.tvConfirmSpecialty)
        val etDescription: EditText = findViewById(R.id.etDescription)
        val spinnerSpecialties: Spinner = findViewById(R.id.spinnerSpecialties)
        val radioGroupType: RadioGroup = findViewById(R.id.radioGroupType)
        val tvConfirmType: TextView = findViewById(R.id.tvConfirmType)
        val tvConfirmDoctorName: TextView = findViewById(R.id.tvConfirmDoctorName)
        val spinnerDoctors: Spinner = findViewById(R.id.spinnerDoctors)
        val tvConfirmDate: TextView = findViewById(R.id.tvConfirmDate)
        val tvConfirmTime: TextView = findViewById(R.id.tvConfirmTime)
        val etScheduledDate: EditText = findViewById(R.id.etScheduledDate)
        //val selectedTimeRadioBtn: RadioButton? = null

        tvConfirmDescription.text = etDescription.text.toString()
        tvConfirmSpecialty.text = spinnerSpecialties.selectedItem.toString()

        val selectedRadioBtnId = radioGroupType.checkedRadioButtonId
        val selectedRadioType = radioGroupType.findViewById<RadioButton>(selectedRadioBtnId)

        tvConfirmType.text = selectedRadioType.text.toString()

        tvConfirmDoctorName.text = spinnerDoctors.selectedItem.toString()
        tvConfirmDate.text = etScheduledDate.text.toString()
        tvConfirmTime.text = selectedTimeRadioBtn?.text.toString()
    }





}