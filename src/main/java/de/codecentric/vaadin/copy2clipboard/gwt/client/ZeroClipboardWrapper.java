package de.codecentric.vaadin.copy2clipboard.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Handles access to a single ZeroClipboard instance. Some amount of hacky code :-(
 * 
 * @author fabian.lange
 * 
 */
public class ZeroClipboardWrapper extends HTML {

    /**
     * we have only one instance of ZeroClipboard SWF. To transport that concept usage shall be done
     * using this singleton
     */
    public static ZeroClipboardWrapper INSTANCE = new ZeroClipboardWrapper();

    /**
     * reference to the button the copy 2 clipboard flash will overlay
     */
    private VCopy2ClipboardButton activeButton;

    private ZeroClipboardWrapper() {
	getElement().getStyle().setPosition(Position.ABSOLUTE);
	getElement().getStyle().setZIndex(20001);
	getElement().setId("zeroClipboardHandler");
	RootPanel.get().add(this);
	setHTML(initCopy2Clipboard(GWT.getModuleBaseURL(), getElement()));
    }

    /**
     * Called by the VCopy2ClipboardButton which wants its text to be used and be overlayed by the
     * flash. Should happen in a mouse over listener.
     * 
     * @param button
     */
    public void use(VCopy2ClipboardButton button) {
	activeButton = button;
	setButton(button);
	setClipboardText(button.clipboardText);
    }

    /**
     * Called when for some reason the button no longer wants to be overlayed with flash.
     * 
     * @param button
     */
    public void doNotUse(VCopy2ClipboardButton button) {
	if (activeButton == button) {
	    activeButton = null;
	}
    }

    private void setButton(VCopy2ClipboardButton button) {
	setPixelSize(button.getElement().getOffsetWidth(), button.getElement().getOffsetHeight());
	DOM.setStyleAttribute(getElement(), "left", button.getAbsoluteLeft() + "px");
	DOM.setStyleAttribute(getElement(), "top", button.getAbsoluteTop() + "px");
	activeButton = button;
    }

    public void copyComplete() {
	if (activeButton != null) {
	    activeButton.onComplete();
	}
    }

    public void hide() {
	activeButton = null;
	DOM.setStyleAttribute(getElement(), "left", "-20000px");
    }

    /**
     * Sets the text to the corresponding DOM object which can be copied to the clip board.
     * 
     * @param text
     *            the text to copy to the clip board.
     */
    private native void setClipboardText(String text)
    /*-{
        var clip = $doc.getElementById("zeroClipboardHandler").clip;
        clip.setText(text);
    }-*/;

    /**
     * Initialize java script ZeroClipboard objects in the DOM.
     * 
     * @param baseUrl
     *            the base URL of the vaadin application.
     * @param buttonElement
     *            the element of the button.
     */
    private native String initCopy2Clipboard(String baseUrl, Element buttonElement)
    /*-{
        var path = baseUrl + 'zeroclipboard/ZeroClipboard10.swf';
        $wnd.ZeroClipboard.setMoviePath(path);

        var clip = $wnd.ZeroClipboard.newClient();

    	var widget = this;
        // notify me when the copy action is done:
        clip.addEventListener('complete', function (client, text) {
                widget.@de.codecentric.vaadin.copy2clipboard.gwt.client.ZeroClipboardWrapper::copyComplete()(); 
        });
        
        clip.addEventListener('mouseout', function() {
      		widget.@de.codecentric.vaadin.copy2clipboard.gwt.client.ZeroClipboardWrapper::hide()();
      	});
        
        // store the object on div
        $doc.getElementById("zeroClipboardHandler").clip = clip;
    	return clip.getHTML(200, 50);
     }-*/;

}
