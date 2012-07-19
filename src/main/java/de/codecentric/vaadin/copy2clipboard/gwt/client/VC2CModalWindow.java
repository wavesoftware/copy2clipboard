/* 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.codecentric.vaadin.copy2clipboard.gwt.client;

import com.google.gwt.user.client.Event;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.ui.VWindow;

/**
 * <p>
 * A placement of VWindow implementation to fix issue with Copy2ClipboardButton component in a modal
 * window.
 * </p>
 * 
 * @see http://code.google.com/p/vaadin-ckeditor/issues/detail?id=10
 * @see https://vaadin.com/forum/-/message_boards/view_message/238571
 * 
 * @since 1.2
 * @author ttran
 **/
public class VC2CModalWindow extends VWindow {

    /**
     * Whether this window is modal.
     */
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
