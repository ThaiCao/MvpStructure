package com.mvp.structure.widgets.styledcontrol;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.tabs.TabLayout;
import com.mvp.structure.R;

/**
 * Created by thai.cao on 8/1/2019.
 */

public class StyledTabLayout extends TabLayout {

    public StyledTabLayout(Context context) {
        super(context);
        init(context, null);
    }

    public StyledTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StyledTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        setTabTextColors(Color.BLACK, Color.BLACK);
        final Typeface normalTypeFace = TypefaceUtil.getTypeface("fonts/" + FontConstants.REGULAR, getContext());
        final Typeface boldTypeFace = TypefaceUtil.getTypeface("fonts/" + FontConstants.BOLD, getContext());
        addOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {
                TextView text = (TextView) tab.getCustomView();
                text.setTypeface(boldTypeFace);
            }

            @Override
            public void onTabUnselected(Tab tab) {
                TextView text = (TextView) tab.getCustomView();
                text.setTypeface(normalTypeFace);
            }

            @Override
            public void onTabReselected(Tab tab) {

            }
        });
        setSelectedTabIndicatorColor(Color.BLACK);
        setBackgroundResource(R.drawable.tab_indicator_color);
    }

    @Override
    public void addTab(@NonNull Tab tab) {
        StyledTextView styledTextView = new StyledTextView(getContext());
        styledTextView.setText(tab.getText());
        styledTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        styledTextView.setTextColor(Color.BLACK);
        styledTextView.setGravity(GRAVITY_CENTER);
        tab.setCustomView(styledTextView);
        super.addTab(tab);
    }
}
