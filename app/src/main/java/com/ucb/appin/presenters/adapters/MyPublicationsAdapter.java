package com.ucb.appin.presenters.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.Image;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ucb.appin.R;
import com.ucb.appin.data.model.AvisosBean;
import com.ucb.appin.presenters.IMyPublicationsPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyPublicationsAdapter extends RecyclerView.Adapter<MyPublicationsAdapter.CardViewHolder> {

    private final Context mainContext;
    private final List<AvisosBean> items;
    //private UtilDcompras utilDcompras;

    private IMyPublicationsPresenter iMyPublicationsPresenter;

    public MyPublicationsAdapter(Context mainContext, List<AvisosBean> items, IMyPublicationsPresenter iMyPublicationsPresenter) {
        this.mainContext = mainContext;
        this.items = items;
        //this.utilDcompras = new UtilDcompras();
        this.iMyPublicationsPresenter = iMyPublicationsPresenter;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_row_my_publications,parent,false);
        return new CardViewHolder(v,mainContext,items);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        AvisosBean item = items.get(position); //get item from my List<Place>
        holder.itemView.setTag(item); //save items in Tag

        holder.titulo.setText(item.getTitulo());
        holder.descripcion.setText(item.getDescripcion());
        holder.btnEdit.setTag(item);
        holder.btnDelete.setTag(item);

        try {
            if(!item.getImagen().isEmpty()){
                byte[] imaget64 = Base64.decode(item.getImagen(), Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(imaget64, 0, imaget64.length);

                Bitmap output = Bitmap.createBitmap(decodedImage.getWidth(), decodedImage
                        .getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(output);

                final int color = 0xff424242;
                final Paint paint = new Paint();
                final Rect rect = new Rect(0, 0, decodedImage.getWidth(), decodedImage.getHeight());
                final RectF rectF = new RectF(rect);
                final float roundPx = 1024;

                paint.setAntiAlias(true);
                canvas.drawARGB(0, 0, 0, 0);
                paint.setColor(color);
                canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(decodedImage, rect, rect, paint);

                holder.imgImage.setImageBitmap(output);
            }
        }catch (Exception error){}

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iMyPublicationsPresenter.showEditAviso((AvisosBean) view.getTag());
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iMyPublicationsPresenter.confirmDeleteAviso((AvisosBean) view.getTag());
            }
        });
    }


    @Override
    public int getItemCount() {
        int size = 0;
        if(items != null){
            size = items.size();
        }
        return size;
    }

    //class static
    class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.txt_title)
        TextView titulo;
        @BindView(R.id.txt_description)
        TextView descripcion;

        ImageButton btnEdit;
        ImageButton btnDelete;
        ImageView imgImage;

        private Context context;
        private List<AvisosBean> items = new ArrayList<AvisosBean>();

        public CardViewHolder(View v, Context context, List<AvisosBean> items){
            super(v);
            this.context = context;
            this.items = items;
            ButterKnife.bind(this, v); //with butterKnife

            this.titulo = v.findViewById(R.id.txt_title);
            this.descripcion = v.findViewById(R.id.txt_description);

            this.btnDelete = v.findViewById(R.id.btn_delete);
            this.btnEdit = v.findViewById(R.id.btn_edit);
            this.imgImage = v.findViewById(R.id.img_image);

            v.setOnClickListener(this); //click
        }

        //click within RecyclerView
        @Override
        public void onClick(View v) {
            int position= getAdapterPosition();
            AvisosBean avisosBean = this.items.get(position);
            //compraPresenter.modificarCompra(compra);
            //Snackbar.make(v.getRootView(), "Compra Realizada>>" + compra.getDescripcion(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }
}
