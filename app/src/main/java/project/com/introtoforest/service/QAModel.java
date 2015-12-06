package project.com.introtoforest.service;

import android.graphics.drawable.Drawable;

/**
 * Created by Vison on 2015/12/6.
 */
public class QAModel {
    private static final int OPTION_SIZE = 4;

    private Drawable questionImage;
    private String correctAnswer;
    private String[] wrongAnswer = new String[OPTION_SIZE];

    public void setQuestionImage(Drawable img) {
        questionImage = img;
    }

    public void setCorrectAnswer(String ans) {
        correctAnswer = ans;
    }

    public void setWrongAnswer(String ans1, String ans2, String ans3) {
        wrongAnswer[0] = ans1;
        wrongAnswer[1] = ans2;
        wrongAnswer[2] = ans3;

    }

    public Drawable getQuestionImage() {
        return questionImage;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getWrongAnswer(int i) {
        return wrongAnswer[i];
    }

    public void shuffleWrongAnswer(String[] array) {
        int currentIndex = array.length, randomIndex ;
        String temporaryValue;

        // While there remain elements to shuffle...
        while (0 != currentIndex) {

            // Pick a remaining element...
            randomIndex = (int)Math.floor(Math.random() * currentIndex);
            currentIndex -= 1;

            // And swap it with the current element.
            temporaryValue = array[currentIndex];
            array[currentIndex] = array[randomIndex];
            array[randomIndex] = temporaryValue;
        }

    }
}
