package project.com.introtoforest.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import project.com.introtoforest.R;
import project.com.introtoforest.service.GameService;
import project.com.introtoforest.view.OptionView;

/**
 * Created by Vison on 2015/11/27.
 */
public class GameActivity extends AppCompatActivity {
    GameService mGameService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        OptionView options = (OptionView) findViewById(R.id.options);
        ImageView questionImg = (ImageView) findViewById(R.id.question_img);

        mGameService = new GameService(this);
        mGameService.setBindingOptionView(options);
        mGameService.setBindingQuestionView(questionImg);

        mGameService.startNextStage();
    }
}
