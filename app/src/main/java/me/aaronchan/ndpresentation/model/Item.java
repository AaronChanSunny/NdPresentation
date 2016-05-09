package me.aaronchan.ndpresentation.model;

public class Item {
    private boolean mIsHeader;
    private String mTitle;
    private String mDesc;
    private Class mActivity;

    public Item(boolean isHeader, String title) {
        mIsHeader = isHeader;
        mTitle = title;
    }

    public Item(boolean isHeader, String title, String desc, Class activity) {
        mIsHeader = isHeader;
        mTitle = title;
        mDesc = desc;
        mActivity = activity;
    }

    public boolean isHeader() {
        return mIsHeader;
    }

    public void setIsHeader(boolean isHeader) {
        mIsHeader = isHeader;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String desc) {
        mDesc = desc;
    }

    public Class getActivity() {
        return mActivity;
    }

    public void setActivity(Class activity) {
        mActivity = activity;
    }
}