package com.zz4955.testmultiobjcoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;


public class PersonDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte n = "n".getBytes()[0];
        byte p = in.readByte(); // Gets a byte at the current readerIndex and increases the readerIndex by 1 in this buffer.
        in.resetReaderIndex();
        if (n != p) { // 如果开头字符不是n，则说明是一个Person对象
            // 把读取的起始位置重置
            ByteBufToBytes reader = new ByteBufToBytes(in.readableBytes());
            out.add(ByteObjConverter.byteToObject(reader.read(in)));
        } else {
            // 执行其它的decode
            ctx.fireChannelRead(in);
        }
    }
}
