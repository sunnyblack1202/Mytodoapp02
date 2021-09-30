package com.example.mytodoapp2;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DeleteConfirmDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.dialog_title_delete);
        builder.setMessage(R.string.dialog_msg_delete);
        builder.setPositiveButton(R.string.dialog_btn_ok, new DialogButtonClickListener());
        builder.setNegativeButton(R.string.dialog_btn_ng, new DialogButtonClickListener());

        AlertDialog dialog = builder.create();
        return dialog;
    }

    private class DialogButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String msg = "";

            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //TODO
                    // 削除
                    msg = getString(R.string.dialog_ok_delete_toast);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    //TODO
                    // キャンセル
                    msg = getString(R.string.dialog_ng_delete_toast);
                    break;
            }

            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
        }
    }
}