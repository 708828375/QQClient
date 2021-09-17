package com.digger.qqclient.view;

import com.digger.qqclient.service.MessageClientService;
import com.digger.qqclient.service.UserClientService;
import com.digger.qqclient.utils.Utility;

/**
 * @Description : 客户端菜单界面
 * @Author : 孙梦琼
 * @Date : 2021/9/12 22:23
 * @Version : 1.0
 **/
public class QQView {

    public static void main(String[] args) {
        new QQView().mainView();
        System.out.println("退出系统");
    }

    private boolean loop = true;//控制菜单的循环显示
    private String key = "";//获取键盘输入
    private UserClientService userClientService = new UserClientService();//用来登录服务器、注册用户
    private MessageClientService messageClientService = new MessageClientService(); //用户私聊、群发消息

    //显示主菜单
    public void mainView() {

        while (loop) {
            System.out.println("===========欢迎登录网络通信系统============");
            System.out.println("\t\t1.登录系统");
            System.out.println("\t\t9.退出系统");
            System.out.print("请输入你的选择：");
            key = Utility.readString(1);
            switch (key) {
                case "1":
                    System.out.print("输入你的用户名：");
                    String UserID = Utility.readString(50);
                    System.out.print("输入你的密码：");
                    String pwd = Utility.readString(50);
                    if (userClientService.checkUser(UserID, pwd)) {//如果验证登录成功
                        System.out.println("用户(" + UserID + ")登录成功");
                        while (loop) {
                            System.out.println("=========网络通信系统二级菜单(用户" + UserID + ")=========");
                            System.out.println("\t\t1.显示在线用户列表");
                            System.out.println("\t\t2.群发消息");
                            System.out.println("\t\t3.私聊消息");
                            System.out.println("\t\t4.发送文件");
                            System.out.println("\t\t9.退出系统");
                            System.out.print("请输入你的选择：");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    System.out.println("\n显示在线用户列表");
                                    userClientService.onlineFriendList();
                                    break;
                                case "2":
                                    System.out.print("请输入想对大家说的话：");
                                    String s = Utility.readString(100);
                                    break;
                                case "3":
                                    System.out.println("私聊消息");
                                    System.out.print("请输入你要私聊的用户（在线）：");
                                    String chatUserId = Utility.readString(10);
                                    System.out.print(UserID + "请输入你的聊天内容：");
                                    String content = Utility.readString(100);
                                    System.out.println("\n" + UserID + "对" + chatUserId + "说：" + content);
                                    messageClientService.privateChat(UserID, chatUserId, content);
                                    break;
                                case "4":
                                    System.out.println("发送文件");
                                    break;
                                case "9":
                                    //无异常退出系统
                                    userClientService.logout();
                                    loop = false;
                                    break;
                            }
                        }
                    } else {
                        System.out.println("登录失败");
                    }
                    break;
                case "9":
                    loop = false;
                    break;
            }
        }

    }
}
