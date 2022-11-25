package com.example.plannr.util;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.plannr.R;

public final class ButtonGenerator extends AppCompatActivity {
    private RelativeLayout relativeLayout;
    public static ButtonGenerator buttonGenerator;

    private ButtonGenerator() {

    }

    public static ButtonGenerator getInstance() {
        if(buttonGenerator == null)
            buttonGenerator = new ButtonGenerator();
        return buttonGenerator;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout = findViewById(R.id.relativeLayout);
    }

    public Button createButton() {
        Button button = new Button(this);
        button.setText("Dynamically created Button");
        button.setLayoutParams(new
                RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(ButtonGenerator.this, "This button is created dynamically",
                        Toast.LENGTH_SHORT).show();
            }
        });

        return button;
    }
}