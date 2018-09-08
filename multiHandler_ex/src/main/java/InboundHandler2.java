import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InboundHandler2 extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(InboundHandler2.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("InboundHandler2.channelRead: ctx :" + ctx);
        System.out.print("Client said: " + ByteBufUtils.convertByteBufToString((ByteBuf) msg));

        ctx.write(msg); // inboundhandler类型的消息传递是从1到2的，是inbounderHandler1.channelRead()处理后才到这里，
        // 下面的也一样，是inbounderHandler1.channelReadComplete处理完才会调用inbounderHandler2.channelReadComplete，
        // 这里实现的功能是：一边处理客户端发来的数据，一边写出准备送往客户端的数据，只有当客户端的数据读取并处理完后，才会开始发送数据给客户端。
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("InboundHandler2.channelReadComplete");
        ctx.flush();
    }
}
