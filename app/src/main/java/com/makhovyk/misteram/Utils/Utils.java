package com.makhovyk.misteram.Utils;

import com.makhovyk.misteram.R;

public class Utils {

    public static int getTagDrawableResource(String tag) {
        int drawable = 0;
        switch (tag) {
            case "gift":
                drawable = R.drawable.ic_gift;
                break;
            case "drinks":
                drawable = R.drawable.ic_drinks;
                break;
            case "soup":
                drawable = R.drawable.ic_soup;
                break;
            case "delivery to door":
                drawable = R.drawable.ic_delivery_to_door;
                break;
            case "specific time":
                drawable = R.drawable.ic_specific_time;
                break;
        }
        return drawable;
    }
}
