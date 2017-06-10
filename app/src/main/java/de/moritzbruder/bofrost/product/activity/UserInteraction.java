package de.moritzbruder.bofrost.product.activity;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

import de.moritzbruder.bofrost.R;
import de.moritzbruder.bofrost.layout.SpecialTheme;
import de.moritzbruder.bofrost.product.Product;
import de.moritzbruder.bofrost.product.activity.types.CombineInteraction;
import de.moritzbruder.bofrost.product.activity.types.FavoriteInteraction;
import de.moritzbruder.bofrost.product.activity.types.PhotoInteraction;
import de.moritzbruder.bofrost.product.activity.types.QuestionInteraction;
import de.moritzbruder.bofrost.user.User;
import de.moritzbruder.bofrost.user.UserView;

/**
 * Created by morit on 10.06.2017.
 */
public class UserInteraction {

    public static final ArrayList<InteractionType> types = new ArrayList<>();

    public static abstract class InteractionType {

        public abstract boolean is (String type);

        abstract public View getViewRepresentation (UserInteraction interaction, String data, String image, boolean friendHighlighted, SpecialTheme theme, Context context);

    }

    private Product interactionProduct;

    public void setInteractionProduct(Product interactionProduct) {
        this.interactionProduct = interactionProduct;
    }

    public Product getInteractionProduct() {
        return interactionProduct;
    }

    public String getUserid() {
        return userid;
    }

    String userid;
    public String type;
    String data;
    String image;

    public View getView (boolean listEnd, boolean friendHighlighted, SpecialTheme theme, Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.list_item_user_interaction, null, false);
        FrameLayout content = (FrameLayout) v.findViewById(R.id.contentView);
        content.addView(getViewRepresentation(data, friendHighlighted, theme, context));
        UserView userView = (UserView) v.findViewById(R.id.userView);
        userView.setUser(userid, context);
        if (listEnd) v.findViewById(R.id.lineEnder).setVisibility(View.VISIBLE);

        return v;
    }

    private View getViewRepresentation (String data, boolean friendHighlighted, SpecialTheme theme, Context context) {
        View v = new View(context);

        if (PhotoInteraction.shared.is(type)) {
            v = PhotoInteraction.shared.getViewRepresentation(this, data, image, friendHighlighted, theme, context);
        } else if (CombineInteraction.shared.is(type)) {
            v = CombineInteraction.shared.getViewRepresentation(this, data, image, friendHighlighted, theme, context);
        } else if (QuestionInteraction.shared.is(type)) {
            v = QuestionInteraction.shared.getViewRepresentation(this, data, image, friendHighlighted, theme, context);
        } else if (FavoriteInteraction.shared.is(type)) {
            v = FavoriteInteraction.shared.getViewRepresentation(this, data, image, friendHighlighted, theme, context);
        }

        v.setBackgroundColor(Color.TRANSPARENT);

        return v;
    }

}