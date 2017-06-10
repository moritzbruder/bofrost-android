package de.moritzbruder.bofrost.product.activity;

import android.view.View;

import de.moritzbruder.bofrost.layout.SpecialTheme;

/**
 * Created by morit on 10.06.2017.
 */
public abstract class UserInteraction {

    abstract public int getImportance ();

    abstract public View getViewRepresentation (boolean friendHighlighted, SpecialTheme theme);

}