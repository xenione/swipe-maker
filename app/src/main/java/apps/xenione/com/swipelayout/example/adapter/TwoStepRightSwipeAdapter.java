package apps.xenione.com.swipelayout.example.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import apps.xenione.com.swipelayout.R;
import apps.xenione.com.swipelayout.example.data.Album;
import apps.xenione.com.swipelayout.example.swipe.TwoStepRightCoordinatorLayout;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Eugeni on 10/04/2016.
 */
public class TwoStepRightSwipeAdapter extends RecyclerView.Adapter<TwoStepRightSwipeAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemDismissed(int position);

        void onItemAction(int position);
    }

    private List<Album> mAlbums;
    private OnItemClickListener mOnItemClickListener;

    public TwoStepRightSwipeAdapter(List<Album> albums) {
        this.mAlbums = albums;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_two_step_right_swipe, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Album album = getItem(position);
        Picasso.with(context).load(album.getResource()).placeholder(R.color.placeholder).into(holder.discImage);
        holder.title.setText(album.getName());
        holder.bandName.setText(album.getBandName());
        holder.delete.setOnClickListener(new OnDismissListener(position));
        holder.action.setOnClickListener(new OnActionListener(position));
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.coordinatorLayout.sync();
    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void deleteItem(int position) {
        mAlbums.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }


    private Album getItem(int position) {
        return mAlbums.get(position);
    }

    private class OnDismissListener implements View.OnClickListener {

        private int position;

        public OnDismissListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            mOnItemClickListener.onItemDismissed(position);
        }
    }

    private class OnActionListener implements View.OnClickListener {

        private int position;

        public OnActionListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            mOnItemClickListener.onItemAction(position);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TwoStepRightCoordinatorLayout coordinatorLayout;
        @Bind(R.id.title)
        public TextView title;
        @Bind(R.id.bandName)
        public TextView bandName;
        @Bind(R.id.bg_disc)
        public ImageView discImage;
        @Bind(R.id.delete)
        public ImageButton delete;
        @Bind(R.id.action)
        public ImageButton action;

        public ViewHolder(View view) {
            super(view);
            coordinatorLayout = (TwoStepRightCoordinatorLayout) view;
            ButterKnife.bind(this, view);
        }
    }
}