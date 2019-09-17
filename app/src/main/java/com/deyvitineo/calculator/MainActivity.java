package com.deyvitineo.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private double results = 0;
    private TextView inputText;
    private TextView resultText;
    private String lastOperation = "";
    private boolean resultCalculated = false;
    private boolean operating = false;
    private DecimalFormat formatter = new DecimalFormat("#.##");
    private boolean divideByZero = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputText = findViewById(R.id.inputText);
        resultText = findViewById(R.id.resultText);
        inputText.setText("");
        resultText.setText("");
    }

    public void onClickListener(View view){
        String buttonText = (String) ((Button) view).getText();

        //Makes sure that if a number is pressed after the = button is pressed, it will start a new
        //calculation whereas if an operator is pressed, it will continue on that operation.
        if(resultCalculated && !isDigit(buttonText)){
            resultCalculated = false;
        }
        //If an operator is pressed before a digit, nothing happens (as intended for now).
        if(inputText.getText().toString().equals("") && !isDigit(buttonText)){
            //do nothing for now. Handle negatives in the future
        } else if(isDigit(buttonText)){ //if a digit is pressed, it'd decide whether it is a new operation or the same using the boolean above calculated;
            if(resultCalculated){ //new operation
                resultText.setText("");
                inputText.setText("");
                inputText.setText(inputText.getText() + buttonText);
                resultCalculated = false;
                operating = true;
            } else{ //same operation
                inputText.setText(inputText.getText() + buttonText);
                operating = true;
            }
        } else if(!isDigit(buttonText)){
            if(lastOperation.equals("")) { //adds the operator if none was added before
                inputText.setText(inputText.getText().toString() + buttonText);
                lastOperation = buttonText;
                operating = false;
            } else if(operating && buttonText.equals(lastOperation)) { //adds operator only if it is the same type that was added before. In other words, one operation can only handle one operator.
                inputText.setText(inputText.getText().toString() + buttonText);
                operating = false;
            }
        }
    }
    //Handler for the = button
    public void onEqualsClicked(View view){
        handleEquals(inputText.getText().toString());
        System.out.println(results);
        if(divideByZero == true){
            inputText.setText("Cannot divide by 0");
            resultText.setText(String.valueOf(formatter.format(results)));
            results = 0;
            lastOperation = "";
            resultCalculated = true;
            divideByZero = false;
        } else {
            resultText.setText(String.valueOf(formatter.format(results)));
            inputText.setText(String.valueOf(formatter.format(results)));
            results = 0;
            lastOperation = "";
            resultCalculated = true;
        }
    }
    //Handles the clear button. It sets all values to the default/0
    public void onClear(View view){
        inputText.setText("");
        resultText.setText("0");
        results = 0;
        lastOperation = "";
    }

    //This function handles the equation passed on after the = button is pressed.
    public double handleEquals(String equation) {
        if (equation.contains("+")) {
            String[] numbers = equation.split("\\+");
            results = Double.parseDouble(numbers[0]);
            for (int i = 1; i < numbers.length; i++) {
                results += Double.parseDouble(numbers[i]);
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
                    if(Double.parseDouble(numbers[i]) == 0){ //if a number is divided by 0, return 0 instead of crashing.
                        results = 0;
                        divideByZero = true;
                        return results;
                    } else{
                        results /= Double.parseDouble(numbers[i]);
                    }
                }
        } else if(equation.contains("-")) {
            String minusEquation;
            if (equation.charAt(0) == '-') {
                minusEquation = "0" + equation;
            } else {
                minusEquation = equation;
            }
            String[] numbers = minusEquation.split("-");
            results = Double.parseDouble(numbers[0]);
            for (int i = 1; i < numbers.length; i++) {
                results -= Double.parseDouble(numbers[i]);
            }
        }
        return results;
    }

    //This function takes a string and it returns true if it is a digit.
    // In this case, the strings passed will be 1 char long.
    public boolean isDigit(String str){
        return str.matches(".*\\d.*");
    }
}
