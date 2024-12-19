package com.example.myapplication;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

public class MainActivity extends AppCompatActivity implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    private TextView tvOutput;
    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvOutput = findViewById(R.id.gestureText);
        mDetector = new GestureDetectorCompat(this, this);
        mDetector.setOnDoubleTapListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        tvOutput.setText("Вниз");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        tvOutput.setText("Нажмите и удерживайте");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        tvOutput.setText("Одно касание");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        tvOutput.setText("Прокрутка\nКоординаты: X=" + distanceX + " Y=" + distanceY);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        tvOutput.setText("Долгое нажатие");
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
        tvOutput.setText("Бросок\nСкорость: X=" + velocityX + " Y=" + velocityY);
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        tvOutput.setText("Двойное нажатие");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        tvOutput.setText("Событие двойного касания");
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        tvOutput.setText("Подтверждено однократное нажатие\n");
        return true;
    }
}