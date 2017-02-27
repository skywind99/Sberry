package kr.hs.namgong.jms;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static android.R.attr.content;
import static android.R.attr.data;
import static android.R.attr.name;
import static android.R.attr.title;

public class DetailActivity extends AppCompatActivity {
    TextView date_tv, name_tv, title_tv, content_tv;
    String url;
    String str_date, str_name, str_title, str_content, str_file;
    WebView web, wv_file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        url = "http://jingeon.ms.kr" + intent.getStringExtra("hrefStr");
        date_tv = (TextView) findViewById(R.id.tv_date);
        name_tv = (TextView) findViewById(R.id.tv_name);
        title_tv = (TextView) findViewById(R.id.tv_title);

//        content_tv = (TextView) findViewById(R.id.tv_content);
        web = (WebView) findViewById(R.id.wv_content);
        web.getSettings().setJavaScriptEnabled(true);

// 스크롤바 없애기
        web.setHorizontalScrollBarEnabled(false);
        web.setVerticalScrollBarEnabled(false);
        web.setBackgroundColor(0);
        web.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);



        wv_file = (WebView) findViewById(R.id.wv_file);
        wv_file.getSettings().setJavaScriptEnabled(true);
// 스크롤바 없애기
        wv_file.setHorizontalScrollBarEnabled(false);
        wv_file.setVerticalScrollBarEnabled(false);
        wv_file.setBackgroundColor(0);
        wv_file.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wv_file.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);


        date_tv.setText(url);
        name_tv.setText("aaa");


        title_tv.setText(intent.getStringExtra("hrefStr"));

        new JsoupListView().execute();

    }

    public class JsoupListView extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Document doc = Jsoup.connect(url).get();

                for (Element table : doc.select("div.boardRead")) {
                    Log.i("aaa", "table=" + table);

                    Elements title = table.select("dl.infoPre").select("dd");
                    Element name = table.select("dl.infoNext").select("dd").get(0);
                    Element date = table.select("dl.infoNext").select("dd").get(1);
                    Elements content = table.select("div[class=boardReadBody smartOutput]");

                    Element file = table.select("div.boradReadFooter").get(1);

                    str_date = date.text();
                    str_name = name.text();
                    str_title = title.text();
                    str_file = file.toString();
                    str_content = content.toString();
//                    String href = row.select("a").get(0).attr("href");
//                    Log.i("aaa", "href=" + href);
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
            title_tv.setText(str_title);
            name_tv.setText(str_name);

            date_tv.setText(str_date);
//            content_tv.setText(Html.fromHtml(str_content));


            web.loadData(str_content, "text/html;charset=UTF-8", null);
            wv_file.loadData(str_file, "text/html;charset=UTF-8", null);
            ;

        }
    }

}
