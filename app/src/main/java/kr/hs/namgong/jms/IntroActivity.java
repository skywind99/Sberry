package kr.hs.namgong.jms;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class IntroActivity extends AppCompatActivity {
    public ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("학교장 인사"));
        tabLayout.addTab(tabLayout.newTab().setText("학교연혁"));
        tabLayout.addTab(tabLayout.newTab().setText("학교상징"));
        tabLayout.addTab(tabLayout.newTab().setText("학교현황"));
//        tabLayout.addTab(tabLayout.newTab().setText("Face\nBook"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:010-3787-3848"));
                startActivity(intent);

            }
        });


        viewPager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter_intro adapter = new PagerAdapter_intro
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    fab.setVisibility(View.GONE);
                }
                if (tab.getPosition() == 1) {
                    fab.setVisibility(View.VISIBLE);
                }
                if (tab.getPosition() == 2) {
                    fab.setVisibility(View.VISIBLE);
                }
                if (tab.getPosition() == 3) {
                    fab.setVisibility(View.GONE);
                }
                if (tab.getPosition() == 4) {
                    fab.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void movingTab(int position) {
        viewPager.setCurrentItem(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
