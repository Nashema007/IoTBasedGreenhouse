package com.example.iotbasedgreenhouse.utilities;

public class AppConfig {

    public static final String CHANNEL_ID = "Greenhouse";

    public static final String GET_REAL_TIME = "http://192.168.4.1";
    public static final String GET_YEARLY_VALUES = "http://192.168.4.2/iotgreenhouse/api/yearly_values.php";
    public static final String GET_MONTHLY_VALUES = "http://192.168.4.2/iotgreenhouse/api/monthly_values.php";
    public static final String GET_WEEKLY_VALUES = "http://192.168.4.2/iotgreenhouse/api/weekly_values.php";
    public static final String GET_DAILY_VALUES = "http://192.168.4.2/iotgreenhouse/api/daily_values.php";
}