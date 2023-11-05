package com.example.eletriccar.ui

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.eletriccar.R
import com.example.eletriccar.data.CarFactory
import com.example.eletriccar.data.CarsApi
import com.example.eletriccar.data.local.CarsContract
import com.example.eletriccar.data.local.CarsContract.CarEntry.COLUMN_NAME_BATTERY
import com.example.eletriccar.data.local.CarsContract.CarEntry.COLUMN_NAME_POWER
import com.example.eletriccar.data.local.CarsContract.CarEntry.COLUMN_NAME_PRICE
import com.example.eletriccar.data.local.CarsContract.CarEntry.COLUMN_NAME_REFILL
import com.example.eletriccar.data.local.CarsContract.CarEntry.COLUMN_NAME_URL_PHOTO
import com.example.eletriccar.data.local.CarsContract.CarEntry.TABLE_NAME
import com.example.eletriccar.data.local.CarsDbHelper
import com.example.eletriccar.domain.Car
import com.example.eletriccar.ui.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class CarFragment : Fragment() {

    lateinit var fabCalculate: FloatingActionButton
    lateinit var carList: RecyclerView
    lateinit var progress: ProgressBar
    lateinit var noInternetImage: ImageView
    lateinit var noInternetText: TextView
    lateinit var carsApi: CarsApi

    val carsArray: ArrayList<Car> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.car_fragment,
            container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRetrofit()
        setupView(view)
        setupListeners()

    }

    override fun onResume() {
        super.onResume()
        if (checkForInternet(context)) {
            //  callService()
            getAllCars()
        } else {
            emptyState()
        }
    }

    fun setupRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://igorbag.github.io/cars-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        carsApi = retrofit.create(CarsApi::class.java)
    }

    fun getAllCars() {
        carsApi.getAllCars().enqueue(object : Callback<List<Car>> {
            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {
                if (response.isSuccessful) {
                    progress.isVisible = false
                    noInternetImage.isVisible = false
                    noInternetText.isVisible = false
                    response.body()?.let {
                        setupList(it)
                    }
                } else {
                    Toast.makeText(context, R.string.response_error, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                Toast.makeText(context, R.string.response_error, Toast.LENGTH_LONG).show()
            }

        })
    }

    fun emptyState() {
        progress.isVisible = false
        carList.isVisible = false
        noInternetImage.isVisible = true
        noInternetText.isVisible = true
    }

    fun setupView(view: View) {
        view.apply {
            fabCalculate = findViewById(R.id.fab_calculate)
            carList = findViewById(R.id.rv_list)
            progress = findViewById(R.id.pb_loading)
            noInternetImage = findViewById(R.id.iv_empty_state)
            noInternetText = findViewById(R.id.tv_no_wifi)
        }
    }

    fun setupList(listD: List<Car>) {
        val carAdapter = CarAdapter(listD)

        carList.apply {
            isVisible = true
            adapter = carAdapter
        }
        carAdapter.carItemLister = { car ->
            val battery = car.battery
        }
    }

    fun setupListeners() {
        fabCalculate.setOnClickListener {
            startActivity(Intent(context, AutonomyCalculateActivity::class.java))
        }
    }

    fun callService() {
        val urlBase = "https://igorbag.github.io/cars-api/cars.json"
        progress.isVisible = true
        MyTask().execute(urlBase)
    }

    fun checkForInternet(context: Context?): Boolean {
        val connectivityManager =
            context?.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val network = connectivityManager.activeNetwork ?: return false

            val activeNetwork =
                connectivityManager.getNetworkCapabilities(
                    network
                ) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    inner class MyTask : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            Log.d("MyTasK", "iniciando...")
            progress.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg url: String?): String {
            var urlConnection: HttpURLConnection? = null

            try {
                val urlBase = URL(url[0])

                urlConnection = urlBase.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = 60000
                urlConnection.readTimeout = 60000
                urlConnection.setRequestProperty(
                    "Accept",
                    "application/json"
                )

                val responseCode = urlConnection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    var response = urlConnection.inputStream.bufferedReader().use { it.readText() }
                    publishProgress(response)
                } else {
                    Log.e("Erro", "Serviço indisponivel no momento ....")
                }
            } catch (ex: Exception) {
                Log.e("Erro", "Erro ao realizar processamento ....")
            } finally {
                urlConnection?.disconnect()
            }

            return ""
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {
                val jsonArray = JSONTokener(values[0]).nextValue() as JSONArray

                for (i in 0 until jsonArray.length()) {
                    val id = jsonArray.getJSONObject(i).getString("id")
                    Log.d("ID ->", id)

                    val price = jsonArray.getJSONObject(i).getString("price")
                    Log.d("Price ->", price)

                    val battery = jsonArray.getJSONObject(i).getString("battery")
                    Log.d("Battery ->", battery)

                    val power = jsonArray.getJSONObject(i).getString("power")
                    Log.d("Power ->", power)

                    val refill = jsonArray.getJSONObject(i).getString("refill")
                    Log.d("Refill ->", refill)

                    val urlPhoto = jsonArray.getJSONObject(i).getString("urlPhoto")
                    Log.d("urlPhoto ->", urlPhoto)

                    val model = Car(
                        id = id.toInt(),
                        price = price,
                        battery = battery,
                        power = power,
                        refill = refill,
                        urlPhoto = urlPhoto,
                        isFavorite = false
                    )
                    carsArray.add(model)
                }
                progress.isVisible = false
                noInternetImage.isVisible = false
                noInternetText.isVisible = false
                //  setupList()
            } catch (ex: Exception) {
                Log.e("Erro ->", ex.message.toString())
            }
        }
    }

    fun saveOnDataBase(car: Car) {
        val dbHelper = CarsDbHelper(requireContext())
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(
                COLUMN_NAME_PRICE, car.price
            )
            put(
                COLUMN_NAME_BATTERY, car.battery
            )
            put(
                COLUMN_NAME_POWER, car.power
            )
            put(
                COLUMN_NAME_REFILL, car.refill
            )
            put(
                COLUMN_NAME_URL_PHOTO, car.urlPhoto
            )
        }

        val newRegister = db?.insert(TABLE_NAME, null, values)
    }

}