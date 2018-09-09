package com.zz4955.testmulticorderandhandler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;

public class StringEncoder extends ChannelOutboundHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(StringEncoder.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        logger.info("StringEncoder response to client.");
        String serverMsg = (String) msg;

        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(serverMsg.getBytes()));
        response.headers().set(CONTENT_TYPE, "text/plain");
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        ctx.write(response);
        ctx.flush();
    }
}
