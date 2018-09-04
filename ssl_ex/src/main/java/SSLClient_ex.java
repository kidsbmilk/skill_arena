import javax.net.SocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyStore;

public class SSLClient_ex {
    private static String CLIENT_KEY_STORE = "/Users/soybeanmilk/Documents/java_workhome/ssl_ex/src/main/java/client_ks";

    private static String CLIENT_KEY_STORE_PASSWORD = "456456"; // 由于((SSLServerSocket) _socket).setNeedClientAuth(true);这里是true，
    // 开启了双向验证，所以，这里的代码用于在建立连接时向服务器出示自己的证书。

    public static void main(String[] args) throws Exception {
        // Set the key store to use for validating the server cert.
        System.setProperty("javax.net.ssl.trustStore", CLIENT_KEY_STORE);

        System.setProperty("javax.net.debug", "ssl,handshake"); // 这个debug可以改为error,info，不同的日志级别。

        SSLClient_ex client = new SSLClient_ex();
//        Socket s = client.clientWithoutCert();
        Socket s = client.clientWithCert(); // 由于((SSLServerSocket) _socket).setNeedClientAuth(true);这里是true，
        // 开启了双向验证，所以，这里的代码用于在建立连接时向服务器出示自己的证书。

        PrintWriter writer = new PrintWriter(s.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        writer.println("hello");
        writer.flush();
        System.out.println(reader.readLine());
        s.close();
    }

    private Socket clientWithCert() throws Exception { // 由于((SSLServerSocket) _socket).setNeedClientAuth(true);这里是true，
        // 开启了双向验证，所以，这里的代码用于在建立连接时向服务器出示自己的证书。
        SSLContext context = SSLContext.getInstance("TLS");
        KeyStore ks = KeyStore.getInstance("jceks");

        ks.load(new FileInputStream(CLIENT_KEY_STORE), null);
        KeyManagerFactory kf = KeyManagerFactory.getInstance("SunX509");
        kf.init(ks, CLIENT_KEY_STORE_PASSWORD.toCharArray());
        context.init(kf.getKeyManagers(), null, null);

        SocketFactory factory = context.getSocketFactory();
        Socket s = factory.createSocket("localhost", 8443);
        return s;
    }

    private Socket clientWithoutCert() throws Exception {
        SocketFactory sf = SSLSocketFactory.getDefault();
        Socket s = sf.createSocket("localhost", 8443);
        return s;
    }
}
