package com.digger.qqclient.service;

import com.digger.qqcommon.Message;
import com.digger.qqcommon.MessageType;

import java.io.*;

/**
 * @Author: Digger
 * @Description: 文件传输服务
 * @Date: create in 2021/9/20 11:24
 */
public class FileClientServer {

    /**
     * 发送文件给指定用户
     * @param src 文件所在路径
     * @param dst 文件接收的路径
     * @param senderId 发送人的ID
     * @param getterId 接收人的ID
     */
    public void sendFileToOne(String src, String dst, String senderId, String getterId) {
        //读取src文件，封装到message中
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_FILE_MES);
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setSrc(src);
        message.setDst(dst);

        //需要将文件读取
        FileInputStream fileInputStream = null;
        byte[] fileBytes = new byte[(int)new File(src).length()];
        try {
            fileInputStream = new FileInputStream(src);
            //读取文件
            fileInputStream.read(fileBytes);
            //将文件字节数组设置到message对象
            message.setFileBytes(fileBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //关闭
            if(fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("\n" + senderId + "给" + getterId + "发送文件：" + src + "到对方的电脑目录：" + dst);

        //发送
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
