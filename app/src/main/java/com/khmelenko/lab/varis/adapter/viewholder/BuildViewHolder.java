package com.khmelenko.lab.varis.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.khmelenko.lab.varis.R;
import com.khmelenko.lab.varis.adapter.OnListItemListener;
import com.khmelenko.lab.varis.widget.BuildView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * View holder for the Build data
 *
 * @author Dmytro Khmelenko
 */
public final class BuildViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @Bind(R.id.item_build_card_view)
    View mParent;

    @Bind(R.id.item_build_data)
    public BuildView mBuildView;

    private final OnListItemListener mListener;

    public BuildViewHolder(View itemView, OnListItemListener listener) {
        super(itemView);
        mListener = listener;
        ButterKnife.bind(this, itemView);
        itemView.setClickable(true);
        mParent.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            mListener.onItemSelected(getLayoutPosition());
        }
    }
}
