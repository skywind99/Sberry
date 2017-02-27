package kr.hs.namgong.jms;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import kr.hs.namgong.jms.util.ImageLoader;


public class TabFragment1_notice extends Fragment implements AdapterView.OnItemClickListener {
    Button btn_reser;
    private ListView listView;
    NoticeActivity mContext;
    private ArrayList<HashMap<String, String>> mList, mList2;
    private ImageLoader mImageLoader;
    private ViewAdapter mAdapter;
    private String url = "http://jingeon.ms.kr/board.list?mcode=1617&cate=1119&page=1";//

    String href_str ;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mContext = (MainActivity) getActivity();
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_1_notice, container, false);
        listView = (ListView)view.findViewById(R.id.listview_notice);
        mContext = (NoticeActivity) getActivity();
        mImageLoader = new ImageLoader(getContext());//이미지 불러오기
        mAdapter = new ViewAdapter(getContext(), mImageLoader);//어뎁터만들기
        listView.setAdapter(mAdapter);// 리스뷰에 값 넣는 어뎁터
        listView.setOnItemClickListener(this);
        new JsoupListView().execute();
        return view;

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("mListSize", mList.size() + "/" + position);
        String item2 = mAdapter.getList().get(position).get("href");  //15개  포지션이 16

        //클릭했을때 페이지 넘어가서 게시물 내용 보여주기

        Toast.makeText(getActivity(), item2, Toast.LENGTH_LONG).show();
        Intent intent2 = new Intent(getContext(), DetailActivity.class);
//        Intent intent2 = new Intent(getContext(), SingleItemView.class);
        intent2.putExtra("hrefStr", item2);
//        intent2.putExtra("titleStr2", mAdapter.getList().get(position).get(NAME));
//
        startActivity(intent2);
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

                for (Element table : doc.select("table.boardList")) {
                    Log.i("aaa", "table=" + table);

                    for (Element row : table.select("tr:gt(1)")) {
                        HashMap<String, String> map = new HashMap<String, String>();

                        Elements title = row.select("td.title");
                        String href = row.select("a").get(0).attr("href");
                        Log.i("aaa", "href=" + href);

//                        Element name = row.select("td ").get(3);
//                        Element date = row.select("td").get(4);


                        map.put("title", title.text());//index.. etc)498,497...
                        map.put("href", href);
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
}
