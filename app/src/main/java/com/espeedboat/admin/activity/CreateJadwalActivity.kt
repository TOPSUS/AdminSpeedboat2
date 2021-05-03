package com.espeedboat.admin.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.espeedboat.admin.R
import com.espeedboat.admin.adapters.DropdownAdapter
import com.espeedboat.admin.client.RetrofitClient
import com.espeedboat.admin.model.Dropdown
import com.espeedboat.admin.model.Jadwal
import com.espeedboat.admin.model.Response
import com.espeedboat.admin.service.JadwalService
import com.espeedboat.admin.service.KapalService
import com.espeedboat.admin.service.PelabuhanService
import com.espeedboat.admin.utils.Constants
import com.espeedboat.admin.utils.SessionManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import retrofit2.Call
import retrofit2.Callback
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CreateJadwalActivity : AppCompatActivity() {
    private var sessionManager: SessionManager? = null
    private var profileToolbar: ImageView? = null
    private var notifToolbar: ImageView? = null
    private var back: ImageView? = null
    private var autoCompleteKapal: AutoCompleteTextView? = null
    private var autoCompleteAsal: AutoCompleteTextView? = null
    private var autoCompleteTujuan: AutoCompleteTextView? = null
    private var tanggal: TextInputEditText? = null
    private var waktuBerangkat: TextInputEditText? = null
    private var estimasi: TextInputEditText? = null
    private var harga: TextInputEditText? = null
    private var timePicker: MaterialTimePicker? = null
    private var remove: Button? = null
    private var submit: Button? = null
    private var toolbar: Toolbar? = null
    private var title: TextView? = null
    private var listKapal: MutableList<Dropdown>? = null
    private var listPelabuhanAsal: MutableList<Dropdown>? = null
    private var listPelabuhanTujuan: MutableList<Dropdown>? = null
    private var pelabuhanAsalAdapter: DropdownAdapter? = null
    private var pelabuhanTujuanAdapter: DropdownAdapter? = null
    private var idJadwal = 0
    private var idPelabuhanAsal = 0
    private var idPelabuhanTujuan = 0
    private var idKapal = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_jadwal)
        init()
        setRemoveVisible(false)
        setToolbar()
        val bundle = intent.extras
        if (bundle != null) {
            val id = bundle.getInt(Constants.JADWAL_ID, 0)
//            if (id > 0) {
//                title?.setText(R.string.menu_jadwal_edit)
//                setRemoveVisible(true)
//                getDataJadwal(id)
//            }
        }
        kapalValue
        pelabuhanValue
        setClickListener()
    }

    private fun getDataJadwal(id: Int) {
        idJadwal = id
        val service = RetrofitClient.getClient().create<JadwalService>(JadwalService::class.java)

        val showJadwal = service.showJadwal(sessionManager!!.authToken, id)
        showJadwal.enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                Log.d("data jadwal", response.body().toString())
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        val data = response.body()!!.data
//                        setJadwalValue(data.jadwal);
                    } else {
                        Toast.makeText(applicationContext, "Failed show Jadwal", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "Failed Fetch Jadwal", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                Toast.makeText(applicationContext, "Error Fetch Jadwal", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun init() {
        remove = findViewById(R.id.btn_remove)
        autoCompleteKapal = findViewById(R.id.autoCompleteKapal)
        autoCompleteAsal = findViewById(R.id.autoCompleteAsal)
        autoCompleteTujuan = findViewById(R.id.autoCompleteTujuan)
        submit = findViewById(R.id.btn_submit)
        tanggal = findViewById(R.id.tanggalForm)
        waktuBerangkat = findViewById(R.id.waktuForm)
        toolbar = findViewById(R.id.main_toolbar)
        profileToolbar = findViewById(R.id.toolbar_profile)
        notifToolbar = findViewById(R.id.toolbar_notification)
        back = findViewById(R.id.toolbar_back)
        title = findViewById(R.id.toolbar_title)
        sessionManager = SessionManager(this)
        listKapal = ArrayList()
        listPelabuhanAsal = ArrayList()
        listPelabuhanTujuan = ArrayList()
        estimasi = findViewById(R.id.estimasiForm)
        harga = findViewById(R.id.hargaForm)
    }

    private fun setRemoveVisible(con: Boolean) {
        if (con) {
            remove!!.visibility = View.VISIBLE
        } else {
            remove!!.visibility = View.GONE
        }
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        title?.setText(R.string.menu_jadwal_create)
        profileToolbar?.setVisibility(View.GONE)
        back?.setVisibility(View.VISIBLE)
        back?.setOnClickListener(View.OnClickListener { v: View? -> onBackPressed() })
    }

    private val kapalValue: Unit
        private get() {
            autoCompleteKapal?.clearListSelection()
            val service = RetrofitClient.getClient().create<KapalService>(KapalService::class.java)
            val getUserKapal = service.getUserKapal(sessionManager!!.authToken)
            getUserKapal.enqueue(object : Callback<Response> {
                override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                    if (response.isSuccessful) {
                        if (response.body()!!.status == 200) {
                            val data = response.body()!!.data
                            if (data.dropdown.size > 0) {
                                var i = 0
                                var selectedId = -1
                                for (drops in data.dropdown) {
                                    listKapal!!.add(drops)
                                }
                                val dropdownAdapter = DropdownAdapter(this@CreateJadwalActivity, listKapal)
                                autoCompleteKapal!!.setAdapter(dropdownAdapter)
                            } else {
                                Toast.makeText(applicationContext, "Please Create Kapal First", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Toast.makeText(applicationContext, "Failed to Show Dropdown Kapal", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(applicationContext, "Failed to Fetch Dropdown Kapal", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }
            })
        }

    private val pelabuhanValue: Unit
        private get() {
            val service = RetrofitClient.getClient().create(PelabuhanService::class.java)
            val getUserKapal = service.getPelabuhanList(sessionManager!!.authToken)
            getUserKapal.enqueue(object : Callback<Response> {
                override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                    if (response.isSuccessful) {
                        if (response.body()!!.status == 200) {
                            val data = response.body()!!.data
                            if (data.dropdown.size > 0) {
                                for (drops in data.dropdown) {
                                    listPelabuhanAsal!!.add(drops)
                                    listPelabuhanTujuan!!.add(drops)
                                }
                                pelabuhanAsalAdapter = DropdownAdapter(this@CreateJadwalActivity, listPelabuhanAsal)
                                pelabuhanTujuanAdapter = DropdownAdapter(this@CreateJadwalActivity, listPelabuhanTujuan)
                                autoCompleteAsal!!.setAdapter(pelabuhanAsalAdapter)
                                autoCompleteTujuan!!.setAdapter(pelabuhanTujuanAdapter)
                            } else {
                                Toast.makeText(applicationContext, "Data Pelabuhan is Null", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Toast.makeText(applicationContext, "Failed to Show Dropdown Pelabuhan", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(applicationContext, "Failed to Fetch Dropdown Pelabuhan", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }
            })
        }

    private fun setClickListener() {
        autoCompleteKapal!!.onItemClickListener = OnItemClickListener { parent: AdapterView<*>, view: View?, position: Int, id: Long ->
            idKapal = parent.getItemIdAtPosition(position).toInt()
        }

        autoCompleteAsal!!.onItemClickListener = OnItemClickListener { parent: AdapterView<*>, view: View?, position: Int, id: Long ->
            idPelabuhanAsal = parent.getItemIdAtPosition(position).toInt()
//            Toast.makeText(applicationContext, "Pelabuhan $idPelabuhanAsal", Toast.LENGTH_LONG).show()
            checkPelabuhan()
        }

        autoCompleteTujuan!!.onItemClickListener = OnItemClickListener { parent: AdapterView<*>, view: View?, position: Int, id: Long ->
            idPelabuhanTujuan = parent.getItemIdAtPosition(position).toInt()
//            Toast.makeText(applicationContext, "Pelabuhan $idPelabuhanTujuan", Toast.LENGTH_LONG).show()
            checkPelabuhan()
        }

        tanggal!!.setOnClickListener { v: View? -> setTanggalListener() }

        waktuBerangkat!!.setOnClickListener { v: View? -> setWaktuListener() }

        submit!!.setOnClickListener { v: View? -> createJadwal }
    }

    private val createJadwal: Unit
        private get() {

            if (checkPelabuhan()) {
                val dateFormat: DateFormat = SimpleDateFormat("dd MMM yyyy")
                val date = dateFormat.parse(tanggal?.getText().toString()).time

                val df: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                val inTanggal = df.format(date)

                val inWaktu = waktuBerangkat?.getText().toString()
                val inEstimasi = estimasi?.text.toString()
                val inHarga = harga?.text.toString()

                val service = RetrofitClient.getClient().create<JadwalService>(JadwalService::class.java)
                val createJadwal = service.createJadwal(sessionManager?.authToken, idKapal, idPelabuhanAsal,
                        idPelabuhanTujuan, inTanggal, inWaktu, inEstimasi, inHarga)
                createJadwal.enqueue(object : Callback<Response> {
                    override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                        if (response.isSuccessful) {
                            if (response.body()!!.status == 200) {
                                Toast.makeText(applicationContext, "Success create jadwal", Toast.LENGTH_LONG).show()
                                onBackPressed()
                            } else {
                                Toast.makeText(applicationContext, "Failed to create jadwal", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Toast.makeText(applicationContext, "Failed to create jadwal", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<Response>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }
                })
            }
    }

    private fun checkPelabuhan(): Boolean {
        if (idPelabuhanAsal == idPelabuhanTujuan) {
            Toast.makeText(applicationContext, "Please Select Different Pelabuhan", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun setTanggalListener() {
        var date = MaterialDatePicker.todayInUtcMilliseconds()
        if (tanggal?.text.toString().length != 0) {
            val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            try {
                val formatDate = formatter.parse(tanggal!!.text.toString())
                date = formatDate.time
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
        val fm = this.supportFragmentManager
        val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(date)
                .build()
        datePicker.show(fm, "open date picker")
        datePicker.addOnPositiveButtonClickListener { selection: Any? ->
            val dateFormat: DateFormat = SimpleDateFormat("dd MMM yyyy")
            val hasilTanggal = dateFormat.format(selection)
            tanggal!!.setText(hasilTanggal)
        }
    }

    private fun setWaktuListener() {
        val fm = this.supportFragmentManager
        val timePicker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText("Select time")
                .build()
        timePicker.show(fm, "open time picker")
        timePicker.addOnPositiveButtonClickListener { selection: Any? ->
            val hour = timePicker.hour
            val minute = timePicker.minute

            waktuBerangkat!!.setText("$hour:$minute")
        }
    }
}