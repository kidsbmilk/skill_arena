package com.zz4955.ehcache_springboot_ex2.controller.wilkt;

import com.jcraft.jsch.*;
import com.zz4955.ehcache_springboot_ex2.tool.wilkt.MyUserInfo;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@RestController
public class CommandController {
    private static JSch jSch = new JSch();
    private static Session session;
    private static InputStream inputStream;
    private static OutputStream outputStream;
    private static BufferedReader in;

    static {
        try {
            session = jSch.getSession("0.0.0.0");
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    /**
     * 输入输出真的是一个非常麻烦的东西。
     * 必须实现跟SecureCRT一样的功能、一样的体验。
     * 当服务器端输出Your [EMAIL] password:，等待用户输入密码或者扫二维码时，我也要把这些东西展示在前端，等待用户操作。
     * 当用户扫描登录后或者输入密码后，我也要在前端中展示登录成功信息，这个需要前端配合，前端等待服务器端给它发信息（前端主动轮询或者服务器端主动推送），
     * 服务器端需要开一个后台线程去查看ssh连接是否有登录成功的消息到来。
     */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/start")
    public String start() throws JSchException, IOException, InterruptedException {
        session = jSch.getSession("zhangzhen5", "relay.xiaomi.com");
        session.setUserInfo(new MyUserInfo());
        session.connect();
        ChannelShell channel = (ChannelShell) session.openChannel("shell");
        channel.connect();
        inputStream = channel.getInputStream();
        outputStream = channel.getOutputStream();
        in = new BufferedReader(new InputStreamReader(inputStream));
        outputStream.write("\r\n".getBytes());
        outputStream.flush();
        String stp = solveSshRead(2);
        return stp.substring(7);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/getLoginSuccessInfo")
    public String getLoginSuccessInfo() throws IOException, InterruptedException {
        String temp = solveSshRead(1);
        return temp;
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "exec")
    public String exec(@RequestParam("cmd") String cmd) throws IOException, InterruptedException {
        StringBuilder sb = new StringBuilder();
        outputStream.write((cmd + "\r\n").getBytes());
        outputStream.flush();
        if(cmd.equals("exit")) {
            return solveSshRead(3);
        }
        return solveSshRead(2);
    }

    private String solveSshRead(int count) throws IOException, InterruptedException {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbResult = new StringBuilder();
        byte[] tmp = new byte[1024];
        String stp;
        while(true) {
            while (inputStream.available() > 0) {
                int i = inputStream.read(tmp, 0, 1024);
                if (i < 0) {
                    return sb.toString();
                }
                String msgtp = new String(tmp, 0, i);
                sb.append(msgtp);
                sbResult.append(msgtp);
            }
            stp = sb.toString();
            char[] input = new char[7];
            boolean isequal = false;
            if(stp.length() > 7) {
                stp.getChars(0, 7, input, 0);
                isequal = isEqual(input, 7);
            }
            if(isequal) {
                stp = stp.substring(7);
                solveEachLine(stp);
                sb = new StringBuilder();
                break;
            } else {
                solveEachLine(stp);
                sb = new StringBuilder();
            }
            count --;
            if(count <= 0) {
                break;
            }
            Thread.sleep(1 * 1000);
        }
        return sbResult.toString();
    }

    private boolean isEqual(char[] input, int size) {
        char[] cs = new char[7];
        cs[0] = '\r';
        cs[1] = '\n';
        cs[2] = '\r';
        cs[3] = '\n';
        cs[4] = '\u001B';
        cs[5] = '[';
        cs[6] = 'c';
        for(int i = 0; i < size; i ++) {
            if(cs[i] != input[i]) {
                return false;
            }
        }
        return true;
    }

    private String solveEachLine(String stp) {
        while(true) {
            int ind = stp.indexOf("\r\n");
            if(ind == -1) {
                break;
            }
            System.out.println(stp.substring(0, ind));
            stp = stp.substring(ind + 2);
        }
        System.out.println(stp);
        return stp;
    }
}
