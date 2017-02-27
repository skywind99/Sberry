package kr.hs.namgong.jms;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SingleItemView extends Activity implements View.OnClickListener {
    // Declare Variables
    String title;
    private SingleItemView mContext;
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private String downUrl;
    private Button btn;
    private Button school_home_btn;
    String name;
    String date;
    String href;
    String position;
    //    ImageLoader imageLoader = new ImageLoader(this);
    ProgressDialog mProgressDialog;
    String url;
    ArrayList<HashMap<String, String>> arraylist;
    String imgSrcStr;
    TextView txtrank;
    TextView txttail;
    TextView txtdetail;
    TextView txtcountry;
    Elements main_text;
    Element tail_text;
    Element aa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        // Get the view from singleitemview.xml
        setContentView(R.layout.notice_detail);

        Intent i = getIntent();
        // Get the result of rank
//        title = i.getStringExtra("title");
        // Get the result of country
        href = i.getStringExtra("href");
        url = "http://jingeon.ms.kr" + href;
        // Get the result of population
//        name = i.getStringExtra("name");
        // Get the result of flag
//        date = i.getStringExtra("date");

//        Toast.makeText(getBaseContext(), href, Toast.LENGTH_LONG).show();
        // Locate the TextViews in singleitemview.xml
        txtrank = (TextView) findViewById(R.id.content_subject);
        txtcountry = (TextView) findViewById(R.id.content_date);
        TextView txtpopulation = (TextView) findViewById(R.id.content_writer);
        txtdetail = (TextView) findViewById(R.id.textView_detail);
        txttail = (TextView) findViewById(R.id.textView_tail);
        btn = (Button) findViewById(R.id.btn);

        txtrank.setText(title);
        txtcountry.setText(name);
        txtpopulation.setText(date);
        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class
        // imageLoader.DisplayImage(flag, imgflag);

        new Title().execute();

        btn.setOnClickListener(this);

    }

    private class Title extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(SingleItemView.this);
            mProgressDialog.setTitle("Android Basic JSoup Tutorial");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create an array
            arraylist = new ArrayList<HashMap<String, String>>();

            try {
                // Connect to the Website URL
                Document doc = Jsoup.connect(url).get();
                // Document doc = Jsoup.parse(url, "", Parser.xmlParser());

                // Identify Table Class "worldpopulation"
                // Log.i("aaa", doc.toString());
                main_text = doc.select("div.board");
                aa = main_text.select("div.boardReadBody").get(0);

                Log.i("aaa", main_text.text());
                tail_text = doc.select("div.boradReadFooter").get(1);
                Elements imgSrc = tail_text.select("a[href]");
                // Get only src from img src
                downUrl = imgSrc.attr("href");

                //skywind99

                // for (Element table : doc.select("div.boardReadBody
                // smartOutput")) {
                // Log.i("aaa", table.toString());
                // // Identify all the table row's(tr)
                // for (Element row : table.select("a")) {
                // HashMap<String, String> map = new HashMap<String, String>();
                // Log.i("aaa", row.toString());
                // Elements imgSrc = row.select("img[content]");
                // // Get only src from img src
                // imgSrcStr = imgSrc.attr("content");
                //
                // // Retrive Jsoup Elements
                // // Get the first td
                // map.put("rank", row.text());
                // // Get the second td
                // map.put("country", imgSrcStr);
                // // Get the third td
                // // map.put("population", tds.get(2).text());
                // // Get the image src links
                // // map.put("flag", imgSrcStr);
                // // Set all extracted Jsoup Elements into the array
                // arraylist.add(map);
                // }
                // }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // ImageView iv = new ImageView(SingleItemView.this);
            // iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
            // LayoutParams.MATCH_PARENT));
            // iv.setBackgroundResource(R.id.Slayout);
            txtdetail.setText(Html.fromHtml(aa.toString()));
            txttail.setText(tail_text.text());
            if (txttail.getText().toString().isEmpty()) {
                btn.setVisibility(View.INVISIBLE);
            } else {
                btn.setVisibility(View.VISIBLE);
            }
//            Toast.makeText(getBaseContext(), imgSrcStr, Toast.LENGTH_LONG).show();

            // listview = (ListView) findViewById(R.id.listview1);
            // // Pass the results into ListViewAdapter.java
            // adapter = new ListViewAdapter(SingleItemView.this, arraylist);
            // // Set the adapter to the ListView
            // listview.setAdapter(adapter);
            // // Close the progressdialog

            mProgressDialog.dismiss();
        }
    }

    private void checkPermission() {
        //사용 권한이 있는지 체크 (없으면 -1, 있으면 0)
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            //최초 권한 요청인지 사용자에 의한 재요청인지 체크
            if (ActivityCompat.shouldShowRequestPermissionRationale(mContext,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //재요청인 경우
                ActivityCompat.requestPermissions(mContext,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            } else {
                //최초 요청인 경우
                ActivityCompat.requestPermissions(mContext,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        } else {
            //권한이 있는 경우
            download(downUrl, "다운로드", "다운로드중..", ""); //다운로드
            //url, 제목, 설명, 추가경로
        }
    }

    //권한 동의, 거부관련 다이얼로그
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //권한 동의
                    download(downUrl, "다운로드", "다운로드중..", "");

                } else {
                    //권한 동의x
                    Toast.makeText(mContext, "동의 안하면 안 됨.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }
    }

    public void download(String url, String title, String des, String path) {
        DownloadManager mDownloadManager = null; //다운로드 매니저.
        int mDownloadQueueId; //다운로드 큐 아이디
        String mFileName; //파일다운로드 완료후...파일을 열기 위해 저장된 위치를 입력해둔다.
        if (mDownloadManager == null) {
            mDownloadManager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
        }
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(title);
        request.setDescription(des);
        List<String> pathSegmentList = Uri.parse(url).getPathSegments();
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/" + path).mkdirs(); //설치 경로
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS + "/" + path + "/", pathSegmentList.get(pathSegmentList.size() - 1));
        mFileName = pathSegmentList.get(pathSegmentList.size() - 1);
        Toast.makeText(SingleItemView.this, "Download 폴더에 저장되었습니다.", Toast.LENGTH_SHORT).show();

        mDownloadQueueId = (int) mDownloadManager.enqueue(request);

    }

    @Override
    public void onClick(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle("다운로드")
                .setMessage(downUrl)
                .setCancelable(true)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkPermission(); //퍼미션 체크 및 다운로드 시작

                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        Dialog dialog = builder.create();
        dialog.show();
    }
}
