package com.sd.demo.app;

import com.sd.libcore.app.FApplication;
import com.sd.libcore.utils.FActivityStack;

public class App extends FApplication
{
    @Override
    protected void onCreateMainProcess()
    {
        FActivityStack.getInstance().setDebug(true);
    }
}
