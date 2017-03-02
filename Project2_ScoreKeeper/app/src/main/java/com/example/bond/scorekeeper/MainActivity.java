package com.example.bond.scorekeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PublicKey;

public class MainActivity extends AppCompatActivity {

    /* Team static */
    public static final int TEAM_A = 1;
    public static final int TEAM_B = 2;

    /* Runs static */
    public static final int SCORE_1 = 1;
    public static final int SCORE_2 = 2;
    public static final int SCORE_3 = 3;
    public static final int SCORE_4 = 4;
    public static final int SCORE_5 = 5;
    public static final int SCORE_6 = 6;

    /* TextView*/
    private TextView team_a_score = null;
    private TextView team_b_score = null;

    private TextView team_a_wickets = null;
    private TextView team_b_wickets = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Text views for score */
        team_a_score = (TextView) findViewById(R.id.team_a_score);
        team_b_score = (TextView) findViewById(R.id.team_b_score);

        /* Text view for wickets*/
        team_a_wickets = (TextView) findViewById(R.id.team_a_wickets);
        team_b_wickets = (TextView) findViewById(R.id.team_b_wickets);

        /* Score buttons for team A*/
        Button team_a_1 = (Button) findViewById(R.id.team_a_1);
        Button team_a_2 = (Button) findViewById(R.id.team_a_2);
        Button team_a_3 = (Button) findViewById(R.id.team_a_3);
        Button team_a_4 = (Button) findViewById(R.id.team_a_4);
        Button team_a_5 = (Button) findViewById(R.id.team_a_5);
        Button team_a_6 = (Button) findViewById(R.id.team_a_6);

        /* Score buttons for team B*/
        Button team_b_1 = (Button) findViewById(R.id.team_b_1);
        Button team_b_2 = (Button) findViewById(R.id.team_b_2);
        Button team_b_3 = (Button) findViewById(R.id.team_b_3);
        Button team_b_4 = (Button) findViewById(R.id.team_b_4);
        Button team_b_5 = (Button) findViewById(R.id.team_b_5);
        Button team_b_6 = (Button) findViewById(R.id.team_b_6);

        /* Wicket buttons for team A and B*/
        Button team_a_wicket_btn = (Button) findViewById(R.id.team_a_wicket_btn);
        Button team_b_wicket_btn = (Button) findViewById(R.id.team_b_wicket_btn);

        /* Reset button*/
        Button reset_btn = (Button) findViewById(R.id.reset);

        /* Set the click listeners on score buttons of team A */
        team_a_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyScore(TEAM_A, SCORE_1);
            }
        });

        team_a_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyScore(TEAM_A, SCORE_2);
            }
        });

        team_a_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyScore(TEAM_A, SCORE_3);
            }
        });

        team_a_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyScore(TEAM_A, SCORE_4);
            }
        });

        team_a_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyScore(TEAM_A, SCORE_5);
            }
        });

        team_a_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyScore(TEAM_A, SCORE_6);
            }
        });

        /* Set the click listeners on score buttons of team B */
        team_b_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyScore(TEAM_B, SCORE_1);
            }
        });

        team_b_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyScore(TEAM_B, SCORE_2);
            }
        });

        team_b_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyScore(TEAM_B, SCORE_3);
            }
        });

        team_b_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyScore(TEAM_B, SCORE_4);
            }
        });

        team_b_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyScore(TEAM_B, SCORE_5);
            }
        });

        team_b_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyScore(TEAM_B, SCORE_6);
            }
        });


        /* Set the click listener on wicket button of team A*/
        team_a_wicket_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyWicket(TEAM_A);
            }
        });
        /* Set the click listener on wicket button of team B*/
        team_b_wicket_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyWicket(TEAM_B);
            }
        });

        /* Click listener for the reset button*/
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team_a_score.setText("0");
                team_b_score.setText("0");
                team_a_wickets.setText("0");
                team_b_wickets.setText("0");
            }
        });
    }

    /* Modify the score of the game */
    private void modifyScore(int team, int score){
        String currScoreString;
        int currScoreInt;
        switch (team){
            case TEAM_A:
                currScoreString = team_a_score.getText().toString().trim();
                currScoreInt = Integer.parseInt(currScoreString);
                team_a_score.setText(String.valueOf(currScoreInt + score));
                break;

            case TEAM_B:
                currScoreString = team_b_score.getText().toString().trim();
                currScoreInt = Integer.parseInt(currScoreString);
                team_b_score.setText(String.valueOf(currScoreInt + score));
                break;

            default:
                return;
        }
        return;
    }

    /* Modify the wickets*/
    private void modifyWicket(int team){
        String currWicketString;
        int currWicketInt;
        switch (team){
            case TEAM_A:
                currWicketString = team_a_wickets.getText().toString().trim();
                currWicketInt = Integer.parseInt(currWicketString);
                if(currWicketInt < 10){
                    team_a_wickets.setText(String.valueOf(currWicketInt + 1));
                }
                else {
                    Toast.makeText(this,R.string.wicket_msg,Toast.LENGTH_SHORT).show();
                }
                break;

            case TEAM_B:
                currWicketString = team_b_wickets.getText().toString().trim();
                currWicketInt = Integer.parseInt(currWicketString);
                if(currWicketInt < 10){
                    team_b_wickets.setText(String.valueOf(currWicketInt + 1));
                } else {
                    Toast.makeText(this,R.string.wicket_msg,Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                return;
        }
        return;
    }
}
