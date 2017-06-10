package de.moritzbruder.bofrost.user;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by morit on 10.06.2017.
 */
public interface UserAPI {

    @GET("/user/{id}")
    Call<User> getUser(@Path("id") String userId);

    @GET("/leaderboard")
    Call<List<User>> getLeaderboard();

}