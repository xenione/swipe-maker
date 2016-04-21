# Easy Swipe Maker

Library to build your own swipe out item views. this examples are only taken **5 minutes** each.
Have a look at demos app available on google play: <https://play.google.com/store/apps/details?id=apps.xenione.com.swipelayout>

## Example of **Both Side Swipe**

![both_side_swipe](https://cloud.githubusercontent.com/assets/4138527/14615552/8428c3e2-05a6-11e6-8b85-4627a6c79d87.gif)

## Example of **Right Side Swipe**

![drag_swipe](https://cloud.githubusercontent.com/assets/4138527/14615553/8429c044-05a6-11e6-8d80-3d19d29e1a31.gif)

## Example of **Right Two Steps Swipe** with amazing color change

![two_step_swipe](https://cloud.githubusercontent.com/assets/4138527/14615554/842ed408-05a6-11e6-8111-f11d91844031.gif)

### Are you ready? I show you how:

Let's do it that one (Right Side Swipe):

![screenshoot](https://cloud.githubusercontent.com/assets/4138527/14615699/3c94e41a-05a7-11e6-8cca-4e97219d63b9.png)


1.Extend AbsCoordinatorLayout and create your own Coordianator in this case I called it HalfRightCoordinatorLayout

```java 
    public class HalfRightCoordinatorLayout extends AbsCoordinatorLayout {
```

Override method doInitialViewsLocation() hook when views are place on the screen and set anchors for the swipe widget.
Anchor are the boundaries between swipe slides (look at layout next point). we want that swipe slide within button (mBackgroundView see next point for further clarification) boundaries.

```java 
    @Override
    public void doInitialViewsLocation() {
        mForegroundView = (SwipeLayout) findViewById(R.id.foregroundView);
        mBackgroundView = findViewById(R.id.backgroundView);
        mForegroundView.anchor(mBackgroundView.getRight(), mBackgroundView.getLeft());
    }
    
```
2.Make your layout according to the previous point
Note: SwipeLayout id have to be *@+id/foregroundView*

```java
<?xml version="1.0" encoding="utf-8"?>
<apps.xenione.com.swipelayout.example.swipe.HalfRightCoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <ImageButton
        android:id="@+id/backgroundView"
        android:background="@color/colorAccent"
        android:src="@drawable/ic_delete"
        android:padding="@dimen/button_padding"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"/>

    <apps.xenione.com.swipelayout.lib.SwipeLayout
        android:id="@+id/foregroundView"
        android:layout_width="match_parent"
        android:layout_height="@dimen/item_height"
        android:orientation="vertical">

        <ImageView
            android:id="@+id/bg_disc"
            android:layout_width="match_parent"
            android:scaleType="centerCrop"
            android:layout_height="match_parent" />

        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <TextView
                style="@style/SubTitleTextStyle"
                android:id="@+id/title"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                tools:text="Sao"
                android:layout_below="@+id/bandName"
                android:layout_alignRight="@+id/bandName"
                android:layout_alignEnd="@+id/bandName" />

            <TextView
                style="@style/TitleTextStyle"
                android:id="@+id/bandName"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                tools:text="La Gossa Sorda"
                android:layout_alignParentTop="true"
                android:layout_alignParentRight="true"
                android:layout_alignParentEnd="true"
                android:layout_marginTop="30dp"
                android:layout_marginRight="30dp"
                android:layout_marginEnd="30dp" />

        </RelativeLayout>

    </apps.xenione.com.swipelayout.lib.SwipeLayout>

</apps.xenione.com.swipelayout.example.swipe.HalfRightCoordinatorLayout>;
```
3.Make your amazing transformations, go back to your class that inherit from AbsCoordinatorLayout and you have a hook called onTranslateChange(...)

```java 
    @Override
    public void onTranslateChange(float global, int index, float relative) {
    }
```

where be can keep aware of swipe progress

global: give us percent(1% 0-1) :0 means left limit and 1 means right limit.

index and relative: in case we have more than 1 section that happend where we give more than 2 anchor points (like Two Steps Swipe)

index: is the index of the secction (if we have 3 anchor points we have 2 sections: One from anchor 1 to 2 and the second secction from anchor 2 to 3)

relative: gives us the precent(1% 0-1) within the index section.
    
In our case we have only one section so we don't have to take care about that.
so lets add a nice transition effect:
    
```java 
    @Override
    public void onTranslateChange(float global, int index, float relative) {
         backgroundView.setAlpha(global);
    }
```
