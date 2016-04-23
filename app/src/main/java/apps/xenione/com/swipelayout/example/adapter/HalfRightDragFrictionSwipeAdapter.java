package apps.xenione.com.swipelayout.example.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import apps.xenione.com.swipelayout.R;
import apps.xenione.com.swipelayout.example.data.Album;
import apps.xenione.com.swipelayout.example.swipe.HalfRightDragFrictionCoordinatorLayout;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Eugeni on 10/04/2016.
 */
public class HalfRightDragFrictionSwipeAdapter extends RecyclerView.Adapter<HalfRightDragFrictionSwipeAdapter.ViewHolder> {

    public interface OnItemDismissListener {
        void onItemDismissed(int position);
    }

    private List<Album> mAlbums;
    private OnItemDismissListener mOnItemDismissListener;

    public HalfRightDragFrictionSwipeAdapter(List<Album> albums) {
        this.mAlbums = albums;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_half_right_drag_friction_swipe, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Album album = getItem(position);
        Context context = holder.itemView.getContext();
        Picasso.with(context).load(album.getResource()).placeholder(R.color.placeholder).into(holder.discImage);
        holder.coordinatorLayout.setOnClickListener(new OnItemDismiss(position));
        holder.title.setText(album.getName());
        holder.bandName.setText(album.getBandName());
        holder.delete.setOnClickListener(new OnItemDismiss(position));
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

    public void deleteItem(int position) {
        mAlbums.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }


    private Album getItem(int position) {
        return mAlbums.get(position);
    }

    public void setOnItemDismissListener(OnItemDismissListener listener) {
        mOnItemDismissListener = listener;
    }

    public class OnItemDismiss implements View.OnClickListener {

        private int position;

        public OnItemDismiss(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            mOnItemDismissListener.onItemDismissed(position);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        public TextView title;
        @Bind(R.id.bandName)
        public TextView bandName;
        @Bind(R.id.bg_disc)
        public ImageView discImage;
        @Bind(R.id.backgroundView)
        public View delete;
        public HalfRightDragFrictionCoordinatorLayout coordinatorLayout;

        public ViewHolder(View view) {
            super(view);
            coordinatorLayout = (HalfRightDragFrictionCoordinatorLayout) view;
            ButterKnife.bind(this, view);
        }
    }
}