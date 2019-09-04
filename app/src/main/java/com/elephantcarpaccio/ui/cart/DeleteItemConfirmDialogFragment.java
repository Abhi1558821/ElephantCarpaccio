package com.elephantcarpaccio.ui.cart;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.elephantcarpaccio.R;
import com.elephantcarpaccio.utils.Constants;

public class DeleteItemConfirmDialogFragment extends DialogFragment {

    private CartContract.DeleteListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final long personId = getArguments().getLong(Constants.ITEM_ID);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.are_you_sure);

        builder.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> listener.setConfirm(true, personId));

        builder.setNegativeButton(android.R.string.cancel, (dialogInterface, i) -> listener.setConfirm(false, personId));
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (CartContract.DeleteListener) context;
    }
}
