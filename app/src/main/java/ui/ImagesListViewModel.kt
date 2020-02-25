package ui


import adapters.ImageListAdapter
import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import base.BaseViewModel
import constants.PIXABY_API_KEY
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import model.Hits
import model.ResponsePOJO
import model.db.ImgDao
import network.ImageApi
import javax.inject.Inject


class ImagesListViewModel(private val imgDao: ImgDao): BaseViewModel(){
    @Inject
    lateinit var varimageapi: ImageApi
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    private var respse: MutableLiveData<List<Hits>>? = null



    val errorMessage:MutableLiveData<String> = MutableLiveData()

    private var queryString = "fruits"
    var searchtxt: ObservableField<String>? = null
    val errorClickListener = View.OnClickListener { loadPosts(queryString) }



    private lateinit var subscription: Disposable

    init{

      //  loadPosts(queryString)
    }

    fun getHits(queryst: String?): LiveData<List<Hits>> { //if the list is null
        if (respse == null) {
            respse = MutableLiveData<List<Hits>>()
            //we will load it asynchronously from server in this method
            loadPosts(queryst)
        }
        //finally we will return the list
        return respse as MutableLiveData<List<Hits>>
    }

     fun loadPosts(queryString: String?){
        var imgType = "photo"

      subscription = varimageapi.getPixbyResponse(PIXABY_API_KEY,queryString,imgType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveListStart() }
            .doOnTerminate { onRetrieveListFinish() }
            .subscribe(
                { result -> onRetrieveListSuccess(result) },
                {error -> onRetrieveListError(error) }
            )
    }

    private fun onRetrieveListStart(){
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveListFinish(){
        loadingVisibility.value = View.GONE

    }

    private  fun onRetrieveListSuccess(rsppojo: ResponsePOJO){

        val hitsresp = rsppojo.hits


        Single.fromCallable {
            // need to return a non-null object, since Rx 2 doesn't allow nulls
            imgDao.hitscount
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
               { result ->
                   checkroomdb(result,hitsresp)
               },
               { error ->
                    Log.e(TAG, "Couldn't read values from database", error)
                }
            )

        respse?.value = hitsresp
        Log.v("image tags",hitsresp[19].tags)

    }

    private fun onRetrieveListError(err: Throwable?){

        Single.fromCallable {
            // need to return a non-null object, since Rx 2 doesn't allow nulls
            imgDao.all
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    poplistfromdb(result)
                },
                { error ->
                    Log.e(TAG, "Couldn't read values from database", error)
                }
            )

        Log.e("Throwable error",err.toString())
      //  println(dbList)
    }

    private fun poplistfromdb(rmhitsresp: List<Hits>){
        Log.v("Success","Successfully saved to db")
        respse?.value = rmhitsresp

    }
private fun checkroomdb(hitcnt: Int?, rmhitsresp: List<Hits>){
    Log.d("int cnt", hitcnt.toString())



if(hitcnt?.compareTo( 0)!! < 1) {
    Single.fromCallable {
        // need to return a non-null object, since Rx 2 doesn't allow nulls
        imgDao.insertAll(*rmhitsresp.toTypedArray())
    }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            { result ->
                Log.e(TAG, "Successfully written to database")

            },
            { error ->
                Log.e(TAG, "Couldn't read User from database", error)
            }
        )
}


}


    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }


}
