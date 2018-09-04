import java.io.*;
import java.security.Security;
import java.security.PrivilegedActionException;

import javax.net.ssl.*;
import com.sun.net.ssl.internal.ssl.Provider;

public class SSLServer {

    public static void main(String[] args) throws Exception {
        boolean debug = true;
        int intSSLport = 4443;

        {
            Security.addProvider(new Provider());

            System.setProperty("javax.net.ssl.keyStore","./server.jks");
            System.setProperty("javax.net.ssl.keyStorePassword", "123456");
        }

        if(debug){
            System.setProperty("javax.net.debug", "all");
        }
        try {
            SSLServerSocketFactory sslServerSocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketfactory.createServerSocket(intSSLport);
            SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();

            while (true) {
                PrintWriter out = new PrintWriter(sslSocket.getOutputStream(),true);
                BufferedReader in = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    out.println(inputLine);
                    System.out.println(inputLine);
                }

                out.close();
                in.close();
                sslSocket.close();
                sslServerSocket.close();

            }
        }
        catch (Exception exp) {
            PrivilegedActionException priexp = new PrivilegedActionException(exp);
            System.out.println(" Priv exp --- " + priexp.getMessage());
            System.out.println(" Exception occurred .... " + exp);
            exp.printStackTrace();
        }

    }

}