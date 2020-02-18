package com.ian.mypixxx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ian.mypixxx.databinding.ActivityMainBinding
import ui.ImagesListViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ImagesListViewModel
    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.imgrecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        viewModel = ViewModelProvider(this).get(ImagesListViewModel::class.java)

        viewModel.errorMessage.observe(this, Observer {
                errorMessage -> if(errorMessage != null) showError(errorMessage) else hideError()
        })
        binding.viewModel = viewModel
    }
    private fun showError( errorMessage:String){
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError(){
        errorSnackbar?.dismiss()
    }
}
