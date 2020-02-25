package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */

public class MainActivity extends AppCompatActivity {
    int quantity=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //increment quantity
    public void increment(View view) {
        if(quantity==100)
        {
            Toast.makeText(this, "You can't have more than 100 coffees!", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity++;
        displayQuanity(quantity);

    }
    //decrement quantity
    public void decrement(View view) {
        if(quantity==1)
        {
            Toast.makeText(this, "You can't have negative quantity!", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity--;
        displayQuanity(quantity);

    }

      //This method is called when the order button is clicked.

    public void submitOrder(View view) {
        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        // Calculate the price
        int price =calculatePrice(hasChocolate, hasWhippedCream);
        // Display the order summary on the screen
        Log.v("MainActivity", "The price is "+price);

        //Figure out Client Name
        EditText nameText = (EditText) findViewById(R.id.name_field);
        String nameValue = nameText.getText().toString();
        String priceMessage=createOrderSummary(price, hasWhippedCream,hasChocolate,  nameValue);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order for "+ nameValue);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }





    }

    /**
     * Calculates the price of the order.
     * @param  hasChocolate if client chose to have chocolate
     * @param hasWhippedCream if client chose to have whipped Cream
     * @return total price
     */
public int calculatePrice(boolean hasChocolate, boolean hasWhippedCream)
{
    int basePrice=5;

    if(hasWhippedCream)
        basePrice+=1;

    if(hasChocolate)
        basePrice+=2;

    int price=quantity *basePrice;
    return price;
}
    /**
     * Create summary of the order.
     * @param nameValue is thje name of the client
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @param price of the order
     * @return text summary
     */
private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String nameValue)
{


    String priceMessage =getString(R.string.order_summary_name, nameValue);
    priceMessage+= "\n"+getString(R.string.order_summary_whipped_cream,addWhippedCream);
    priceMessage+="\n" +getString(R.string.order_summary_chocolate,addChocolate);
    priceMessage+= "\n" +getString(R.string.order_summary_quantity,quantity);
    priceMessage+= "\n"+getString(R.string.order_summary_price,
            NumberFormat.getCurrencyInstance().format(price));
    priceMessage+= "\n"+getString(R.string.thank_you);

    return priceMessage;
}


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuanity(int numberOfCofees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCofees);
    }


}
