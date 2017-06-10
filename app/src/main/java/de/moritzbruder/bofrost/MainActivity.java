package de.moritzbruder.bofrost;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import de.moritzbruder.bofrost.level.Level;
import de.moritzbruder.bofrost.product.Product;
import de.moritzbruder.bofrost.product.ProductAPI;
import de.moritzbruder.bofrost.user.User;
import de.moritzbruder.bofrost.user.UserAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView textViewLevel;
    TextView textViewPoints2Levelup;
    CircularProgressBar progress;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(Color.WHITE);

        textViewLevel = (TextView) findViewById(R.id.textViewLevel);
        textViewPoints2Levelup = (TextView) findViewById(R.id.textViewPoints2Levelup);
        progress = (CircularProgressBar) findViewById(R.id.progressBarLevels);

        Intent i = new Intent(this, ProductActivity.class);
        startActivity(i);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.178.28:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserAPI api = retrofit.create(UserAPI.class);

        api.getUser("1").enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                setUser(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });


        findViewById(R.id.questionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Woohoo, los geht's!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, QuestionActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.scoreboard) {
            startActivity(new Intent(MainActivity.this, LeaderboardActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void setUser(User user) {
        this.user = user;
        if (this.user != null) {
            textViewLevel.setText(""+Level.getLevelByPoints(user.getPoints()));
            textViewPoints2Levelup.setText(""+Level.getPointsToNext(user.getPoints()));
            progress.setProgress(Level.getProgress(user.getPoints()));
        }
    }
}