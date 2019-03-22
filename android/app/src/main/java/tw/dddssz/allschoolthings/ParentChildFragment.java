package tw.dddssz.allschoolthings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class ParentChildFragment extends Fragment {

    RecyclerView recycler_view;
//    private ParentChildAdapter adapter;
//    private ArrayList<String> mData = new ArrayList<>();


    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

//    List<PersonUtils> personUtilsList;
    List<ParentChildActivity> parentchildActivityList;

    RequestQueue rq;

    String request_url = "https://cloud.culture.tw/frontsite/trans/SearchShowAction.do?method=doFindTypeJ&category=4";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parentchild,null);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 準備資料，塞50個項目到ArrayList裡
//        for(int i = 1; i < 51; i++) {
//            mData.add("項目"+i);
//        }

        rq = Volley.newRequestQueue(getActivity());
        // 連結元件
        recycler_view = (RecyclerView) getView().findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        // 設置RecyclerView為列表型態
        recycler_view.setLayoutManager(new LinearLayoutManager( getActivity()));
        // 設置格線
        recycler_view.addItemDecoration(new DividerItemDecoration( getActivity(), DividerItemDecoration.VERTICAL));

        // 將資料交給adapter
//        adapter = new ParentChildAdapter(getActivity(),mData);
        // 設置adapter給recycler_view
//        recycler_view.setAdapter(adapter);

        parentchildActivityList = new ArrayList<>();
        sendRequest();
    }

    private void sendRequest() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for(int i = 0; i < response.length(); i++){

                    ParentChildActivity parentchildActivity = new ParentChildActivity();

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        parentchildActivity.setActivitytitle(jsonObject.getString("title"));
                        parentchildActivity.setActivitystartDate(jsonObject.getString("startDate"));
                        parentchildActivity.setActivityendDate(jsonObject.getString("endDate"));
//                        personUtils.setPersonLastName(jsonObject.getString("lastname"));
//                        personUtils.setJobProfile(jsonObject.getString("jobprofile"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    parentchildActivityList.add(parentchildActivity);

                }

                mAdapter = new ParentChildAdapter(getActivity(),parentchildActivityList);

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

}
