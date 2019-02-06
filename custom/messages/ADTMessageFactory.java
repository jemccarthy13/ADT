package messages;

/**
 * Create messages
 */
public class ADTMessageFactory {

	/**
	 * Create message of msgType with a sender ID
	 * 
	 * @param msgType
	 * @param senderId
	 * @return a new message
	 */
	public static ADTBaseMessage createMessage(Class<ADTBaseMessage> msgType, int senderId) {
		ADTBaseMessage msg = null;
		try {
			msg = msgType.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		msg.setSender(senderId);
		return msg;
	}
}
