package messages;

/**
 * A message to end the session
 */
public class ADTEndSessionMessage extends ADTBaseMessage {

	/** Serialization */
	private static final long serialVersionUID = 4565637232317355793L;

	/**
	 * Constructor
	 * 
	 * @param sessionID
	 */
	public ADTEndSessionMessage(int sessionID) {
		super(sessionID);
	}

	@Override
	public void process() {
		// Auto-generated method stub
	}

	@Override
	public String getCommand() {
		return "end";
	}

}
