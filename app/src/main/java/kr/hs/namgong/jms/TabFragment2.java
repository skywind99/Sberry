package kr.hs.namgong.jms;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class TabFragment2 extends Fragment {
    String url = "http://jingeon.ms.kr/main";
    ProgressDialog mProgressDialog;
    TextView txttitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_fragment_2, container, false);
        Button titlebutton = (Button) view.findViewById(R.id.titlebutton);
// Capture button click
        txttitle = (TextView) view.findViewById(R.id.titletxt);
        titlebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Execute Title AsyncTask
                new Title().execute();
            }
        });


        return view;
    }

    private class Title extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setTitle("Android Basic JSoup Tutorial");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect(url).get();
                // Get the html document title
                title = document.select("span.date").text();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set title into TextView

            txttitle.setText(title);
            mProgressDialog.dismiss();
        }
    }

}
