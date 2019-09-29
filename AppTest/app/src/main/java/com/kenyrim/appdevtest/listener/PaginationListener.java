package com.kenyrim.appdevtest.listener;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kenyrim.appdevtest.interfaces.PagenCallback;

public class PaginationListener extends RecyclerView.OnScrollListener {

    private final PagenCallback callback;
    private final Utils utils = new Utils();

    public PaginationListener(PagenCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onScrolled(RecyclerView list, int dx, int dy) {
        super.onScrolled(list, dx, dy);

        int position = getFindLastVisibleItemPosition(list);


        int offset = list.getAdapter().getItemCount() - 1;
        State state = new State(offset);

        if (position == offset) {

            if (utils.isDistinct(state)) {
                utils.setState(state);
                callback.onListStateChanged(state);
            }

        }
    }

    private int getFindLastVisibleItemPosition(RecyclerView list) {
        Class recyclerViewLMClass = list.getLayoutManager().getClass();

        if (recyclerViewLMClass == LinearLayoutManager.class || LinearLayoutManager.class.isAssignableFrom(recyclerViewLMClass)) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) list.getLayoutManager();
            return linearLayoutManager.findLastVisibleItemPosition();

        }
        throw new IllegalStateException("Unknown LayoutManager class: " + recyclerViewLMClass.toString());
    }

    public static class State {

        private final int offset;

        State(int offset) {
            this.offset = offset;
        }

    }

    private static class Utils {

        private State state = null;

        boolean isDistinct(State state) {
            return this.state == null || this.state.offset != state.offset;
        }

        void setState(State state) {
            this.state = state;
        }
    }
}