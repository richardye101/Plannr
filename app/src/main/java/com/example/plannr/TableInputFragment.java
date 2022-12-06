package com.example.plannr;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.plannr.databinding.FragmentTableInputBinding;
import com.example.plannr.presenters.TableInputPresenter;

public class TableInputFragment extends Fragment {

    private FragmentTableInputBinding binding;
    private static Context context;

    EditText tableInput;
    TextView testView;
    Button genButton;
    TableInputPresenter presenter;

    public TableInputFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getContext();
        presenter = new TableInputPresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTableInputBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tableInput = binding.tableInput;
        genButton = binding.genButton;
        testView = binding.testView;

        genButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.validate(getTableInput().toUpperCase(), TableInputFragment.this);
            }
        });
    }

    public static void toast(String error) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
    }

    public String getTableInput() {
        return tableInput.getText().toString();
    }

    public EditText getTableInputView() {
        return tableInput;
    }

    public TextView getTest() {
        return testView;
    }
}