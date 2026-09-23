package com.example.unitconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Spinner categorySpinner;
    private Spinner fromSpinner;
    private Spinner toSpinner;
    private EditText inputValue;
    private Button convertButton;
    private TextView resultText;

    private final String[] categories = {
            "Length",
            "Weight",
            "Volume"
    };

    private final String[] lengthUnits = {
            "Centimetres",
            "Metres",
            "Kilometres",
            "Inches",
            "Feet"
    };

    private final String[] weightUnits = {
            "Grams",
            "Kilograms",
            "Pounds",
            "Ounces"
    };

    private final String[] volumeUnits = {
            "Millilitres",
            "Litres",
            "Gallons",
            "Cups"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Connect Java variables with XML views
        categorySpinner = findViewById(R.id.categorySpinner);
        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);
        inputValue = findViewById(R.id.inputValue);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        // Set category dropdown
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );

        categoryAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        categorySpinner.setAdapter(categoryAdapter);

        // Load default category
        updateUnitSpinners("Length");

        // Change units when category changes
        categorySpinner.setOnItemSelectedListener(
                new android.widget.AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            android.widget.AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        String selectedCategory =
                                categories[position];

                        updateUnitSpinners(selectedCategory);

                        // Reset previous result
                        resultText.setText(
                                "Your result will appear here"
                        );
                    }

                    @Override
                    public void onNothingSelected(
                            android.widget.AdapterView<?> parent) {
                    }
                }
        );

        // Convert button
        convertButton.setOnClickListener(v -> performConversion());
    }

    private void updateUnitSpinners(String category) {

        String[] units;

        switch (category) {

            case "Weight":
                units = weightUnits;
                break;

            case "Volume":
                units = volumeUnits;
                break;

            case "Length":
            default:
                units = lengthUnits;
                break;
        }

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                units
        );

        unitAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        fromSpinner.setAdapter(unitAdapter);
        toSpinner.setAdapter(unitAdapter);

        // Set different default source and target units
        if (units.length > 1) {
            fromSpinner.setSelection(0);
            toSpinner.setSelection(1);
        }
    }

    private void performConversion() {

        String input = inputValue.getText()
                .toString()
                .trim();

        // Empty input validation
        if (input.isEmpty()) {

            Toast.makeText(
                    this,
                    "Please enter a value",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        double value;

        // Numeric validation
        try {
            value = Double.parseDouble(input);

        } catch (NumberFormatException e) {

            Toast.makeText(
                    this,
                    "Please enter a valid number",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        String category =
                categorySpinner.getSelectedItem().toString();

        String fromUnit =
                fromSpinner.getSelectedItem().toString();

        String toUnit =
                toSpinner.getSelectedItem().toString();

        double result;

        switch (category) {

            case "Length":
                result = convertLength(
                        value,
                        fromUnit,
                        toUnit
                );
                break;

            case "Weight":
                result = convertWeight(
                        value,
                        fromUnit,
                        toUnit
                );
                break;

            case "Volume":
                result = convertVolume(
                        value,
                        fromUnit,
                        toUnit
                );
                break;

            default:
                return;
        }

        String formattedResult =
                formatResult(result);

        resultText.setText(
                String.format(
                        Locale.getDefault(),
                        "%s %s = %s %s",
                        input,
                        fromUnit,
                        formattedResult,
                        toUnit
                )
        );
    }

    // -------------------------------
    // LENGTH CONVERSION
    // Base unit: Metres
    // -------------------------------

    private double convertLength(
            double value,
            String from,
            String to) {

        double valueInMetres;

        switch (from) {

            case "Centimetres":
                valueInMetres = value / 100.0;
                break;

            case "Kilometres":
                valueInMetres = value * 1000.0;
                break;

            case "Inches":
                valueInMetres = value * 0.0254;
                break;

            case "Feet":
                valueInMetres = value * 0.3048;
                break;

            case "Metres":
            default:
                valueInMetres = value;
                break;
        }

        switch (to) {

            case "Centimetres":
                return valueInMetres * 100.0;

            case "Kilometres":
                return valueInMetres / 1000.0;

            case "Inches":
                return valueInMetres / 0.0254;

            case "Feet":
                return valueInMetres / 0.3048;

            case "Metres":
            default:
                return valueInMetres;
        }
    }

    // -------------------------------
    // WEIGHT CONVERSION
    // Base unit: Grams
    // -------------------------------

    private double convertWeight(
            double value,
            String from,
            String to) {

        double valueInGrams;

        switch (from) {

            case "Kilograms":
                valueInGrams = value * 1000.0;
                break;

            case "Pounds":
                valueInGrams = value * 453.59237;
                break;

            case "Ounces":
                valueInGrams = value * 28.349523125;
                break;

            case "Grams":
            default:
                valueInGrams = value;
                break;
        }

        switch (to) {

            case "Kilograms":
                return valueInGrams / 1000.0;

            case "Pounds":
                return valueInGrams / 453.59237;

            case "Ounces":
                return valueInGrams / 28.349523125;

            case "Grams":
            default:
                return valueInGrams;
        }
    }

    // -------------------------------
    // VOLUME CONVERSION
    // Base unit: Litres
    // -------------------------------

    private double convertVolume(
            double value,
            String from,
            String to) {

        double valueInLitres;

        switch (from) {

            case "Millilitres":
                valueInLitres = value / 1000.0;
                break;

            case "Gallons":
                valueInLitres = value * 3.785411784;
                break;

            case "Cups":
                valueInLitres = value * 0.2365882365;
                break;

            case "Litres":
            default:
                valueInLitres = value;
                break;
        }

        switch (to) {

            case "Millilitres":
                return valueInLitres * 1000.0;

            case "Gallons":
                return valueInLitres / 3.785411784;

            case "Cups":
                return valueInLitres / 0.2365882365;

            case "Litres":
            default:
                return valueInLitres;
        }
    }

    // Format result so unnecessary decimal zeros are removed
    private String formatResult(double result) {

        if (result == Math.floor(result)) {
            return String.format(
                    Locale.getDefault(),
                    "%.0f",
                    result
            );
        }

        return String.format(
                Locale.getDefault(),
                "%.4f",
                result
        );
    }
}