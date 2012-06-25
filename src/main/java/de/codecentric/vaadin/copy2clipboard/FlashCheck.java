package de.codecentric.vaadin.copy2clipboard;

import java.util.Map;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractComponent;

/**
 * Server side component for the VFlashCheck widget.
 */
@com.vaadin.ui.ClientWidget(de.codecentric.vaadin.copy2clipboard.gwt.client.VFlashCheck.class)
public class FlashCheck extends AbstractComponent {

    /**
     * Serial ID
     */
    private static final long serialVersionUID = 4472314444727159478L;
    /**
     * True if flash is enabled in the browser
     */
    private boolean flashEnabled = true;

    /**
     * @see com.vaadin.ui.AbstractComponent#paintContent(com.vaadin.terminal.PaintTarget)
     */
    @Override
    public void paintContent(PaintTarget target) throws PaintException {
	super.paintContent(target);
    }

    /**
     * Receive and handle events and other variable changes from the client.
     * 
     * {@inheritDoc}
     */
    @Override
    public void changeVariables(Object source, Map<String, Object> variables) {
	super.changeVariables(source, variables);
	if (variables.containsKey("FLASH_NA")) {
	    flashEnabled = false;
	} else if (variables.containsKey("FLASH_OK")) {
	    flashEnabled = true;
	}
    }

    /**
     * @return the flashEnabled
     */
    public boolean isFlashEnabled() {
	return flashEnabled;
    }

}
