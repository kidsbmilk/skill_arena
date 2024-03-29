package com.zz4955.testmultiobjcoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;


public class StringDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 判断是否是String协议
        byte n = "n".getBytes()[0];
        byte p = in.readByte();
        // 把读取的起始位置重置
        in.resetReaderIndex();
        if (n == p) { // 开头字符是n，则说明是一个String对象
            ByteBufToBytes reader = new ByteBufToBytes(in.readableBytes());
            String msg = new String(reader.read(in));
            Person person = buildPerson(msg);
            out.add(person);
            //in.release();
        } else {
            ctx.fireChannelRead(in);
        }
    }

    private Person buildPerson(String msg) {
        Person person = new Person();
        String[] msgArray = msg.split(";|:");
        person.setName(msgArray[1]);
        person.setAge(Integer.parseInt(msgArray[3]));
        person.setSex(msgArray[5]);
        return person;
    }
}
