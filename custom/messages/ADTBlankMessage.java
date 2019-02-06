package messages;

/**
 * Just a message with nothing in it
 */
public class ADTBlankMessage extends ADTBaseMessage {

	/** Serialization */
	private static final long serialVersionUID = 5614331530020500879L;

	/**
	 * Blank messages have nothing to process
	 */
	@Override
	public void process() {
		return;
	}

	@Override
	public String getCommand() {
		return "blank";
	}

}
