package tw.dddssz.allschoolthings;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Calendar;

public class Leave extends AppCompatActivity implements View.OnClickListener
{


    private Button startbtn , endbtn ,sendbtn;
    private Spinner timespn1 , timespn2, leavespn;
    private String time1,time2;
    private String date_start,date_end,time_start,time_end,category,reason;
    private String TAG="ast=>";
    private EditText leavereason;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave);

        //-------------抓取遠端資料庫設定執行續------------------------------ 不知道跑多久,不加此段會閃退
        StrictMode.setThreadPolicy(new
                StrictMode.
                        ThreadPolicy.Builder().
                detectDiskReads().
                detectDiskWrites().
                detectNetwork().
                penaltyLog().
                build());
        StrictMode.setVmPolicy(
                new
                        StrictMode.
                                VmPolicy.
                                Builder().
                        detectLeakedSqlLiteObjects().
                        penaltyLog().
                        penaltyDeath().
                        build());
//---------------------------------------------------------------------

        setupViewComponent();
    }

    private void setupViewComponent()
    {
        startbtn =(Button)findViewById(R.id.m0001_bn01);
        endbtn =(Button)findViewById(R.id.m0001_bn02);
        sendbtn =(Button)findViewById(R.id.m0001_bn03);
        timespn1 =(Spinner)findViewById(R.id.m0001_spn01);
        timespn2 =(Spinner)findViewById(R.id.m0001_spn02);
        leavespn =(Spinner)findViewById(R.id.m0001_spn03);
        leavereason = (EditText)findViewById(R.id.m0001_et01);

                /*設定 spinner item 選項
        ArrayAdapter<CharSequence> adapSexList = ArrayAdapter.createFromResource(this,R.array.m0501_a001,android.R.layout.simple_spinner_item);
        adapSexList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s001.setAdapter(adapSexList);*/
        ArrayAdapter<CharSequence> adaptimeList1 = ArrayAdapter.createFromResource(this , R.array.time , android.R.layout.simple_spinner_item );
        adaptimeList1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adaptimeList2 = ArrayAdapter.createFromResource(this , R.array.time , android.R.layout.simple_spinner_item );
        adaptimeList2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapleaveList = ArrayAdapter.createFromResource(this , R.array.leave , android.R.layout.simple_spinner_item );
        adapleaveList.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        timespn1.setAdapter(adaptimeList1);
        timespn2.setAdapter(adaptimeList2);
        leavespn.setAdapter(adapleaveList);
        timespn1.setOnItemSelectedListener(itemclick1);  //注意是Spinner.OnItemSelectedListener的監聽事件, 不是onclicklistener , 會閃退
        timespn2.setOnItemSelectedListener(itemclick2);
        leavespn.setOnItemSelectedListener(itemclick3);

        startbtn.setOnClickListener(this);
        endbtn.setOnClickListener(this);
        sendbtn.setOnClickListener(this);
    }

    private Spinner.OnItemSelectedListener itemclick1=new Spinner.OnItemSelectedListener(){  //注意是Spinner.OnItemSelectedListener的監聽事件, 不是onclicklistener , 會閃退
        // 正確生成的話會有兩個空的建構式, 1.onItemSelected(AdapterView<?> parent, View view, int position, long id)  2.onNothingSelected(AdapterView<?> parent)
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            time1 = parent.getSelectedItem().toString().trim();
            Log.d(TAG,"time1:"+time1);
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {
        }
    };

    private Spinner.OnItemSelectedListener itemclick2=new Spinner.OnItemSelectedListener(){  //注意是Spinner.OnItemSelectedListener的監聽事件, 不是onclicklistener , 會閃退
        // 正確生成的話會有兩個空的建構式, 1.onItemSelected(AdapterView<?> parent, View view, int position, long id)  2.onNothingSelected(AdapterView<?> parent)
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            time2 = parent.getSelectedItem().toString().trim();
            Log.d(TAG,"time2:"+time2);
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {
        }
    };

    private Spinner.OnItemSelectedListener itemclick3=new Spinner.OnItemSelectedListener(){  //注意是Spinner.OnItemSelectedListener的監聽事件, 不是onclicklistener , 會閃退
        // 正確生成的話會有兩個空的建構式, 1.onItemSelected(AdapterView<?> parent, View view, int position, long id)  2.onNothingSelected(AdapterView<?> parent)
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            category = parent.getSelectedItem().toString().trim();
            Log.d(TAG,"category:"+category);
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {
        }
    };

    @Override
    public void onClick(View v)
    {
        Calendar now = Calendar.getInstance();
        switch (v.getId()){
            case R.id.m0001_bn01:  //開始日期
                DatePickerDialog datePicDlg = new DatePickerDialog(Leave.this,
                        datePicDlgOnDateSelLis,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH));

//                datePicDlg.setTitle(getString(R.string.m0901_datetitle));
                datePicDlg.setMessage(getString(R.string.m0001_bn01));
                datePicDlg.setIcon(android.R.drawable.ic_dialog_info);
                datePicDlg.setCancelable(false);
                datePicDlg.show();
                break;

            case  R.id.m0001_bn02:  //結束日期
                DatePickerDialog datePicDlg2 = new DatePickerDialog(Leave.this,
                        datePicDlgOnDateSelLis2,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH));

//                datePicDlg.setTitle(getString(R.string.m0901_datetitle));
                datePicDlg2.setMessage(getString(R.string.m0001_bn02));
                datePicDlg2.setIcon(android.R.drawable.ic_dialog_info);
                datePicDlg2.setCancelable(false);
                datePicDlg2.show();
                break;

            case R.id.m0001_bn03:  //送出
                date_start = startbtn.getText().toString().trim();
                date_end = endbtn.getText().toString().trim();
                reason = leavereason.getText().toString().trim();
                if(date_start.equals("") || date_end.equals("") || time1.equals("") || time2.equals("") || category.equals("") || reason.equals("") ){
                    Toast.makeText(getApplicationContext(), getString(R.string.m0001_toast), Toast.LENGTH_SHORT).show();

                }else{
                    mysql_insert();
                    Toast.makeText(getApplicationContext(), getString(R.string.m0001_success), Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private DatePickerDialog.OnDateSetListener datePicDlgOnDateSelLis = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            if(monthOfYear>=9){
                startbtn.setText( Integer.toString(year) + "-"+
                        Integer.toString(monthOfYear + 1) +  "-"+
                        Integer.toString(dayOfMonth)  );
                //getString(R.string.n_yy)+getString(R.string.n_mm) +getString(R.string.n_dd)
            }else{
                startbtn.setText( Integer.toString(year) + "-0"+
                        Integer.toString(monthOfYear + 1) +  "-"+
                        Integer.toString(dayOfMonth)  );
                //getString(R.string.n_yy)+getString(R.string.n_mm) +getString(R.string.n_dd)
            }
        }
    };

    private DatePickerDialog.OnDateSetListener datePicDlgOnDateSelLis2 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            if(monthOfYear>=9){
                endbtn.setText( Integer.toString(year) + "-"+
                        Integer.toString(monthOfYear + 1) +  "-"+
                        Integer.toString(dayOfMonth)  );
                //getString(R.string.n_yy)+getString(R.string.n_mm) +getString(R.string.n_dd)
            }else{
                endbtn.setText( Integer.toString(year) + "-0"+
                        Integer.toString(monthOfYear + 1) +  "-"+
                        Integer.toString(dayOfMonth)  );
                //getString(R.string.n_yy)+getString(R.string.n_mm) +getString(R.string.n_dd)
            }

        }
    };

    //-------------------------------------------------------
    private void mysql_insert() {
        //id	date_start	time_start	date_end	time_end	category	reason	student_id	parent_id	teacher_id	absent_id
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("date_start", date_start));  //注意日期傳送資料型態可被資料庫接收2019-03-10不能用2019/03/10
        nameValuePairs.add(new BasicNameValuePair("time_start", time1));
        nameValuePairs.add(new BasicNameValuePair("date_end", date_end));  //注意日期傳送資料型態可被資料庫接收2019-03-10不能用2019/03/10
        nameValuePairs.add(new BasicNameValuePair("time_end", time2));
        nameValuePairs.add(new BasicNameValuePair("category", category));
        nameValuePairs.add(new BasicNameValuePair("reason", reason));
        nameValuePairs.add(new BasicNameValuePair("student_id", "1"));
        nameValuePairs.add(new BasicNameValuePair("parent_id", "1"));
        nameValuePairs.add(new BasicNameValuePair("teacher_id", "1"));
        nameValuePairs.add(new BasicNameValuePair("absent_id", "1"));

        //-----------------------------------------------
        String result = DBConnector.executeInsert("SELECT * FROM leave_record", nameValuePairs);
        //-----------------------------------------------
        Log.d(TAG,""+nameValuePairs);
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
