package com.digger.qqclient.service;

import com.digger.qqcommon.Message;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @Description : 与服务器端一直保持通信的线程
 * @Author : 孙梦琼
 * @Date : 2021/9/12 23:22
 * @Version : 1.0
 **/
public class ClientConnectServerThread extends Thread{
    //该线程需要持有socket
    private Socket socket;

    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while(true){
            try {
                System.out.println("客户端线程，正在等待从服务器端读取数据");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();//若服务器端没有消息，会一直阻塞在这里等待
                //TODO 使用Message
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
