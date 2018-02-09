package ag.protocol.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

import ag.protocol.Frame;
import ag.protocol.FrameMarshaller;
import ag.protocol.FrameUnmarshaller;
import ag.protocol.Transport;

public class DefaultTransport implements Transport {
	private final Socket socket;
	private final FrameMarshaller marshaller;
	private final FrameUnmarshaller unmarshaller;
	
	public DefaultTransport(Socket socket) {
		this.socket = socket;
		this.marshaller = new FrameMarshallerImpl();
		this.unmarshaller = new FrameUnmarshallerImpl();
	}
	
	@Override
	public Frame send(byte[] content, boolean binary) throws IOException {
		//definindo o frame
		Frame frame = new Frame();
		frame.setTypeAsRequest();
		frame.setPayload(content);
		if (binary) frame.setPayloadAsBinary();
		//
		byte[] f = marshaller.marshal(frame);
		socket.getOutputStream().write(f);
		socket.getOutputStream().write(new byte[]{0x00, 0x00, 0x00});
		socket.getOutputStream().flush();
		//
		return frame;
	}

	@Override
	public Frame receive() throws IOException {
		//faz a leitura do stream de recebimento do socket
		InputStream input = socket.getInputStream();
		//criar um local de armazenamento dos bytes dos dados recebidos
		ByteBuffer buffer = ByteBuffer.allocate(1029);
		//faz a leitura byte a byte
		int countZeroByte = 0;
		while(true){
			//recupera 1 byte
			byte[] b = new byte[1];
			int l = input.read(b);
			//caso 1 byte tenha sido recuperado
			if (l > 0){
				//coloca no buffer
				buffer.put(b);
				//verifica se o byte é zero
				if (b[0] == 0x00){
					countZeroByte++;
				} else {
					countZeroByte = 0;
				}
				//verifica se é final de mensagem (3 bytes zeros)
				if (countZeroByte == 3){
					break;
				}
			}
		}
		//converte de byte para frame
		byte[] f = Arrays.copyOf(buffer.array(), buffer.position()-3);//TODO -3+1
		return unmarshaller.unmarshal(f);
	}

}
