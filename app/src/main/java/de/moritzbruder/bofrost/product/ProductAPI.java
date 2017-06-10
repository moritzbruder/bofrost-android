package de.moritzbruder.bofrost.product;

import java.util.List;

import de.moritzbruder.bofrost.product.activity.UserInteraction;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by morit on 10.06.2017.
 */
public interface ProductAPI {

    @GET("/product/{prodId}")
    Call<Product> loadProduct(@Path("prodId") String productId, @Header("User-Id") String userId);

    @GET("/product/{prodId}/completedChallenges")
    Call<List<UserInteraction>> loadInteractions(@Path("prodId") String productId, @Header("User-Id") String userId);

}