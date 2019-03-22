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
import android.widget.Toast;

import org.joda.time.DateTime;

import java.util.ArrayList;

import noman.weekcalendar.WeekCalendar;
import noman.weekcalendar.listener.OnDateClickListener;

public class CommunicationFragment extends Fragment {

    private RecyclerView recycler_view;
    private CommuntcationAdapter adapter;

    private ArrayList<String> mData = new ArrayList<>();
    private ArrayList<String> mDataTest = new ArrayList<>();

    private WeekCalendar weekCalendar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_communication, null);

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        // 準備資料，塞50個項目到ArrayList裡
        for (int i = 1; i < 11; i++) {
            mData.add("事項" + i);
        }

        //test change recycleview data
        for (int j = 1; j < 5; j++) {
            mDataTest.add("Test" + j);
        }

        weekCalendar = (WeekCalendar) getView().findViewById(R.id.weekCalendar);
        //-----weekCalendar監聽-----
        weekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {

                adapter.updateList(mDataTest);
                Toast.makeText(CommunicationFragment.this.getContext(), "You Selected " + dateTime.toString(), Toast
                        .LENGTH_SHORT).show();
            }
        });

        // 連結元件
        recycler_view = (RecyclerView) getView().findViewById(R.id.recycler_view);
        // 設置RecyclerView為列表型態
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 設置格線
        recycler_view.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        // 將資料交給adapter
        adapter = new CommuntcationAdapter(mData);
        // 設置adapter給recycler_view
        recycler_view.setAdapter(adapter);
    }
}
