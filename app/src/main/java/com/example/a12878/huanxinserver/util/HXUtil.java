package com.example.a12878.huanxinserver.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.a12878.huanxinserver.MainActivity;
import com.example.a12878.huanxinserver.base.User;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.util.NetUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.a12878.huanxinserver.base.User.*;

/**
 * Created by 12878 on 2018/7/26.
 */

public class HXUtil {

    private static EMMessageListener mMessageListener;
    private static MessageToExoPlay mMessageToExoPlay;

    public interface MessageToExoPlay{
        void getMessage(String[] message);
    }

    public static void setMEssageToExoPlay(MessageToExoPlay messageToExoPlay){
        mMessageToExoPlay = messageToExoPlay;
    }

    public static void init(Context context){

        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        // 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
        options.setAutoTransferMessageAttachments(true);
        // 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        options.setAutoDownloadThumbnail(true);
        //初始化
        EMClient.getInstance().init(context, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);

        EMClient.getInstance().login(testUserName2, testPwd,new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("main", "登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！");
            }
        });

        EMClient.getInstance().addConnectionListener(new MyConnectionListener());
        setMsgListener();
    }

    //设置消息监听
    private static void setMsgListener(){

        mMessageListener= new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {


            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {

            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
                //收到已读回执
            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
                //收到已送达回执
            }

            @Override
            public void onMessageRecalled(List<EMMessage> messages) {
                //消息被撤回
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
            }
        };
        //消息监听
        EMClient.getInstance().chatManager().addMessageListener(mMessageListener);
    }

    //移除消息监听
    public static void removeMessageListener() {
        EMClient.getInstance().chatManager().removeMessageListener(mMessageListener);
    }


    //网络连接状态监听
    private static class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {

        }

        @Override
        public void onDisconnected(final int error) {
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    doSendMsg(error);
                }
            }.start();
        }

        private void doSendMsg(int error){
            Message message = Message.obtain();
            message.arg1 = error;
            message.what = 1;
            mHandler.sendMessage(message);
        }


        Handler mHandler = new Handler(){
            /**
             * handleMessage接收消息后进行相应的处理
             * @param msg
             */
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==1){
                    if (msg.arg1 == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                    } else if (msg.arg1 == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                    } else if (NetUtils.hasNetwork(MainActivity.mContext)) {
                        //连接不到聊天服务器
                    } else {
                        //当前网络不可用，请检查网络设置
                    }
                }
            }
        };
    }

    //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
    //如果是群聊，设置chattype，默认是单聊
    public static void sendTextMessage(String content, String toChatUsername){
        EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
        EMClient.getInstance().chatManager().sendMessage(message);
    }


    public static void sendTcMessage(String content, String toChatUsername) {

        EMMessage cmdMsg = EMMessage.createSendMessage(EMMessage.Type.CMD);

        //支持单聊和群聊，默认单聊，如果是群聊添加下面这行
        //cmdMsg.setChatType(EMMessage.ChatType.GroupChat)
        //content可以自定义
        EMCmdMessageBody cmdBody = new EMCmdMessageBody(content);
        //发送给某个人
        cmdMsg.setTo(toChatUsername);
        cmdMsg.addBody(cmdBody);
        EMClient.getInstance().chatManager().sendMessage(cmdMsg);
    }
}
