package com.jxust.codec;

public enum MessageType {

    LOGIN_REQ((byte)1),
    REQISTER_REQ((byte)2),
    LOGOUT_REQ((byte)3),
    DIRECT_CHAT_REQ((byte)4),
    ADD_FRIEND_REQ((byte)5),
    REMOVE_FRIEND_REQ((byte)6),
    CREATE_GROUP_REQ((byte)7),
    GROUP_CHAT_TRANS((byte)8),
    REMOVE_GROUP_REQ((byte)9),
    FAST_RES((byte)0)
    ;


    private byte type;

    MessageType(byte type) {
        this.type = type;
    }

    public byte getType(){
        return type;
    }
}
