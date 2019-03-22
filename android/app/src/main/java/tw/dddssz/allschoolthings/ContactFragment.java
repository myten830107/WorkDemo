package tw.dddssz.allschoolthings;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactFragment extends Fragment   implements View.OnClickListener, ExpandableListView.OnChildClickListener
{
    private static final String ITEM_NAME = "Item Name";
    private static final String ITEM_SUBNAME = "Item Subname";

    private TextView txtAns01;
    private String b_itemname;
    private String b_subitemname;
    private String b_txtdesc;
    private String b_txtsubdesc;
    private SimpleExpandableListAdapter mExpaListAdap;
    private ExpandableListView expandlist;
    List<Map<String, String>> groupList = new ArrayList<>();
    List<List<Map<String, String>>> childList2D = new ArrayList<>();

    private final String TAG = "tcnr08=>";
    private int id , subid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, null);
        expandlist = (ExpandableListView)view.findViewById(R.id.contact_expandable_list);
        // preparing list data
        prepareListData();
        // 設定 expandablelistview adapter
        mExpaListAdap = new Simple_ExpandableListAdapter(getActivity(), groupList,
                R.layout.expandlist_group_contact, //android.代表空的
//                android.R.layout.simple_expandable_list_item_2, //android.代表空的
                new String[]{ITEM_NAME, ITEM_SUBNAME},
                new int[]{R.id.m0504_t002, android.R.id.text2},
//                new String[]{ITEM_NAME, ITEM_SUBNAME},
//                new int[]{android.R.id.text1, android.R.id.text2},

//                childList2D, android.R.layout.simple_expandable_list_item_2,
                childList2D, R.layout.expandlist_item_contact,  //此處設定子項目的layout
                new String[]{ITEM_NAME, ITEM_SUBNAME},
                new int[]{R.id.m0504_t003, android.R.id.text2})      ;  //更改第一個子項目TextView顯示id
        //new String[]{ITEM_NAME, ITEM_SUBNAME},
        //new int[]{android.R.id.text1, android.R.id.text2});


        expandlist.setAdapter(mExpaListAdap);
//        expandlist.setAdapter(new MyExpandableListViewAdapter(getActivity()));
        // setting list adapter
//-----------------------------------------------------------------------------------------------------------------
    return view;
//        return inflater.inflate(R.layout.fragment_contact, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        txtAns01=(TextView)view.findViewById(R.id.m0504_t001);
        b_itemname=getString(R.string.m0504_titem);
        b_subitemname=getString(R.string.m0504_tsubitem);
        b_txtdesc=getString(R.string.m0504_tdesc);
        b_txtsubdesc=getString(R.string.m0504_tsubdesc);

//        expandlist = (ExpandableListView)view.findViewById(R.id.expandablelist);
//        ExpandableListView expandlist = getExpandableListView();

//        expandableListView.setGroupIndicator(null);  //让前面的箭头消失(设置为空)
//        expandlist.setOnGroupExpandListener(gel);
//        txtAns01.setText(getString(R.string.m0504_title));
        txtAns01.setOnClickListener(this);

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
//        int newexpandlistheight = displayMetrics.heightPixels * 13 / 20; // 設定ScrollView使用尺寸的4/5
        int newexpandlistwidth = displayMetrics.widthPixels * 1; // 設定ScrollView使用尺寸的4/5
//        expandlist.getLayoutParams().height = newexpandlistheight;  // 重定ScrollView大小
        expandlist.getLayoutParams().width = newexpandlistwidth;  // 重定ScrollView大小

        expandlist.setOnChildClickListener(this);  //設定監聽group,child
    }

    @Override
    public void onClick(View v)
    {
        Uri uri = Uri.parse("tel:0800580995");  //台中市教育局
        Intent it = new Intent(Intent.ACTION_DIAL , uri);
        //Intent it = new Intent(Intent.ACTION_CALL, uri); 直接打電話出去
        startActivity(it);
    }


    private void prepareListData()
    {
        //宣告 list 內容 使用陣列 Map
//        List<Map<String, String>> groupList = new ArrayList<Map<String, String>>();
//        List<List<Map<String, String>>> childList2D = new ArrayList<List<Map<String, String>>>();
        //---第一層------------------
        for (int i = 0; i < 6; i++) {
            Map<String, String> group = new HashMap<String, String>();
            //String id1 = "m0504_s"+ String.format("%03d" , i );
            id = getResources().getIdentifier( "m0504_a"+ String.format("%02d" , i ) , "string" ,  getActivity().getPackageName());  //getPackageName()
            //Log.d(TAG , id1);
            //group.put(ITEM_NAME, b_itemname + i);
            group.put(ITEM_NAME,  getString(id) );
            //group.put(ITEM_SUBNAME, b_txtdesc + i);  //群組說明
            groupList.add(group);
            //---------------第二層-----------------------
            List<Map<String, String>> childList = new ArrayList<Map<String, String>>();
            try{
                for (int j = 0; j < 3; j++) {
                    Map<String, String> child = new HashMap<String, String>();
                    subid = getResources().getIdentifier( "m0504_a" + String.format("%02d" , i )+ String.format("%01d" , j ) , "string" , getActivity().getPackageName() );
                    //child.put(ITEM_NAME, b_subitemname + i + j);
                    child.put(ITEM_NAME, getString(subid) );
                    //child.put(ITEM_SUBNAME, b_txtsubdesc + i + j);  //子選項說明
                    childList.add(child);
                }

            }catch (Exception e){
                return;
            }

            childList2D.add(childList);
            //---------第二層 end-----------
        }
        //--第一層 end----------------------


    }


    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
    {
        //        String b_txtans = getString(R.string.m0504_title); // 聯絡方式
        int title_string =  getResources().getIdentifier( "m0504_a0"+ groupPosition + childPosition , "string" , getActivity().getPackageName() ) ;
        String ans = "按此撥打  "+ getString(title_string) +"  分機: " + (groupPosition+1) + "0" + childPosition;

        txtAns01.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_menu_call, 0, 0 , 0);  //左上右下
        txtAns01.setText(ans);
        txtAns01.setTextSize(18);
        txtAns01.setTextColor(getResources().getColor(R.color.color_bg_eb6567));
        txtAns01.setPadding(0,15,0,5);
        return false;
//        return super.onChildClick(parent, v, groupPosition, childPosition, id);//此行在最後，不要自己打，是override自動產生
    }



}
