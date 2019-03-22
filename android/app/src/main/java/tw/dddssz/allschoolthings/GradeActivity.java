package tw.dddssz.allschoolthings;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GradeActivity extends AppCompatActivity {

    private RecyclerView recycler_view;
//    private GradeAdapter adapter;
//    private ArrayList<String> mData = new ArrayList<>();

    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
//    private ArrayList<String> mData2 = new ArrayList<>();

    List<Grade> gradeList;

    RequestQueue rq;

    String request_url = "https://tcnr15-zihen.000webhostapp.com/allschoolthings/allschoolthings_android_score_api.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        //---------------------------------------------------
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
                .build());
        //---------------------------------------------------------

        // 準備資料，塞50個項目到ArrayList裡
//        for(int i = 1; i < 31; i++) {
//            mData.add("日期"+i);
//        }
        Spinner spinner = (Spinner)findViewById(R.id.spinner_schoolyear);
        String[] year = {"106 學年度第一學期", "106 學年度第二學期", "107 學年度第一學期", "107 學年度第二學期", "108 學年度第一學期","108 學年度第二學期"};
        ArrayAdapter<String> yearList = new ArrayAdapter<>(GradeActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                year);
        spinner.setAdapter(yearList);

        rq = Volley.newRequestQueue(this);
        // 連結元件
        recycler_view = (RecyclerView) findViewById(R.id.recycler_grade);
        // 設置RecyclerView為列表型態
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        // 設置格線
        recycler_view.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // 將資料交給adapter
//        adapter = new GradeAdapter(mData);
        // 設置adapter給recycler_view
//        recycler_view.setAdapter(adapter);

        gradeList = new ArrayList<>();
        sendRequest();
    }

    private void sendRequest() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for(int i = 0; i < response.length(); i++){

                    Grade grade = new Grade();

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        grade.setGradename(jsonObject.getString("name"));
                        grade.setGradescore(jsonObject.getString("score"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    gradeList.add(grade);

                }

                mAdapter = new GradeAdapter(GradeActivity.this,gradeList);

                recycler_view.setAdapter(mAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.i("Volley Error: ", error);
            }
        });

        rq.add(jsonArrayRequest);
    }

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


}
