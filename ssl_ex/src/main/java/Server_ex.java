import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server_ex extends Thread {
    private Socket socket;
    private static int count = 0;

    public Server_ex(Socket socket) {
        this.socket = socket;
        count ++;
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            String data = reader.readLine();
            System.out.println("client : " + data);
            writer.println("#" + String.valueOf(count) + " server return : " + data);
            writer.close();
            socket.close();
        } catch (IOException e) {

        }
    }

    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(8080);
        while (true && count < 5) {
            new Server_ex(ss.accept()).start();
        }
        ss.close();
    }
}

/*
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server_ex {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(8888);
            System.out.println("启动服务器....");
            Socket s = ss.accept();
            System.out.println("客户端:"+s.getInetAddress().getLocalHost()+"已连接到服务器");

            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            //读取客户端发送来的消息
            String mess = br.readLine();
            System.out.println("客户端："+mess);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            bw.write(mess+"eerere \n");
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/