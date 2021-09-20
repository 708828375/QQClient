package com.digger.qqclient.service;

import com.digger.qqcommon.Message;
import com.digger.qqcommon.MessageType;
import com.sun.corba.se.impl.orbutil.ObjectStreamClassUtil_1_3;

import javax.sound.midi.Soundbank;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @Description : 与服务器端一直保持通信的线程
 * @Author : 孙梦琼
 * @Date : 2021/9/12 23:22
 * @Version : 1.0
 **/
public class ClientConnectServerThread extends Thread {
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
        while (true) {
            try {
                System.out.println("客户端线程，正在等待从服务器端读取数据");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();//若服务器端没有消息，会一直阻塞在这里等待
                //判断message类型，然后做相应的业务处理
                if (message.getMessageType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)) {//如果是服务端返回的在线好友列表
                    //取出在线列表信息并显示:在线用户列表形式-》100 200 紫霞仙子
                    String[] onlineUsers = message.getContent().split(" ");
                    System.out.println("==========当前在线用户列表==========");
                    for (int i = 0; i < onlineUsers.length; i++) {
                        System.out.println("用户： " + onlineUsers[i]);
                    }
                }else if(MessageType.MESSAGE_PRIVATE_CHAT.equals(message.getMessageType())){//如果是用户私聊消息
                    System.out.println("\n" + message.getSender() + "对" + message.getGetter() + "说：" + message.getContent());
                }else if(MessageType.MESSAGE_GROUP_CHAT.equals(message.getMessageType())){//如果收到的是群发消息
                    System.out.println(message.getSender() + "对大家说：" + message.getContent());
                }else if(MessageType.MESSAGE_FILE_MES.equals(message.getMessageType())){//如果收到的是文件消息
                    //接收文件，将文件存入指定路径
                    System.out.println("\n" + message.getSender() + "给" + message.getGetter() + "发送文件:" + message.getSrc() + "到我的电脑：" + message.getDst());
                    //取出message的文件字节数组，通过文件输出流写出到一个磁盘
                    FileOutputStream fileOutputStream = new FileOutputStream(message.getDst());
                    fileOutputStream.write(message.getFileBytes());
                    fileOutputStream.close();
                    System.out.println("\n 保存文件成功~");
                }else{
                    System.out.println("是其他类型的message，暂时不处理。。。。");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
