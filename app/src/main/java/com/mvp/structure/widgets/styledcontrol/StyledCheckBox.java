package com.mvp.structure.widgets.styledcontrol;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatCheckBox;

/**
 * Created by thai.cao on 8/1/2019.
 */

public class StyledCheckBox extends AppCompatCheckBox {
    private String font = FontConstants.REGULAR;

    public StyledCheckBox(Context context) {
        super(context);
    }

    public StyledCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public StyledCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        font = "fonts/" + FontConstants.REGULAR;

        if (getTypeface()!=null){
            int style = getTypeface().getStyle();
            if (style== Typeface.BOLD){
                font = "fonts/" + FontConstants.BOLD;
            }else if (style == Typeface.BOLD_ITALIC){
                font = "fonts/" + FontConstants.BOLD_ITALIC;
            }else if (style == Typeface.NORMAL){
                font = "fonts/" + FontConstants.REGULAR;
            }else if (style == Typeface.ITALIC){
                font = "fonts/" + FontConstants.ITALIC;
            }
        }else{
            font = "fonts/" + FontConstants.REGULAR;
        }

        setTypeface(TypefaceUtil.getTypeface(font,context));
        setIncludeFontPadding(false);
        float extraPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, context.getResources().getDisplayMetrics());
        setLineSpacing(extraPadding, 1);
    }
}
