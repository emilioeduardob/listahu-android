package com.dotech_hosting.listahu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.dotech_hosting.listahu.adapters.DenunciaAdapter;
import com.dotech_hosting.listahu.models.DenunciaWrapper;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class DenunciasActivity extends AppCompatActivity {

    @Bind(R.id.rvDenuncias)
    public RecyclerView rv;
    private DenunciaAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncias);
        ButterKnife.bind(this);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        mAdapter = new DenunciaAdapter(this);
        rv.setAdapter(mAdapter);
        Intent i = getIntent();
        String number = i.getStringExtra("numero");
        queryNumber(number);
    }

    private void queryNumber(String number) {
        ApiService api = APIBackend.getApiInstance();
        Call<DenunciaWrapper> getDenuncias = api.getDenuncias(1, number);
        getDenuncias.enqueue(new Callback<DenunciaWrapper>() {
            @Override
            public void onResponse(Response<DenunciaWrapper> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    mAdapter.setData(response.body().results);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
