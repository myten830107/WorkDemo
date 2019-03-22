package tw.dddssz.allschoolthings;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements View.OnClickListener
{

    private CircleImgbutton login_logo;
    private EditText username,password;
    private Button login_btn;
    private String username_data,pwd_data;
    private static String TAG="ast=>";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        login_logo = (CircleImgbutton)findViewById(R.id.m0004_logo);
        username = (EditText)findViewById(R.id.m0004_username);
        password = (EditText)findViewById(R.id.m0004_pwd);
        login_btn = (Button)findViewById(R.id.m004_btn01);
        login_btn.setOnClickListener(this);

        // 動態調整高度 抓取使用裝置尺寸
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // String px = displayMetrics.widthPixels + " x " +
        // displayMetrics.heightPixels;
        // String dp = displayMetrics.xdpi + " x " + displayMetrics.ydpi;
        // String density = "densityDpi = " + displayMetrics.densityDpi +
        // ", density=" + displayMetrics.density + ", scaledDensity = " +
        // displayMetrics.scaledDensity;
        // myname.setText(px + "\n" + dp + "\n" +density + "\n" +
        // newscrollheight);
        int newlogomargin = displayMetrics.widthPixels * 1 / 3; // 設定ScrollView使用尺寸的4/5
        int newlogomargin1 = displayMetrics.heightPixels * 1 / 8; // 設定ScrollView使用尺寸的4/5
        //viewPager.getLayoutParams().width = newviewPagerwidth;  // 重定ScrollView大小
        RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(newlogomargin,newlogomargin);  //RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
        rp.setMargins(newlogomargin, newlogomargin1, newlogomargin, 0);
        login_logo.setLayoutParams(rp);

    }


    @Override
    public void onClick(View v)
    {
        if(username.getText().toString().trim().length()!=10){
            Toast.makeText(getApplicationContext(),getString(R.string.username_toast),Toast.LENGTH_SHORT).show();
        }else{
            username_data = username.getText().toString().trim();
            pwd_data = password.getText().toString().trim();

            // 讀取MySQL 資料
            try {
                String result = DBConnector.executeQuery("SELECT * FROM account WHERE name='"+username_data+"' AND password='"+pwd_data+"'");  //注意變數要用單引號包住!!!
                /* SQL 結果有多筆資料時使用JSONArray jsonArray = new JSONArray(result);
                 *只有一筆資料時直接建立JSONObject物件 JSONObject jsonData = new JSONObject(result); */

                if (result.length()<=11){
                    Toast.makeText(getApplicationContext(),getString(R.string.pwd_toast),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),getString(R.string.login_success),Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray = new JSONArray(result);
                    if(jsonArray.length()==1){

                        JSONObject jsonData = jsonArray.getJSONObject(0);
                        Log.d(TAG,"jd:"+jsonData);

                        String name = jsonData.getString("name");
                        String identity_id = jsonData.getString("identity_id");
                        String email = jsonData.getString("email");

//做包裹---------------------------------------------------------------------------------------------------
                        Intent intent_login=new Intent(Login.this,MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("name",name);
//                bundle.putString("password",pwd);
                        bundle.putString("identity_id",identity_id);
                        bundle.putString("email",email);

                        intent_login.putExtras(bundle);
//                    intent_login.setClass();
                        startActivity(intent_login);
                        Log.d(TAG,"start");
                    }

                }


            } catch (Exception e) {
                 Log.e("log_tag", e.toString());
            }

        }

    }



}
