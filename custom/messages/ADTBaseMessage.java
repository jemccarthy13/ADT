package messages;

/**
 * Base "Message" to be sent/received via the client/server
 */
public abstract class ADTBaseMessage implements ADTMessage {

	/** Generated for serialization */
	private static final long serialVersionUID = 6870829317845940606L;

	/** Required to send messages through input/output streams */
	private int sender;

	/**
	 * Public constructor
	 */
	public ADTBaseMessage() {
		this.sender = -1;
	}

	/**
	 * Constructor given a sender ID
	 * 
	 * @param sender
	 */
	public ADTBaseMessage(int sender) {
		this.sender = sender;
	}

	/**
	 * @return the sender
	 */
	public int getSender() {
		return this.sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(int sender) {
		this.sender = sender;
	}
}
