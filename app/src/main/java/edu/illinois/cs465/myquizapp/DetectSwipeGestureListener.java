package edu.illinois.cs465.myquizapp;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Jerry on 4/18/2018.
 */

public class DetectSwipeGestureListener extends GestureDetector.SimpleOnGestureListener {

    // Minimal x and y axis swipe distance.
    private static int MIN_SWIPE_DISTANCE_X = 100;
    private static int MIN_SWIPE_DISTANCE_Y = 100;

    // Maximal x and y axis swipe distance.
    private static int MAX_SWIPE_DISTANCE_X = 1000;
    private static int MAX_SWIPE_DISTANCE_Y = 1000;

    // Source activity that display message in text view.
    private MainActivity activity = null;

    public MainActivity getActivity() {
        return activity;
    }

    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }

    /* This method is invoked when a swipe gesture happened. */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        return true;
    }

    // Invoked when single tap screen.
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        //this.activity.displayMessage("Single tap occurred.");
        //this.activity.single_tap();
        return true;
    }

    // Invoked when double tap screen.
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        //this.activity.displayMessage("Double tap occurred.");
        this.activity.double_tap();
        return true;
    }
}

/*
        <Button
            android:id="@+id/button_miss"
            android:layout_width="150dp"
            android:layout_height="120dp"
            android:layout_row="0"
            android:layout_column="0"
            android:padding="10dip"
            android:text="Miss"
            android:textSize="18dip"
            android:bottomRightRadius="20dp"
            android:bottomLeftRadius="20dp"
            android:topLeftRadius="20dp"
            android:topRightRadius="20dp"
            android:theme="@style/PrimaryButton" />

        <Button
            android:id="@+id/button_free"
            android:layout_width="150dp"
            android:layout_height="120dp"
            android:text="Free"
            android:layout_row="0"
            android:layout_column="1"
            android:textSize="18dip"
            android:padding="10dip"
            android:theme="@style/PrimaryButton" />

        <Button
            android:id="@+id/button_twopoints"
            android:layout_width="150dp"
            android:layout_height="120dp"
            android:text="2 Points"
            android:layout_row="1"
            android:layout_column="0"
            android:textSize="18dip"
            android:padding="10dip"
            android:theme="@style/PrimaryButton" />


        <Button
            android:id="@+id/button_threepoints"
            android:layout_width="150dp"
            android:layout_height="120dp"
            android:text="3 Points"
            android:layout_row="1"
            android:layout_column="1"
            android:textSize="18dip"
            android:padding="10dip"
            android:theme="@style/PrimaryButton" />
        */