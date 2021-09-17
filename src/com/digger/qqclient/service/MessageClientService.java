package com.digger.qqclient.service;

import com.digger.qqcommon.Message;
import com.digger.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: Digger
 * @Description:
 * @Date: create in 2021/9/16 19:19
 */
public class MessageClientService {


    //私聊
    public void privateChat(String sender, String getter, String content) {
        //封装一个message
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_PRIVATE_CHAT);
        message.setSender(sender);
        message.setGetter(getter);
        message.setContent(content);
        try {
            //将消息发给服务器
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(sender).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //群聊
    public void groupChat(String sender, String content) {
        //封装message
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_GROUP_CHAT);
        message.setSender(sender);
        message.setContent(content);
        try {
            //将消息发给服务器
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(sender).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
