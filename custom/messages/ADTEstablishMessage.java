package messages;

/**
 * A message to establish a client/server connection
 */
public class ADTEstablishMessage extends ADTBaseMessage {

	/** For serialization */
	private static final long serialVersionUID = 998531641107367258L;

	/**
	 * @param i the sender of the message
	 */
	public ADTEstablishMessage(int i) {
		this.setSender(i);
	}

	@Override
	public void process() {
		// handle the message
	}

	@Override
	public String getCommand() {
		return "establish";
	}

}
