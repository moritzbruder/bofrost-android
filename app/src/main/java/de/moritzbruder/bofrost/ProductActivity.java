package de.moritzbruder.bofrost;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.moritzbruder.bofrost.product.Product;
import de.moritzbruder.bofrost.product.ProductAPI;
import de.moritzbruder.bofrost.product.activity.ActivityAdapter;
import de.moritzbruder.bofrost.product.activity.UserInteraction;
import de.moritzbruder.bofrost.user.User;
import de.moritzbruder.bofrost.user.UserView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductActivity extends AppCompatActivity {

    public static final String KEY_PRODUCT_ID = "product_id";

    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("");

        Bundle extras = getIntent().getExtras();
        String loaded = "644";
        if (extras != null) loaded = extras.getString(KEY_PRODUCT_ID);

        final String productId = loaded;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.178.28:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProductAPI api = retrofit.create(ProductAPI.class);

        api.loadProduct(productId, User.Session.getLoggedInId()).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                product = response.body();
                Picasso.with(ProductActivity.this).load(product.getImageUrl()).into(((ImageView) findViewById(R.id.imageViewProductThumbnail)));
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });

        api.loadInteractions(productId, User.Session.getLoggedInId()).enqueue(new Callback<List<UserInteraction>>() {
            @Override
            public void onResponse(Call<List<UserInteraction>> call, Response<List<UserInteraction>> response) {
                Log.d("interactions", "loaded");
                List<UserInteraction> list = response.body();
                if (list != null) {
                    while (product == null) {
                        //wait
                    }
                    for (UserInteraction i: list) {
                        Log.d("interaction", i.type);
                        i.setInteractionProduct(product);
                    }
                    ActivityAdapter adapter = new ActivityAdapter(productId, list, ProductActivity.this);
                    ((ListView) findViewById(R.id.activityFeed)).setAdapter(adapter);
                    ((ListView) findViewById(R.id.activityFeed)).setOnScrollChangeListener(new View.OnScrollChangeListener() {
                        @Override
                        public void onScrollChange(View view, int i, int scY, int i2, int i3) {
                            Log.d("scY", scY + "-" + i + "-" + i2 + "-" + i3);
                            float progress = scY / 500.0f;
                            if (progress > 1) progress = 1.0f;
                            toolbar.setBackgroundColor(Color.argb((int) progress * 255, 22, 38, 119));
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<UserInteraction>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product, menu);
        return true;
    }
}
