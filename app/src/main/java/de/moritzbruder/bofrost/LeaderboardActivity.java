package de.moritzbruder.bofrost;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.moritzbruder.bofrost.product.ProductAPI;
import de.moritzbruder.bofrost.user.User;
import de.moritzbruder.bofrost.user.UserAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setTitleTextColor(Color.WHITE);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.178.28:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserAPI api = retrofit.create(UserAPI.class);

        api.getLeaderboard().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> scoreboard = response.body();
                ((ListView) findViewById(R.id.scoreboard)).setAdapter(new Adapter(scoreboard, LeaderboardActivity.this));
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return true;
    }

    class Adapter extends BaseAdapter {

        List<User> scoreboard;
        Context context;

        public Adapter (List<User> scoreboard, Context context) {
            this.scoreboard = scoreboard;
            this.context = context;
        }

        @Override
        public int getCount() {
            return scoreboard.size();
        }

        @Override
        public Object getItem(int i) {
            return scoreboard.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(context);
            final View v = inflater.inflate(R.layout.list_item_leaderboard, null, false);

            TextView name = (TextView) v.findViewById(R.id.textViewName);
            TextView number = (TextView) v.findViewById(R.id.textViewNumber);
            ImageView icon = (ImageView) v.findViewById(R.id.imageView);

            User u  = scoreboard.get(i);
            name.setText(u.getName());
            number.setText((i + 1) + ".");
            Picasso.with(context).load(u.getIconUrl()).into(icon);

            float scale = 1.0f;

            if (i == 0) scale = 1.6f;
            if (i == 1) scale = 1.4f;
            if (i == 2) scale = 1.2f;

            //BIIG
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) (icon.getLayoutParams());
            int h = (int) (scale * params.height);
            params.height = h;
            params.width = h;
            Log.d("h", "" + h);
            icon.setLayoutParams(params);

            return v;
        }
    }
}