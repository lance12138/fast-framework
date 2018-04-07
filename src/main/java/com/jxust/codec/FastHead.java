package com.jxust.codec;

import io.netty.buffer.ByteBuf;
import javolution.io.Struct;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class FastHead extends Struct implements Serializable{

    public Signed8 headLength = new Signed8();
    public Signed8 fastVersion = new Signed8();
    public Signed8 messageType = new Signed8();
    public Signed32 bodyLength = new Signed32();
    public final BitField serializeMethod = new BitField(4);
    public final BitField compressMethod = new BitField(4);
    public final Unsigned32 reserverd = new Unsigned32();
    public Unsigned32 seqNum = new Unsigned32();

    public static final byte HEAD_LENGTH=16;

    public FastHead() {
        headLength.set(HEAD_LENGTH);
        fastVersion.set((byte)1);
        serializeMethod.set((byte)0);
        compressMethod.set((byte)0);
    }

    public void serialize(ByteBuf byteBuf) {
        int size = this.size();
        ByteBuffer innerByteBuffer = getByteBuffer();
        int startPosition=0;
        for (int i=startPosition;i<startPosition+size;++i) {
            byteBuf.writeByte(innerByteBuffer.get(i));
        }
    }

    public void deserialize(ByteBuf byteBuf) {
        ByteBuf internalByteBuf = byteBuf.copy();
        byte[] bytes = new byte[internalByteBuf.readableBytes()];
        internalByteBuf.getBytes(0,bytes);
        this.setByteBuffer(ByteBuffer.wrap(bytes),0);
        internalByteBuf.release();
    }

    @Override
    public boolean isPacked() {
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("fastVersion=");
        sb.append(fastVersion);
        sb.append(", headLength=");
        sb.append(headLength);
        sb.append(", bodyLength=");
        sb.append(bodyLength);
        sb.append(", messageType");
        sb.append(messageType);
        sb.append(", seqNum=");
        sb.append(seqNum);
        return sb.toString();
    }

}
