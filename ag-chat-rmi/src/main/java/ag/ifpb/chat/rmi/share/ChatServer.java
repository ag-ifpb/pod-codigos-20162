package ag.ifpb.chat.rmi.share;

public interface ChatServer {
	
	/**
	 * Verifica a existência do usuário a partir
	 * do email e, caso não exista, cria o usuário.
	 * Após isto, ou caso já exista, adiciona-se o usuário
	 * em repositório de "usuários existentes" e o adiciona no
	 * repositório de "usuários conectados".
	 * 
	 * Database:
	 *  -- usuários existentes
	 * 	users (
	 *    email varchar(100)
	 *  )
	 *  
	 *  -- usuários conectados
	 *  users_connected (
	 *    email varchar(100),
	 *    is_removed boolean
	 *  )
	 * 
	 * [adicional]:
	 * - caso o usuário já esteja conectado,
	 * remover a sua conectar.
	 * 
	 * 
	 * @param email
	 * @return
	 */
	Session login(String email);
	
	/**
	 * Mantém a mensagem até saber que todos receberam
	 * e encaminha as mensagens para todos os usuários
	 * que estão no repositório de "usuários existentes".
	 * 
	 * Database:
	 *  message (
	 *    id long,
	 *    from varchar(100),
	 *    text varchar(256),
	 *    is_removed boolean
	 *  )
	 *  
	 *  -- percorrer todos os usuários existentes
	 *  -- e a partir de uma mensagem criar uma lista
	 *  -- de "message_user" com a mensagem+usuário (por tupla).
	 *  -- isto resultará em 1 mensagem enviada para N usuários existentes,
	 *  -- logo terá como resultado N "message_user"
	 *  message_user(
	 *    message_id long,
	 *    to varchar(100), --email do destinatário
	 *    is_sended boolean
	 *  )
	 * 
	 * @param msg
	 */
	void persistAndforwardToAll(Message msg);
	
	/**
	 * Remove a mensagem depois de saber que todos
	 * a receberam.
	 * 
	 * [adicional]:
	 * - alterar o campo "is_removed" em "message" para "true"
	 * 
	 * @param msg
	 */
	void remove(Message msg);
	
}
