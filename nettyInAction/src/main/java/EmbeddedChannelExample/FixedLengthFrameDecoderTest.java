package EmbeddedChannelExample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

public class FixedLengthFrameDecoderTest {

    @Test
    public void testFramesDecoded() {
        ByteBuf buf = Unpooled.buffer();
        for(int i = 0; i < 9; i ++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        assertTrue(channel.writeInbound(input.retain()));
        assertTrue(channel.finish());

        ByteBuf read = channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        assertNull(channel.readInbound());
        buf.release();
    }
}
