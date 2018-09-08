import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloClientIntHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(HelloClientIntHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("HelloClientIntHandler.channelRead");
        System.out.print("Server said: " + ByteBufUtils.convertByteBufToString((ByteBuf) msg));
//        if(ByteBufUtils.convertByteBufToString((ByteBuf) msg).equals("close")) { // 不能在这里这样写，因为可能会发生粘包，并不等于close，如果加上个定长的分包的handler，可以这样写。
//            ctx.close();
//        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("HelloClientIntHandler.channelActive");
        ctx.write(ByteBufUtils.convertStringToByteBuf(ctx, "Are you ok?\n"));
        ctx.flush(); // ctx.write()方法执行后，需要调用flush()方法才能令它立即执行。
    }
}
