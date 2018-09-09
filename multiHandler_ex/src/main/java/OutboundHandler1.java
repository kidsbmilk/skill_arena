import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutboundHandler1 extends ChannelOutboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(OutboundHandler1.class);
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        logger.info("OutboundHandler1.write");
        String str = ByteBufUtils.convertByteBufToString((ByteBuf)msg);
        System.out.print(str);

        ctx.write(ByteBufUtils.convertStringToByteBuf(ctx, "I am ok!\n"));
        ctx.write(msg);
        ctx.flush(); // ctx.write()方法执行后，需要调用flush()方法才能令它立即执行。

        // flush之后，msg对象会被释放，所以要提前保存str。
        if(str.equals("close")) {
            ctx.close();
            System.out.println("close server");
            HelloServer.bindFuture.channel().close();
        }
    }
}
