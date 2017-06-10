package de.moritzbruder.bofrost.user;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.moritzbruder.bofrost.R;
import de.moritzbruder.bofrost.user.User;

/**
 * Created by morit on 10.06.2017.
 */
public class UserView extends LinearLayout {

    ImageView imageViewProfile;
    TextView textViewName;

    private User user;

    public UserView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initComponent(context);
    }

    public void setUser(User user, Context context) {
        this.user = user;
        this.textViewName.setText(user.getName());
        Picasso.with(context).load(user.getIconUrl()).into(imageViewProfile);
    }

    private void initComponent(Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.user_view, null, false);
        imageViewProfile = (ImageView) v.findViewById(R.id.imageViewProfile);
        textViewName = (TextView) v.findViewById(R.id.textViewName);
        this.addView(v);

    }
}