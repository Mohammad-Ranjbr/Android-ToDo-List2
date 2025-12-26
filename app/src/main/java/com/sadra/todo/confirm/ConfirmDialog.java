package com.sadra.todo.confirm;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.sadra.todo.R;
import com.sadra.todo.listener.ConfirmListener;

public class ConfirmDialog extends DialogFragment {

    private boolean isDeleteAll;
    private ConfirmListener confirmListener;

    public ConfirmDialog(boolean isDeleteAll) {
        this.isDeleteAll = isDeleteAll;
    }

    public void setListener(ConfirmListener confirmListener) {
        this.confirmListener = confirmListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.confirm_dialog, null, false);
        builder.setView(view);

        View confirmButton = view.findViewById(R.id.btn_confirm_confirmDialog);
        View cancelButton = view.findViewById(R.id.btn_cancel_confirmDialog);
        TextView confirmDialogMessage = view.findViewById(R.id.tv_confirm_dialog_message);
        confirmDialogMessage.setText(isDeleteAll ? getString(R.string.delete_all_dialog_message) : getString(R.string.delete_item_dialog_message));
        confirmButton.setOnClickListener(v -> {
            confirmListener.onConfirmClick();
            dismiss();
        });
        cancelButton.setOnClickListener(v -> dismiss());

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }

}
