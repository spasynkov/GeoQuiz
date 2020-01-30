package com.example.geoquiz;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {
	private Button mTrueButton;
	private Button mFalseButton;
	private Button mNextButton;
	private TextView mQuestionTextView;

	private Question[] mQuestionBank = new Question[]{
			new Question(R.string.question_australia, true),
			new Question(R.string.question_oceans, true),
			new Question(R.string.question_mideast, false),
			new Question(R.string.question_africa, false),
			new Question(R.string.question_americas, true),
			new Question(R.string.question_asia, true),
	};

	private int mCurrentIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);

		mQuestionTextView = findViewById(R.id.question_text_view);

		mTrueButton = findViewById(R.id.true_button);
		mTrueButton.setOnClickListener((view) -> {
			checkAnswer(true);
		});

		mFalseButton = findViewById(R.id.false_button);
		mFalseButton.setOnClickListener((view) -> {
			checkAnswer(false);
		});

		mNextButton = findViewById(R.id.next_button);
		mNextButton.setOnClickListener((view) -> {
			mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
			updateQuestion();
		});

		updateQuestion();
	}

	private void updateQuestion() {
		int questionId = mQuestionBank[mCurrentIndex].getTextResId();
		mQuestionTextView.setText(questionId);
	}

	private void checkAnswer(boolean userPressedTrue) {
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

		int toastToShowId;
		if (userPressedTrue == answerIsTrue) {
			toastToShowId = R.string.correct_toast;
		} else {
			toastToShowId = R.string.incorrect_toast;
		}

		Toast.makeText(QuizActivity.this,
				toastToShowId,
				Toast.LENGTH_SHORT)
				.show();
	}
}
