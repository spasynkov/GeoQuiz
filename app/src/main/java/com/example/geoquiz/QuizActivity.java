package com.example.geoquiz;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {
	private Button mTrueButton;
	private Button mFalseButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);

		mTrueButton = findViewById(R.id.true_button);
		mTrueButton.setOnClickListener((view) -> {
			Toast toast = Toast.makeText(QuizActivity.this,
					R.string.correct_toast,
					Toast.LENGTH_SHORT);
			// toast.setGravity(Gravity.TOP, 0, 0);
			toast.show();
		});

		mFalseButton = findViewById(R.id.false_button);
		mFalseButton.setOnClickListener((view) -> {
			Toast toast = Toast.makeText(QuizActivity.this,
					R.string.incorrect_toast,
					Toast.LENGTH_SHORT);
			// toast.setGravity(Gravity.TOP, 300, 50);
			toast.show();
		});
	}
}
