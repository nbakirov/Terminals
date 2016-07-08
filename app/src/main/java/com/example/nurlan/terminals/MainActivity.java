package com.example.nurlan.terminals;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.os.AsyncTask;

public class MainActivity extends AppCompatActivity {
    final String LOG_TAG = "nb_log";
    ProgressBar progressBar;
    private onbuttonclickHttpPost mTask = null;

    @Override
    // в классе онкреат нельзя ставить другие задачи, кроме того ,что проводится первая отрисовка

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        downloadPage();
    }

    private void downloadPage() {
        if (mTask != null
                && mTask.getStatus() != onbuttonclickHttpPost.Status.FINISHED) {
            mTask.cancel(true);
        }
        mTask = (onbuttonclickHttpPost) new onbuttonclickHttpPost().execute();

    }

    @Override

    // onDestroy сохраняет в отличие от finish данные. Например, для сворачивания приложения. cancel убивает задачу.
    //Служба которая сохраняет задачу, которая ставит на пауза - content manager or connectivity manager

    protected void onDestroy() {
        super.onDestroy();

        if (mTask != null
                && mTask.getStatus() != onbuttonclickHttpPost.Status.FINISHED) {
            mTask.cancel(true);
            mTask = null;
        }
    }

    /*
    класс для тяжелых задач AsyncTask
    работа с классом состоит из трех частей. Один метод обязательный


     */
    public class onbuttonclickHttpPost extends AsyncTask<String, Void, Void> {
        public class execute {
        }

        @Override
        //PreExecute метод вызывается перед загрузкой данных
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        /*
        Метод doInBackground для выполнения всей тяжелой работы - в нашем случае загрузка данных. Метод обязателен
        к реализации, если используется наследование AsyncTask
        */
        protected Void doInBackground(String... params) {
            byte[] result = null;
            String str = "";
            TerminalsDatabase db = new TerminalsDatabase(MainActivity.this);
           db.deleteDatabase();
            HttpClient httpclient = new DefaultHttpClient(); // httpclient - дефолтный веб-клиент используется для загрузки данных по ссылке
            HttpPost httppost = new HttpPost(
                    "http://doshcard.kg:8080/GetPoints"); // httppost для передачи данных, связанных с авторизацией, чтобы ссылку не показывать

            try {
                HttpResponse response = httpclient.execute(httppost); // response содержит все данные о странице в байтовом виде. Инфо о сервере, статус подключения страницы и т.п.

                StatusLine statusLine
                        = response.getStatusLine();
                String responseStr = EntityUtils.toString(response.getEntity());

                StringReader stringReader = new StringReader(responseStr);
                InputSource inputSource = new InputSource(stringReader);
                DocumentBuilderFactory dbfact = DocumentBuilderFactory
                        .newInstance();
                DocumentBuilder builder = null;

                DocumentBuilder builderCompany = null;

                try {
                    builder = dbfact.newDocumentBuilder();
                } catch (ParserConfigurationException ex) {
                    ex.printStackTrace();
                }


                Document doc = null;

                try {
                    doc = (Document) builder.parse(inputSource);
                    doc.getDocumentElement().normalize();
                    NodeList nodeLst = doc.getElementsByTagName("point");


                    for (int s = 0; s < nodeLst.getLength(); s++) {

                        Node fstNode = nodeLst.item(s);

                        if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

                            Element fstElmnt = (Element) fstNode;
                            NodeList fstNmElmntLst = fstElmnt
                                    .getElementsByTagName("point_id");

                            Element fstNmElmnt = (Element) fstNmElmntLst
                                    .item(0);
                            NodeList fstNm = fstNmElmnt.getChildNodes();


                            NodeList secNmElmntLst = fstElmnt
                                    .getElementsByTagName("point_name");
                            Element secNmElmnt = (Element) secNmElmntLst
                                    .item(0);
                            NodeList secNm = secNmElmnt.getChildNodes();

                            NodeList thrdNmElmntLst = fstElmnt
                                    .getElementsByTagName("point_lat");
                            Element thrdNmElmnt = (Element) thrdNmElmntLst
                                    .item(0);
                            NodeList thrdNm = thrdNmElmnt.getChildNodes();

                            NodeList frNmElmntLst = fstElmnt
                                    .getElementsByTagName("point_long");
                            Element frNmElmnt = (Element) frNmElmntLst.item(0);

                            NodeList frNm = frNmElmnt.getChildNodes();


                            Point point = new Point(

                                    ((Node) secNm.item(0)).getNodeValue()
                                            .toString(),
                                    Double.valueOf(((Node) thrdNm.item(0)).getNodeValue()
                                            .toString()),
                                    Double.valueOf(((Node) frNm.item(0)).getNodeValue()
                                            .toString()));

                            db.addPoint(point);
                        }

                    }


                } catch (SAXException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            } catch (ClientProtocolException e) {
            } catch (IOException e) {
            }

            return null;
        }

        /**
         * on getting result
         */
        @Override
        protected void onPostExecute(Void result) { // метод вызывается после выполнения тяжелой работы (загрузки данных). Необязательный к реализации, но рекомендуется.
            super.onPostExecute(result);
            progressBar.setProgress(0);
           Intent it = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(it);
            finish();
        }
    }

}
