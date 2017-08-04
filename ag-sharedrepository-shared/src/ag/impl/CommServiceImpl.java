package ag.impl;

import java.io.FileNotFoundException;
import java.io.IOException;

import ag.CommService;
import ag.Message;
import ag.MessageManager;
import ag.RepositoryLocator;

public class CommServiceImpl implements CommService {
	private static final String FUNC_NAME = "hello()";//'./'
	private static final String MSG_REQ_TYPE = "msgReq";//req.txt
	private static final String MSG_RESP_TYPE = "msgResp";//resp.txt
	private final MessageManager manager;
	
	public CommServiceImpl(RepositoryLocator locator) {
		this.manager = new MessageManager(locator);
	}
	
	@Override
	public Message recv(String dest) throws FileNotFoundException, IOException {
		//dest = 'node2' (server)
		if (dest.equals("node2")){
			//ler uma mensagem a partir de uma função e um tipo de mensagem
			return manager.read(FUNC_NAME, MSG_REQ_TYPE, dest);
		} else {
			//ler uma mensagem a partir de uma função e um tipo de mensagem
			return manager.read(FUNC_NAME, MSG_RESP_TYPE, dest);
		}
	}
	
	@Override
	public void send(Message message) throws FileNotFoundException, IOException {
		//dest = 'node2' (server)
		if (message.getTo().equals("node2")){
			//ler uma mensagem a partir de uma função e um tipo de mensagem
			manager.write(FUNC_NAME, MSG_RESP_TYPE, message);
		} else {
			//ler uma mensagem a partir de uma função e um tipo de mensagem
			manager.write(FUNC_NAME, MSG_REQ_TYPE, message);
		}
	}

}