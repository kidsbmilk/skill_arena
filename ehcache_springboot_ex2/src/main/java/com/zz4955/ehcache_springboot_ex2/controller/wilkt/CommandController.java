package com.zz4955.ehcache_springboot_ex2.controller.wilkt;

import com.jcraft.jsch.*;
import com.zz4955.ehcache_springboot_ex2.tool.wilkt.MyUserInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
public class CommandController {
    private static JSch jSch = new JSch();
    private static Session session;

    static {
        try {
            session = jSch.getSession("0.0.0.0");
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/start")
    public String start() throws JSchException, IOException {
        session = jSch.getSession("zhangzhen5", "relay.xiaomi.com");
        session.setUserInfo(new MyUserInfo());
        session.connect();
        ChannelShell channel = (ChannelShell) session.openChannel("shell");
        channel.connect();
        InputStream inputStream = channel.getInputStream();
        OutputStream outputStream = channel.getOutputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        outputStream.write("\n".getBytes());
        outputStream.write("exit".getBytes());
        outputStream.flush();
        String msg = null;
        StringBuilder sb = new StringBuilder();
        boolean b = true;
        while((msg = in.readLine())!=null){
            if(msg.isEmpty()) {
                continue;
            }
            if(b) {
                System.out.println(msg.substring(4 + 3)); // 这个为什么要去除几前3个字符？我猜与上面的写入"\n"的回显有关。
                // 这个是观察出来的，具体原因还有待研究。
                b = false;
                continue;
            }
            System.out.println(msg);
            sb.append(msg);
            if(msg.startsWith("FAQ")) {
                outputStream.write("exit".getBytes());
                outputStream.flush();
//                break;
            }
        }
        in.close();
        return sb.toString();
    }

    @RequestMapping(method = RequestMethod.GET, value = "exec")
    public String exec(@RequestParam("cmd") String cmd) throws JSchException, InterruptedException {
        ChannelExec ec = (ChannelExec) session.openChannel("exec");
        ec.setCommand("ls");
        ec.setInputStream(null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ec.setErrStream(baos);
        ec.setOutputStream(baos);
        ec.connect();
//        while(!ec.isClosed()) {
//            Thread.sleep(500);
//        }
        Thread.sleep(5000);
        ec.disconnect();
        session.disconnect();
        return baos.toString();
    }
}
