package com.example.project_lab_xml.ui.maps

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.project_lab_xml.BuildConfig
import com.example.project_lab_xml.databinding.FragmentMapsBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yandex.mapkit.Animation

import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView


class MapsFragment : Fragment() {

    private var _binding: FragmentMapsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var mapView: MapView
    private lateinit var fab: FloatingActionButton



    private val inputListener = object  : InputListener{
        override fun onMapTap(p0: Map, p1: Point) {
//            Log.w("gg","${p1.latitude}\n${p1.longitude}")
//
//            Toast.makeText(activity,"${p1.latitude}\n${p1.longitude}",Toast.LENGTH_SHORT).show()

        }
        override fun onMapLongTap(p0: Map, p1: Point) {
            Log.d("gg","${p1.latitude}/n${p1.longitude}")
            Toast.makeText(activity,"${p1.latitude}/n${p1.longitude}",Toast.LENGTH_SHORT).show()
        }
    }
    private val cameraListener = object : CameraListener{
        override fun onCameraPositionChanged(
            p0: Map,
            p1: CameraPosition,
            p2: CameraUpdateReason,
            p3: Boolean
        ) {
            fab.rotation = -p1.azimuth
        }

    }

    private fun checkDarkMod(): Boolean{
        val darkModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK// Retrieve the Mode of the App.
        return darkModeFlags == Configuration.UI_MODE_NIGHT_YES
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        mapView = binding.mapview
        fab = binding.fab
        mapView.map.addInputListener(inputListener)
        mapView.map.addCameraListener(cameraListener)
        mapView.map.isNightModeEnabled = checkDarkMod()
        fab.setOnClickListener {
            mapView.map.move(CameraPosition(
                mapView.map.cameraPosition.target,
                mapView.map.cameraPosition.zoom,
                0f,
                mapView.map.cameraPosition.tilt
            ),
            Animation(Animation.Type.SMOOTH, 0.2f),
            Map.CameraCallback{}
            )
        }
        return binding.root
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}