package com.digger.qqclient.service;

import java.util.HashMap;

/**
 * @Description : 管理客户端与服务器端连接的线程
 * @Author : 孙梦琼
 * @Date : 2021/9/12 23:49
 * @Version : 1.0
 **/
public class ManageClientConnectServerThread {
    //将多个线程放入到一个HashMap中，key:userId,value:线程
    private static HashMap<String,ClientConnectServerThread> hm = new HashMap<>();

    public static HashMap<String, ClientConnectServerThread> getHm() {
        return hm;
    }

    public static void setHm(HashMap<String, ClientConnectServerThread> hm) {
        ManageClientConnectServerThread.hm = hm;
    }

    //将线程加入到HashMap中
    public static void addClientConnectServerThread(String userId,ClientConnectServerThread clientConnectServerThread){
        hm.put(userId,clientConnectServerThread);
    }
    //从HashMap中获取一个线程
    public static ClientConnectServerThread getClientConnectServerThread(String userId){
        return hm.get(userId);
    }
}
