package com.trujca.mapoli.ui.category.view;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.firestore.FirebaseFirestore;
import com.trujca.mapoli.R;

import java.util.HashMap;
import java.util.Map;


public class AddCategoryDialog extends DialogFragment {
    NoticeDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(" must implement NoticeDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.add_category);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.new_category_modal, null);
        EditText text = view.findViewById(R.id.category_name_edit_text);

        builder.setView(view)
                .setPositiveButton(R.string.add, (dialog, id) -> {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    String categoryName = text.getText().toString();

                    Map<String, Object> category = new HashMap<>();
                    category.put("name", categoryName);

                    db.collection("categories").document("NAME")
                            .set(category)
                            .addOnSuccessListener(aVoid -> {
                                Log.d(TAG, "DocumentSnapshot successfully written! " + categoryName);
                                listener.onDialogPositiveClick(AddCategoryDialog.this);
                            })
                            .addOnFailureListener(e -> Log.w(TAG, "Error writing document", e));


                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> dismiss());
        // Create the AlertDialog object and return it
        return builder.create();
    }


    public interface NoticeDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
    }

}

