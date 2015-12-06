package project.com.introtoforest.service;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.util.ArrayList;

import project.com.introtoforest.R;
import project.com.introtoforest.view.OptionView;

/**
 * Created by Vison on 2015/12/5.
 */
public class GameService {

    private static final int OPTION_SIZE = 4;

    private Context mContext;
    private ArrayList<QAModel> qaModels;
    private OptionView mOptionView;
    private ImageView mQuestionView;
    private int current;

    public GameService (Context context){
        qaModels = new ArrayList<QAModel>(15);
        mContext = context;

        current = 0;
        initModels();
    }


    private void initModels() {
        // initModels for QA models
        QAModel a = new QAModel();
        a.setQuestionImage(mContext.getResources().getDrawable(R.drawable.sample_pic));
        a.setCorrectAnswer("灰喉山椒鳥");
        a.setWrongAnswer("白喉山椒鳥", "黃喉山椒鳥", "小山椒鳥");
        qaModels.add(a);
    }

    /**
     * process of a single QA
     */
    public void startNextStage() {
        setQuestion(current);
        setRandomAnswer(current);
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
    }

    public void setBindingQuestionView(ImageView imageView) {
        mQuestionView = imageView;
    }
}
