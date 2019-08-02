package com.mvp.structure.base;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.mvp.structure.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by thai.cao on 7/30/2019.
 */
public class BaseDialogFragment  extends DialogFragment {
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyleDialog();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        setCancelOnTouchOutSide(getDialog(), getCancelOnTouchOutSide());
    }

    protected void setUpDialogParams(Dialog dialog) {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        setUpDialogParams(dialog);
        return dialog;
    }

    protected boolean getCancelOnTouchOutSide() {
        return true;
    }

    protected void setStyleDialog() {
        setStyle(STYLE_NO_TITLE, R.style.AlertDialogTheme80);
    }

    public void setCancelOnTouchOutSide(Dialog dialog, boolean cancelOnTouchOutSide) {
        dialog.setCanceledOnTouchOutside(cancelOnTouchOutSide);
        dialog.setCancelable(cancelOnTouchOutSide);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}