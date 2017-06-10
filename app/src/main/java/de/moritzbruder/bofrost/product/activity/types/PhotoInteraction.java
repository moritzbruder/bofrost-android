package de.moritzbruder.bofrost.product.activity.types;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.moritzbruder.bofrost.R;
import de.moritzbruder.bofrost.layout.SpecialTheme;
import de.moritzbruder.bofrost.product.activity.UserInteraction;

/**
 * Created by morit on 10.06.2017.
 */
public class PhotoInteraction extends UserInteraction.InteractionType {

    private PhotoInteraction () {
        UserInteraction.types.add(this);
    }

    public static final PhotoInteraction shared = new PhotoInteraction();

    @Override
    public boolean is(String type) {
        return type.equals("photo");
    }

    @Override
    public View getViewRepresentation(UserInteraction interaction,String data, String image, boolean friendHighlighted, SpecialTheme theme, Context c) {
        Log.d("PhotoInteraction", "it is");
        LayoutInflater inflater = LayoutInflater.from(c);
        View v = inflater.inflate(R.layout.interaction_photo, null, false);
        ((TextView) v.findViewById(R.id.textViewChallenge)).setText(data);
        Picasso.with(c).load(image).into((ImageView) v.findViewById(R.id.imageViewChallenge));
        Log.d("image", image);
        return v;
    }
}