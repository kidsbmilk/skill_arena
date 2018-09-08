import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ByteBufUtils {

    public static String convertByteBufToString(ByteBuf buf) {
        String str;
        if(buf.hasArray()) { // 处理堆缓冲区
            str = new String(buf.array(), buf.arrayOffset() + buf.readerIndex(), buf.readableBytes());
        } else { // 处理直接缓冲区以及复合缓冲区
            byte[] bytes = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(), bytes);
            str = new String(bytes, 0, buf.readableBytes());
        }
        return str;
    }

    public static ByteBuf convertStringToByteBuf(ChannelHandlerContext ctx, String str) {
        ByteBuf encoded = ctx.alloc().buffer(4 * str.length());
        encoded.writeBytes(str.getBytes());
        return encoded;
    }
}
