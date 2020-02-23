package network

import io.reactivex.Observable
import model.Hits
import model.ResponsePOJO
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {
    /**
     * Get the list of the pots from the API
     */

  //  https://pixabay.com/api/?key=15301866-e6143038f3f970114927b938e&q=yellow+flowers&image_type=photo
    @GET("/api?")
    fun getPixbyResponse(@Query("key") key: String?,
                         @Query("q") q: String?,
                         @Query("image_type") image_type: String?): Observable<ResponsePOJO>
}