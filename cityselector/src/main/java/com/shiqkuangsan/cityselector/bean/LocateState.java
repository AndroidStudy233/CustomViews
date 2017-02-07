package com.shiqkuangsan.cityselector.bean;

/**
 * 定位状态bean(定位中,定位成功,定位失败)
 */
public interface LocateState {
    int LOCATING = 1;
    int SUCCESS = 0;
    int FAILED = -1;
}
