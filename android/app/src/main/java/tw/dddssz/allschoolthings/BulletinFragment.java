package tw.dddssz.allschoolthings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class BulletinFragment extends Fragment  implements View.OnClickListener {

    //    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.m0000);
//    }
    ViewPager viewPager;
    SliderViewPagerAdapter adapter;
    LinearLayout sliderDots;
    private int dotCounts;
    private ImageView[] dots;
    private TextView t001,t002,t003,t004,t005,t006,t007;
    private TextView t011,t012,t013,t014,t015,t016,t017;
    private TextView t021,t022,t023,t024,t025,t026,t027;
    private CircleImgbutton b001;
    private TextView t000;
    private LinearLayout li01;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bulletin, null);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setupViewComponent();
    }

    private void setupViewComponent()
    {
        t000 = (TextView)getView().findViewById(R.id.m0000_t000);
        t001 = (TextView)getView().findViewById(R.id.m0000_t001);
        t002 = (TextView)getView().findViewById(R.id.m0000_t002);
        t003 = (TextView)getView().findViewById(R.id.m0000_t003);
        t004 = (TextView)getView().findViewById(R.id.m0000_t004);
        t005 = (TextView)getView().findViewById(R.id.m0000_t005);
        t006 = (TextView)getView().findViewById(R.id.m0000_t006);
        t007 = (TextView)getView().findViewById(R.id.m0000_t007);

        t011 = (TextView)getView().findViewById(R.id.m0000_t011);
        t012 = (TextView)getView().findViewById(R.id.m0000_t012);
        t013 = (TextView)getView().findViewById(R.id.m0000_t013);
        t014 = (TextView)getView().findViewById(R.id.m0000_t014);
        t015 = (TextView)getView().findViewById(R.id.m0000_t015);
        t016 = (TextView)getView().findViewById(R.id.m0000_t016);
        t017 = (TextView)getView().findViewById(R.id.m0000_t017);

        t021 = (TextView)getView().findViewById(R.id.m0000_t021);
        t022 = (TextView)getView().findViewById(R.id.m0000_t022);
        t023 = (TextView)getView().findViewById(R.id.m0000_t023);
        t024 = (TextView)getView().findViewById(R.id.m0000_t024);
        t025 = (TextView)getView().findViewById(R.id.m0000_t025);
        t026 = (TextView)getView().findViewById(R.id.m0000_t026);
        t027 = (TextView)getView().findViewById(R.id.m0000_t027);

        li01 = (LinearLayout)getView().findViewById(R.id.m0000_li01);


        b001=(CircleImgbutton)getView().findViewById(R.id.m0000_b001);
        b001.setOnClickListener(this);
        viewPager = getView().findViewById(R.id.viewPager);
        adapter = new SliderViewPagerAdapter(getActivity());
        viewPager.setAdapter(adapter);


        // 動態調整高度 抓取使用裝置尺寸
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // String px = displayMetrics.widthPixels + " x " +
        // displayMetrics.heightPixels;
        // String dp = displayMetrics.xdpi + " x " + displayMetrics.ydpi;
        // String density = "densityDpi = " + displayMetrics.densityDpi +
        // ", density=" + displayMetrics.density + ", scaledDensity = " +
        // displayMetrics.scaledDensity;
        // myname.setText(px + "\n" + dp + "\n" +density + "\n" +
        // newscrollheight);
        int newviewPagerheight = displayMetrics.heightPixels * 8 / 20; // 設定ScrollView使用尺寸的4/5
        int newviewPagerwidth = displayMetrics.widthPixels * 1 / 12; // 設定ScrollView使用尺寸的4/5
        int newviewPagerheight_tb = displayMetrics.heightPixels * 1 / 60; // 設定ScrollView使用尺寸的4/5
        viewPager.getLayoutParams().height = newviewPagerheight;  // 重定ScrollView大小
        //viewPager.getLayoutParams().width = newviewPagerwidth;  // 重定ScrollView大小
        viewPager.setPadding( newviewPagerwidth , newviewPagerheight_tb , newviewPagerwidth ,0 );  //左上右下;   下方newviewPagerheight_tb

        int newtextheight = displayMetrics.heightPixels * 1 / 6; // 設定ScrollView使用尺寸的4/5
        int newtextwidth = displayMetrics.widthPixels * 1 / 6; // 設定ScrollView使用尺寸的4/5
//        t001.getLayoutParams().width = newtextwidth;
//        t001.getLayoutParams().height = newtextheight;
        b001.getLayoutParams().width = newtextwidth;
        b001.getLayoutParams().height = newtextheight;

        int newmargin = displayMetrics.widthPixels * 19/20;
        t000.setWidth(newmargin);
        t000.getBackground().setAlpha(155);
//        int newmargin1 = displayMetrics.widthPixels * 19/140;
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(newmargin1,newmargin1);  //RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
//        li01.setLayoutParams(lp);

        int trpadding = displayMetrics.widthPixels * 1/24;  //1 / 25
        int trpaddingheight = displayMetrics.heightPixels * 1 / 20;
        t001.setPadding(trpadding,0,trpadding,trpaddingheight);
        t002.setPadding(trpadding,0,trpadding,trpaddingheight);
        t003.setPadding(trpadding,0,trpadding,trpaddingheight);
        t004.setPadding(trpadding,0,trpadding,trpaddingheight);
        t005.setPadding(trpadding,0,trpadding,trpaddingheight);
        t006.setPadding(trpadding,0,trpadding,trpaddingheight);
        t007.setPadding(trpadding,0,trpadding,trpaddingheight);

        t011.setPadding(trpadding,0,trpadding,trpaddingheight);
        t012.setPadding(trpadding,0,trpadding,trpaddingheight);
        t013.setPadding(trpadding,0,trpadding,trpaddingheight);
        t014.setPadding(trpadding,0,trpadding,trpaddingheight);
        t015.setPadding(trpadding,0,trpadding,trpaddingheight);
        t016.setPadding(trpadding,0,trpadding,trpaddingheight);
        t017.setPadding(trpadding,0,trpadding,trpaddingheight);

        t021.setPadding(trpadding,0,0,trpaddingheight);
        t022.setPadding(trpadding,0,0,trpaddingheight);
        t023.setPadding(trpadding,0,0,trpaddingheight);
        t024.setPadding(trpadding,0,0,trpaddingheight);
        t025.setPadding(trpadding,0,0,trpaddingheight);
        t026.setPadding(trpadding,0,0,trpaddingheight);
        t027.setPadding(trpadding,0,0,trpaddingheight);


        CalendarWeek();  //生成行事曆


        //---------------------slider設定--------------------------
        sliderDots = getView().findViewById(R.id.SliderDots);


        dotCounts = adapter.getCount();
        dots = new ImageView[dotCounts];

        for(int i=0;i<dotCounts;i++) {
            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.slidershow_nonactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 2, 8, 0);
            sliderDots.addView(dots[i], params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.slidershow_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                for(int i=0;i<dotCounts;i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.slidershow_nonactive_dot));
                }	                dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(),R.drawable.slidershow_dot));
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }	        });
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),3000,6000);
    }

    @Override
    public void onClick(View v)
    {
//        Intent it = new Intent(getActivity() , Lunch.class);
        Uri uri = Uri.parse("https://fatraceschool.moe.gov.tw/frontend/search.html");
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(it);
    }



    private void CalendarWeek(){
        Calendar cg = Calendar.getInstance();//設定日曆新物件
        int month = cg.get(Calendar.MONTH)+1;  //月份+1
        int date = cg.get(Calendar.DATE);  //日期
        int week_day = cg.get(Calendar.DAY_OF_WEEK);  //星期幾
//        int DAY_OF_MONTH= cg.get(Calendar.DAY_OF_MONTH);  //和date一樣
//        int DAY_OF_WEEK_IN_MONTH= cg.get(Calendar.DAY_OF_WEEK_IN_MONTH);  //這個月的第幾個禮拜
//        int DAY_OF_YEAR= cg.get(Calendar.DAY_OF_YEAR);  //一年裡的第幾天
//        long time = System.currentTimeMillis();
//        String timeString = getTimeString(time);
        t000.setText(String.valueOf(month)+"月");
//        date = 16;
//        week_day = 7;
        switch (week_day){
            case 1:  //星期日
                t017.setText(String.valueOf(date));
                t011.setText(String.valueOf(date+1));
                t012.setText(String.valueOf(date+2));
                t013.setText(String.valueOf(date+3));
                t014.setText(String.valueOf(date+4));
                t015.setText(String.valueOf(date+5));
                t016.setText(String.valueOf(date+6));
                break;
            case 2:  //星期一
                t017.setText(String.valueOf(date-1));
                t011.setText(String.valueOf(date));
                t012.setText(String.valueOf(date+1));
                t013.setText(String.valueOf(date+2));
                t014.setText(String.valueOf(date+3));
                t015.setText(String.valueOf(date+4));
                t016.setText(String.valueOf(date+5));
                break;
            case 3:  //星期二
                t017.setText(String.valueOf(date-2));
                t011.setText(String.valueOf(date-1));
                t012.setText(String.valueOf(date));
                t013.setText(String.valueOf(date+1));
                t014.setText(String.valueOf(date+2));
                t015.setText(String.valueOf(date+3));
                t016.setText(String.valueOf(date+4));
                break;
            case 4:  //星期三
                t017.setText(String.valueOf(date-3));
                t011.setText(String.valueOf(date-2));
                t012.setText(String.valueOf(date-1));
                t013.setText(String.valueOf(date));
                t014.setText(String.valueOf(date+1));
                t015.setText(String.valueOf(date+2));
                t016.setText(String.valueOf(date+3));
                break;
            case 5:  //星期四
                t017.setText(String.valueOf(date-4));
                t011.setText(String.valueOf(date-3));
                t012.setText(String.valueOf(date-2));
                t013.setText(String.valueOf(date-1));
                t014.setText(String.valueOf(date));
                t015.setText(String.valueOf(date+1));
                t016.setText(String.valueOf(date+2));
                break;
            case 6:  //星期五
                t017.setText(String.valueOf(date-5));
                t011.setText(String.valueOf(date-4));
                t012.setText(String.valueOf(date-3));
                t013.setText(String.valueOf(date-2));
                t014.setText(String.valueOf(date-1));
                t015.setText(String.valueOf(date));
                t016.setText(String.valueOf(date+1));
                break;
            case 7:  //星期六
                t017.setText(String.valueOf(date-6));
                t011.setText(String.valueOf(date-5));
                t012.setText(String.valueOf(date-4));
                t013.setText(String.valueOf(date-3));
                t014.setText(String.valueOf(date-2));
                t015.setText(String.valueOf(date-1));
                t016.setText(String.valueOf(date));
                break;
        }


    }



    private class MyTimerTask extends TimerTask
    {
        @Override
        public void run() {
            try{
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(viewPager.getCurrentItem()==0){
                            viewPager.setCurrentItem(1);
                        }else if(viewPager.getCurrentItem()==1){
                            viewPager.setCurrentItem(2);
                        }else if(viewPager.getCurrentItem()==2){
                            viewPager.setCurrentItem(3);
                        }else if(viewPager.getCurrentItem()==3) {
                            viewPager.setCurrentItem(4);
                        }else{
                            viewPager.setCurrentItem(0);
                        }
                    }
                });

            }catch (NullPointerException e){
                e.printStackTrace();
            }

        }
    }
}
