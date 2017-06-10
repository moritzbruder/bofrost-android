package de.moritzbruder.bofrost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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