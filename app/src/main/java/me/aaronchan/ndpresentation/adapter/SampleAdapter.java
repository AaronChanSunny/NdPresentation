package me.aaronchan.ndpresentation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.aaronchan.ndpresentation.R;

/**
 * Created by Administrator on 2016/5/12.
 */
public class SampleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mNames;

    public SampleAdapter(List<String> names) {
        mNames = names;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sample, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.mText.setText(mNames.get(position));
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mText;

        public ViewHolder(View itemView) {
            super(itemView);

            mText = (TextView) itemView.findViewById(R.id.tv_sample);
        }
    }
}
