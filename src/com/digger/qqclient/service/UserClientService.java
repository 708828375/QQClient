package com.digger.qqclient.service;

import com.digger.qqcommon.Message;
import com.digger.qqcommon.MessageType;
import com.digger.qqcommon.User;

import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @Description : 完成用户登录、注册等功能
 * @Author : 孙梦琼
 * @Date : 2021/9/12 23:01
 * @Version : 1.0
 **/
public class UserClientService {

    private User user = new User();
    private Socket socket;

    public boolean checkUser(String userId,String pwd){
        boolean check = false;
        user.setId(userId);
        user.setPassword(pwd);
        try {
            //创建与服务器的连接
            Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
            //将用户对象发送给服务器去验证
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(user);

            //接收服务器端回复的Message消息
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message message = (Message) ois.readObject();

            //通过收到服务器端的消息判断登录状态
            if(message.getMessageType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)){//登录成功
                //创建一个与服务器端保持通信的线程->创建线程类ClientConnectServerThread
                ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(socket);
                //启动线程
                clientConnectServerThread.start();
                //为了客户端的扩展，将线程加入到集合中进行管理
                ManageClientConnectServerThread.addClientConnectServerThread(userId,clientConnectServerThread);
                check = true;
            }else{
                //如果登录失败，关闭与服务器的连接socket
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    //向服务器端请求在线用户列表
    public void onlineFriendList(){
        //发送一个Message，类型为MESSAGE_GET_ONLINE_FRIEND
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        message.setSender(user.getId());
        //发送给服务器
        try {
            //得到当前线程的socket对应的ObjectOutputStream对象
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(user.getId()).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //向服务器发送退出系统的消息
    public void logout(){
        Message message = new Message();
        message.setSender(user.getId());
        message.setMessageType(MessageType.MESSAGE_CLIENT_EXIT);
        //发送给服务器
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(user.getId()).getSocket().getOutputStream());
            oos.writeObject(message);
            System.out.println(user.getId() + "退出了系统。。。。");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
