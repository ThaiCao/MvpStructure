package com.mvp.structure.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.mvp.structure.R;

/**
 * Created by thai.cao on 8/2/2019.
 */
public class RecyclerViewDividerItemDecoration  extends RecyclerView.ItemDecoration {
    private Drawable mDivider;

    public RecyclerViewDividerItemDecoration(Context context) {
        mDivider = context.getResources().getDrawable(R.drawable.recyclerview_devider_line);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}