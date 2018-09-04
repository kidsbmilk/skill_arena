import java.io.*;

import javax.net.ssl.*;
import com.sun.net.ssl.internal.ssl.Provider;
import java.security.Security;

public class SSLClient {

    public static void main(String[] args) throws Exception {
        String strServerName = "localhost";
        int intSSLport = 4443;
        PrintWriter out = null;
        BufferedReader in = null;
        boolean debug = true;
        {
            Security.addProvider(new Provider());

            System.setProperty("javax.net.ssl.trustStore","./client.jks");
            System.setProperty("javax.net.ssl.trustStorePassword", "123456");

        }
        if(debug){
            System.setProperty("javax.net.debug", "all");
        }
        try {

            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket sslSocket = (SSLSocket) sslsocketfactory.createSocket(strServerName, intSSLport);

            out = new PrintWriter(sslSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));

            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput = "Hello Testing ";
            out.println(userInput);

            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("echo: " + in.readLine());
            }

            out.println(userInput);

            out.close();
            in.close();
            stdIn.close();
            sslSocket.close();
        }

        catch (Exception exp) {
            System.out.println(" Exception occurred .... " + exp);
            exp.printStackTrace();
        }

    }

}
