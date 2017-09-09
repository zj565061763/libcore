package com.fanwe.library.utils;

import java.util.Random;

/**
 * Created by Administrator on 2017/3/9.
 */

public class SDRandom extends Random
{

    @Override
    public int nextInt(int n)
    {
        if (n <= 0)
        {
            return 0;
        } else
        {
            return super.nextInt(n);
        }
    }
}
