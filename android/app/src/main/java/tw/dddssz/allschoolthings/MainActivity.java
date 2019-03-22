package tw.dddssz.allschoolthings;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tw.dddssz.allschoolthings.providers.FriendsContentProvider;

public class MainActivity extends AppCompatActivity{

    private DrawerLayout mDrawerLayout;

    //所需要申請的權限數組
    private static final String[] permissionsArray = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE };

    private List<String> permissionsList = new ArrayList<String>();

    //申請權限後的返回碼
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 1;

    private static ContentResolver mContRes;
    private  static ArrayList<String> recAry = new ArrayList<String>();
    private String[] MYCOLUMN = new String[]{"id","semester","height","weight","bmi","eye_l","eye_r","student_id","remark"  };  //資料表欄位名稱
    private static String TAG="ast=>";

    private Bundle bundle;
    private static String name,identity_id,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkRequiredPermission(this);

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
        dbmysql();  //將MySQL寫入SQLite

        setupViewComponent();
        loadFragment(new BulletinFragment());
    }

    private void setupViewComponent() {
        setupNewActionBar();
        setupDrawerView();
        setupBottomNavigation();
        //---------開始讀取bundle資料-----------
        bundle = this.getIntent().getExtras();  //----很重要!!很重要!!很重要!!所以說3遍 要讀取bundle的內容 必須要跟他在同一個方法裡面
//        Log.d(TAG,""+bundle);
        name = bundle.getString("name");
        identity_id = bundle.getString("identity_id");
        email = bundle.getString("email");
//        Log.d(TAG,"name:"+name);
//        Log.d(TAG,"identity_id:"+identity_id);
//        Log.d(TAG,"email:"+email);
    }

    private void setupNewActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_dashboard_black_24dp);
    }

    private void setupBottomNavigation() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
//        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_bar);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;

                switch (menuItem.getItemId()) {
                    case R.id.navigation_bulletin:
                        fragment = new BulletinFragment();
                        break;

                    case R.id.navigation_contact:
                        fragment = new ContactFragment();
                        break;

                    case R.id.navigation_communication:
                        fragment = new CommunicationFragment();
                        break;

                    case R.id.navigation_campus:
                        fragment = new CampusFragment();
                        break;
                    case R.id.navigation_activity:
                        fragment = new ParentChildFragment();
                        break;
                }

                return loadFragment(fragment);
            }
        });
    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
    private void setupDrawerView() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.drawer_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        // menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        switch (menuItem.getItemId()){
                            case R.id.drawer_grade:
                                Intent intent01 = new Intent(MainActivity.this, GradeActivity.class);
                                startActivity(intent01);
                                break;
                            case R.id.drawer_record:
                                Intent intent02 = new Intent(MainActivity.this,HealthRecord.class);
                                startActivity(intent02);
                                break;
                            case R.id.drawer_attend:
                                Intent intent03 = new Intent(MainActivity.this, AttendActivity.class);
                                startActivity(intent03);
                                break;
                            case R.id.drawer_leave:
                                Intent intent04 = new Intent(MainActivity.this,Leave.class);
                                startActivity(intent04);
                                break;
                            case R.id.drawer_setting:
                                break;
                            case R.id.drawer_layout:
                                break;

                        }
                        return true;
                    }
                });

    }

    private void checkRequiredPermission(final Activity activity){
        for (String permission : permissionsArray) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
            }
        }
        if(permissionsList.size()!=0){
            ActivityCompat.requestPermissions(activity,permissionsList.toArray(new
                    String[permissionsList.size()]),REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for(int i=0;i<permissions.length;i++){
                    if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
//                        Toast.makeText(getApplicationContext(),permissions[i]+"權限申請成功!",Toast.LENGTH_LONG).show();
                    }else{
//                        Toast.makeText(getApplicationContext(),"權限被拒絕："+permissions[i],Toast.LENGTH_LONG).show();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    // 讀取MySQL 資料
    private void dbmysql() {
        mContRes = getContentResolver();
        Cursor cur_dbmysql = mContRes.query(FriendsContentProvider.CONTENT_URI, MYCOLUMN, null, null, null);
        cur_dbmysql.moveToFirst(); // 一定要寫，不然會出錯
        // ------
        try {
            String result = DBConnector.executeQuery("SELECT * FROM health WHERE student_id=1 ORDER BY id");
            /** SQL 結果有多筆資料時使用JSONArray 只有一筆資料時直接建立JSONObject物件 JSONObject
             * jsonData = new JSONObject(result); */
            JSONArray jsonArray = new JSONArray(result);

            if (jsonArray.length() > 0) { // MySQL 連結成功有資料
                Uri uri = FriendsContentProvider.CONTENT_URI;
                mContRes.delete(uri, null, null); // 匯入前,刪除所有SQLite資料
                // ----------------------------

                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonData = jsonArray.getJSONObject(i);
                    //"id","semester","height","weight","bmi","eye_l","eye_r","student_id","remark"
                    ContentValues newRow = new ContentValues();
                    newRow.put("id", jsonData.getString("id").toString());
                    newRow.put("semester", jsonData.getString("semester").toString());
                    newRow.put("height", jsonData.getString("height").toString());
                    newRow.put("weight", jsonData.getString("weight").toString());
                    newRow.put("bmi", jsonData.getString("bmi").toString());
                    newRow.put("eye_l", jsonData.getString("eye_l").toString());
                    newRow.put("eye_r", jsonData.getString("eye_r").toString());
                    newRow.put("student_id", jsonData.getString("student_id").toString());
                    newRow.put("remark", jsonData.getString("remark").toString());
                    // ---------
                    mContRes.insert(FriendsContentProvider.CONTENT_URI, newRow);

                }
            }
        } catch (Exception e) {
            // Log.e("log_tag", e.toString());
        }
        cur_dbmysql.close();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        dbmysql();  //重抓MySQL到SQLite
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
