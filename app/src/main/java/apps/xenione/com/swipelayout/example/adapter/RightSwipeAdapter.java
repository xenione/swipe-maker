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
import apps.xenione.com.swipelayout.example.swipe.RightCoordinatorLayout;

/**
 * Created by Eugeni on 10/04/2016.
 */
public class RightSwipeAdapter extends RecyclerView.Adapter<RightSwipeAdapter.ViewHolder> {

    public interface OnItemDismissListener {
        void onItemDismissed(int position);
    }

    public interface OnItemSelectListener {
        void onItemSelected(int position);
    }

    private List<Album> mAlbums;
    private OnItemDismissListener mOnItemDismissListener;
    private OnItemSelectListener mOnItemSelectListener;

    public RightSwipeAdapter(List<Album> albums) {
        this.mAlbums = albums;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_right_swipe, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Album album = getItem(position);
        holder.coordinatorLayout.sync();
        holder.foreground.setOnClickListener(new OnItemSelectedClick(position));
        holder.coordinatorLayout.setOnDismissListener(new OnItemDismiss(position));
        holder.title.setText(album.getName());
        holder.bandName.setText(album.getBandName());
        Picasso.with(context).load(album.getResource()).into(holder.discImage);
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

    public void setOnItemItemSelectListener(OnItemSelectListener listener) {
        mOnItemSelectListener = listener;
    }

    public class OnItemDismiss implements RightCoordinatorLayout.OnDismissListener {

        private int position;

        public OnItemDismiss(int position) {
            this.position = position;
        }

        @Override
        public void onDismissed() {
            if (mOnItemDismissListener != null) {
                mOnItemDismissListener.onItemDismissed(position);
            }
        }
    }

    private class OnItemSelectedClick implements View.OnClickListener{

        private int position;

        public OnItemSelectedClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (mOnItemDismissListener != null) {
                mOnItemSelectListener.onItemSelected(position);
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView bandName;
        public ImageView discImage;
        public View foreground;
        public RightCoordinatorLayout coordinatorLayout;

        public ViewHolder(final View view) {
            super(view);
            coordinatorLayout = (RightCoordinatorLayout) view;
            title = (TextView) view.findViewById(R.id.title);
            bandName = (TextView) view.findViewById(R.id.bandName);
            discImage = (ImageView) view.findViewById(R.id.bg_disc);
            foreground= view.findViewById(R.id.foregroundView);
        }
    }
}