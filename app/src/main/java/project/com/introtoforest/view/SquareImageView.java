package project.com.introtoforest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.Image;
import android.util.AttributeSet;
import android.widget.ImageView;

import project.com.introtoforest.R;

/**
 * Created by Vison on 2015/12/2.
 */
public class SquareImageView extends ImageView {

    boolean dependOnWidth = false;
    boolean dependOnHeight = false;

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SquareImageView);
        if (ta != null) {
            dependOnWidth = ta.getBoolean(R.styleable.SquareImageView_dependOnWidth, false);
            dependOnHeight = ta.getBoolean(R.styleable.SquareImageView_dependOnHeight, false);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (dependOnWidth) {
            heightMeasureSpec = widthMeasureSpec;
        } else if (dependOnHeight) {
            widthMeasureSpec = heightMeasureSpec;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
