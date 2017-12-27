package com.fanwe.demo.model;

import com.fanwe.lib.selectmanager.FSelectManager;
import com.fanwe.lib.utils.FIterateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */

public class DataModel extends FSelectManager.SelectableModel
{
    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public static List<DataModel> getListModel(int count)
    {
        final List<DataModel> listModel = new ArrayList<>();
        FIterateUtil.foreach(count, new FIterateUtil.SimpleIterateCallback()
        {
            @Override
            public boolean next(int i)
            {
                DataModel model = new DataModel();
                model.setName(String.valueOf(i));
                listModel.add(model);
                return false;
            }
        });

        return listModel;
    }
}
