package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Play extends AppCompatActivity {
    private int as1, as2, result, resultTrue;
    private TextView mScoreView, mQuestion, mResult;
    private ImageButton mTrueButton, mFalseButton;
    private static final long COUNT_IN_MILLIS = 3000;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private boolean mAnswer;
    private int mScore = 0;
    SQLiteHandle sqLiteHandle;
    private int maxScore = 0;
    List<User> users = new ArrayList<>();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqLiteHandle = new SQLiteHandle(this);
        setContentView(R.layout.activity_play);

        progressBar = findViewById(R.id.progress);
        mScoreView = (TextView) findViewById(R.id.points);
        mQuestion = (TextView) findViewById(R.id.question);
        mResult = (TextView) findViewById(R.id.result);
        mTrueButton = findViewById(R.id.trueButton);
        mFalseButton = findViewById(R.id.falseButton);
        users.clear();
        this.users = getUsersHighScore();
        updateQuestion();
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mAnswer == true) {
                    countDownTimer.cancel();
                    mScore++;
                    updateScore(mScore);
                    updateQuestion();
                } else {
                    Intent i = new Intent(Play.this, ResultsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("finalScore", mScore);
                    bundle.putString("question", "" + as1 + "+" + as2);
                    bundle.putString("result", "=" + resultTrue);
                    if (users.size() > 0) {
                        if (mScore < users.get(0).getScore()){
                            bundle.putInt("maxScore", users.get(0).getScore());
                            createUser(mScore);
                            i.putExtras(bundle);
                            startActivity(i);
                            finish();
                        }else {
                            bundle.putInt("maxScore", mScore);
                            createUser(mScore);
                            i.putExtras(bundle);
                            startActivity(i);
                            finish();
                        }
                    } else {
                        bundle.putInt("maxScore", mScore);
                        createUser(mScore);
                        i.putExtras(bundle);
                        startActivity(i);
                        finish();
                    }
                }
            }
        });

        //Logic for false button
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAnswer == false) {
                    countDownTimer.cancel();
                    mScore++;
                    updateScore(mScore);
                    updateQuestion();
                } else {
                    Intent i = new Intent(Play.this, ResultsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("finalScore", mScore);
                    bundle.putString("question", "" + as1 + "+" + as2);
                    bundle.putString("result", "=" + resultTrue);
                    if (users.size() > 0) {
                        if (mScore < users.get(0).getScore()){
                            bundle.putInt("maxScore", users.get(0).getScore());
                            createUser(mScore);
                            i.putExtras(bundle);
                            startActivity(i);
                            finish();
                        }else {
                            bundle.putInt("maxScore", mScore);
                            createUser(mScore);
                            i.putExtras(bundle);
                            startActivity(i);
                            finish();
                        }
                    } else {
                        bundle.putInt("maxScore", mScore);
                        createUser(mScore);
                        i.putExtras(bundle);
                        startActivity(i);
                        finish();
                    }

                }
            }
        });
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                progressBar.setProgress((int) timeLeftInMillis);
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                Intent i = new Intent(Play.this, ResultsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("finalScore", mScore);
                bundle.putString("question", "" + as1 + "+" + as2);
                bundle.putString("result", "=" + resultTrue);
                if (users.size() > 0) {
                    if (mScore < users.get(0).getScore()){
                        bundle.putInt("maxScore", users.get(0).getScore());
                        createUser(mScore);
                        i.putExtras(bundle);
                        startActivity(i);
                        finish();
                    }else {
                        bundle.putInt("maxScore", mScore);
                        createUser(mScore);
                        i.putExtras(bundle);
                        startActivity(i);
                        finish();
                    }
                } else {
                    bundle.putInt("maxScore", mScore);
                    createUser(mScore);
                    i.putExtras(bundle);
                    startActivity(i);
                    finish();
                }

            }
        }.start();
    }

    private void updateQuestion() {
        Random rand = new Random();
        int a = rand.nextInt(50);
        this.as1 = a;
        int b = rand.nextInt(50);
        this.as2 = b;
        this.resultTrue = a+ b;
        mQuestion.setText(a + "+" + b);
        if (a % 2 == 0) {
            this.mResult.setText("=" + (a + b));
            this.result = (a + b);
            this.mAnswer = true;
        } else {
            int c = rand.nextInt(5) + 1;
            this.mResult.setText("=" + (a + b + c));
            this.result = (a + b + c);
            this.mAnswer = false;
        }
        timeLeftInMillis = COUNT_IN_MILLIS;
        startCountDown();

    }

    private void updateScore(int point) {
        mScoreView.setText("" + mScore);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private List<User> getUsersHighScore() {
        users.clear();
        List<User> users = sqLiteHandle.getAllUsers();
        if (users.size() == 0) {
            User user = new User(0);
            users.add(user);
        }
        return users;

    }

    private void createUser(int score) {
        User user = new User(score);
        long value = sqLiteHandle.addUser(user);
        if (value > 0) {
//            getAllUsers();
//            Log.d(String.valueOf("Thêm người dùng với điểm: "+ score), " Thành công");

        } else {
            Log.d("Thêm người dùng: ", "Thất bại");
        }
    }

    @Override
    public void onBackPressed() {
        askToClose();
    }

    private void askToClose() {
        countDownTimer.cancel();
        AlertDialog.Builder builder = new AlertDialog.Builder(Play.this);
        builder.setMessage("Are you sure you want to quit?");
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


}