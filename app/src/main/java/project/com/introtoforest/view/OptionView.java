package project.com.introtoforest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Attr;

import java.util.ArrayList;

import project.com.introtoforest.R;

/**
 * Created by Vison on 2015/12/2.
 */
public class OptionView extends LinearLayout {

    private int background = 0x30000000;
    private int margins = 1;
    private static final int OPTION_SIZE = 4;

    private ArrayList<OptionItem> options;

    public OptionView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);

        options = new ArrayList<OptionItem>(OPTION_SIZE);

        int[] attrsArray = new int[] {
                android.R.attr.id, // 0
                android.R.attr.background, // 1
                android.R.attr.layout_width, // 2
                android.R.attr.layout_height // 3
        };

        TypedArray ta = getContext().obtainStyledAttributes(attrs, attrsArray);
        background = ta.getColor(1, background);

        ta.recycle();

        setAttribute(attrs);
    }

    // set attribute of XML to view
    protected void setAttribute(AttributeSet attrs) {


        // set size of view
        setMinimumHeight(50);
        setMinimumWidth(50);

        // set layout
        setOrientation(VERTICAL);


        // add text view
        for (int i=0;i<OPTION_SIZE;i++) {
            options.add(new OptionItem(getContext()));
            addTextView(options.get(i));
        }

        addBottomView(4, 10);

    }

    private void addTextView(OptionItem item) {
        item.setGravity(Gravity.CENTER);
        item.setBackground(getResources().getDrawable(R.drawable.option_color));
        LinearLayout.LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 2.0f);
        item.setLayoutParams(params);
        params.setMargins(0, 0, 0, margins);
        item.setClickable(true); // enable click

        addView(item);
    }

    /**
     *
     * @param serial the serial num of question
     * @param total total num of question
     */
    private void addBottomView(int serial, int total) {
        TextView bottom = new TextView(getContext());
        bottom.setText(serial + " of " + total);
        bottom.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f);
        bottom.setLayoutParams(params);
        bottom.setBackgroundColor(Color.WHITE);
        addView(bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }



    public class OptionItem extends TextView {
        private boolean correct;
        public OptionItem(Context context) {
            super(context);
            setBackgroundColor(Color.WHITE);
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO:

                    Log.d("is correct or not", Boolean.toString(correct));
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
        return options.get(i);
    }
}
