/* 
 * Copyright 2012 codecentric AG
 * 
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
import com.vaadin.terminal.gwt.client.ui.VButton;

/**
 * Client side component for the copy to clip board widget. This widget needs the flash player.
 * 
 * @author henning.treu@codecentric.de
 */
public class VCopy2ClipboardButton extends VButton {

    /**
     * A key which identifies the copy2clipboard resource in the UIDL.
     */
    public static final String VAR_RESOURCE = "copy2clipboard";

    /**
     * A key which identifies the copy2clipboard value in the UIDL.
     */
    public static final String VAR_CLIPBOARD_TEXT = "copy2clipboard.text";

    /**
     * A key which indicates that the text was copied to the clipboard.
     */
    public static final String VAR_TEXT_COPIED = "textCopied";

    /**
     * The paintable id.
     */
    private String paintableId;

    /**
     * The ApplicationConnection for this widget.
     */
    ApplicationConnection appConnection;

    /**
     * The text to copy to the clipboard.
     */
    protected String clipboardText;

    /**
     * @see com.vaadin.terminal.gwt.client.Paintable#updateFromUIDL(com.vaadin.terminal.gwt.client.UIDL,
     *      com.vaadin.terminal.gwt.client.ApplicationConnection)
     */
    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
	super.updateFromUIDL(uidl, client);
	paintableId = uidl.getId();
	appConnection = client;

	// Ensure correct implementation,
	// but don't let container manage caption etc.
	if (client.updateComponent(this, uidl, false)) {
	    return;
	}

	clipboardText = "";
	if (uidl.getChildByTagName(VAR_RESOURCE) != null) {
	    UIDL resource = uidl.getChildByTagName(VAR_RESOURCE);

	    if (resource.hasAttribute(VAR_CLIPBOARD_TEXT)) {
		clipboardText = resource.getStringAttribute(VAR_CLIPBOARD_TEXT);
	    }
	}

    }

    /**
     * (non-Javadoc)
     * 
     * @see com.vaadin.terminal.gwt.client.ui.VButton#onBrowserEvent(com.google.gwt.user.client.Event)
     */
    @Override
    public void onBrowserEvent(Event event) {
	super.onBrowserEvent(event);
	if (event.getTypeInt() == Event.ONMOUSEOVER) {
	    ZeroClipboardWrapper.INSTANCE.use(this);
	}
    }

    /**
     * This method will be called on successful copy to clip board action.
     */
    public void onComplete() {
	appConnection.updateVariable(paintableId, VAR_TEXT_COPIED, true, true);
    }

    @Override
    protected void onDetach() {
	ZeroClipboardWrapper.INSTANCE.doNotUse(this);
	super.onDetach();
    }

}
