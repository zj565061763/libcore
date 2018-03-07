package com.fanwe.library.event;

import android.telephony.TelephonyManager;

/**
 * 电话状态变更事件
 */
public class ECallStateChanged
{
    /**
     * @see TelephonyManager#CALL_STATE_IDLE
     * @see TelephonyManager#CALL_STATE_RINGING
     * @see TelephonyManager#CALL_STATE_OFFHOOK
     */
    public int state;
    /**
     * 来电号码
     */
    public String incomingNumber;
}
