package de.moritzbruder.bofrost.product;

import com.google.gson.annotations.SerializedName;

/**
 * Created by morit on 10.06.2017.
 */
public class Product {

    String description;
    String name;
    @SerializedName("image")
    String imageUrl;

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
