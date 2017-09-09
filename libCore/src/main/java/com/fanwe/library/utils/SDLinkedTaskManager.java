package com.fanwe.library.utils;

import java.util.LinkedList;

/**
 * 链式执行管理类
 */
public class SDLinkedTaskManager
{

    private LinkedList<SDLinkedTask> listTask = new LinkedList<>();

    public boolean hasTask(SDLinkedTask task)
    {
        int index = indexOf(task);
        if (index < 0)
        {
            return false;
        }
        if (index >= listTask.size())
        {
            return false;
        }
        return true;
    }

    public boolean hasNext(SDLinkedTask task)
    {
        int index = indexOf(task);
        if (index < 0)
        {
            return false;
        }
        if (index >= listTask.size() - 1)
        {
            return false;
        }
        return true;
    }

    public int indexOf(SDLinkedTask task)
    {
        if (task == null)
        {
            return -1;
        }
        return listTask.indexOf(task);
    }

    public boolean runNext(SDLinkedTask task, boolean removeCurrent)
    {
        if (!hasNext(task))
        {
            return false;
        }

        SDLinkedTask nextTask = getTask(listTask.indexOf(task) + 1);
        if (removeCurrent)
        {
            remove(task);
        }
        nextTask.run();
        return true;
    }

    public SDLinkedTask getTask(int index)
    {
        if (index >= 0 && index < listTask.size())
        {
            return listTask.get(index);
        }
        return null;
    }

    public SDLinkedTaskManager add(SDLinkedTask task)
    {
        if (task == null)
        {
            return this;
        }

        task.setManager(this);
        listTask.add(task);
        return this;
    }

    public void start()
    {
        SDLinkedTask task = getTask(0);
        if (task != null)
        {
            task.run();
        }
    }

    public boolean remove(SDLinkedTask task)
    {
        if (!hasTask(task))
        {
            return false;
        }
        return listTask.remove(task);
    }

    public void clear()
    {
        listTask.clear();
    }

    public static abstract class SDLinkedTask
    {
        private SDLinkedTaskManager manager;

        public final void setManager(SDLinkedTaskManager manager)
        {
            this.manager = manager;
        }

        public abstract void run();

        public final void runNext()
        {
            manager.runNext(this, false);
        }

        public final int index()
        {
            return manager.indexOf(this);
        }
    }
}
