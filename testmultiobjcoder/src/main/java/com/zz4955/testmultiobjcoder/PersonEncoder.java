package com.zz4955.testmultiobjcoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PersonEncoder extends MessageToByteEncoder<Person>  {

    @Override
    protected void encode(ChannelHandlerContext ctx, Person msg, ByteBuf out) throws Exception {
        out.writeBytes(ByteObjConverter.objectToByte(msg));
        ctx.flush();
//        ctx.close();
    }
}
