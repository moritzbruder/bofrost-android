package de.moritzbruder.bofrost.user;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.moritzbruder.bofrost.R;
import de.moritzbruder.bofrost.product.Product;
import de.moritzbruder.bofrost.product.ProductAPI;
import de.moritzbruder.bofrost.user.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by morit on 10.06.2017.
 */
public class UserView extends LinearLayout {

    ImageView imageViewProfile;
    TextView textViewName;
    ProgressBar loadingSpinner;

    private User user;

    public UserView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initComponent(context);
    }

    public void setUser (User user, Context context) {
        loadingSpinner.setVisibility(View.GONE);
        this.user = user;
        textViewName.setText(user.getName());
        Picasso.with(context).load(user.getIconUrl()).into(imageViewProfile);
    }

    public void setUser(String userId, final Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.178.28:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserAPI api = retrofit.create(UserAPI.class);

        api.getUser(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                setUser(response.body(), context);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void initComponent(Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.user_view, null, false);
        imageViewProfile = (ImageView) v.findViewById(R.id.imageViewProfile);
        textViewName = (TextView) v.findViewById(R.id.textViewName);
        loadingSpinner = (ProgressBar) v.findViewById(R.id.loadingSpinner);
        this.addView(v);

    }
}