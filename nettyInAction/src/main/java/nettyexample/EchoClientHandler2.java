package nettyexample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

@ChannelHandler.Sharable
public class EchoClientHandler2 extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("2 active");
        ctx.writeAndFlush(Unpooled.copiedBuffer("2 rocks!", CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
        System.out.println("read 2");
        System.out.println("Client 2 received: " + in);
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!222", CharsetUtil.UTF_8));
    }

//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        cause.printStackTrace();
//        System.out.println("2");
//        ctx.close();
//    }

}
