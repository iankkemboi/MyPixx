package ui


import adapters.ImageListAdapter
import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import base.BaseViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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

    val imgListAdapter: ImageListAdapter = ImageListAdapter()
    val errorMessage:MutableLiveData<String> = MutableLiveData()

    private var queryString = "fruits"
    var searchtxt: ObservableField<String>? = null
    val errorClickListener = View.OnClickListener { loadPosts(queryString) }



    private lateinit var subscription: Disposable

    init{

        loadPosts(queryString)
    }



     fun loadPosts(queryString: String?){
        var imgType = "photo"
       /* subscription = Observable.fromCallable { imgDao.all }
            .concatMap {
                    dbList ->
                if(dbList.isEmpty())
                    varimageapi.getPixbyResponse(PIXABY_API_KEY,queryString,imgType).concatMap {
                            apiPostList -> imgDao.insertAll(*apiPostList.toTypedArray())
                        Observable.just(apiPostList)
                    }
                else
                    Observable.just(dbList)
            }*/
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
       /* Observable.just(imgDao.hitscount)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {result -> checkroomdb(result,hitsresp) },
                { error -> onRetrieveListError(error)})*/


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
        imgListAdapter.updatePostList(hitsresp)
        Log.v("list gett", rsppojo.hits[0].user)
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
        imgListAdapter.updatePostList(rmhitsresp)

    }
private fun checkroomdb(hitcnt: Int?, rmhitsresp: List<Hits>){
    Log.d("int cnt", hitcnt.toString())

       /* Observable.just(imgDao.insertAll(*rmhitsresp.toTypedArray()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( { Log.d("RxJava", "Insert SUCCESS")  },
                {error -> onRetrieveListError(error)})*/

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
