package com.example.moviedb.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {
    LinearLayoutManager manager;

    public PaginationScrollListener(LinearLayoutManager manager){
        this.manager = manager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int item_count = manager.getChildCount();
        int total_item_count = manager.getItemCount();
        int first_visible_item_position = manager.findFirstVisibleItemPosition();

        if (!isLoading() && !isLastPage()){
            if ((first_visible_item_position + item_count >= total_item_count && first_visible_item_position >= 0 && total_item_count >= getTotalPageCount())) {
                loadMoreItems();
            }
        }
    }

    protected abstract void loadMoreItems();

    public abstract int getTotalPageCount();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();


}
