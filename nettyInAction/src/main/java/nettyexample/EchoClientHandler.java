package nettyexample;

import io.netty.buffer.*;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

@ChannelHandler.Sharable                                                        // #1  该注解标示该处理器是可以在通道间共享的
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("Active");
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8)); //#2 通道连接上后写入消息 记得flush() 很重要
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object in) {
        System.out.println("Read");
        ByteBuf temp = ((ByteBuf)in).retainedSlice();
        System.out.println("Client received: " + ByteBufUtil
                .hexDump(temp.readBytes(temp.readableBytes())));  //#4
//        System.out.println("Client received: " + utils.convertByteBufToString(in));
//        ctx.writeAndFlush(Unpooled.copiedBuffer("test write again", CharsetUtil.UTF_8));
        ctx.fireChannelRead(in);
        ReferenceCountUtil.release(temp);
        System.out.println(1/0);
    }
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx,              //#5
//                                Throwable cause) {
////        cause.printStackTrace();
//        System.out.println("1");
////        ctx.close();
////        ctx.fireExceptionCaught(cause);
//    }

}