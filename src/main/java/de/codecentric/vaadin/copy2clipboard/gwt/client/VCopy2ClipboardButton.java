package de.codecentric.vaadin.copy2clipboard.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
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
     * A key which inidcates that the flash movie should be retained on unload.
     */
    public static final String VAR_CLIPBOARD_RETAIN_CLIP = "copy2clipboard.retainClip";

    /**
     * A key which indicates that the text was copied to the clipboard.
     */
    public static final String VAR_TEXT_COPIED = "textCopied";

    /**
     * The prefix of the button id in the DOM.
     */
    private static final String BUTTON_ID = "copy2clipboardButton";

    /**
     * The paintable id.
     */
    private String paintableId;

    /**
     * The ApplicationConnection for this widget.
     */
    ApplicationConnection appConnection;

    /**
     * A flag to protect java script instantiation.
     */
    private boolean firstLoad;

    /**
     * The id of the button element in the DOM.
     */
    private String buttonId;

    /**
     * Whether to retain the DOM element of the flash movie. This should be true for buttons that
     * hide and come back (e.g. buttins in popups).
     */
    private boolean retainClipElement;

    /**
     * The text to copy to the clipboard.
     */
    private String clipboardText;

    /**
     * Constructor
     */
    public VCopy2ClipboardButton() {
	firstLoad = true;
    }

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
	retainClipElement = false;
	if (uidl.getChildByTagName(VAR_RESOURCE) != null) {
	    UIDL resource = uidl.getChildByTagName(VAR_RESOURCE);

	    if (resource.hasAttribute(VAR_CLIPBOARD_TEXT)) {
		clipboardText = resource.getStringAttribute(VAR_CLIPBOARD_TEXT);
	    }
	    if (resource.hasAttribute(VAR_CLIPBOARD_RETAIN_CLIP)) {
		retainClipElement = resource.getBooleanAttribute(VAR_CLIPBOARD_RETAIN_CLIP);
	    }
	}

	if (firstLoad) {
	    buttonId = BUTTON_ID + paintableId;
	    Element root = getElement();
	    // add the id. this allows multiple clip board buttons on the page:
	    DOM.setElementAttribute(root, "id", buttonId);
	    // glue the js and flash to the component:
	    glueCopy(GWT.getModuleBaseURL(), root, buttonId);

	    firstLoad = false;
	}

	setClipboardText(clipboardText, buttonId);
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
	    showClip(buttonId); // mouse is ours, show the flash movie
	}
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.google.gwt.user.client.ui.Widget#onUnload()
     */
    @Override
    protected void onUnload() {
	if (!retainClipElement) {
	    destroyClip(buttonId); // destroy the DOM elements
	}

	super.onUnload();
    }

    /**
     * This method will be called on successful copy to clip board action.
     */
    public void onComplete() {
	appConnection.updateVariable(paintableId, VAR_TEXT_COPIED, true, true);
    }

    /**
     * Callback for the flash movie. Will be called when the mouse leaves the overlay.
     */
    public void onMouseout() {
	hideClip(buttonId);
    }

    /**
     * Initialize java script ZeroClipboard objects in the DOM.
     * 
     * @param baseUrl
     *            the base URL of the vaadin application.
     * @param buttonElement
     *            the element of the button.
     * @param buttonId
     *            the id of the button element in the DOM.
     */
    private native void glueCopy(String baseUrl, Element buttonElement, String buttonId)
    /*-{
        var path = baseUrl + 'ZeroClipboard10.swf';
        $wnd.ZeroClipboard.setMoviePath(path);

        var clip = $wnd.ZeroClipboard.newClient(); // new instance
        clip.zIndex = 20001; // float just over a vaadin popup
        clip.glue(buttonElement); // magic
        clip.hide(); // initially hide the flash movie
          
        var app = this;

        // notify me when the copy action is done:
        var completeListener = function copyComplete(client, text) { 
                app.@de.codecentric.vaadin.copy2clipboard.gwt.client.VCopy2ClipboardButton::onComplete()(); 
        }
        clip.addEventListener('complete', completeListener);
        
        // notify me when the mouse leaves the flash movie:
        var mouseoutListener = function mouseoutL() { 
                app.@de.codecentric.vaadin.copy2clipboard.gwt.client.VCopy2ClipboardButton::onMouseout()(); 
        }
        clip.addEventListener('mouseout', mouseoutListener);
     
        // store the object on the button
        $doc.getElementById(buttonId).clip = clip;
     }-*/;

    /**
     * Sets the text to the corresponding DOM object which can be copied to the clip board.
     * 
     * @param text
     *            the text to copy to the clip board.
     * @param buttonId
     *            the id of the button element in the DOM.
     */
    private native void setClipboardText(String text, String buttonId)
    /*-{
        var clip = $doc.getElementById(buttonId).clip;
        clip.setText(text);
    }-*/;

    /**
     * Hides the flash overlay.
     * 
     * @param buttonId
     *            the id of the button element in the DOM.
     */
    private native void hideClip(String buttonId)
    /*-{
        var clip = $doc.getElementById(buttonId).clip;
        clip.hide();
    }-*/;

    /**
     * Shows the flash overlay.
     * 
     * @param buttonId
     *            the id of the button element in the DOM.
     */
    private native void showClip(String buttonId)
    /*-{
        var clip = $doc.getElementById(buttonId).clip;
        clip.show();
    }-*/;

    /**
     * Destroys the flash overlay.
     * 
     * @param buttonId
     *            the id of the button element in the DOM.
     */
    private native void destroyClip(String buttonId)
    /*-{
        var clip = $doc.getElementById(buttonId).clip;
        clip.destroy();
    }-*/;

}
