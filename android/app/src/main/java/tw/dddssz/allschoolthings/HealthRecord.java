package tw.dddssz.allschoolthings;


import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import tw.dddssz.allschoolthings.providers.FriendsContentProvider;

//import tw.dddssz.allschoolthings.providers.FriendsContentProvider;

public class HealthRecord extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private String[] listFromResource , health_height ,health_weight ,bmi_array ,health_eye_l ,health_eye_r ,health_bmi_array;
    private double[] health_bmi;
    private ArrayList<Map<String, Object>> mList;
    private HashMap<String, Object> item;
    //    private String[] bmi_array;
    private double[] h;
    private ArrayList<String> recSet;
    public static String myselection = "";
    public static String myargs[] = new String[]{};
    public static String myorder = "id ASC"; // 排序欄位
    //--------------------------
    private static ContentResolver mContRes;
    private String[] MYCOLUMN = new String[]{"id","semester","height","weight","bmi","eye_l","eye_r","student_id","remark"  };  //資料表欄位名稱
    private  static ArrayList<String> recAry = new ArrayList<String>();
    int tcount;
    // ------------------
    String msg = null;

    String sqlctl = "SELECT * FROM health";  //semester`, `height`, `weight`, `bmi`, `eye_l`, `eye_r`, `student_id`  //height ,weight ,bmi ,eye_l ,eye_r
    String TAG = "tcnr08=>";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.health_record);

        //-------------抓取遠端資料庫設定執行續------------------------------ 不知道跑多久,不加此段會閃退
//        StrictMode.setThreadPolicy(new
//                StrictMode.
//                        ThreadPolicy.Builder().
//                detectDiskReads().
//                detectDiskWrites().
//                detectNetwork().
//                penaltyLog().
//                build());
//        StrictMode.setVmPolicy(
//                new
//                        StrictMode.
//                                VmPolicy.
//                                Builder().
//                        detectLeakedSqlLiteObjects().
//                        penaltyLog().
//                        penaltyDeath().
//                        build());
//---------------------------------------------------------------------

//        Mysqlsel(sqlctl);
//        dbmysql();
        setupViewComponent();
    }

    private void setupViewComponent()
    {
//        recSet =recAry;

        recSet = u_selectdb(myselection, myargs, myorder);

        //學期
//        listFromResource = getResources().getStringArray(R.array.m0003_semester);  //字串陣列
        //身高
//        health_height = getResources().getStringArray(R.array.m0003_height);
        //體重
//        health_weight = getResources().getStringArray(R.array.m0003_weight);
        //BMI值
//        String[] bmi_array=new String[listFromResource.length];  //宣告陣列長度
//        for(int i=0; i<listFromResource.length ; i++ ){
//            double h = Double.parseDouble(health_height[i]);
//            double bmi_height = h/100;
//            double bmi_weight= Double.parseDouble(health_weight[i]);
//            double bmi =bmi_weight/(bmi_height*bmi_height);
//            bmi_array[i] =String.format("%.4f", bmi);
//        }
//            java保留小数--四舍五入--想保留几位就几位
//            String.format("%.nf",d);----表示保留N位！！！format("%.nf",double)

        //BMI值建議
        //視力L/R

        mList = null;  //先清空再重新放陣列

        mList = new ArrayList<Map<String, Object>>();
//        item = new HashMap<String, Object>();

        health_bmi =new double[recSet.size()];
        health_bmi_array =new String[recSet.size()];


        for (int i = 0; i < recSet.size(); i++) {
            String bmi_sug="";
            Map<String, Object> item = new HashMap<String, Object>();
            String[] fld = recSet.get(i).split("#");

            for(int j=0; j < recSet.size(); j++){
                health_bmi[j] =  Double.parseDouble(fld[4]);
                if(health_bmi[j]<=14.15){
                    bmi_sug = "您的小孩體重過輕，建議多補充營養";
                }else if(health_bmi[j]>14.15&&health_bmi[j]<=19.3){
                    bmi_sug = "您的小孩體重正常，繼續健康成長吧！";
                }else if(health_bmi[j]>19.3&&health_bmi[j]<=21.8){
                    bmi_sug = "您的小孩體重過重，建議調整飲食多運動";
                }else if(health_bmi[j]>21.8){
                    bmi_sug = "您的小孩屬於肥胖，建議讓小孩養成良好飲食及運動習慣";
                }

                health_bmi_array[j] = bmi_sug;
            }


            item.put("semester", fld[1]);
            item.put("height",fld[2]);  // health_height[i]
            item.put("weight",fld[3]);  //health_weight[i]
            item.put("bmi",fld[4]);  //bmi_array[i]
            item.put("bmi_sug",health_bmi_array[i]);
            item.put("eye","裸視:  " + fld[5] +"   /   裸視:  " + fld[6]);  //health_eye_l[i]  health_eye_r[i]

            mList.add(item);
//            item.put("txtView", "id:" + fld[0] + "\nname:" + fld[1] + "\ngroup:" + fld[2] + "\naddr:" + fld[3]);
        }

        ListView listview = (ListView)findViewById(R.id.health_list);

        SimpleAdapter adapter = new SimpleAdapter(this, mList,
                R.layout.health_list_item,
                new String[]{"semester","height","weight","bmi","bmi_sug","eye"},
                new int[]{R.id.m0003_tt00 ,R.id.m0003_tt01 ,R.id.m0003_tt02,R.id.m0003_tt03,R.id.m0003_tt04,R.id.m0003_tt05 }
        );

        // "height" ,"weight" , "bmi"  , "sug" ,"vision"
        // R.id.m0003_tt01 ,  R.id.m0003_tt02 ,  R.id.m0003_tt03 ,  R.id.m0003_tt04 ,  R.id.m0003_tt005

        listview.setAdapter(adapter);
//        lsetListAdapter(adapter);
        //----------------------------------------------------------------

//        ListView listview = getListView();
        //自動調整螢幕高度
        listview.setTextFilterEnabled(true);
        listview.setOnItemClickListener(listviewOnItemClkLis);
    }



    // 讀取MySQL 資料
//    private void dbmysql() {
//        mContRes = getContentResolver();
//        // --------------------------- 先刪除 SQLite 資料------------
//        Uri uri = FriendsContentProvider.CONTENT_URI;
//        mContRes.delete(uri, null, null); // 刪除所有資料
//        Cursor cur_dbmysql = mContRes.query(FriendsContentProvider.CONTENT_URI, MYCOLUMN, null, null, null);
//        cur_dbmysql.moveToFirst(); // 一定要寫，不然會出錯
//        // ------
//        try {
//            String result = DBConnector.executeQuery("SELECT * FROM health WHERE student_id=1 ORDER BY id");
//            Log.d(TAG,"r="+result);
//            /** SQL 結果有多筆資料時使用JSONArray 只有一筆資料時直接建立JSONObject物件 JSONObject
//             * jsonData = new JSONObject(result); */
//            JSONArray jsonArray = new JSONArray(result);
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonData = jsonArray.getJSONObject(i);
//                //"id","semester","height","weight","bmi","eye_l","eye_r","student_id","remark"
//                ContentValues newRow = new ContentValues();
//                newRow.put("id", jsonData.getString("id").toString());
//                newRow.put("semester", jsonData.getString("semester").toString());
//                newRow.put("height", jsonData.getString("height").toString());
//                newRow.put("weight", jsonData.getString("weight").toString());
//                newRow.put("bmi", jsonData.getString("bmi").toString());
//                newRow.put("eye_l", jsonData.getString("eye_l").toString());
//                newRow.put("eye_r", jsonData.getString("eye_r").toString());
//                newRow.put("student_id", jsonData.getString("student_id").toString());
//                newRow.put("remark", jsonData.getString("remark").toString());
//                // ---------
//                mContRes.insert(FriendsContentProvider.CONTENT_URI, newRow);
//
//            }
//        } catch (Exception e) {
//            // Log.e("log_tag", e.toString());
//        }
//        cur_dbmysql.close();
//        Cursor cur = mContRes.query(FriendsContentProvider.CONTENT_URI, MYCOLUMN, null, null, null);
//        cur.moveToFirst(); // 一定要寫，不然會出錯
//        // ---------------------------
//        try {
//            String result = DBConnector.executeQuery("SELECT * FROM health");
//            //以下程式碼一定要放在前端藍色程式碼執行之後，才能取得狀態碼
//            //存取類別成員 DBConnector.httpstate 判定是否回應 200(連線要求成功)
//            Log.d(TAG, "httpstate=" + DBConnector.httpstate);
//            if (DBConnector.httpstate == 200) {
//                Uri uri = FriendsContentProvider.CONTENT_URI;
//                mContRes.delete(uri, null, null);
//                Toast.makeText(getBaseContext(), "已經完成由伺服器會入資料",
//                        Toast.LENGTH_LONG).show();
//            } else {
//                int checkcode = DBConnector.httpstate / 100;
//                switch (checkcode) {
//                    case 1:
//                        msg = "資訊回應(code:" + DBConnector.httpstate + ")";
//                        break;
//                    case 2:
//                        msg = "已經完成由伺服器會入資料(code:" + DBConnector.httpstate + ")";
//                        break;
//                    case 3:
//                        msg = "伺服器重定向訊息，請稍後在試(code:" + DBConnector.httpstate + ")";
//                        break;
//                    case 4:
//                        msg = "用戶端錯誤回應，請稍後在試(code:" + DBConnector.httpstate + ")";
//                        break;
//                    case 5:
//                        msg = "伺服器error responses，請稍後在試(code:" + DBConnector.httpstate + ")";
//                        break;
//                }
//                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
//            }
//            // 選擇讀取特定欄位
//            // String result = DBConnector.executeQuery("SELECT id,name FROM
//            // member");
//            /*******************************************************************************************
//             * SQL 結果有多筆資料時使用JSONArray 只有一筆資料時直接建立JSONObject物件 JSONObject
//             * jsonData = new JSONObject(result);
//             *******************************************************************************************/
//            JSONArray jsonArray = new JSONArray(result);
//            // -------------------------------------------------------
//            if (jsonArray.length() > 0) { // MySQL 連結成功有資料
//                Uri uri = FriendsContentProvider.CONTENT_URI;
//                mContRes.delete(uri, null, null); // 匯入前,刪除所有SQLite資料
//                // ----------------------------
//                // 處理JASON 傳回來的每筆資料
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonData = jsonArray.getJSONObject(i);
//
//                    ContentValues newRow = new ContentValues();
//                    // --(1) 自動取的欄位
//                    // --取出 jsonObject
//                    // 每個欄位("key","value")-----------------------
//                    Iterator itt = jsonData.keys();
//                    while (itt.hasNext()) {
//                        String key = itt.next().toString();
//                        String value = jsonData.getString(key); // 取出欄位的值
//                        if (value == null) {
//                            continue;
//                        } else if ("".equals(value.trim())) {
//                            continue;
//                        } else {
//                            jsonData.put(key, value.trim());
//                        }
//                        // ------------------------------------------------------------------
//                        newRow.put(key, value.toString()); // 動態找出有幾個欄位
//                        // -------------------------------------------------------------------
//                        Log.d(TAG, "第" + i + "個欄位 key:" + key + " value:" + value);
//
//                    }
//                    // ---(2) 使用固定已知欄位---------------------------
//                    // newRow.put("id", jsonData.getString("id").toString());
//                    // newRow.put("name",
//                    // jsonData.getString("name").toString());
//                    // newRow.put("grp", jsonData.getString("grp").toString());
//                    // newRow.put("address", jsonData.getString("address")
//                    // .toString());
//                    // -------------------加入SQLite---------------------------------------
//                    mContRes.insert(FriendsContentProvider.CONTENT_URI, newRow);
//                }
//                // ---------------------------
//            } else {
//                Toast.makeText(M0003.this, "主機資料庫無資料", Toast.LENGTH_LONG).show();
//            }
//            // --------------------------------------------------------
//        } catch (Exception e) {
//            Log.e("log_tag", e.toString());
//        }
//        cur.close();
        // ---------------------------------------
//        sqliteupdate(); // 抓取SQLite資料
        // ----------------------------------------
//    }


    private ArrayList<String> u_selectdb(String myselection, String[] myargs, String myorder) {
        mContRes = getContentResolver();  //必須加此行
        Cursor c = mContRes.query(FriendsContentProvider.CONTENT_URI, MYCOLUMN, null, null, null);
//        tcount = c.getCount();
        int columnCount = c.getColumnCount();
        while (c.moveToNext()) {
            String fldSet = "";
            for (int ii = 0; ii < columnCount; ii++)
                fldSet += c.getString(ii) + "#";
            recAry.add(fldSet);
        }
        c.close();
        return recAry;
    }

//    private void Mysqlsel(String sqlctl)
//    {
////        db = DbOpenHelper.getWritableDatabase();
//        //------------------------------------------------------
//        try {
//            String result = DBConnector.executeQuery(sqlctl);
//            /**************************************************************************
//             * SQL 結果有多筆資料時使用JSONArray
//             * 只有一筆資料時直接建立JSONObject物件 JSONObject
//             * jsonData = new JSONObject(result);
//             **************************************************************************/
//            JSONArray jsonArray = new JSONArray(result);
//            //宣告字串長度
//            health_height=new String[jsonArray.length()];
//            health_weight=new String[jsonArray.length()];
//            bmi_array=new String[jsonArray.length()];
//            health_eye_l=new String[jsonArray.length()];
//            health_eye_r=new String[jsonArray.length()];
//            // ---
//            for (int i = 0; i < jsonArray.length(); i++) {  //幾筆資料
//                JSONObject jsonData = jsonArray.getJSONObject(i);  //JSONObject幾個欄位
//                // // 取出 jsonObject 中的字段的值的空格
//                Iterator itt = jsonData.keys();
////                TableRow tr = new TableRow(M1416.this);  //新增欄位
////                tr.setLayoutParams(row_layout);
////    tr.setGravity(Gravity.CENTER_HORIZONTAL);
//                while (itt.hasNext()) {
//                    String key = itt.next().toString();  //欄位名稱
//                    String value = jsonData.getString(key);  //值
//                    if (value == null) {
//                        continue;
//                    } else if ("".equals(value.trim())) {
//                        continue;
//                    } else {
//                        jsonData.put(key, value.trim());
//                    }
//                    // --
////                    TextView tv = new TextView(M1416.this);// tv 繼承TextView
////                    tv.setId(i); // 寫入配置碼ID 代號
////                    tv.setText(value);
////                    tv.setLayoutParams(view_layout);
////                    tr.addView(tv);
//                    switch (key){   //semester`, `height`, `weight`, `bmi`, `eye_l`, `eye_r`, `student_id`
//                        case "height":
//                            health_height[i]=value;
//                            Log.d(TAG, health_height[i]);
//                            break;
//                        case "weight":
//                            health_weight[i]=value;
//                            Log.d(TAG, health_weight[i]);
//                            break;
//                        case "bmi":
//                            bmi_array[i]=value;
//                            Log.d(TAG, bmi_array[i]);
//                            break;
//                        case "eye_l":
//                            health_eye_l[i]=value;
//                            Log.d(TAG, health_eye_l[i]);
//                            break;
//                        case "eye_r":
//                            health_eye_r[i]=value;
//                            Log.d(TAG, health_eye_r[i]);
//                            break;
//                    }
//
//                }
////                user_list.addView(tr);
//
//            }
//
//        } catch (Exception e) {
//            Log.d(TAG, e.toString());
//        }
//    }

    AdapterView.OnItemClickListener listviewOnItemClkLis = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {
            //mTxtResult.setText(listFromResource[position]);
        }
    };


//    private ArrayList<String> u_selectdb(String myselection, String[] myargs, String myorder) {
//        ArrayList<String> recAry = new ArrayList<String>();
//        mContRes = getContentResolver();
//        Cursor c = mContRes.query(FriendsContentProvider.CONTENT_URI, MYCOLUMN, null, null, null);
//        tcount = c.getCount();
//        int columnCount = c.getColumnCount();
//        while (c.moveToNext()) {
//            String fldSet = "";
//            for (int ii = 0; ii < columnCount; ii++)
//                fldSet += c.getString(ii) + "#";
//            recAry.add(fldSet);
//        }
//        c.close();
//        return recAry;
//    }


    @Override
    public void onBackPressed()
    {
//        super.onBackPressed();
        Toast.makeText(getApplicationContext(),getString(R.string.back_toast),Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer_back, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()){
            case R.id.action_settings:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        return false;
    }
}


