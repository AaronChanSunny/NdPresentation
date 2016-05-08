package me.aaronchan.ndpresentation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by aaronchan on 16/5/8.
 */
public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<Item> mItems;

    public ItemAdapter(List<Item> items) {
        mItems = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;

        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_content, parent, false);
            holder = new ItemViewHolder(itemView);
        } else {
            View headerView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_header, parent, false);
            holder = new HeaderViewHolder(headerView);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item item = mItems.get(position);

        if (holder.getItemViewType() == TYPE_ITEM) {
            ItemViewHolder itemHolder = (ItemViewHolder) holder;

            itemHolder.mTitle.setText(item.getTitle());
            itemHolder.mDesc.setText(item.getDesc());
        } else {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

            headerHolder.mHeader.setText(item.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).isHeader() ? TYPE_HEADER : TYPE_ITEM;
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView mHeader;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            mHeader = (TextView) itemView.findViewById(R.id.tv_header);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView mTitle;
        TextView mDesc;

        public ItemViewHolder(final View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mDesc = (TextView) itemView.findViewById(R.id.tv_desc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Item item = mItems.get(getLayoutPosition());
                    Context context = itemView.getContext();

                    Intent intent = new Intent(context, item.getActivity());
                    context.startActivity(intent);
                }
            });
        }
    }

}
