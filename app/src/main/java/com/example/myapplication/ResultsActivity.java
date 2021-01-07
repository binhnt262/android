package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {
    TextView mFinalScore;
    private TextView mQuestion, mResult, mBestScore, mCurrentScore;
    ImageView replay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        mFinalScore = (TextView)findViewById(R.id.points);
        mQuestion = (TextView) findViewById(R.id.question);
        mResult = (TextView) findViewById(R.id.result);
        replay = findViewById(R.id.replay);
        mBestScore = findViewById(R.id.edtBestScore);
        mCurrentScore = findViewById(R.id.edtCurrentScore);
        Bundle bundle = getIntent().getExtras();
        int score = bundle.getInt("finalScore");
        String question = bundle.getString("question");
        String result = bundle.getString("result");
        int bestScore = bundle.getInt("maxScore");

        mFinalScore.setText(""+score);
        mQuestion.setText(""+question);
        mResult.setText(""+result);
        mBestScore.setText(""+bestScore);
        mCurrentScore.setText(""+score);
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsActivity.this, Play.class);
                startActivity(intent);
                finish();
            }
        });
    }
}