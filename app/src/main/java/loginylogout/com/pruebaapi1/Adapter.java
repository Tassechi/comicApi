package loginylogout.com.pruebaapi1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import loginylogout.com.pruebaapi1.models.Comic;

/**
 * Created by orimu on 15/05/2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<Comic> dataset;
    private Context context;

    public Adapter (Context context){
        this.context = context;
        dataset = new ArrayList<>();
    }


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mostrador, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        Comic c = dataset.get(position);
        holder.nombretexto.setText(c.getName());

        Glide.with(context)
                .load("https://static.comicvine.com/" + c.getNumber() + "png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.Imagen01);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void adicionarlistacomic(ArrayList<Comic> listaComic) {
        dataset.addAll(listaComic);
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView Imagen01;
        private TextView nombretexto;


        public ViewHolder(View itemView) {
            super(itemView);

            Imagen01 = (ImageView) itemView.findViewById(R.id.Imagen01);
            nombretexto = itemView.findViewById(R.id.nombretext);
        }
    }


}
