package tw.dddssz.allschoolthings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendActivity extends AppCompatActivity {

    //private ArrayList<Map<String, Object>> mList;
    private int year,month,day,week;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend);
        setupViewComponent();
    }

    private void setupViewComponent() {

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        week = c.get(Calendar.DAY_OF_WEEK);

        String list1[] = {year+getString(R.string.n_yy)+(month+1)+getString(R.string.n_mm)+day+getString(R.string.m_dd),
                year+getString(R.string.n_yy)+(month+1)+getString(R.string.n_mm)+(day-1)+getString(R.string.m_dd),
                year+getString(R.string.n_yy)+(month+1)+getString(R.string.n_mm)+(day-2)+getString(R.string.m_dd)};

        String[] list2 = {"星期日","星期一","星期二"};

        String[] list3 = {"出席","出席","出席"};

        //mList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();

        for(int i = 0; i < list1.length; i++){
            Map<String, Object> item = new HashMap<String, Object>();

            item.put("a0001_txt01", list1[i]);
            item.put("a0001_txt02", list2[i]);
            item.put("a0001_txt03", list3[i]);
            items.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                items,
                R.layout.list_item_attend,
                new String[]{"a0001_txt01", "a0001_txt02", "a0001_txt03"},
                new int[]{R.id.a0001_txt01, R.id.a0001_txt02,R.id.a0001_txt03}
        );
        //setListAdapter(adapter);
        ListView listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(onClickListView);
//        ListView listview = getListView();
//        listview.setTextFilterEnabled(true);
//        switch (week){
//            case  Calendar.SUNDAY:
//                tt02.setText("星期日");
//                break;
//            case  Calendar.MONDAY:
//                tt02.setText("星期一");
//                break;
//            case  Calendar.TUESDAY:
//                tt02.setText("星期二");
//                break;
//            case  Calendar.WEDNESDAY:
//                tt02.setText("星期三");
//                break;
//            case  Calendar.THURSDAY:
//                tt02.setText("星期四");
//                break;
//            case  Calendar.FRIDAY:
//                tt02.setText("星期五");
//                break;
//            case  Calendar.SATURDAY:
//                tt02.setText("星期六");
//                break;
//        }
    }
    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(AttendActivity.this, DetailsAttendActivity.class);
            startActivity(intent);
        }
    };

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



