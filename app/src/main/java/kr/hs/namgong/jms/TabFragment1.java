package kr.hs.namgong.jms;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class TabFragment1 extends Fragment {
    Button btn_reser;
    MainActivity mContext;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mContext = (MainActivity) getActivity();
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_1, container, false);

        mContext = (MainActivity) getActivity();
        btn_reser = (Button) view.findViewById(R.id.fr1_btn_reser);
        btn_reser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.movingTab(3);
            }
        });
        return view;

    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//
//    }
}
