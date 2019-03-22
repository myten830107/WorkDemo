package tw.dddssz.allschoolthings;

import android.content.Context;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Simple_ExpandableListAdapter extends android.widget.SimpleExpandableListAdapter
{
    private Context context;
    private List<? extends Map<String, String>> groupList ;
    private List<List<Map<String, String>>> childList2D ;


    public Simple_ExpandableListAdapter(Context context, List<? extends Map<String, ?>> groupData, int groupLayout, String[] groupFrom, int[] groupTo, List<? extends List<? extends Map<String, ?>>> childData, int childLayout, String[] childFrom, int[] childTo)
    {
        super(context, groupData, groupLayout, groupFrom, groupTo, childData, childLayout, childFrom, childTo);
        this.context = context;
        this.groupList = (List<? extends Map<String, String>>) groupData;
        this.childList2D = (List<List<Map<String, String>>>) childData;
    }



}
