package kr.hs.namgong.jms;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.hs.namgong.jms.util.ImageLoader;

import static kr.hs.namgong.jms.R.id.free;
import static kr.hs.namgong.jms.R.id.free;

public class StartActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, ViewFlipperAction.ViewFlipperCallback {
    RelativeLayout rl_intro, rl_free, rl_activity, rl_lunch, rl_notice;


    List<ImageView> indexes;

    ViewFlipper flipper;
    private ListView listView;

    Context context;
    private ArrayList<HashMap<String, String>> mList, mList2;
    private ViewAdapter_main mAdapter;
    private String url = "http://jingeon.ms.kr/";//

    private ImageLoader mImageLoader;
    private ProgressDialog mProgressDialog;
    static String TITLE = "title";
    static String NAME = "name";
    static String FLAG = "flag";
    static String HREFSTR = "href_str";
    static String RANK = "rank";
    static String SubTitle = "subTitle";
    private BottomPullToRefreshView pullView2 = null;
    int url_page = 1;

    private boolean flag = false;
    private TextView txtInner;
    private String hrefStr;
    private Intent intent;
    String rankStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        rl_intro = (RelativeLayout) findViewById(R.id.intro);
        rl_free = (RelativeLayout) findViewById(free);
        rl_activity = (RelativeLayout) findViewById(R.id.activity);
        rl_lunch = (RelativeLayout) findViewById(R.id.lunch);
        rl_notice = (RelativeLayout) findViewById(R.id.notice);

        flipper = (ViewFlipper) findViewById(R.id.flipper);
        ImageView index0 = (ImageView) findViewById(R.id.imgIndex0);
        ImageView index1 = (ImageView) findViewById(R.id.imgIndex1);
        ImageView index2 = (ImageView) findViewById(R.id.imgIndex2);

        rl_intro.setOnClickListener(this);
        rl_free.setOnClickListener(this);
        rl_activity.setOnClickListener(this);
        rl_lunch.setOnClickListener(this);
        rl_notice.setOnClickListener(this);

        indexes = new ArrayList<>();
        indexes.add(index0);
        indexes.add(index1);
        indexes.add(index2);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.viewflipper1, flipper, false);
        View view2 = inflater.inflate(R.layout.viewflipper2, flipper, false);
        View view3 = inflater.inflate(R.layout.viewflipper3, flipper, false);
        //inflate 한 view 추가
        flipper.addView(view1);
        flipper.addView(view2);
        flipper.addView(view3);
        flipper.setFlipInterval(3000);
        flipper.startFlipping();
        //리스너설정 - 좌우 터치시 화면넘어가기

        flipper.setOnTouchListener(new ViewFlipperAction(this, flipper));
        listView = (ListView) findViewById(R.id.listview);
        mImageLoader = new ImageLoader(getBaseContext());//이미지 불러오기
        mAdapter = new ViewAdapter_main(getBaseContext(), mImageLoader);//어뎁터만들기


        listView.setAdapter(mAdapter);// 리스뷰에 값 넣는 어뎁터


        new JsoupListView().execute();

        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("mListSize", mList.size() + "/" + position);
        String item2 = mAdapter.getList().get(position).get(HREFSTR);  //15개  포지션이 16

        //클릭했을때 페이지 넘어가서 게시물 내용 보여주기

//        Toast.makeText(getBaseContext(), item2, Toast.LENGTH_LONG).show();
//        Intent intent2 = new Intent(MainActivity.this, DetailActivity.class);
//        intent2.putExtra("hrefStr2", item2);
//        intent2.putExtra("titleStr2", mAdapter.getList().get(position).get(NAME));

//        startActivity(intent2);
    }


    public class JsoupListView extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create an array

            //여기서 새로만듬
            mList = new ArrayList<HashMap<String, String>>();

            try {
                Document doc = Jsoup.connect(url).get();

//                Log.i("aaa", doc.toString());

                int i = 0;

                for (Element table : doc.select("div[id=out_bbs1tabList1]")) {
                    Log.i("aaa", "table=" + table);

                    for (Element row : table.select("li")) {
                        HashMap<String, String> map = new HashMap<String, String>();

                        Elements title = row.select("a");
//                        Element name = row.select("td ").get(3);
//                        Element date = row.select("td").get(4);

                        map.put("title", title.text());//index.. etc)498,497...
//                        map.put("rank", date.text());
//                        map.put("name", name.text());

//                        Log.i("aaa", "name" + name.text());
                        Log.i("aaa", "title" + title.text());
//                        Log.i("aaa", "rank" + date.text());
                        mList.add(map);
                    }
                }
            } catch (
                    IOException e)

            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (
                    IllegalArgumentException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            mAdapter.addData(mList);

        }
    }

    @Override
    public void onFlipperActionCallback(int position) {
        Log.d("ddd", "" + position);
        for (int i = 0; i < indexes.size(); i++) {
            ImageView index = indexes.get(i);
            //현재화면의 인덱스 위치면 녹색
            if (i == position) {
                index.setImageResource(R.drawable.radio_on);
            }
            //그외
            else {
                index.setImageResource(R.drawable.radio_off);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.intro:
                Intent i = new Intent();// 액티비티간 이동///
                startActivity(new Intent(this, IntroActivity.class));
                break;
            case R.id.activity:
                Intent a = new Intent();
                startActivity(new Intent(this, ActivityActivity.class));
                break;
            case free:
                Intent f = new Intent();
                startActivity(new Intent(this, FreeActivity.class));
                break;
            case R.id.lunch:
                Intent l = new Intent();
                startActivity(new Intent(this, LunchActivity.class));
                break;
            case R.id.notice:
                Intent n = new Intent();
                startActivity(new Intent(this, NoticeActivity.class));
                break;

            default:
                break;
        }
    }
}
