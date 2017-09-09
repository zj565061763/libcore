package com.fanwe.library.model;


import com.fanwe.library.common.SDSelectManager;

public class ImageModel implements SDSelectManager.Selectable
{

    private int id;

    private String bucketId;

    /**
     * 照片添加日期
     */
    private long addDate;
    /**
     * 照片路径
     */
    private String uri;
    /**
     * 大小
     */
    private long size;

    private String displayName;

    private boolean selected;


    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getBucketId()
    {
        return bucketId;
    }

    public void setBucketId(String bucketId)
    {
        this.bucketId = bucketId;
    }

    public long getAddDate()
    {
        return addDate;
    }

    public void setAddDate(long addDate)
    {
        this.addDate = addDate;
    }

    @Override
    public boolean isSelected()
    {
        return selected;
    }

    @Override
    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public long getSize()
    {
        return size;
    }

    public void setSize(long size)
    {
        this.size = size;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }
}
