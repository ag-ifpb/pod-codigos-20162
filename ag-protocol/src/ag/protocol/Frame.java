package ag.protocol;

/**
 * Representa o frame de dados que será enviado
 * ou recebido pelo cliente e servidor.
 * 
 * @author arigarcia
 *
 */
public class Frame{
	private boolean isRequest;
	private boolean isText;
	private int length;
	private byte[] payload;
	
	public Frame() {
		isRequest = true;//0
		isText = true;//1
		length = 1;
		payload = new byte[length];
	}
	
	public boolean isRequest() {
		return isRequest;
	}
	
	public void setTypeAsRequest(){
		isRequest = true;
	}
	
	public void setTypeAsResponse(){
		isRequest = false;
	}
	
	public boolean isText() {
		return isText;
	}
	
	public void setPayloadAsText(){
		isText = true;
	}
	
	public void setPayloadAsBinary(){
		isText = false;
	}
	
	public int getLength() {
		return length;
	}
	
	public void setLength(int length) {
		this.length = length;
		this.payload = new byte[length];
	}
	
	public byte[] getPayload() {
		return payload;
	}
	
	public void setPayload(byte[] value){
		payload = value;
		length = payload.length;
	}
	
}
