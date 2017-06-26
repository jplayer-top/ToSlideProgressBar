package com.example.peo.tspb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.library.ToSlideProgressBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ToSlideProgressBar mTspb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTspb = (ToSlideProgressBar) findViewById(R.id.toSlideProgressBar);
        LinearLayout layout = (LinearLayout) findViewById(R.id.menuLinearLayout);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                mTspb.setRateWithAnim();
                break;
            case R.id.button2:
                mTspb.resetView();
                break;
            case R.id.button3:
                mTspb.goneRight();
                break;
            case R.id.button4:
                mTspb.goneLeft();
                break;
            case R.id.button5:
                mTspb.resumeView();
                break;
            case R.id.button6:
                mTspb.pauseView();
                break;
        }
    }
}
