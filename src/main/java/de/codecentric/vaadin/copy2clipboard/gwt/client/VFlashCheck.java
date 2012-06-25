package de.codecentric.vaadin.copy2clipboard.gwt.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;

/**
 * Client side widget which communicates with the server. Messages from the server are shown as HTML
 * and mouse clicks are sent to the server.
 */
public class VFlashCheck extends Widget implements Paintable {

    /** Set the CSS class name to allow styling. */
    public static final String CLASSNAME = "v-flashcheck";

    /**
     * Set to true after check is made. Do flash check only once.
     */
    private boolean flashCheckDone;
    /** The client side widget identifier */
    protected String paintableId;

    /** Reference to the server connection object. */
    protected ApplicationConnection client;

    /**
     * The constructor should first call super() to initialize the component and then handle any
     * initialization relevant to Vaadin.
     */
    public VFlashCheck() {
	setElement(Document.get().createDivElement());
	setStyleName(CLASSNAME);
	flashCheckDone = false;
    }

    /**
     * @see com.vaadin.terminal.gwt.client.Paintable#updateFromUIDL(com.vaadin.terminal.gwt.client.UIDL,
     *      com.vaadin.terminal.gwt.client.ApplicationConnection)
     */
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
	// This call should be made first.
	// It handles sizes, captions, tooltips, etc. automatically.
	if (client.updateComponent(this, uidl, true)) {
	    // If client.updateComponent returns true there has been no changes and we
	    // do not need to update anything.
	    return;
	}
	// Save reference to server connection object to be able to send
	// user interaction later
	this.client = client;

	// Save the client side identifier (paintable id) for the widget
	paintableId = uidl.getId();

	if (!flashCheckDone) {
	    if (FlashUtils.isFlashEnabledInBrowser()) {
		client.updateVariable(paintableId, "FLASH_OK", "OK", true);
	    } else {
		client.updateVariable(paintableId, "FLASH_NA", "OK", true);
	    }
	    flashCheckDone = true;
	}

    }

}
