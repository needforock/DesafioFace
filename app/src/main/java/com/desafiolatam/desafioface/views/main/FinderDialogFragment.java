package com.desafiolatam.desafioface.views.main;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.desafiolatam.desafioface.R;
import com.desafiolatam.desafioface.network.users.GetUsers;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Soporte on 18-Oct-17.
 */

public class FinderDialogFragment extends DialogFragment {

    private FinderCallBack callBack;
    private String name;

    public FinderDialogFragment() {
    }

    public static FinderDialogFragment newInstance() {
        return new FinderDialogFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBack = (FinderCallBack) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_finder, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ImageButton button = (ImageButton) view.findViewById(R.id.searchBtn);
        final EditText editText = (EditText) view.findViewById(R.id.searchEt);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            name = editText.getText().toString();
                if(name.trim().length()>0) {
                    setCancelable(false);
                    button.setVisibility(View.INVISIBLE);
                    editText.setVisibility(View.INVISIBLE);
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("page", "1");
                    map.put("name", name);
                    new QueryUsers(-1, getContext()).execute(map);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );
    }

    private class QueryUsers extends GetUsers{


        public QueryUsers(int additionalPages, Context context) {
            super(additionalPages, context);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dismiss();
            callBack.search(name);
        }
    }



}


