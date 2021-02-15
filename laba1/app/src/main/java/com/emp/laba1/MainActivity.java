package com.emp.laba1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

import bsh.EvalError;
import bsh.Interpreter;

public class MainActivity extends AppCompatActivity {
    private Button button0, button1, button2, button3, button4, button5, button6,
            button7, button8, button9, buttonAdd, buttonSub, buttonDivision,
            buttonMul, buttonDot, buttonC, buttonEqual;
    private EditText result;
    private EditText expression;

    private boolean wasLastOperationAction = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button0 = findViewById(R.id.number_0);
        button1 = findViewById(R.id.number_1);
        button2 = findViewById(R.id.number_2);
        button3 = findViewById(R.id.number_3);
        button4 = findViewById(R.id.number_4);
        button5 = findViewById(R.id.number_5);
        button6 = findViewById(R.id.number_6);
        button7 = findViewById(R.id.number_7);
        button8 = findViewById(R.id.number_8);
        button9 = findViewById(R.id.number_9);
        buttonDot = findViewById(R.id.dot);
        buttonAdd = findViewById(R.id.plus);
        buttonSub = findViewById(R.id.minus);
        buttonMul = findViewById(R.id.multiply);
        buttonDivision = findViewById(R.id.divide);
        buttonC = findViewById(R.id.clear);
        buttonEqual = findViewById(R.id.equals);
        result = findViewById(R.id.result);
        expression = findViewById(R.id.expression);

        button0.setOnClickListener(v -> inputNumber("o"));
        button1.setOnClickListener(v -> inputNumber("1"));
        button2.setOnClickListener(v -> inputNumber("2"));
        button3.setOnClickListener(v -> inputNumber("3"));
        button4.setOnClickListener(v -> inputNumber("4"));
        button5.setOnClickListener(v -> inputNumber("5"));
        button6.setOnClickListener(v -> inputNumber("6"));
        button7.setOnClickListener(v -> inputNumber("7"));
        button8.setOnClickListener(v -> inputNumber("8"));
        button9.setOnClickListener(v -> inputNumber("9"));

        buttonAdd.setOnClickListener(v -> inputOperation('+'));
        buttonSub.setOnClickListener(v -> inputOperation('-'));
        buttonMul.setOnClickListener(v -> inputOperation('*'));
        buttonDivision.setOnClickListener(v -> inputOperation('/'));

        buttonDot.setOnClickListener(v -> {
            if (expression == null || expression.getText().toString().isEmpty()) {
                expression.setText("0.");
            } else {
                if (wasLastOperationAction) {
                    expression.setText(expression.getText().toString().concat("0."));
                } else {
                    expression.setText(expression.getText().toString().concat("."));
                }
            }
        });

        buttonEqual.setOnClickListener(v -> {
            String resultNumber = calculateResult(expression.getText().toString());
            result.setText(calculateResult(resultNumber));
            expression.setText(resultNumber);
        });

        buttonC.setOnClickListener(v -> {
            expression.setText("");
            result.setText("");
        });
    }

    private void inputNumber(String number) {
        expression.setText(expression.getText().toString() + number);
        result.setText(calculateResult(expression.getText().toString()));
        wasLastOperationAction = false;
    }

    private void inputOperation(char operation) {
        if (expression == null || expression.getText().toString().isEmpty()) {
            expression.setText("");
        } else {
            if (wasLastOperationAction) {
                expression.setText(replaceLastSymbol(expression.getText().toString(), operation));
            } else {
                expression.setText(expression.getText().toString().concat(String.valueOf(operation)));
            }
            wasLastOperationAction = true;
        }
    }

    private String replaceLastSymbol(String startingString, char symbol) {
        char[] symbols = startingString.toCharArray();
        symbols[symbols.length - 1] = symbol;
        return String.valueOf(symbols);
    }

    private String calculateResult(String expression) {
        String result;
        try {
            Interpreter interpreter = new Interpreter();
            interpreter.eval("result = " + expression);
            result = interpreter.get("result").toString();
        } catch (EvalError e) {
            result = "Invalid expression";
        }
        return result;
    }
}