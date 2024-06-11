package io.shiftai.travelsmart.fragments.home.plan

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextPaint
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import io.shiftai.travelsmart.R
import io.shiftai.travelsmart.data.Plan
import io.shiftai.travelsmart.databinding.FragmentExploreBinding
import io.shiftai.travelsmart.databinding.FragmentWeatherBinding
import io.shiftai.travelsmart.util.hideBottomNavigationView
import io.shiftai.travelsmart.util.verticalGradientText
import org.json.JSONException
import org.json.JSONObject
import java.text.DecimalFormat

class WeatherFragment : Fragment() {
    private lateinit var binding: FragmentWeatherBinding
    private lateinit var df: DecimalFormat
    private val args by navArgs<WeatherFragmentArgs>()
    private val url = "https://api.openweathermap.org/data/2.5/weather"
    private val appid = "e53301e27efa0b66d05045d91b2742d3"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        df = DecimalFormat("#.##")
        val plan = args.plan
        getWeatherDetails(plan)

        binding.back.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable("plan", plan)
            }
            findNavController().navigate(R.id.action_weatherFragment_to_planFragment, bundle)
        }
    }

    private fun getWeatherDetails(plan: Plan) {
        val city = plan.destination
        val country = "PK"
        if (city.isEmpty()) {
            binding.tvResult.text = "City field can not be empty!"
        } else {
            val tempUrl = "$url?q=$city&appid=$appid"
            val stringRequest = StringRequest(
                Request.Method.POST, tempUrl,
                Response.Listener { response ->
                    var output = ""
                    try {
                        val jsonResponse = JSONObject(response)
                        val jsonArray = jsonResponse.getJSONArray("weather")
                        val jsonObjectWeather = jsonArray.getJSONObject(0)
                        val description = jsonObjectWeather.getString("description")
                        val jsonObjectMain = jsonResponse.getJSONObject("main")
                        val temp = jsonObjectMain.getDouble("temp") - 273.15
                        val feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15
                        val pressure = jsonObjectMain.getInt("pressure")
                        val humidity = jsonObjectMain.getInt("humidity")
                        val jsonObjectWind = jsonResponse.getJSONObject("wind")
                        val wind = jsonObjectWind.getString("speed")
                        val jsonObjectClouds = jsonResponse.getJSONObject("clouds")
                        val clouds = jsonObjectClouds.getString("all")
                        val jsonObjectSys = jsonResponse.getJSONObject("sys")
                        val countryName = jsonObjectSys.getString("country")
                        val cityName = jsonResponse.getString("name")
                        binding.tvResult.setTextColor(Color.WHITE)
                        output += "Current weather of $cityName ($countryName)"
                        output += "\n Temp: ${df.format(temp)} °C"
                        output += "\n Feels Like: ${df.format(feelsLike)} °C"
                        output += "\n Humidity: $humidity%"
                        output += "\n Description: $description"
                        output += "\n Wind Speed: $wind m/s (meters per second)"
                        output += "\n Cloudiness: $clouds%"
                        output += "\n Pressure: $pressure hPa"
                        binding.tvResult.text = output
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(requireContext(), error.toString().trim(), Toast.LENGTH_SHORT).show()
                }
            )
            val requestQueue: RequestQueue = Volley.newRequestQueue(requireContext())
            requestQueue.add(stringRequest)
        }
    }

    override fun onResume() {
        super.onResume()

        hideBottomNavigationView()
    }
}