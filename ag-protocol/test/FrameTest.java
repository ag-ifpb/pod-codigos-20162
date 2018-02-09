import ag.protocol.Frame;
import ag.protocol.FrameMarshaller;
import ag.protocol.FrameUnmarshaller;
import ag.protocol.impl.FrameMarshallerImpl;
import ag.protocol.impl.FrameUnmarshallerImpl;
import ag.protocol.util.Util;

public class FrameTest {

	public static void main(String[] args) {
		//
		Frame frame = new Frame();//01
		//frame.setTypeAsResponse();//11
		//frame.setPayloadAsBinary();//10 = 4
		frame.setPayload("HELO GUYS".getBytes());
		//imprimir
		System.out.println("-------- FRAME ----------");
		System.out.println("IsReq:        \t" + frame.isRequest());
		System.out.println("IsText:       \t" + frame.isText());
		System.out.println("Length:       \t" + frame.getLength());
		System.out.println("Content:      \t" + new String(frame.getPayload()));
		System.out.println("Content-bin:  \t0x" + Util.byteArrayToHexString(frame.getPayload()));
		//
		FrameMarshaller marshaller = new FrameMarshallerImpl();
		byte[] serialized = marshaller.marshal(frame);
		//imprimir
		System.out.println("-------- STREAM ----------");
		System.out.println("Frame:       \t0x" + Util.byteArrayToHexString(serialized));
		//
		FrameUnmarshaller unmarshaller = new FrameUnmarshallerImpl();
		Frame result = unmarshaller.unmarshal(serialized);
		//imprimir
		System.out.println("-------- RESULT ----------");
		System.out.println("IsReq:    \t" + result.isRequest());
		System.out.println("IsText:   \t" + result.isText());
		System.out.println("Length:   \t" + result.getLength());
		System.out.println("Content:  \t" + new String(result.getPayload()));
		//
		
	}
}
