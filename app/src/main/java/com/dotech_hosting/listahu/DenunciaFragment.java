package com.dotech_hosting.listahu;

import android.media.Image;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dotech_hosting.listahu.models.Denuncia;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * A placeholder fragment containing a simple view.
 */
public class DenunciaFragment extends Fragment {

    public static final String DENUNCIA_ID = "denunciaId";

    @Bind(R.id.textViewNumero)
    TextView mNumero;
    @Bind(R.id.textViewMensaje)
    TextView mDescripcion;
    @Bind(R.id.imageViewTipoDenuncia)
    ImageView mIcono;
    @Bind(R.id.imageViewDenuncia)
    ImageView mFoto;
    private long mDenunciaId;

    public DenunciaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_denuncia, container, false);
        ButterKnife.bind(this, view);
        setupUI();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDenunciaId = getArguments().getLong(DENUNCIA_ID, 0);
    }

    private void setupUI() {
        Realm realm = Realm.getDefaultInstance();
        Denuncia denuncia = realm.where(Denuncia.class)
                .equalTo("id", mDenunciaId)
                .findFirst();

        if (denuncia != null) {
            mDescripcion.setText(denuncia.getDesc());
            mNumero.setText(denuncia.getNumero());

            Picasso.with(getContext())
                    .load(denuncia.getScreenshot())
                    .placeholder(R.drawable.not_available)
                    .into(mFoto, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                        }
                    });

            if (denuncia.getTipo().equals("Extorsión")) {
                mIcono.setImageResource(R.drawable.ic_icon_evil);
            } else {
                mIcono.setImageResource(R.drawable.ic_icon_warning);
            }
        }
        realm.close();
    }

    public static Fragment newInstance(long denunciaId) {
        DenunciaFragment denunciaFragment = new DenunciaFragment();
        Bundle args = new Bundle();
        args.putLong(DENUNCIA_ID, denunciaId);
        denunciaFragment.setArguments(args);
        return denunciaFragment;
    }
}
