package com.airsharing.company.airsharing.model;

/**
 * Created by surin on 2017. 10. 16..
 */

public class IPAddress {
    private static String ip;
    private static int port;

    public IPAddress() {
        this.ip = "13.124.32.165";
        this.port = 8080;
    }

    public static String getIp() {
        return ip;
    }

    public static int getPort() {
        return port;
    }
}
