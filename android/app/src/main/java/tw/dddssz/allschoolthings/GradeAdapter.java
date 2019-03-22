package tw.dddssz.allschoolthings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.ViewHolder> {

//    private List<String> mData;

    private Context context;
    private List<Grade> grade;

    public GradeAdapter(Context context, List grade) {
        this.context = context;
        this.grade = grade;
    }

//    GradeAdapter(List<String> data) {
//        mData = data;
//    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 連結項目布局檔list_item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grade, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 設置txtItem要顯示的內容

//        holder.itemView.setTag(grade.get(position));
//
//        Grade pu = grade.get(position);

        holder.txtItem.setText(grade.get(position).getGradename());
        holder.txtSocre.setText(grade.get(position).getGradescore());
    }

    @Override
    public int getItemCount() {
        return grade.size();
    }

    // 建立ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder{
        // 宣告元件
        private TextView txtItem,txtSocre;

        ViewHolder(View itemView) {
            super(itemView);
            txtItem = (TextView) itemView.findViewById(R.id.txt_subject);
            txtSocre = (TextView) itemView.findViewById(R.id.txt_socre);
        }
    }
}
