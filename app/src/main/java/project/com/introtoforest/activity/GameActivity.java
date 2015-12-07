package project.com.introtoforest.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import project.com.introtoforest.R;
import project.com.introtoforest.service.BackgroundSound;
import project.com.introtoforest.service.GameService;
import project.com.introtoforest.view.OptionView;

/**
 * Created by Vison on 2015/11/27.
 */
public class GameActivity extends AppCompatActivity {
    GameService mGameService;
    BackgroundSound mBSService;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        OptionView options = (OptionView) findViewById(R.id.options);
        ImageView questionImg = (ImageView) findViewById(R.id.question_img);

        mGameService = new GameService(this);
        mGameService.setBindingOptionView(options);
        mGameService.setBindingQuestionView(questionImg);


        // start background sound
        intent = new Intent(this, BackgroundSound.class);
        startService(intent);

        mGameService.setEnddingAction(new GameService.EndingCallback() {
            @Override
            public void onEnding(int score) {
                showInputDialog(score);
            }
        });
        mGameService.startNextStage();

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopService(intent);
    }

    private void showInputDialog(int score) {
        LayoutInflater inflater = getLayoutInflater();
        View promptView = inflater.inflate(R.layout.dialog_ending, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);
        TextView content = (TextView) promptView.findViewById(R.id.content);
        content.setText("得分 "+ score +" 分");
        Button restartBtn = (Button) promptView.findViewById(R.id.restart_btn);
        Button quitBtn= (Button) promptView.findViewById(R.id.quit_btn);

        // create an alert dialog
        final AlertDialog alert = alertDialogBuilder.create();

        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGameService.resetGame();
                mGameService.startNextStage();
                alert.dismiss();
            }
        });

        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGameService.resetGame();
                finish();
            }
        });

        alert.show();

    }
}
