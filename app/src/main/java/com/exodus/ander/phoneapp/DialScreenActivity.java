package com.exodus.ander.phoneapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class DialScreenActivity extends AppCompatActivity {

    private ImageButton callButton;
    private TextView dialedNumberTextView;
    public static final String ARRAYINDEX = "array_index_for_this_number";

    private int dialCount = 0;
    private String initialThree="";
    private String middleThree="";
    private String finalFour="";



    //This is for keeping track of the full number that has been entered without formatting
    private String currentString="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial_screen);
        this.dialedNumberTextView = findViewById(R.id.tv_dialed_number);
        this.callButton = findViewById(R.id.btn_call);
        this.callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Checks to see if the number is included in the contacts
                int arrayIndex = ValidContactListSingleton.getInstance().isValidNumber(currentString);
                if(arrayIndex!=-1) {
                    Intent i = new Intent(DialScreenActivity.this, CallScreenActivity.class);
                    i.putExtra(ARRAYINDEX, arrayIndex);
                    clearDisplay();
                    currentString="";
                    startActivity(i);
                }
                else{
                    Toast.makeText(DialScreenActivity.this, "Number Invalid", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    public void onNumberButtonClick(View view){

        Button numberButtonClicked = (Button) view;
        String numberClicked = (String) numberButtonClicked.getText();

        if (dialCount < 3)
        {
            initialThree = initialThree + numberClicked;
            ++dialCount;
            setDisplay();
        }
        else if(dialCount < 6)
        {
            middleThree = middleThree+numberClicked;
            ++dialCount;
            setDisplay();
        }
        else if (dialCount < 10)
        {
            finalFour = finalFour+numberClicked;
            ++dialCount;
            setDisplay();
        }
        //Will only concatenate the next number if less than 10 numbers have been entered
        /*if (currentText.length()<10)
        {
            Button button = (Button) view;
            String number = button.getText().toString();
            currentText = currentText + number;
            dialedNumberTextView.setText(currentText);
        }
        else
        {
            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(50); //You can manage the time of the blink with this parameter
            anim.setStartOffset(20);
            anim.setRepeatCount(2);
            dialedNumberTextView.startAnimation(anim);
        }*/
    }

    //TODO: fix
    public void onDeleteButtonClick(View view)
    {
        if (dialCount!=0) {
            if (dialCount < 4)
            {
                initialThree = initialThree.substring(0, dialCount-1);
            }
            else if (dialCount < 7)
            {
                middleThree = middleThree.substring(0,dialCount-4);
            }
            else {
                finalFour = finalFour.substring(0, dialCount-7);
            }
            currentString = currentString.substring(0, dialCount - 1);
            dialCount--;
            setDisplay();
        }
    }

    public void setDisplay(){
        if (dialCount<=3) {
            dialedNumberTextView.setText(initialThree);
            currentString = initialThree;
        }
        else if (dialCount<=6) {
            String newText = initialThree + "-" + middleThree;
            dialedNumberTextView.setText(newText);
            currentString = initialThree + middleThree;
        }
        else if (dialCount<=10){
            String newText = "("+initialThree+")"+middleThree+"-"+finalFour;
            dialedNumberTextView.setText(newText);
            currentString = initialThree+middleThree+finalFour;
        }
    }

    public void clearDisplay(){
        dialCount = 0;
        initialThree="";
        middleThree="";
        finalFour="";
        dialedNumberTextView.setText("");
    }




}
