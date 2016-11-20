# Easy Swipe Maker

From the library to build your own swipeout item views. These examples will only take 5 minutes each. Have a look at the demos app available on [google play](https://play.google.com/store/apps/details?id=apps.xenione.com.swipelayout)

## Example of a **Swipe of Both Sides**

![both_side_swipe](https://cloud.githubusercontent.com/assets/4138527/14615552/8428c3e2-05a6-11e6-8b85-4627a6c79d87.gif)

## Example of **Right Side Swipe**

![drag_swipe](https://cloud.githubusercontent.com/assets/4138527/14615553/8429c044-05a6-11e6-8d80-3d19d29e1a31.gif)

## Example of a **Two-steps Right Swipe** with amazing color change

![two_step_swipe](https://cloud.githubusercontent.com/assets/4138527/14615554/842ed408-05a6-11e6-8111-f11d91844031.gif)

### Are you ready? I'll show you how:

Let's do this one (Right Side Swipe):

![screenshoot](https://cloud.githubusercontent.com/assets/4138527/14615699/3c94e41a-05a7-11e6-8cca-4e97219d63b9.png)

Add it on your project:

Gradle:
```java 
compile 'com.xenione.libs:swipe-maker:1.1.1'
```


1.Extend AbsCoordinatorLayout and create your own Coordinator, in this case I called it HalfRightCoordinatorLayout

```java 
    public class HalfRightCoordinatorLayout extends AbsCoordinatorLayout {
```

Override the doInitialViewsLocation() method. This hook arises when views are placed on the screen. Set anchors for the swipe widget.
Anchors are the boundaries between swipe slides (look at the layout at the next point). We want that swipe slides within the button "mBackgroundView" boundaries.

```java 
    @Override
    public void doInitialViewsLocation() {
        mForegroundView = (SwipeLayout) findViewById(R.id.foregroundView);
        mBackgroundView = findViewById(R.id.backgroundView);
        mForegroundView.anchor(mBackgroundView.getRight(), mBackgroundView.getLeft());
    }
    
```
2.Make your layout according to the previous point
Note: SwipeLayout Id must be *@id/foregroundView* and Background View must be *@id/backgroundView*

```java
<?xml version="1.0" encoding="utf-8"?>
<apps.xenione.com.swipelayout.example.swipe.HalfRightCoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <ImageButton
        android:id="@id/backgroundView"
        android:background="@color/colorAccent"
        android:src="@drawable/ic_delete"
        android:padding="@dimen/button_padding"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"/>

    <com.xenione.libs.swipemaker.SwipeLayout
        android:id="@id/foregroundView"
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

    </com.xenione.libs.swipemaker.SwipeLayout>

</apps.xenione.com.swipelayout.example.swipe.HalfRightCoordinatorLayout>;
```
3.Make your amazing transformations- Go back to your class inherited from AbsCoordinatorLayout (im our case HalfRightCoordinatorLayout ) and you have a hook called onTranslateChange(...).

```java 
    @Override
    public void onTranslateChange(float global, int index, float relative) {
    }
```

where you can monitor swipe progress.

The parameter descriptions are:

**global**: give us percentage (1%: 0-1) :0 means left limit and 1 means right limit.

**index** and **relative**: in case we have more than 1 section that would happened if we had given more than 2 anchor points (such as Two-step Swipe)

**index**: is the index of the section (if we had got 3 anchor points we would have 2 sections: One from anchor 1 to 2 and the second from anchor 2 to 3)

**relative**: gives us the precentage(1%: 0-1) within the index section.
    
In our case we have only one section so we don't have to take care of that.
So let's add a nice transition effect:
    
```java 
    @Override
    public void onTranslateChange(float global, int index, float relative) {
         backgroundView.setAlpha(global);
    }
```

## Other example of what you can do with Easy Swipe Maker 

### Before-After Effect

![before_after_effect](https://cloud.githubusercontent.com/assets/4138527/19211444/9713cbdc-8d3c-11e6-84af-18a18ab02efb.gif)

find out more at [github](https://github.com/xenione/BeforeAfterEffect)

## Last version v1.1.1 - Feature added: Allow vertical swipe.


![vertical swipe](https://cloud.githubusercontent.com/assets/4138527/19872352/93fbcf0a-9fb9-11e6-82b5-efee365bb2e8.gif)

# License
-------

    Copyright 2016 Eugeni Josep Senent i Gabriel

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.



