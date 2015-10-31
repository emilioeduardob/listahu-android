package com.dotech_hosting.listahu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.dotech_hosting.listahu.adapters.DenunciaAdapter;
import com.dotech_hosting.listahu.models.Denuncia;
import com.dotech_hosting.listahu.models.DenunciaWrapper;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class DenunciasActivity extends AppCompatActivity {

    @Bind(R.id.rvDenuncias)
    public RecyclerView rv;
    private DenunciaAdapter mAdapter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncias);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        mAdapter = new DenunciaAdapter(this);
        rv.setAdapter(mAdapter);
        Intent i = getIntent();
        String number = i.getStringExtra("numero");
        queryNumber(number);
    }

    private void queryNumber(String number) {
        RealmQuery<Denuncia> query = realm.where(Denuncia.class);
        if (number != null) {
            query = query.equalTo("numero", number);
        }
        RealmResults<Denuncia> data = query.findAll();
        mAdapter.setData(data);
    }
}
