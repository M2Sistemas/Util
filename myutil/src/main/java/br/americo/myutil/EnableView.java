package br.americo.myutil;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class EnableView {

    /**
     * @param view   campo
     * @param status ativo/inativo
     */
    public static void campos(View view, boolean status) {
        if (view instanceof EditText) {
            EditText edt = (EditText) view;
            edt.setEnabled(status);
            edt.setCursorVisible(status);
            edt.setFocusable(status);
            edt.setFocusableInTouchMode(status);
        } else if (view instanceof ImageButton) {
            ImageButton ibtn = (ImageButton) view;
            ibtn.setClickable(status);
            ibtn.setEnabled(status);
        } else if (view instanceof Button) {
            Button btn = (Button) view;
            btn.setClickable(status);
            btn.setEnabled(status);
        } else if (view instanceof Spinner) {
            Spinner spn = (Spinner) view;
            spn.setEnabled(status);
        }
    }
}