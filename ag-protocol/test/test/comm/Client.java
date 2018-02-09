package test.comm;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import ag.protocol.Frame;
import ag.protocol.Transport;
import ag.protocol.impl.DefaultTransport;
import ag.protocol.util.Util;

public class Client {

	public static void main(String[] args) throws UnknownHostException, IOException {
		//client
		Socket socket = new Socket("localhost", 10999);
		Transport transport = new DefaultTransport(socket);
		Frame frame = transport.send("HELO".getBytes(), false);
		//
		System.out.println("-------- FRAME ----------");
		System.out.println("IsReq:        \t" + frame.isRequest());
		System.out.println("IsText:       \t" + frame.isText());
		System.out.println("Length:       \t" + frame.getLength());
		System.out.println("Content:      \t" + new String(frame.getPayload()));
	}
}
