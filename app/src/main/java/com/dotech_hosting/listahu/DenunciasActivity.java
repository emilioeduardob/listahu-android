package com.dotech_hosting.listahu;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SearchViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.denuncias_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                queryNumber(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                queryNumber(query);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

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
            query = query.beginsWith("numero", number);
        }
        RealmResults<Denuncia> data = query.findAll();
        mAdapter.setData(data);
    }
}
