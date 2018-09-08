import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutboundHandler2 extends ChannelOutboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(OutboundHandler2.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        logger.info("OutboundHandler2.write");
        System.out.print(ByteBufUtils.convertByteBufToString((ByteBuf)msg)); // 客户端发来的数据
//        ctx.write(msg);
        super.write(ctx, msg, promise); // 传递给下一个OutboundHandler来写数据。
        ctx.write(ByteBufUtils.convertStringToByteBuf(ctx, "test 2 outbound\n")); // 不论是ctx.write，还是super.write，都要经过channelPipeline
        ctx.write(ByteBufUtils.convertStringToByteBuf(ctx, "close"));
    }
}
