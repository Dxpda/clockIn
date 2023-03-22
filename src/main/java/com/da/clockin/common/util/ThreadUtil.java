//开发时间 : 2023/3/18 12:43

package com.da.clockin.common.util;

import java.util.Map;

public class ThreadUtil {
    private final static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    public static Map<String, Object> getThreadLocal() {
        return threadLocal.get();
    }

    public static void setThreadLocal(Map<String, Object> map) {
        threadLocal.set(map);
    }

    public static Long getId() {
        return (Long) getThreadLocal().get("id");
    }
    public static int getPower() {
        return (int) getThreadLocal().get("power");
    }

    public static String getUser() {
        return String.valueOf(getThreadLocal().get("user"));
    }

}
