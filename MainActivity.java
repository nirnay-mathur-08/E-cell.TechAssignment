package com.example.calc;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView ResultTv, SolutionTv;
    // You can keep these variable declarations if you plan to use them later,
    // but your current assignId/onClick logic doesn't require them.
    MaterialButton ButtonC, ButtonOpen, ButtonClose, ButtonDivide, Button9, Button8, Button7, ButtonAdd, Button6, Button5, Button4, ButtonMultiply, Button3, Button2, Button1, ButtonSub, ButtonAC, ButtonEquals, Button0, ButtonDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            // Find the TextViews
            ResultTv = findViewById(R.id.result_tv);
            SolutionTv = findViewById(R.id.solution_tv);

            // Assign IDs and listeners
            assignId(R.id.button_c);
            assignId(R.id.button_ac);
            assignId(R.id.button_0);
            assignId(R.id.button_1);
            assignId(R.id.button_2);
            assignId(R.id.button_3);
            assignId(R.id.button_4);
            assignId(R.id.button_5);
            assignId(R.id.button_6);
            assignId(R.id.button_7);
            assignId(R.id.button_8);
            assignId(R.id.button_9);
            assignId(R.id.button_add);
            assignId(R.id.button_sub);
            assignId(R.id.button_divide);
            assignId(R.id.button_multiply);
            assignId(R.id.button_openbracket);
            assignId(R.id.button_closebracket);
            assignId(R.id.button_dot);
            assignId(R.id.button_equals);

            return insets;
        });
    } // <-- onCreate() method ends here

    /**
     * Finds a button by its ID and sets this Activity as its OnClickListener.
     *
     * @param id The resource ID of the MaterialButton.
     */
    void assignId(int id) {
        // Find the button by its ID
        MaterialButton btn = findViewById(id);
        // Set the click listener *only if* the button was found
        if (btn != null) {
            btn.setOnClickListener(this);
        }
    }

    /**
     * This method is called when any view with this listener is clicked.
     */
    @Override
    public void onClick(View view) {
        // Cast the clicked View to a MaterialButton
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String datCal= SolutionTv.getText().toString();

        if(buttonText.equals("AC")){
            SolutionTv.setText("");
            ResultTv.setText("0");
            return;
        }
        else if(buttonText.equals("=")){
            SolutionTv.setText(ResultTv.getText());
            return;
        }
        else if (buttonText.equals("C")){
            datCal=datCal.substring(0,datCal.length()-1);
        }
        else {
            datCal= datCal+buttonText;
        }
        SolutionTv.setText(datCal);

        String finalResult=getResult(datCal);

        if (!finalResult.equals("Err")){
            ResultTv.setText(finalResult);
        }
    }

    String getResult(String data){
        data = data.replaceAll("×", "*");
        data = data.replaceAll("÷", "/");
        try {
            Context context=Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable=context.initStandardObjects();
            String finalResult= context.evaluateString(scriptable,data,"javascript",1,null).toString();

            if(finalResult.endsWith(".0")){
                finalResult=finalResult.replace(".0","");
            }

            return finalResult;
        }catch (Exception e){
            return "Err";
        }
    }

} // <-- MainActivity class ends here