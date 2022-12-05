package com.example.plannr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.plannr.databinding.FragmentTableBinding;
import com.example.plannr.students.TableInputPresenter;
import com.example.plannr.students.TableMakerPresenter;

import java.util.ArrayList;


public class TableFragment extends Fragment {
    private FragmentTableBinding binding;
    private ArrayList<String> input;
    TableMakerPresenter presenter;
    LinearLayout linearLayout;

    public TableFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        input = TableInputPresenter.getInput();
        presenter = new TableMakerPresenter(this);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayout = view.findViewById(R.id.toAddLay);
        presenter.generateTable(input);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTableBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * adds new row to table
     * @param s String to be displayed under Session
     * @param c String to be displayed under Course
     */
    public void addNew(String s, String c) {
        View view = getLayoutInflater().inflate(R.layout.table_row, null);
        //find TextViews
        TextView course = view.findViewById(R.id.CoursesText);
        TextView session = view.findViewById(R.id.SessionsText);

        //set values
        course.setText(c);
        session.setText(s);

        //add new row
        linearLayout.addView(view);
    }
}