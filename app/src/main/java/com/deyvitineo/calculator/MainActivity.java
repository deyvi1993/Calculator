package com.deyvitineo.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private double results = 0.0;
    private TextView inputText;
    private TextView resultText;
    private String lastOperation = "";
    private boolean resultCalculated = false;
    private DecimalFormat format = new DecimalFormat("#.##");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputText = findViewById(R.id.inputText);
        resultText = findViewById(R.id.resultText);
        inputText.setText("");
        resultText.setText("");
    }

    // TODO: 9/10/2019 figure out a way so that only one operarator can be typed at once.
    // TODO: 9/11/2019 manage input so that its away from the results and simplify equation 
    public void onClickListener(View view){
        String buttonText = (String) ((Button) view).getText();

        //makes sure that if an operator is selected after the results are shown, it'll perform the math.
        if(resultCalculated && !isDigit(buttonText)){
            resultCalculated = false;
        }
        //this prevents the user typing an operator before a number. Needs to be updated to accept - operator. Currently working fine.
        if(inputText.getText().toString().equals("") && !isDigit(buttonText)){

        } else if(isDigit(buttonText) && resultCalculated){ //after the results are shown, if the user selects a number, it'd delete said results and set the view with the new number
            inputText.setText("");
            inputText.setText(inputText.getText() + buttonText);
            resultCalculated = false;
        } else if(isDigit(buttonText)){ //if input is a digit, it gets typed in the field and the operation is set to empty
            inputText.setText(inputText.getText() + buttonText);
            lastOperation = "";
        } else if(!isDigit(buttonText) && !buttonText.equals(lastOperation) && lastOperation.equals("")){ //if its an operation we check to see if it is equal to the last operation and proceed to set said last operation if its not.
            inputText.setText(inputText.getText().toString() + buttonText);
            lastOperation = buttonText;
        } else if(!isDigit(buttonText) && !buttonText.equals(lastOperation) && !lastOperation.equals("")){ //if its an operation we check to see if it is equal to the last operation and proceed to set said last operation if its not.
            String str = inputText.getText().toString().substring(0, inputText.getText().toString().length() - 1);
            System.out.println("POST " + str);
            inputText.setText(str + buttonText);
            lastOperation = buttonText;
        }  else if(!isDigit(buttonText) && buttonText.equals(lastOperation)){

        }

    }
    public void onEqualsClicked(View view){
        handleEquals(inputText.getText().toString());
        resultText.setText(String.valueOf(results));
        inputText.setText(String.valueOf(results));
        results = 0.0;
        lastOperation = "";
        resultCalculated = true;
    }
    public void onClear(View view){
        inputText.setText("");
        resultText.setText("");
        results = 0.0;
    }

    // TODO: 9/11/2019 rework the handle equals to do the math after 2 numbers and an operator are typed 
    public double handleEquals(String equation) {
        if (equation.contains("+")) {
            String[] numbers = equation.split("\\+");
            results = Double.parseDouble(numbers[0]);
            for (int i = 1; i < numbers.length; i++) {
                results += Double.parseDouble(numbers[i]);
            }
        } else if(equation.contains("-")) {
            String[] numbers = equation.split("-");
            results = Double.parseDouble(numbers[0]);
            for (int i = 1; i < numbers.length; i++) {
                results -= Double.parseDouble(numbers[i]);
            }
        } else if(equation.contains("x")) {
            String[] numbers = equation.split("x");
            results = Double.parseDouble(numbers[0]);
            for (int i = 1; i < numbers.length; i++) {
                results *= Double.parseDouble(numbers[i]);
            }
        } else if((equation.contains("/"))) {
                String[] numbers = equation.split("/");
            results = Double.parseDouble(numbers[0]);
                for (int i = 1; i < numbers.length; i++) {
                    results /= Double.parseDouble(numbers[i]);
                }
            }

        results = Double.parseDouble(format.format(results));
        return results;
    }

    public boolean isDigit(String str){
        if(str.matches(".*\\d.*")){
            return true;
        } else{
            return false;
        }
    }
}
