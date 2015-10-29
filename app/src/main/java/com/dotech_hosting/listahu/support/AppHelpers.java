package com.dotech_hosting.listahu.support;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dotech_hosting.listahu.MainApp;
import com.dotech_hosting.listahu.R;
import com.dotech_hosting.listahu.models.Denuncia;

/**
 * Created by emilio on 10/23/15.
 */
public class AppHelpers {
    private final Denuncia mDenuncia;

    public AppHelpers(Denuncia denuncia) {
       mDenuncia = denuncia;
    }

    public void showToast() {
        Toast toast = new Toast(MainApp.getContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setView(buildLayout());
        toast.show();
    }

    public View buildLayout() {
        Context context = MainApp.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.toast_warning, null);

        TextView textViewMessage = (TextView) layout.findViewById(R.id.textViewMensaje);
        ImageView imageViewIcono = (ImageView) layout.findViewById(R.id.imageIconType);

        textViewMessage.setText(buildMessage());
        imageViewIcono.setImageResource(buildImageResource());

        return layout;
    }

    private int buildImageResource() {
        if (mDenuncia.getTipo().equals("Extorsión")) {
            return R.drawable.ic_icon_evil;
        } else {
            return R.drawable.ic_icon_warning;
        }
    }

    private String buildMessage() {
        String message = "Atención! El número " + mDenuncia.getNumero();
        message += " esta denunciado como " + mDenuncia.getTipo();

        return message;
    }
}
