import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 详解Netty的优雅退出机制和原理：http://www.52im.net/thread-348-1-1.html
 */
public class HelloServer {
    public static ChannelFuture bindFuture;
    public void start(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            // 注册两个OutboundHandler，执行顺序为注册顺序的逆序，所以应该是OutboundHandler2 OutboundHandler1
                            ch.pipeline().addLast(new OutboundHandler1());
                            // ChannelOutboundHandler在注册的时候需要放在最后一个ChannelInboundHandler之前，
                            // 否则将无法传递到ChannelOutboundHandler.
                            ch.pipeline().addLast(new OutboundHandler2());
                            // 注册两个OutboundHandler，执行顺序为注册顺序，所以应该是InboundHandler1 OutboundHandler2
                            ch.pipeline().addLast(new InboundHandler1());
                            ch.pipeline().addLast(new InboundHandler2());
                        }
                    }).option(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture f = b.bind(port).sync();
            bindFuture = f;
            f.channel().closeFuture().sync();
        } finally {
            System.out.println("close group");
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        HelloServer server = new HelloServer();
        server.start(8000);
    }
}



























