package com.jxust.codec;

import com.jxust.protobuf.FastMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class FastEncoder extends MessageToByteEncoder<Object> {

    public static final Logger logger = LoggerFactory.getLogger(FastEncoder.class);
    private static AtomicInteger seqGenerator = new AtomicInteger(0);

    protected void encode(ChannelHandlerContext channelHandlerContext, Object msg, ByteBuf byteBuf) throws Exception {

        byte[] bytes=null;
        FastHead head = new FastHead();

        if(msg instanceof FastMessage.LoginReq) {
            head.messageType.set(MessageType.LOGIN_REQ.getType());
            FastMessage.LoginReq loginReq = (FastMessage.LoginReq) msg;
            bytes=loginReq.toByteArray();
        }else if(msg instanceof FastMessage.RegisterReq) {
            head.messageType.set(MessageType.REQISTER_REQ.getType());
            FastMessage.RegisterReq registerReq = (FastMessage.RegisterReq)msg;
            bytes = registerReq.toByteArray();
        }else if(msg instanceof FastMessage.AddFriendReq) {
            head.messageType.set(MessageType.ADD_FRIEND_REQ.getType());
            FastMessage.AddFriendReq addFriendReq = (FastMessage.AddFriendReq)msg;
            bytes=addFriendReq.toByteArray();
        }else if(msg instanceof FastMessage.CreateGroupReq) {
            head.messageType.set(MessageType.CREATE_GROUP_REQ.getType());
            FastMessage.CreateGroupReq createGroupReq = (FastMessage.CreateGroupReq)msg;
            bytes=createGroupReq.toByteArray();
        }else if(msg instanceof FastMessage.DirectChatReq) {
            head.messageType.set(MessageType.DIRECT_CHAT_REQ.getType());
            FastMessage.DirectChatReq directChatReq = (FastMessage.DirectChatReq)msg;
            bytes = directChatReq.toByteArray();
        }else if(msg instanceof FastMessage.LogoutReq) {
            head.messageType.set(MessageType.LOGOUT_REQ.getType());
            FastMessage.LogoutReq logoutReq = (FastMessage.LogoutReq)msg;
            bytes=logoutReq.toByteArray();
        }else if(msg instanceof FastMessage.FastRes) {
            head.messageType.set(MessageType.FAST_RES.getType());
            FastMessage.FastRes fastRes = (FastMessage.FastRes)msg;
            bytes=fastRes.toByteArray();
        }else if(msg instanceof FastMessage.RemoveFriendReq) {
            head.messageType.set(MessageType.REMOVE_FRIEND_REQ.getType());
            FastMessage.RemoveFriendReq removeFriendReq = (FastMessage.RemoveFriendReq)msg;
            bytes = removeFriendReq.toByteArray();
        }else if(msg instanceof FastMessage.GroupChatTrans) {
            head.messageType.set(MessageType.GROUP_CHAT_TRANS.getType());
            FastMessage.GroupChatTrans groupChatTrans = (FastMessage.GroupChatTrans)msg;
            bytes=groupChatTrans.toByteArray();
        }else if(msg instanceof FastMessage.RemoveGroupReq) {
            head.messageType.set(MessageType.REMOVE_GROUP_REQ.getType());
            FastMessage.RemoveGroupReq removeGroupReq = (FastMessage.RemoveGroupReq)msg;
            bytes=removeGroupReq.toByteArray();
        }

        head.bodyLength.set(bytes.length);
        if(head.messageType.get()>0) {
            head.seqNum.set(seqGenerator.incrementAndGet());
        }

        logger.debug("to send msg:{}",head.toString());
        head.serialize(byteBuf);
        byteBuf.writeBytes(bytes);

    }
}
