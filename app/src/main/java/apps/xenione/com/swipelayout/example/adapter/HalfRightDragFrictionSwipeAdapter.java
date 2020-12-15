package apps.xenione.com.swipelayout.example.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
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

/**
 * Created by Eugeni on 10/04/2016.
 */
public class HalfRightDragFrictionSwipeAdapter extends RecyclerView.Adapter<HalfRightDragFrictionSwipeAdapter.ViewHolder> {

    public interface OnItemDismissListener {
        void onItemDismissed(Album album);
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
        holder.title.setText(album.getName());
        holder.bandName.setText(album.getBandName());
        holder.delete.setOnClickListener(new OnItemDismiss(album));
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

    public void deleteItem(Album album) {
        int index = mAlbums.indexOf(album);
        if (index == -1) {
            return;
        }
        mAlbums.remove(index);
        notifyItemRemoved(index);
        notifyItemRangeChanged(index, getItemCount());
    }


    private Album getItem(int position) {
        return mAlbums.get(position);
    }

    public void setOnItemDismissListener(OnItemDismissListener listener) {
        mOnItemDismissListener = listener;
    }

    public class OnItemDismiss implements View.OnClickListener {

        private Album album;

        public OnItemDismiss(Album album) {
            this.album = album;
        }

        @Override
        public void onClick(View v) {
            mOnItemDismissListener.onItemDismissed(album);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView bandName;
        public ImageView discImage;
        public View delete;
        public HalfRightDragFrictionCoordinatorLayout coordinatorLayout;

        public ViewHolder(View view) {
            super(view);
            coordinatorLayout = (HalfRightDragFrictionCoordinatorLayout) view;
            title = view.findViewById(R.id.title);
            bandName = view.findViewById(R.id.bandName);
            discImage = view.findViewById(R.id.bg_disc);
            delete = view.findViewById(R.id.backgroundView);
        }
    }
}