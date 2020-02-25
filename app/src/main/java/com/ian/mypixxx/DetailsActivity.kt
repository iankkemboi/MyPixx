package com.ian.mypixxx

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ian.mypixxx.databinding.ActivityDetailsBinding
import daggerinjections.vmfactory.ViewModelFactory
import model.Hits
import ui.DetailsViewModel
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var hits: Hits
    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)

        val data = intent.extras
        val hitsresult = data.getParcelable<Hits>("hits")
        viewModel = ViewModelProvider(this, ViewModelFactory(this)).get(DetailsViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.bind(hitsresult)




    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
