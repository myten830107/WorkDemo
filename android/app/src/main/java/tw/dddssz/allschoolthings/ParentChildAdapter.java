package tw.dddssz.allschoolthings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ParentChildAdapter extends RecyclerView.Adapter<ParentChildAdapter.ViewHolder> {

//    private List<String> mData;

    private Context context;
    private List<ParentChildActivity> parentchildActivity;

    public ParentChildAdapter(Context context, List parentchildActivity) {
        this.context = context;
        this.parentchildActivity = parentchildActivity;
    }

//    ParentChildAdapter(List<String> data) {
//        mData = data;
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 連結項目布局檔list_item
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_activity, parent, false);
        ViewHolder viewHolder= new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 設置txtItem要顯示的內容
//        holder.txtItem01.setText(mData.get(position));

        holder.itemView.setTag(parentchildActivity.get(position));
//
        ParentChildActivity pu = parentchildActivity.get(position);

        holder.txtItem.setText(parentchildActivity.get(position).getActivitytitle());
        holder.txtItem01.setText(parentchildActivity.get(position).getActivitystartDate());
        holder.txtItem02.setText(parentchildActivity.get(position).getActivityendDate());
//        holder.pName.setText(pu.getPersonFirstName()+" "+pu.getPersonLastName());
//        holder.pJobProfile.setText(pu.getJobProfile());
    }

    @Override
    public int getItemCount() {

        return parentchildActivity.size();
    }

    // 建立ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder{
        // 宣告元件
        private TextView txtItem,txtItem01,txtItem02;

        public ViewHolder(View itemView) {
            super(itemView);
            txtItem = (TextView) itemView.findViewById(R.id.txtItem);
            txtItem01 = (TextView) itemView.findViewById(R.id.txtItem01);
            txtItem02 = (TextView) itemView.findViewById(R.id.txtItem02);

            // 點擊項目時
            txtItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),
                            "你選擇了活動 " +(getAdapterPosition()+1),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
