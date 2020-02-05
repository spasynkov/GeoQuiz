package com.example.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {
	private Button mTrueButton;
	private Button mFalseButton;
	private static final int REQUEST_CODE_CHEAT = 0;
	private Button mNextButton;
	private TextView mQuestionTextView;
	private Button mCheatButton;

	private Question[] mQuestionBank = new Question[]{
			new Question(R.string.question_australia, true),
			new Question(R.string.question_oceans, true),
			new Question(R.string.question_mideast, false),
			new Question(R.string.question_africa, false),
			new Question(R.string.question_americas, true),
			new Question(R.string.question_asia, true),
	};

	private int mCurrentIndex = 0;

	private static final String TAG = QuizActivity.class.getSimpleName();
	private static final String KEY_INDEX = "index";
	private boolean mIsCheated;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);

		Log.d(TAG, "onCreate(Bundle) called");

		if (savedInstanceState != null) {
			mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
		} else Log.d(TAG, "savedInstanceState == null");

		mQuestionTextView = findViewById(R.id.question_text_view);

		mTrueButton = findViewById(R.id.true_button);
		mTrueButton.setOnClickListener((view) -> {
			checkAnswer(true);
		});

		mFalseButton = findViewById(R.id.false_button);
		mFalseButton.setOnClickListener((view) -> {
			checkAnswer(false);
		});

		mCheatButton = findViewById(R.id.cheat_button);
		mCheatButton.setOnClickListener((view) -> {
			boolean answerTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
			Intent intent = CheatActivity.newIntent(this, answerTrue);
			startActivityForResult(intent, REQUEST_CODE_CHEAT);
		});

		mNextButton = findViewById(R.id.next_button);
		mNextButton.setOnClickListener((view) -> {
			mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
			updateQuestion();
			mIsCheated = false;
		});

		updateQuestion();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy() called");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onStart() called");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "onStop() called");
	}

	/**
	 * Dispatch onResume() to fragments.  Note that for better inter-operation
	 * with older versions of the platform, at the point of this call the
	 * fragments attached to the activity are <em>not</em> resumed.  This means
	 * that in some cases the previous state may still be saved, not allowing
	 * fragment transactions that modify the state.  To correctly interact
	 * with fragments in their proper state, you should instead override
	 * {@link #onResumeFragments()}.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume() called");
	}

	/**
	 * Dispatch onPause() to fragments.
	 */
	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause() called");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(KEY_INDEX, mCurrentIndex);
		Log.d(TAG, "onSaveInstanceState()");
	}

	/**
	 * Dispatch incoming result to the correct fragment.
	 *
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case REQUEST_CODE_CHEAT: {
				proceedCheatResult(resultCode, data);
				break;
			}
		}
	}

	private void proceedCheatResult(int resultCode, Intent data) {
		if (resultCode == RESULT_OK && CheatActivity.wasAnswerShown(data)) {
			mIsCheated = true;
		}
	}

	private void updateQuestion() {
		int questionId = mQuestionBank[mCurrentIndex].getTextResId();
		mQuestionTextView.setText(questionId);
	}

	private void checkAnswer(boolean userPressedTrue) {
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

		int toastToShowId;
		if (userPressedTrue == answerIsTrue) {
			toastToShowId = mIsCheated
					? R.string.judgment_toast
					: R.string.correct_toast;
		} else {
			toastToShowId = R.string.incorrect_toast;
		}

		Toast.makeText(QuizActivity.this,
				toastToShowId,
				Toast.LENGTH_SHORT)
				.show();
	}
}
