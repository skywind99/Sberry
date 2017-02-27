package kr.hs.namgong.jms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class TabFragment4 extends Fragment {
    Button fr4_btn_reser;
    MainActivity Activity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_4, container, false);
        Activity = (MainActivity) getActivity();
        fr4_btn_reser = (Button) view.findViewById(R.id.fr4_btn_commit);
        fr4_btn_reser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity, TabFragment4_one.class);
                startActivity(intent);
            }
        });

        return view;
    }
}