package com.example.starwarsvehicles.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.starwarsvehicles.R
import com.example.starwarsvehicles.databinding.ActivityStarWarsVehiclesBinding
import com.example.starwarsvehicles.databinding.VehicleDetailsLayoutBinding
import com.example.starwarsvehicles.viewModel.StarWarsVehiclesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class StarWarsVehiclesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStarWarsVehiclesBinding
    private val viewModel: StarWarsVehiclesViewModel by viewModel()

    private val starships = mutableMapOf<Int, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_star_wars_vehicles)

        viewModel.starships.observe(this) {
            starships.putAll(it)
            addSpinners(starships.values.toList())
        }

        viewModel.starshipOne.observe(this) {
            bindStarship(binding.vehicleOne, it)
        }

        viewModel.starshipTwo.observe(this) {
            bindStarship(binding.vehicleTwo, it)
        }

        binding.compareHistoryButton.setOnClickListener {
            //open comparison history activity, not implemented yet
            Toast.makeText(this, getString(R.string.not_implemented_toast_text), Toast.LENGTH_LONG).show()
        }
    }

    private fun bindStarship(
        binding: VehicleDetailsLayoutBinding,
        vehicle: StarWarsVehiclesViewModel.Vehicle
    ) {
        binding.vehicleName.text = vehicle.name
        binding.vehicleModel.text = vehicle.model
        binding.vehicleManufacturer.text = vehicle.manufacturer
        binding.vehicleLength.text = vehicle.length
        binding.vehicleCrew.text = vehicle.crew
        binding.vehiclePassengers.text = vehicle.passengers
        binding.vehicleCargoCapacity.text = vehicle.cargoCapacity
        binding.vehicleHyperdriveRating.text = vehicle.hyperdriveRating
        binding.vehicleStarshipClass.text = vehicle.starshipClass
    }

    private fun addSpinners(spinnerValues: List<String>) {
        binding.firstSpinner.adapter = ArrayAdapter(
            this@StarWarsVehiclesActivity,
            android.R.layout.simple_spinner_item,
            spinnerValues
        )
        binding.firstSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    binding.vehicleOne.root.isVisible = true
                    viewModel.onFirstSpinnerSelected(starships.keys.elementAt(position))
                } else {
                    binding.vehicleOne.root.isVisible = false
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.secondSpinner.adapter = ArrayAdapter(
            this@StarWarsVehiclesActivity,
            android.R.layout.simple_spinner_item,
            spinnerValues
        )
        binding.secondSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    binding.vehicleTwo.root.isVisible = true
                    viewModel.onSecondSpinnerSelected(starships.keys.elementAt(position))
                } else {
                    binding.vehicleTwo.root.isVisible = false
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }


}