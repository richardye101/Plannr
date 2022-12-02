package com.example.plannr.students;

import com.example.plannr.services.DatabaseConnection;

public class TableMakerPresenter {
    private DatabaseConnection db;

    TableMakerPresenter() {
        db = DatabaseConnection.getInstance();
    }



}
