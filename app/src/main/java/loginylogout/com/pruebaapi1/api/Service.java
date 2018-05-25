package loginylogout.com.pruebaapi1.api;

import loginylogout.com.pruebaapi1.models.ComicRespuesta;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by orimu on 15/05/2018.
 */

public interface Service {

   @GET("comic")
   Call<ComicRespuesta>obtenerlistacomic(@Query("limit")int limit, @Query("offset")int offset );

}
