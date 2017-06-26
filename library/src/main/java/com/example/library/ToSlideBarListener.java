package com.example.library;

/**
 * Created by Obl on 2017/6/26.
 * com.example.library
 */

public interface ToSlideBarListener {
    void onProgressing(int progress);

    void onEndProgress();

    void onPauseProgress(int curPos);

    void onResumeProgress(int curPos);

    void onStartProgress(ToSlideProgressBar toSlideProgressBar, int curProgress);
}
