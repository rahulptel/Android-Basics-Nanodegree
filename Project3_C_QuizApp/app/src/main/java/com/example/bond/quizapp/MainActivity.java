package com.example.bond.quizapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.StatFs;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    /* Variables to catch the view elements*/
    private Button mSubmit;
    private RadioGroup mRadioGroupQues1;
    private RadioGroup mRadioGroupQues2;
    private CheckBox mQues3A;
    private CheckBox mQues3B;
    private CheckBox mQues3C;
    private CheckBox mQues3D;
    private EditText mQues4;

    private RadioButton mQues1;
    private RadioButton mQues2;

    private static final String mQues4String = "stdio.h";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSubmit = (Button) findViewById(R.id.submit);
        /* Check all answers when the submit button is pressed*/
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Init variables*/
                boolean Ques1Status = false;
                boolean Ques2Status = false;
                boolean Ques3Status = false;
                boolean Ques4Status = false;
                int TotalMarks = 0;
                String resultString = "";

                /*Catch variables to check answers*/
                mRadioGroupQues1 = (RadioGroup) findViewById(R.id.ques_1_radio);
                mRadioGroupQues2 = (RadioGroup) findViewById(R.id.ques_2_radio);
                mQues3A = (CheckBox) findViewById(R.id.ques_3_A);
                mQues3B = (CheckBox) findViewById(R.id.ques_3_B);
                mQues3C = (CheckBox) findViewById(R.id.ques_3_C);
                mQues3D = (CheckBox) findViewById(R.id.ques_3_D);
                mQues4 = (EditText) findViewById(R.id.ques_4);

                /*Check the radio button for question 1*/
                int ques_1_id = mRadioGroupQues1.getCheckedRadioButtonId();
                if(ques_1_id > -1){
                    mQues1 = (RadioButton) findViewById(ques_1_id);
                    if (TextUtils.equals(mQues1.getText(),getString(R.string.ques_1_A))){
                        TotalMarks = TotalMarks + 1;
                        Ques1Status = true;
                    }
                }
                resultString = resultString + "Q.1: " + String.valueOf(Ques1Status)+ "\n" ;

                /*Check the radio button for question 2*/
                int ques_2_id = mRadioGroupQues2.getCheckedRadioButtonId();
                if(ques_2_id > -1){
                    mQues2 = (RadioButton) findViewById(ques_2_id);
                    if (TextUtils.equals(mQues2.getText(), getString(R.string.ques_2_A))){
                        TotalMarks = TotalMarks + 1;
                        Ques2Status = true;
                    }
                }
                resultString = resultString + "Q.2: " + String.valueOf(Ques2Status)+ "\n" ;

                /*Check the check box for question 3*/
                if(mQues3A.isChecked() && mQues3B.isChecked() && mQues3C.isChecked() && !mQues3D.isChecked()){
                    TotalMarks = TotalMarks + 1;
                    Ques3Status = true;
                }
                resultString = resultString + "Q.3: " + String.valueOf(Ques3Status)+ "\n" ;

                /*Check the EditText for question 4*/
                if(!TextUtils.isEmpty(mQues4.getText()) && TextUtils.equals(mQues4.getText(), mQues4String)){
                    TotalMarks = TotalMarks + 1;
                    Ques4Status = true;
                }
                resultString = resultString + "Q.4: " + String.valueOf(Ques4Status)+ "\n" ;
                resultString = resultString + "Total: " + Integer.toString(TotalMarks) + " out of 4";

                /* Generate the dialog box to display when the submit button is pressed */
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(resultString)
                        .setTitle(R.string.score);

                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        if(dialog != null){
                            dialog.dismiss();
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
        }});
    }
}
