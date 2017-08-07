package com.example.hasee.yanshi.address_book.tree;

import android.content.Context;
import android.widget.ExpandableListView;

public class SecondLevelExpandableListView extends ExpandableListView {

        public SecondLevelExpandableListView(Context context) {
			super(context);
        }
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            //999999 is AddressBook size in pixels. ExpandableListView requires AddressBook maximum height in order to do measurement calculations.
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(999999, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
}