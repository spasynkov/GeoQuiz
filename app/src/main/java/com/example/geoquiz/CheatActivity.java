package com.example.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CheatActivity extends AppCompatActivity {

	private static final String EXTRA_ANSWER_IS_TRUE = "com.example.answer_is_true";
	private static final String EXTRA_ANSWER_SHOWN = "com.example.answer_shown";

	private Button mButton;
	private TextView mTextView;
	private boolean mAnswerIsTrue;

	public static Intent newIntent(Context packageContext, boolean isAnswerTrue) {
		return new Intent(packageContext, CheatActivity.class)
				.putExtra(EXTRA_ANSWER_IS_TRUE, isAnswerTrue);
	}

	public static boolean wasAnswerShown(Intent intent) {
		return intent.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);

		mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

		mButton = findViewById(R.id.show_answer_button);
		mTextView = findViewById(R.id.answer_text_view);

		mButton.setOnClickListener((view) -> {
			mTextView.setText(mAnswerIsTrue ? R.string.true_button : R.string.false_button);
			setAnswerShownResult(true);

			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
				int cx = mButton.getWidth() / 2;
				int cy = mButton.getHeight() / 2;
				float radius = mButton.getWidth();
				Animator anim = null;

				anim = ViewAnimationUtils
						.createCircularReveal(mButton, cx, cy, radius, 0);
				anim.addListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						super.onAnimationEnd(animation);
						mButton.setVisibility(View.INVISIBLE);
					}
				});
				anim.start();
			} else {
				mButton.setVisibility(View.INVISIBLE);
			}
		});
	}

	private void setAnswerShownResult(boolean isAnswerShown) {
		Intent data = new Intent()
				.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);

		setResult(RESULT_OK, data);
	}
}
