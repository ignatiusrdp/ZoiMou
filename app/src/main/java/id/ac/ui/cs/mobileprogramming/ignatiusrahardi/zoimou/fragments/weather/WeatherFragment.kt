package id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.fragments.weather

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.R
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.services.GetWeatherService
import id.ac.ui.cs.mobileprogramming.ignatiusrahardi.zoimou.viewmodels.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_weather.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class WeatherFragment : Fragment() {

    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        weatherViewModel =
                ViewModelProvider(this).get(WeatherViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_weather, container, false)


        weatherViewModel.getWeatherData.observe(viewLifecycleOwner, Observer { data ->
           if (data != null){
               weatherTemp.text = data.temp
               yourLocation.text = data.place
               timeUpdated.text = data.time
               weather.text = data.weather
               weatherDescMore.text = data.weatherDescription
               feelsLike.text =data.feelsLike
           }

        })

        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }


}