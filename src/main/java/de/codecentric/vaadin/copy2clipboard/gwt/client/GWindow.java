package de.codecentric.vaadin.copy2clipboard.gwt.client;

import com.google.gwt.user.client.Event;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.ui.VWindow;

/**
 * <p>
 * A placement of VWindow implementation to fix issue with CKEditor component in a modal window.
 * </p>
 * 
 * @see http://code.google.com/p/vaadin-ckeditor/issues/detail?id=10
 * @see https://vaadin.com/forum/-/message_boards/view_message/238571
 * 
 * @since 1.2
 * @author ttran
 **/
public class GWindow extends VWindow {

    private boolean modal;

    /**
     * {@inheritDoc}
     * 
     * @see VWindow#updateFromUIDL(UIDL, ApplicationConnection)
     */
    @Override
    public void updateFromUIDL(final UIDL uidl, final ApplicationConnection client) {
	if (uidl.hasAttribute("modal")) //$NON-NLS-1$
	    modal = uidl.getBooleanAttribute("modal"); //$NON-NLS-1$
	super.updateFromUIDL(uidl, client);
    }

    /**
     * {@inheritDoc}
     * 
     * @see VWindow#onEventPreview(Event)
     */
    @Override
    public boolean onEventPreview(final Event event) {
	if (modal)
	    return true; // why would they block click to other elements?
	return super.onEventPreview(event);
    }
}
