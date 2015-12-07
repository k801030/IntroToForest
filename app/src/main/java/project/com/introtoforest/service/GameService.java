package project.com.introtoforest.service;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import project.com.introtoforest.R;
import project.com.introtoforest.view.OptionView;

/**
 * Created by Vison on 2015/12/5.
 */
public class GameService implements Observer {

    private static final int OPTION_SIZE = 4;
    private static final int TOTAL_QUESTION = 15;

    private Context mContext;
    private ArrayList<QAModel> qaModels;
    private OptionView mOptionView;
    private ImageView mQuestionView;
    private int current;

    // delay constant
    private static final int D_PRESS = 500;
    private static final int D_FADE_OUT = 500;
    private static final int D_NEXT_STAGE = 1000;


    public GameService (Context context){
        qaModels = new ArrayList<QAModel>(15);
        mContext = context;

        current = 1;
        initModels();
    }


    private void initModels() {
        // initModels for QA models
        for (int i=0;i<TOTAL_QUESTION;i++) {
            QAModel a = new QAModel();
            a.setQuestionImage(mContext.getResources().getDrawable(R.drawable.sample_pic));
            a.setCorrectAnswer("灰喉山椒鳥");
            a.setWrongAnswer("白喉山椒鳥", "黃喉山椒鳥", "小山椒鳥");
            qaModels.add(a);
        }
    }

    /**
     * process of a single QA
     */
    public void startNextStage() {

        if (current > TOTAL_QUESTION) {
            // quit the game
            return;
        }

        mOptionView.resetView();

        setQuestion(current);
        setRandomAnswer(current);
        mOptionView.getBottomText().setTotal(TOTAL_QUESTION);
        mOptionView.getBottomText().setSerial(current);

        current++;
    }


    private void setQuestion(int current) {
        Drawable d = qaModels.get(current).getQuestionImage();
        mQuestionView.setImageDrawable(d);
    }

    private void setRandomAnswer(int current) {
        int correctPos = (int)(Math.random()* OPTION_SIZE);
        int j=0;
        for (int i=0;i<OPTION_SIZE;i++) {
            String ans;
            if (i == correctPos) {
                ans = qaModels.get(current).getCorrectAnswer();
                mOptionView.getOption(i).setTextAndCorrectness(ans, true);
            } else {
                ans = qaModels.get(current).getWrongAnswer(j++);
                mOptionView.getOption(i).setTextAndCorrectness(ans, false);
            }

        }
        qaModels.get(current).getCorrectAnswer();
    }


    public void setBindingOptionView(OptionView view) {
        mOptionView = view;
        mOptionView.setGameService(this);
    }

    public void setBindingQuestionView(ImageView imageView) {
        mQuestionView = imageView;
    }

    /**
     *
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        mOptionView.setOptionViewClickable(false);
        final Handler handler = new Handler();

        optionsFadeOut();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                startNextStage();
                mOptionView.setOptionViewClickable(true);

                optionsFadeInInSeq();

            }
        }, D_FADE_OUT+D_NEXT_STAGE);
    }

    private void optionsFadeOut() {
        for (int i=0;i<4;i++) {
            YoYo.with(Techniques.FadeOut)
                    .delay(D_PRESS)
                    .duration(D_FADE_OUT)
                    .playOn(mOptionView.getOption(i));
        }
        YoYo.with(Techniques.FadeOut)
                .delay(D_PRESS)
                .duration(D_FADE_OUT)
                .playOn(mOptionView.getBottomText());

    }

    private void optionsFadeInInSeq() {

        Handler handler = new Handler();

        for (int i=0;i<4;i++) {
            final int k = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    YoYo.with(Techniques.FadeInLeft)
                            .duration(250)
                            .playOn(mOptionView.getOption(k));
                }
            }, i * 200);
        }
    }

}
