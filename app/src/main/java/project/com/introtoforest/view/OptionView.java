package project.com.introtoforest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Observable;

import project.com.introtoforest.R;
import project.com.introtoforest.service.GameService;

/**
 * Created by Vison on 2015/12/2.
 */
public class OptionView extends LinearLayout {

    private int background = 0x30000000;
    private int margins = 1;
    private static final int OPTION_SIZE = 4;
    private boolean clickable = true;

    private ArrayList<OptionItem> mOptions;
    private BottomText mBottomText;
    private GameService mGameService;
    private MediaPlayer mMediaPlayer;

    public OptionView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);

        // init view
        mOptions = new ArrayList<OptionItem>(OPTION_SIZE);
        for (int i=0;i<OPTION_SIZE;i++) {
            mOptions.add(new OptionItem(getContext()));
        }
        mBottomText = new BottomText(getContext());

        // add view
        for (int i=0;i<OPTION_SIZE;i++) {
            addView(mOptions.get(i));
        }
        addView(mBottomText);

        int[] attrsArray = new int[] {
                android.R.attr.id, // 0
                android.R.attr.background, // 1
                android.R.attr.layout_width, // 2
                android.R.attr.layout_height // 3
        };

        TypedArray ta = getContext().obtainStyledAttributes(attrs, attrsArray);
        background = ta.getColor(1, background);

        ta.recycle();

        // set size of view
        setMinimumHeight(50);
        setMinimumWidth(50);

        // set layout
        setOrientation(VERTICAL);
    }



    public void resetView() {
        for (int i=0;i< mOptions.size();i++) {
            mOptions.get(i).setBackgroundColor(Color.WHITE);
            mOptions.get(i).setTextColor(getResources().getColor(R.color.default_text_color));
        }
        setClickable(true);
    }

    public void setGameService(GameService gameService) {
        mGameService = gameService;
        for (int i=0;i<OPTION_SIZE;i++) {
            getOption(i).onClickObservable.addObserver(mGameService);
        }
    }

    /**
     * Option Item is inside Option View
     */

    public class OptionItem extends TextView {
        private boolean correct;
        private Observable onClickObservable;

        public OptionItem(Context context) {
            super(context);
            onClickObservable = new Observable() {
                @Override
                public void notifyObservers() {
                    setChanged();
                    super.notifyObservers();
                }
            };

            setTextColor(getResources().getColor(R.color.default_text_color));
            setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams params = new LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 0, 2.0f);
            setLayoutParams(params);
            params.setMargins(0, 0, 0, margins);
            setClickable(true); // enable click

            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    // set color
                    setTextColor(Color.WHITE);
                    if (correct) {
                        setBackgroundColor(getResources().getColor(R.color.correct));
                        mMediaPlayer = MediaPlayer.create(getContext(), R.raw.correct);
                    } else {
                        setBackgroundColor(getResources().getColor(R.color.wrong));
                        mMediaPlayer = MediaPlayer.create(getContext(), R.raw.wrong);
                    }



                    // play audio
                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.release();
                        }

                    });
                    mMediaPlayer.start();

                    onClickObservable.notifyObservers();
                }
            });
        }

        public void setTextAndCorrectness(String text, boolean correct) {
            this.setText(text);
            this.correct = correct;
        }

        /**
         * set if the option is correct.
         */
        public void setCorrectness(boolean c) {
            correct = c;
        }

        public boolean getCorrectness() {
            return correct;
        }

    }

    public OptionItem getOption(int i) {
        return mOptions.get(i);
    }


    public class BottomText extends TextView {
        int serial, total;

        public BottomText(Context context) {
            super(context);
            setGravity(Gravity.CENTER);
            LayoutParams params = new LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f);
            setLayoutParams(params);
            setBackgroundColor(Color.WHITE);
        }

        public void setSerial(int serial) {
            this.serial = serial;
            setText(serial + " of " + total);
        }

        public void setTotal(int total) {
            this.total = total;
            setText(serial + " of " + total);
        }
    }
    public BottomText getBottomText() {
        return mBottomText;
    }


    /**
     * make un-clickable after choose an option
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return !clickable;
    }

    public void setOptionViewClickable(boolean b) {
        clickable = b;
    }
}
