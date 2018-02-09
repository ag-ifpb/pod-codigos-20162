package test.comm;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import ag.protocol.Frame;
import ag.protocol.Transport;
import ag.protocol.impl.DefaultTransport;

public class Server {

	public static void main(String[] args) throws IOException {
		//
		System.out.println("Servidor...");
		//
		ServerSocket serverSocket = new ServerSocket(10999);
		Socket socket = serverSocket.accept();
		//
		Transport transport = new DefaultTransport(socket);
		Frame result = transport.receive();
		//
		System.out.println("-------- RESULT ----------");
		System.out.println("IsReq:    \t" + result.isRequest());
		System.out.println("IsText:   \t" + result.isText());
		System.out.println("Length:   \t" + result.getLength());
		System.out.println("Content:  \t" + new String(result.getPayload()));
		//
		socket.close();
		serverSocket.close();
	}
}
