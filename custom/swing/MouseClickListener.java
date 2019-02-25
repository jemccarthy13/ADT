package swing;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Baseline MouseListener that expects subclasses to implement mouseClicked only
 */
public abstract class MouseClickListener implements MouseListener {
	@Override
	public abstract void mouseClicked(MouseEvent e);

	@Override
	public void mousePressed(MouseEvent e) {
		// do nothing
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// do nothing
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// do nothing
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// do nothing
	}
}
