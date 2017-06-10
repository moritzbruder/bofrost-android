package de.moritzbruder.bofrost.product.activity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.moritzbruder.bofrost.ProductActivity;
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
public class ActivityAdapter extends BaseAdapter {

    String header = "HEADER";

    List<Object> list;
    Context context;
    String productId;

    public ActivityAdapter (String productId, List<UserInteraction> interactions, Context context) {
        list = new ArrayList<Object>();
        list.add(header);
        list.addAll(interactions);
        this.context = context;
        this.productId = productId;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (list.get(i) == header) {
            LayoutInflater inflater = LayoutInflater.from(context);
            final View v = inflater.inflate(R.layout.list_header, null, false);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.178.28:8000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ProductAPI api = retrofit.create(ProductAPI.class);

            api.loadProduct(productId, User.Session.getLoggedInId()).enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    Product product = response.body();
                    if (product != null) {
                        ((TextView) v.findViewById(R.id.textViewProductName)).setText(product.getName());
                        ((TextView) v.findViewById(R.id.textViewDescription)).setText(product.getDescription());
                        Log.d("image", product.getImageUrl());
                    }
                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {

                }
            });

            return v;
        } else return ((UserInteraction) list.get(i)).getView((i+1) == list.size(), false, null, context);
    }
}
