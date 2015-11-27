package project.com.introtoforest.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import project.com.introtoforest.R;
import project.com.introtoforest.activity.GameActivity;
import project.com.introtoforest.activity.MainActivity;

/**
 * Created by Vison on 2015/11/27.
 */
public class PlayFragment extends Fragment {
    Button mStartBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);

        mStartBtn = (Button) view.findViewById(R.id.start_btn);
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(PlayFragment.this.getActivity(), GameActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }


}
