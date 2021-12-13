package com.trujca.mapoli.ui.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.trujca.mapoli.BR;
import com.trujca.mapoli.ui.common.RecyclerViewClickListener;

public abstract class BaseAdapter<T> extends ListAdapter<T, BaseAdapter<T>.BaseViewHolder> {

    private final RecyclerViewClickListener mRecyclerViewClickListener;

    protected BaseAdapter(@NonNull final DiffUtil.ItemCallback<T> diffCallback, final RecyclerViewClickListener recyclerViewClickListener) {
        super(diffCallback);
        this.mRecyclerViewClickListener = recyclerViewClickListener;
    }

    @Override
    @NonNull
    @CallSuper
    public BaseViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
        BaseViewHolder viewHolder = new BaseViewHolder(binding);
        viewHolder.bind(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    public void onItemClicked(View view, T item) {
        mRecyclerViewClickListener.onRecyclerViewItemClick(view, item);
    }

    protected abstract @LayoutRes
    int getLayoutIdForPosition(int position);

    public class BaseViewHolder extends RecyclerView.ViewHolder {

        private final ViewDataBinding binding;

        public BaseViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(T item) {
            binding.setVariable(BR.item, item);
            binding.executePendingBindings();
        }

        public void bind(BaseAdapter<T> adapter) {
            binding.setVariable(BR.adapter, adapter);
        }
    }
}
