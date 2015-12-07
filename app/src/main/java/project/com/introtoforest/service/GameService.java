package project.com.introtoforest.service;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.security.auth.callback.Callback;

import project.com.introtoforest.R;
import project.com.introtoforest.view.OptionView;

/**
 * Created by Vison on 2015/12/5.
 */
public class GameService implements Observer {

    private static final int OPTION_SIZE = 4;
    private static final int TOTAL_QUESTION = 7;

    private Context mContext;
    private ArrayList<QAModel> qaModels;
    private OptionView mOptionView;
    private ImageView mQuestionView;
    private int current;
    private EndingCallback endingCallback;
    private int score = 0;

    // delay constant
    private static final int D_PRESS = 500;
    private static final int D_FADE_OUT = 500;
    private static final int D_NEXT_STAGE = 1000;
    private static final int D_IMG_FADE_IN = 900;
    private static final int D_OPTION_SHOW = 400;


    public GameService (Context context){
        qaModels = new ArrayList<QAModel>();
        mContext = context;

        current = 1;
        initModels();
        shuffle();
    }


    public void resetGame() {
        score = 0;
        current = 0;
        shuffle();
    }
    /**
     * Raw code
     */
    private void initModels() {
        // initModels for QA models
        // 1
        qaModels.add(new QAModel().setQuestionImageId(R.drawable.forest_1)
                .setCorrectAnswer("莫氏樹蛙").setWrongAnswer("艾氏樹蛙", "巴氏小樹蛙", "長腳赤蛙"));

        // 2
        qaModels.add(new QAModel().setQuestionImageId(R.drawable.forest_2)
                .setCorrectAnswer("小元").setWrongAnswer("大元", "黃品源", "團團圓圓"));

        // 3
        qaModels.add(new QAModel().setQuestionImageId(R.drawable.forest_3)
                .setCorrectAnswer("秀柱花").setWrongAnswer("立倫花", "英文花", "楚瑜花"));

        // 4
        qaModels.add(new QAModel().setQuestionImageId(R.drawable.forest_4)
                .setCorrectAnswer("咬人貓").setWrongAnswer("咬人狗", "咬人草", "咬人花"));

        // 5
        qaModels.add(new QAModel().setQuestionImageId(R.drawable.forest_5)
                .setCorrectAnswer("紅斑蛇").setWrongAnswer("金絲蛇", "斯文豪氏遊蛇", "青竹絲"));

        // 6
        qaModels.add(new QAModel().setQuestionImageId(R.drawable.forest_6)
                .setCorrectAnswer("肖楠步道").setWrongAnswer("白楊步道", "伯朗大道", "秋海棠步道"));

        // 7
        qaModels.add(new QAModel().setQuestionImageId(R.drawable.forest_7)
                .setCorrectAnswer("灰喉山椒鳥").setWrongAnswer("灰山椒鳥", "花翅山椒鳥", "長尾山椒鳥"));

        // 8
        qaModels.add(new QAModel().setQuestionImageId(R.drawable.forest_8)
                .setCorrectAnswer("咖啡樹").setWrongAnswer("臭牡丹", "咬人狗", "肉桂"));

        // 9
        qaModels.add(new QAModel().setQuestionImageId(R.drawable.forest_9)
                .setCorrectAnswer("吊鐘姬蛛").setWrongAnswer("長尾寄居姬蛛", "大姬蛛", "沙地豹蛛"));

        // 10
        qaModels.add(new QAModel().setQuestionImageId(R.drawable.forest_10)
                .setCorrectAnswer("第倫桃").setWrongAnswer("金絲桃", "月桃", "夾竹桃"));

        // 11
        qaModels.add(new QAModel().setQuestionImageId(R.drawable.forest_11)
                .setCorrectAnswer("藪鳥").setWrongAnswer("紅頭山雀", "短翅樹鶯", "繡眼畫眉"));

        // 12
        qaModels.add(new QAModel().setQuestionImageId(R.drawable.forest_12)
                .setCorrectAnswer("捕食").setWrongAnswer("互利共生", "片利共生", "寄生"));


        // 13
        qaModels.add(new QAModel().setQuestionImageId(R.drawable.forest_13)
                .setCorrectAnswer("忠義").setWrongAnswer("德智", "仁愛", "誠信"));


        // 14
        qaModels.add(new QAModel().setQuestionImageId(R.drawable.forest_14)
                .setCorrectAnswer("中間不是天文助教").setWrongAnswer("左邊不是天文助教", "右邊不是天文助教", "全部都是天文助教"));
    }

    private void shuffle() {
        // random it!
        long seed = System.nanoTime();
        Collections.shuffle(qaModels, new Random(seed));
    }

    public void makeCorrectAns() {
        score += 10;
    }

    /**
     * process of a single QA
     */
    public void startNextStage() {
        mOptionView.setOptionViewClickable(false);
        if (current > TOTAL_QUESTION) {
            endingCallback.onEnding(score);
            return;
        }

        // set animation
        hideAll();
        imageFadeIn();
        optionsFadeInInSeq();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mOptionView.setOptionViewClickable(true);
            }
        }, 800 + D_IMG_FADE_IN);
        mOptionView.resetView();

        setQuestion(current);
        setRandomAnswer(current);
        mOptionView.getBottomText().setTotal(TOTAL_QUESTION);
        mOptionView.getBottomText().setSerial(current);

        current++;
    }


    private void setQuestion(int current) {
        int id = qaModels.get(current).getQuestionImageId();
        mQuestionView.setImageDrawable(mContext.getResources().getDrawable(id));
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

        imageFadeOut();
        optionsFadeOut();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                startNextStage();


            }
        }, D_FADE_OUT+D_NEXT_STAGE);
    }

    private void imageFadeOut() {
        YoYo.with(Techniques.FadeOut)
                .delay(D_PRESS)
                .duration(D_FADE_OUT)
                .playOn(mQuestionView);
    }

    private void imageFadeIn() {
        YoYo.with(Techniques.FadeIn)
                .duration(D_IMG_FADE_IN)
                .playOn(mQuestionView);
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
        int i;
        for (i=0;i<4;i++) {
            final int k = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    YoYo.with(Techniques.ZoomIn)
                            .duration(D_OPTION_SHOW)
                            .playOn(mOptionView.getOption(k));
                }
            }, i * 200 + D_IMG_FADE_IN);
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                YoYo.with(Techniques.FadeIn)
                        .duration(500)
                        .playOn(mOptionView.getBottomText());
            }
        }, i * 200 + D_IMG_FADE_IN);

    }

    private void hideAll() {
        for (int i = 0; i < 4; i++) {
            YoYo.with(Techniques.FadeOut)
                    .duration(0)
                    .playOn(mOptionView.getOption(i));
        }
        YoYo.with(Techniques.FadeOut)
                .duration(0)
                .playOn(mOptionView.getBottomText());
    }

    public void setEnddingAction(EndingCallback callback) {
        endingCallback = callback;
    }

    public interface EndingCallback {
        void onEnding(int score);
    }
}
