package messages;

import java.io.Serializable;

/**
 * Base "Message" to be sent/received via the client/server
 */
public interface ADTMessage extends Serializable {

	/** Required to send messages through input/output streams */
	static final long serialVersionUID = -3882460125207713255L;

	/** What to do to process/handle this message */
	public void process();

	/**
	 * The command for this message
	 * 
	 * @return the command
	 */
	public String getCommand();
}
