package ag.protocol.impl;

import java.util.Arrays;

import ag.protocol.Frame;
import ag.protocol.FrameUnmarshaller;

public class FrameUnmarshallerImpl implements FrameUnmarshaller {

	@Override
	public Frame unmarshal(byte[] f) {
		//resultado
		Frame frame = new Frame();
		//extrair apenas os dois primeiros bits no byte
		int first = f[0];
		int two = f[1];
		//verificar se é requisição
		int isReq = (first & 0xC0 & 0x80);
		if (isReq == 0x80){
			frame.setTypeAsResponse();
		} else {
			frame.setTypeAsRequest();
		}
		//verificar se é tipo texto
		int isText = (first & 0xC0 & 0x40);
		if (isText == 0x40){
			frame.setPayloadAsText();
		} else {
			frame.setPayloadAsBinary();
		}
		//capturando o tamanho
		//primeiro + segundo = 0011 1111 | 1111 0000
		//first
		//0011 1111
		//1111 1100 0000 0000
		int newfirst = (first & 0x3F) << 10;
		//two
		//1111 0000
		//0000 0011 1100 0000
		int newtwo   = two  << 2;
		int lenght = (newfirst |  newtwo);
		lenght = lenght >> 6;
		if ((lenght & 0x8000) == 0x8000){//negativo
			lenght = 2 + Math.abs(lenght);
		}
		//capturando o conteúdo
		byte[] content = Arrays.copyOfRange(f, 2, lenght+2);
		frame.setPayload(content);
		//
		return frame;
	}

}
