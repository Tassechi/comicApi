package loginylogout.com.pruebaapi1;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import loginylogout.com.pruebaapi1.api.Service;
import loginylogout.com.pruebaapi1.models.Comic;
import loginylogout.com.pruebaapi1.models.ComicRespuesta;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private static final String TAG = "COMIC";
    private RecyclerView recyclerView;
    private Adapter adapter;
    private int offset;
    private boolean aptoParaCargar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        adapter = new Adapter (this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0){
                    int visibleCount = layoutManager.getChildCount();
                    int totalitemCount = layoutManager.getItemCount();
                    int pastVisibleItens = layoutManager.findFirstCompletelyVisibleItemPosition();

                    if (aptoParaCargar){
                        if ((visibleCount + pastVisibleItens) >= totalitemCount){
                            Log.i(TAG,"Llegamos al final");

                        }
                    }
                }
            }
        });




        retrofit = new Retrofit.Builder()
                .baseUrl("https://comicvine.gamespot.com/api/characters/?api_key=1d897242143ed77df056bff22119c36027857038&format=json")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        aptoParaCargar = true;
        offset = 20;
        obtenerDatos(offset);


    }

    private void obtenerDatos(int offset) {
        Service service = retrofit.create(Service.class);
        Call<ComicRespuesta> comicRespuestaCall = service.obtenerlistacomic(20, offset);

        comicRespuestaCall.enqueue(new Callback<ComicRespuesta>() {
            @Override
            public void onResponse(Call<ComicRespuesta> call, Response<ComicRespuesta> response) {
                aptoParaCargar = true;
                if (response.isSuccessful()){

                    ComicRespuesta comicRespuesta = response.body();
                    ArrayList<Comic> listaComic = comicRespuesta.getResults();



                    adapter.adicionarlistacomic(listaComic);



                }else{

                    Log.e(TAG,"onResponse:" + response.errorBody());


                }


            }

            @Override
            public void onFailure(Call<ComicRespuesta> call, Throwable t) {
                aptoParaCargar = true;
                Log.e(TAG, "OnFailure:" + t.getMessage());



            }
        });




    }


}
