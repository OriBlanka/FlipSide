package com.flipsideMVP;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

public class LoadingDialog {

    private Fragment m_fragment;
    private AlertDialog m_dialog;

    public LoadingDialog(Fragment i_myFrag){m_fragment = i_myFrag;}

    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(m_fragment.getContext());

        LayoutInflater inflater = m_fragment.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));
        builder.setCancelable(true);

        m_dialog = builder.create();
        m_dialog.show();
    }

    public void dismissDialog(){m_dialog.dismiss();}
}
