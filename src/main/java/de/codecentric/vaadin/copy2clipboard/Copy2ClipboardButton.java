package de.codecentric.vaadin.copy2clipboard;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.Button;
import com.vaadin.ui.ClientWidget;
import com.vaadin.ui.Component;

import de.codecentric.vaadin.copy2clipboard.gwt.client.VCopy2ClipboardButton;

/**
 * Server side component for the copy to clip board widget. This widget needs the flash player.
 * 
 * @author henning.treu@codecentric.de
 */
@ClientWidget(VCopy2ClipboardButton.class)
public class Copy2ClipboardButton extends Button {

    /**
     * Generated UID.
     */
    private static final long serialVersionUID = -5330014767347423289L;

    /**
     * The method of the ClipboardListener which will be called.
     */
    private static final Method TEXT_COPIED_METHOD;

    static {
	try {
	    TEXT_COPIED_METHOD = ClipboardListener.class.getDeclaredMethod("copiedToClipboard",
		    new Class[] { ClipboardEvent.class });
	} catch (final java.lang.NoSuchMethodException e) {
	    // This should never happen
	    throw new java.lang.RuntimeException("Internal error finding methods in Button");
	}
    }

    /**
     * The text to copy to the clipboard.
     */
    private String clipboardText;

    /**
     * Whether to retain the DOM element of the flash movie. This should be true for buttons which
     * hide and show (e.g. buttons in popups).
     */
    private final boolean retainClipElement;

    /**
     * Constructor
     */
    public Copy2ClipboardButton() {
	this(null, false);
    }

    /**
     * Constructor with caption.
     * 
     * @param caption
     *            the caption of the button.
     */
    public Copy2ClipboardButton(String caption) {
	this(caption, false);
    }

    /**
     * Constructor with caption and retain flag.
     * 
     * @param caption
     *            the caption of the button.
     * @param retainClipElement
     *            whether to retain the DOM element of the flash movie. Set this to
     *            <code>true</code> for buttons that hide/show during their lifecycle.
     */
    public Copy2ClipboardButton(String caption, boolean retainClipElement) {
	super(caption);
	this.retainClipElement = retainClipElement;
    }

    /**
     * Sets the text which should be copied to the clip board when clicking on the widget.
     * 
     * @param clipboardText
     *            the text to copy.
     */
    public void setClipboardText(String clipboardText) {
	this.clipboardText = clipboardText;
	requestRepaint();
    }

    /**
     * @see com.vaadin.ui.AbstractComponent#paintContent(com.vaadin.terminal.PaintTarget)
     */
    @Override
    public void paintContent(PaintTarget target) throws PaintException {
	super.paintContent(target);

	// send text to client
	target.startTag(VCopy2ClipboardButton.VAR_RESOURCE);
	if (clipboardText != null) {
	    target.addAttribute(VCopy2ClipboardButton.VAR_CLIPBOARD_TEXT, clipboardText);
	}
	target.addAttribute(VCopy2ClipboardButton.VAR_CLIPBOARD_RETAIN_CLIP, retainClipElement);
	target.endTag(VCopy2ClipboardButton.VAR_RESOURCE);
    }

    /**
     * @see com.vaadin.ui.AbstractComponent#changeVariables(java.lang.Object, java.util.Map)
     */
    @Override
    public void changeVariables(Object source, Map<String, Object> variables) {
	super.changeVariables(source, variables);
	// text was copied
	if (variables.containsKey(VCopy2ClipboardButton.VAR_TEXT_COPIED)) {
	    boolean copied = ((Boolean) variables.get(VCopy2ClipboardButton.VAR_TEXT_COPIED))
		    .booleanValue();
	    if (copied) {
		fireEvent(new ClipboardEvent(this));
	    }
	}
    }

    /**
     * Interface for listening for a {@link ClipboardEvent}.
     * 
     * @author treu
     */
    public interface ClipboardListener extends Serializable {

	/**
	 * Called when the text was successfully copied to the clip board.
	 * 
	 * @param event
	 *            An event containing information about the source.
	 */
	public void copiedToClipboard(ClipboardEvent event);

    }

    /**
     * Adds the listener.
     * 
     * @param listener
     *            the Listener to be added.
     */
    public void addListener(ClipboardListener listener) {
	addListener(ClipboardEvent.class, listener, TEXT_COPIED_METHOD);
    }

    /**
     * Removes the listener.
     * 
     * @param listener
     *            the Listener to be removed.
     */
    public void removeListener(ClipboardListener listener) {
	removeListener(ClipboardEvent.class, listener, TEXT_COPIED_METHOD);
    }

    /**
     * An event which is fired in case of successful clip board copy action.
     * 
     * @author treu
     * 
     */
    public class ClipboardEvent extends Event {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -1523301838854930921L;

	/**
	 * Constructor.
	 * 
	 * @param source
	 *            the source component which initialized the event.
	 */
	public ClipboardEvent(Component source) {
	    super(source);
	}

    }

}
