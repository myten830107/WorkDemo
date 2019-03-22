package tw.dddssz.allschoolthings;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class DBConnector
{
    //宣告類別變數以方便存取，並判斷是否連線成功
    public static int httpstate = 0;  //final
    static String result = null;
    static String TAG = "ast=>";
    // ---------------------------
    static InputStream is = null;
    static String line = null;
    static int code;
    static String mysql_code = null;

    //=========================================================================================
//    static String connect_ip ="http://192.168.0.88/android_mysql_connect/"; //localhost
//    static String connect_ip ="http://www.oldpa.tw/android_mysql_connect/"; //智邦
//    static String connect_ip = "http://oldpa88.esy.es/android_mysql_connect/";//Hostinger
//=========================================================================================
//-------000webhost-------
    static String connect_ip = "https://tcnr1608.000webhostapp.com/android_ast_mysql/";  //000webhost 注意資料夾連結名稱
    //-------000webhost--組長-----
//    static String connect_ip ="https://tcnr1091601.000webhostapp.com/android_mysql_connect/";
//    static String connect_ip ="https://tcnr1605.000webhostapp.com/android_mysql_connect/";
//    static String connect_ip ="https://tcnr1608.000webhostapp.com/android_mysql_connect/";
//    static String connect_ip ="https://tcnr1609.000webhostapp.com/android_mysql_connect/";
//    static String connect_ip ="https://tcnr1624.000webhostapp.com/android_mysql_connect/";//班長

    public static String executeQuery(String query_string)
    {
        try
        {
            Thread.sleep(500); // 延遲Thread 睡眠0.5秒
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        try
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(connect_ip + "ast_connect_db_all.php");  //要注意檔名!!!
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

            // selefunc_string -> 給php 使用的參數	query:選擇 insert:新增 update:更新 delete:刪除
            params.add(new BasicNameValuePair("selefunc_string", "query"));

            // query_string -> 給php 使用的參數
            params.add(new BasicNameValuePair("query_string", query_string));
            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            // -----------------------------------------------------------------
            // 使用httpResponse的方法取得http 狀態碼設定給httpstate變數
            httpstate = httpResponse.getStatusLine().getStatusCode();
            Log.d(TAG, "dddd");
            // -----------------------------------------------------------------
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream inputStream = httpEntity.getContent();
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = bufReader.readLine()) != null)
            {
                builder.append(line + "\n");
            }
            inputStream.close();
            result = builder.toString();
        } catch (Exception e)
        {
            Log.d("TAG", "Exception e" + e.toString());
        }
        return result;
    }


    // ---新增資料--------------------------------------------------------------
    public static String executeInsert(String string, ArrayList<NameValuePair> nameValuePairs) {
        is = null;
        result = null;
        line = null;
        try {
            Thread.sleep(500); // 延遲Thread 睡眠0.5秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // ---- 連結MySQL-------------------
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(connect_ip + "ast_connect_db_all.php");  //要注意檔名!!!
            //-----------------------------------
            // selefunc_string -> 給php 使用的參數	query:選擇 insert:新增 update:更新 delete:刪除
            nameValuePairs.add(new BasicNameValuePair("selefunc_string", "insert"));
            //------------------------------------
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch (Exception e) {
            Log.d(TAG, "insert:新增錯誤1" + e.toString());
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
            Log.d(TAG, "reader:" + reader);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            Log.d(TAG, "insert:新增錯誤2:" + e.toString());
        }
        try {
            JSONObject json_data = new JSONObject(result);
            code = (json_data.getInt("code"));
            if (code != 1) Log.d(TAG, "insert:新增錯誤3:" + "..重試..");
        } catch (Exception e) {
            Log.d(TAG, "insert:新增錯誤4:" + e.toString());
        }
        return result;
    }





}
