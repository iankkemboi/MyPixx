package com.ian.mypixxx

import adapters.ImageListAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ian.mypixxx.databinding.ActivityMainBinding
import daggerinjections.vmfactory.ViewModelFactory
import model.Hits
import model.RecyclerViewCallback
import ui.ImagesListViewModel
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


class MainActivity : AppCompatActivity(), RecyclerViewCallback {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ImagesListViewModel
    private var errorSnackbar: Snackbar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.imgrecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        viewModel = ViewModelProvider(this,ViewModelFactory(this)).get(ImagesListViewModel::class.java)

        viewModel.errorMessage.observe(this, Observer {
                errorMessage -> if(errorMessage != null) showError(errorMessage) else hideError()
        })
        binding.viewModel = viewModel

        val qryst = "fruits"
        viewModel.getHits(qryst)
            .observe(this,
                Observer<List<Hits>> { imgList ->

                    val imgadapter = ImageListAdapter(imgList,this@MainActivity)
                    imgadapter.setOnCallbackListener(this)
                    binding.imgrecycler.adapter = imgadapter
                })

       /* binding.imgrecycler.adapter = ImageListAdapter().apply {
            itemClick = { result ->
                Toast.makeText(applicationContext, result.user, Toast.LENGTH_SHORT).show()
            }

        }*/






        binding.search.isActivated = true
        binding.search.queryHint = "Type your keyword here"
        binding.search.onActionViewExpanded()
        binding.search.isIconified = false
        binding.search.clearFocus()

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override  fun onQueryTextSubmit(query: String?): Boolean {
               viewModel.loadPosts(query)

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }
        })





    }

  /*  fun onClick(view: View?, position: Int) {

        Toast.makeText(applicationContext, result.user, Toast.LENGTH_SHORT).show()
    }*/

   /*  override fun onItemClicked(hits: Hits) {
        Toast.makeText(this,"Hit comments ${hits.comments} \n Downloads:${hits.downloads}",Toast.LENGTH_LONG)
            .show()
        Log.i("USER_",hits.user)
    }*/
    private fun showError( errorMessage:String){
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError(){
        errorSnackbar?.dismiss()
    }

    override fun onRecycleViewItemClick(hits: Hits, position: Int) {


        val builder = AlertDialog.Builder(this@MainActivity)

        // Set the alert dialog title
        builder.setTitle("Image Listing")

        // Display a message on alert dialog
        builder.setMessage("Are you sure you want to see more details of this image listing")

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("YES"){dialog, which ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("hits", hits)
            startActivity(intent)
        }



        // Display a neutral button on alert dialog
        builder.setNeutralButton("Cancel"){_,_ ->

        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()


    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
