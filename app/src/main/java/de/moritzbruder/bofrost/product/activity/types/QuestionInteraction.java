package de.moritzbruder.bofrost.product.activity.types;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import de.moritzbruder.bofrost.R;
import de.moritzbruder.bofrost.layout.SpecialTheme;
import de.moritzbruder.bofrost.product.activity.UserInteraction;

/**
 * Created by morit on 10.06.2017.
 */
public class QuestionInteraction extends UserInteraction.InteractionType {

    public static final QuestionInteraction shared = new QuestionInteraction();

    @Override
    public boolean is(String type) {
        return type.equals("question");
    }

    @Override
    public View getViewRepresentation(UserInteraction interaction, String data, String image, boolean friendHighlighted, SpecialTheme theme, Context context) {
        Log.d("QuestionInteraction", "it is");
        LayoutInflater inflater = LayoutInflater.from(context);
        final View v = inflater.inflate(R.layout.interaction_question, null, false);

        Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), "LinLib.ttf");

        TextView question = (TextView) v.findViewById(R.id.textViewQuestion);
        TextView answer = (TextView) v.findViewById(R.id.textViewAnswer);

        String[] split = data.split("\\|");
        String q = split[0];
        String a = split[1];

        question.setText(q);
        answer.setText(a);

        answer.setTypeface(myTypeface);

        return v;
    }
}
