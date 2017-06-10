package de.moritzbruder.bofrost.product.activity.types;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.moritzbruder.bofrost.ProductActivity;
import de.moritzbruder.bofrost.R;
import de.moritzbruder.bofrost.layout.SpecialTheme;
import de.moritzbruder.bofrost.product.Product;
import de.moritzbruder.bofrost.product.ProductAPI;
import de.moritzbruder.bofrost.product.activity.UserInteraction;
import de.moritzbruder.bofrost.user.User;
import de.moritzbruder.bofrost.user.UserAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by morit on 10.06.2017.
 */
public class CombineInteraction extends UserInteraction.InteractionType {

    public static final CombineInteraction shared = new CombineInteraction();

    @Override
    public boolean is(String type) {
        return type.equals("combine");
    }

    @Override
    public View getViewRepresentation(final UserInteraction interaction, String data, String image, boolean friendHighlighted, SpecialTheme theme, final Context context) {
        Log.d("CombineInteraction", "it is");
        LayoutInflater inflater = LayoutInflater.from(context);
        final View v = inflater.inflate(R.layout.interaction_combine, null, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.178.28:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ProductAPI pApi = retrofit.create(ProductAPI.class);
        final UserAPI uApi = retrofit.create(UserAPI.class);

        pApi.loadProduct(data, User.Session.getLoggedInId()).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                final Product combiner = response.body();
                uApi.getUser(interaction.getUserid()).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        ((TextView) v.findViewById(R.id.textViewCaption)).setText("Schon probiert? " + response.body().getName() + " kombiniert seine " + interaction.getInteractionProduct().getName() + " mit " + combiner.getName() + "!");
                        ((TextView) v.findViewById(R.id.textViewPreview)).setText(combiner.getName());
                        Picasso.with(context).load(combiner.getImageUrl()).into((ImageView) v.findViewById(R.id.imageViewPreview));
                        v.findViewById(R.id.productLink).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(context, ProductActivity.class);
                                i.putExtra(ProductActivity.KEY_PRODUCT_ID, combiner.getId());
                                context.startActivity(i);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });
        return v;
    }
}
