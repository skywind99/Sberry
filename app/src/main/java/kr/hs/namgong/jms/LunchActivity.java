package kr.hs.namgong.jms;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LunchActivity extends AppCompatActivity implements OnDateSelectedListener, OnMonthChangedListener  {

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private static ArrayList<HashMap<String, String>> mList, mList2;
    public static String url_month, url_month1;
    private String url = "http://namyangju.hs.kr/lunch.list?ym=";
    private String url_1 = "http://jingeon.ms.kr/lunch.list?ym=";


    @Bind(R.id.calendarView)
    MaterialCalendarView widget;

    @Bind(R.id.textView)
    TextView textView;
    Calendar calendar = Calendar.getInstance();

    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMM");
    Date date = new Date();
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);
        ButterKnife.bind(this);
        url_month = url_1 + sdf1.format(date).toString();

        Log.i("aaa", url_month);
        new JsoupListView().execute();

        widget.setOnDateChangedListener(this);
        widget.setOnMonthChangedListener(this);


        Calendar instance = Calendar.getInstance();
        widget.setSelectedDate(instance.getTime());


        textView.setText(getSelectedDatesString());
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @Nullable CalendarDay date, boolean selected) {
        textView.setText(getSelectedDatesString());
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        getSupportActionBar().setTitle(FORMATTER.format(date.getDate()));
        url_month1 = url + (Integer.parseInt(sdf1.format(date.getDate())));
        Log.i("aaa", url_month1);
        new JsoupListView2().execute();
    }

    private String getSelectedDatesString() {
        CalendarDay date = widget.getSelectedDate();
        try {
            if (mList == null) {

                if (date == null) {
                    return "No Selection";
                }

                return FORMATTER.format(date.getDate());
            } else

            {
                return mList.get(Integer.parseInt(sdf2.format(date.getDate())) - 1).get("title").toString();
            }

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return FORMATTER.format(date.getDate());
    }

    public class JsoupListView extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mList = new ArrayList<HashMap<String, String>>();

            try {
                // Connect to the Website URL
                Document doc = Jsoup.connect(url_month).get();
//                Log.i("aaa", doc.toString());

                Elements table = doc.select("div[id=contentbox]");
                for (Element row : table.select("tr")) {
                    for (Element tds : row.select("td")) {
//                        Log.i("aaa", row.toString());
                        HashMap<String, String> map = new HashMap<String, String>();

                        Elements title = tds.select("span");

                        if (title.text().length() == 0) {
                        } else {
                            map.put("title", title.text());//index.. etc)498,497...
                            mList.add(map);
                        }

                    }
                }
            } catch (
                    IOException e)

            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (
                    IllegalArgumentException e)

            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            textView.setText(getSelectedDatesString());

            for (int i = 0; i < mList.size(); i++) {
                Log.i("aaa", "length" + mList.get(i).get("title").length() + "");
                Log.i("aaa", mList.get(i).get("title").toString() + "");
            }
        }

    }

    public class JsoupListView2 extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            mList = new ArrayList<HashMap<String, String>>();

            try {
                // Connect to the Website URL
                Document doc = Jsoup.connect(url_month1).get();
//                Log.i("aaa", doc.toString());

                Elements table = doc.select("div[id=contentbox]");
                for (Element row : table.select("tr")) {
                    for (Element tds : row.select("td")) {
//                        Log.i("aaa", row.toString());
                        HashMap<String, String> map = new HashMap<String, String>();

                        Elements title = tds.select("span");

                        if (title.text().length() == 0) {
                        } else {
                            map.put("title", title.text());//index.. etc)498,497...
                            mList.add(map);
                        }

                    }
                }
            } catch (
                    IOException e)

            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (
                    IllegalArgumentException e)

            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            textView.setText(getSelectedDatesString());

            for (int i = 0; i < mList.size(); i++) {
                Log.i("aaa", "length" + mList.get(i).get("title").length() + "");
                Log.i("aaa", mList.get(i).get("title").toString() + "");
            }
        }
    }
}
