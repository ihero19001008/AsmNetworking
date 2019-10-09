package com.example.asmnetworking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.asmnetworking.Contants.API_KEY;
import static com.example.asmnetworking.Contants.USER_ID;

public class MainActivity extends AppCompatActivity {


    private List<Photo> photoList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadPhotoes();
    }
    public    void loadPhotoes(){
        AndroidNetworking.post("https://www.flickr.com/services/rest/")
                .addBodyParameter("method", "flickr.favorites.getList")
                .addBodyParameter("api_key", API_KEY)
                .addBodyParameter("user_id", USER_ID)
                .addBodyParameter("format", "json")
                .addBodyParameter("extras", "views, media, path_alias, url_sq, url_t, url_s, url_q, url_m, url_n, url_z, url_c, url_l, url_o")
                .addBodyParameter("nojsoncallback", "1")
                .addBodyParameter("per_page", "10")
                //.addBodyParameter("page", "10")
                //.addBodyParameter("page", "1")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(Fave.class, new ParsedRequestListener<Object>() {
                    @Override
                    public void onResponse(Object response) {
                        Fave fave = (Fave) response;
                        photoList =fave.getPhotos().getPhoto();
                        initRecycleView();

                    }
                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(MainActivity.this, anError.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void initRecycleView(){
        RecyclerView recyclerView = findViewById(R.id.rvShow);
        //LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        PhotoAdapter adapter = new PhotoAdapter(this,photoList);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

    }


}
