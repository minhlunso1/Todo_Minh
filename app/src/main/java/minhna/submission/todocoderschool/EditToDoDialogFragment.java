package minhna.submission.todocoderschool;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Administrator on 15-Feb-16.
 */
public class EditToDoDialogFragment extends DialogFragment {

    EditText edtEdit;
    Button btn;
    Button btn2;
    private int height;
    private int width;

    public interface EditToDoDialogFragmentListener {
        void onFinishEditDialog(long id, String inputText);
        void onFinishDeleteDialog(long id, int position);
    }

    public static EditToDoDialogFragment newInstance(long id, String toDo, int position) {
        EditToDoDialogFragment frag = new EditToDoDialogFragment();
        Bundle args = new Bundle();
        args.putString("toDo", toDo);
        args.putLong("id", id);
        args.putInt("position", position);
        frag.setArguments(args);

        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;

        return inflater.inflate(R.layout.fragment_edit, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn = (Button) view.findViewById(R.id.button);
        btn2 = (Button) view.findViewById(R.id.button2);
        edtEdit = (EditText) view.findViewById(R.id.edtEdit);
        String toDo = getArguments().getString("toDo", "");
        edtEdit.setText(toDo);
        edtEdit.setSelection(toDo.length());
        //getDialog().setTitle("Edit");
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(width / 2 + width / 3, height / 2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditToDoDialogFragmentListener listener = (EditToDoDialogFragmentListener) getActivity();
                listener.onFinishEditDialog(getArguments().getLong("id", -1), edtEdit.getText().toString());
                dismiss();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditToDoDialogFragmentListener listener = (EditToDoDialogFragmentListener) getActivity();
                listener.onFinishDeleteDialog(getArguments().getLong("id", -1), getArguments().getInt("position", -1));
                dismiss();
            }
        });
    }

}
