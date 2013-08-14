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
					new Class<?>[]{ClipboardEvent.class});
		} catch (final java.lang.NoSuchMethodException e) {
			// This should never happen
			throw new java.lang.RuntimeException("Internal error finding methods in Button: " + e);
		}
	}

	/**
	 * The text to copy to the clipboard.
	 */
	private String clipboardText;

	/**
	 * Constructor
	 */
	public Copy2ClipboardButton() {
		this(null);
	}

	/**
	 * Constructor with caption.
	 *
	 * @param caption the caption of the button.
	 */
	public Copy2ClipboardButton(String caption) {
		super(caption);
	}

	/**
	 * Sets the text which should be copied to the clip board when clicking on the widget.
	 *
	 * @param clipboardText the text to copy.
	 */
	public void setClipboardText(String clipboardText) {
		this.clipboardText = clipboardText;
		requestRepaint();
	}

	/**
	 * Gets the text that is set as one to be copied to clipboard.
	 *
	 * @return the clipboard text in widget
	 */
	public String getClipboardText() {
		return clipboardText;
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
				fireEvent(new ClipboardEvent(this, clipboardText));
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
		 * @param event An event containing information about the source.
		 */
		public void copiedToClipboard(ClipboardEvent event);
	}

	/**
	 * Adds the listener.
	 *
	 * @param listener the Listener to be added.
	 */
	public void addListener(ClipboardListener listener) {
		addListener(ClipboardEvent.class, listener, TEXT_COPIED_METHOD);
	}

	/**
	 * Removes the listener.
	 *
	 * @param listener the Listener to be removed.
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
		 * The copied text stored in event
		 */
		private final String copiedText;

		/**
		 * Constructor.
		 *
		 * @param source the source component which initialized the event.
		 * @param copiedText the text that have been copied
		 */
		public ClipboardEvent(Component source, String copiedText) {
			super(source);
			this.copiedText = copiedText;
		}

		public String getCopiedText() {
			return copiedText;
		}
	}
}
