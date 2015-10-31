package com.dotech_hosting.listahu.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dotech_hosting.listahu.R;
import com.dotech_hosting.listahu.models.Denuncia;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

/**
 * Created by emilio on 10/29/15.
 */
public class DenunciaAdapter extends RecyclerView.Adapter<DenunciaAdapter.ViewHolder> {

    private static final String TAG = DenunciaAdapter.class.getSimpleName();
    private LayoutInflater mInflater;
    private List<Denuncia> mData;
    private Context mContext;

    public DenunciaAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void setData(List<Denuncia> data) {
        mData = data;
        this.notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = mInflater.inflate(R.layout.denuncia_list_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Denuncia denuncia = mData.get(position);
        holder.descripcion.setText(denuncia.getDesc());
        Picasso.with(mContext)
                .load(denuncia.getScreenshot())
                .placeholder(R.drawable.not_available)
                .into(holder.foto);
        if (denuncia.getTipo().equals("Extorsión")) {
            holder.icono.setImageResource(R.drawable.ic_icon_evil);
        } else {
            holder.icono.setImageResource(R.drawable.ic_icon_warning);
        }
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }

        return 0;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.textViewMensaje)
        public TextView descripcion;
        @Bind(R.id.imageViewDenuncia)
        public ImageView foto;
        @Bind(R.id.imageViewTipoDenuncia)
        public ImageView icono;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
