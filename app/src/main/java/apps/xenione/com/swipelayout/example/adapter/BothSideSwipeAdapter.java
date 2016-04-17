package apps.xenione.com.swipelayout.example.adapter;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import apps.xenione.com.swipelayout.R;
import apps.xenione.com.swipelayout.example.data.Album;
import apps.xenione.com.swipelayout.example.swipe.BothSideCoordinatorLayout;

/**
 * Created by Eugeni on 10/04/2016.
 */
public class BothSideSwipeAdapter extends RecyclerView.Adapter<BothSideSwipeAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemDismissed(int position);

        void onItemAction(int position);
    }

    private List<Album> mAlbums;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public BothSideSwipeAdapter(Context context, List<Album> albums) {
        this.context = context;
        this.mAlbums = albums;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_both_side_swipe, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Album album = getItem(position);
        holder.coordinatorLayout.sync();
        holder.title.setText(album.getName());
        holder.bandName.setText(album.getBandName());
        holder.discImage.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), album.getResource(), context.getTheme()));
        holder.delete.setOnClickListener(new OnDismissListener(position));
        holder.action.setOnClickListener(new OnActionListener(position));
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

        public BothSideCoordinatorLayout coordinatorLayout;
        public TextView title;
        public TextView bandName;
        public ImageView discImage;
        public ImageButton delete;
        public ImageButton action;

        public ViewHolder(View view) {
            super(view);
            coordinatorLayout = (BothSideCoordinatorLayout) view;
            title = (TextView) view.findViewById(R.id.title);
            bandName = (TextView) view.findViewById(R.id.bandName);
            discImage = (ImageView) view.findViewById(R.id.bg_disc);
            delete = (ImageButton) view.findViewById(R.id.delete);
            action = (ImageButton) view.findViewById(R.id.action);
        }
    }
}