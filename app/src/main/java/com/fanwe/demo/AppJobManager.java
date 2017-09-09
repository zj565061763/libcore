package com.fanwe.demo;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;

/**
 * Created by Administrator on 2017/6/20.
 */

public class AppJobManager extends JobManager
{

    private static AppJobManager sInstance;

    /**
     * Creates a JobManager with the given configuration
     *
     * @param configuration The configuration to be used for the JobManager
     * @see Configuration.Builder
     */
    private AppJobManager(Configuration configuration)
    {
        super(configuration);
    }

    public static AppJobManager getInstance()
    {
        if (sInstance == null)
        {
            synchronized (AppJobManager.class)
            {
                if (sInstance == null)
                {
                    sInstance = new AppJobManager(new Configuration.Builder(App.getInstance()).build());
                }
            }
        }
        return sInstance;
    }
}
