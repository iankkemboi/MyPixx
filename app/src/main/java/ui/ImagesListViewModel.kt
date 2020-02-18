package ui

import adapters.ImageListAdapter
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import base.BaseViewModel
import constants.PIXABY_API_KEY
import io.reactivex.Observable
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
    var queryString = "fruits"
    val errorClickListener = View.OnClickListener { loadPosts(queryString) }



    private lateinit var subscription: Disposable

    init{

        loadPosts(queryString)
    }

    private fun loadPosts(queryString: String?){
        var imgType = "photo"
        subscription = Observable.fromCallable { imgDao.all }
            .concatMap {
                    dbList ->
                if(dbList.isEmpty())
                    varimageapi.getPixbyResponse(PIXABY_API_KEY,queryString,imgType).concatMap {
                            apiPostList -> imgDao.insertAll(*apiPostList.hits.toTypedArray())
                        Observable.just(apiPostList)
                    }
                else
                    Observable.just(dbList)
            }
     //   subscription = varimageapi.getPixbyResponse(PIXABY_API_KEY,queryString,imgType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveListStart() }
            .doOnTerminate { onRetrieveListFinish() }
            .subscribe(
                { result -> onRetrieveListSuccess(result as List<Hits>) },
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

    private fun onRetrieveListSuccess(rsppojo: List<Hits>){
        imgListAdapter.updatePostList(rsppojo)
        Log.v("list gett", rsppojo[0].user)
    }

    private fun onRetrieveListError(err: Throwable?){
        errorMessage.value = err.toString()
        Log.e("Throwable error",err.toString())
        Log.v("list gett","FAIL")
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}
