package com.kenyrim.appdevtest.interfaces;

import com.kenyrim.appdevtest.listener.PaginationListener;

public interface PagenCallback {

        void onListStateChanged(PaginationListener.State state);
    }