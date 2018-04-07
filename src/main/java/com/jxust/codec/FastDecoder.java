package com.jxust.codec;

import com.jxust.protobuf.FastMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FastDecoder extends ByteToMessageDecoder {

    private static final Logger logger = LoggerFactory.getLogger(FastDecoder.class);

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out) throws Exception {

        int readableBytes = byteBuf.readableBytes();
        if(readableBytes<FastHead.HEAD_LENGTH) return;

        FastHead head = new FastHead();
        head.deserialize(byteBuf);
        logger.debug("received msg:{}",head.toString());
        byteBuf.skipBytes(FastHead.HEAD_LENGTH);

        if(byteBuf.readableBytes()<head.bodyLength.get()) return;

        byte[] bytes = new byte[head.bodyLength.get()];
        byteBuf.readBytes(bytes);

        if(head.messageType.get()==MessageType.LOGIN_REQ.getType()) {
            FastMessage.LoginReq loginReq = FastMessage.LoginReq.parseFrom(bytes);
            logger.debug("received loginReq:{}",loginReq.toString());
            out.add(loginReq);
        }else if(head.messageType.get()==MessageType.REQISTER_REQ.getType()) {
            FastMessage.RegisterReq registerReq = FastMessage.RegisterReq.parseFrom(bytes);
            logger.debug("received registerReq:{}",registerReq.toString());
            out.add(registerReq);
        }else if(head.messageType.get()==MessageType.ADD_FRIEND_REQ.getType()) {
            FastMessage.AddFriendReq addFriendReq = FastMessage.AddFriendReq.parseFrom(bytes);
            logger.debug("received addFriendReq:{}",addFriendReq.toString());
            out.add(addFriendReq);
        }else if(head.messageType.get()==MessageType.REMOVE_FRIEND_REQ.getType()) {
            FastMessage.RemoveFriendReq removeFriendReq = FastMessage.RemoveFriendReq.parseFrom(bytes);
            logger.debug("received removeFriendReq:{}",removeFriendReq.toString());
            out.add(removeFriendReq);
        }else if(head.messageType.get()==MessageType.CREATE_GROUP_REQ.getType()) {
            FastMessage.CreateGroupReq createGroupReq = FastMessage.CreateGroupReq.parseFrom(bytes);
            logger.debug("received createGroupReq:{}",createGroupReq.toString());
            out.add(createGroupReq);
        }else if(head.messageType.get()==MessageType.DIRECT_CHAT_REQ.getType()) {
            FastMessage.DirectChatReq directChatReq = FastMessage.DirectChatReq.parseFrom(bytes);
            logger.debug("received directChatReq:{}",directChatReq.toString());
            out.add(directChatReq);
        }else if(head.messageType.get()==MessageType.GROUP_CHAT_TRANS.getType()) {
            FastMessage.GroupChatTrans groupChatTrans = FastMessage.GroupChatTrans.parseFrom(bytes);
            logger.debug("received groupChatTrans:{}",groupChatTrans.toString());
            out.add(groupChatTrans);
        }else if(head.messageType.get()==MessageType.LOGOUT_REQ.getType()) {
            FastMessage.LogoutReq logoutReq = FastMessage.LogoutReq.parseFrom(bytes);
            logger.debug("received logoutReq:{}",logoutReq.toString());
            out.add(logoutReq);
        }else if(head.messageType.get()==MessageType.REMOVE_GROUP_REQ.getType()) {
            FastMessage.RemoveGroupReq removeGroupReq = FastMessage.RemoveGroupReq.parseFrom(bytes);
            logger.debug("received removeGroupReq:{}",removeGroupReq.toString());
            out.add(removeGroupReq);
        }else if(head.messageType.get()==MessageType.FAST_RES.getType()) {
            FastMessage.FastRes fastRes = FastMessage.FastRes.parseFrom(bytes);
            logger.debug("received fastRes:{}",fastRes.toString());
            out.add(fastRes);
        }
    }
}
