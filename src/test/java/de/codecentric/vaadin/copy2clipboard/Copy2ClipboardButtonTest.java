/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.vaadin.copy2clipboard;

import com.vaadin.terminal.PaintTarget;
import de.codecentric.vaadin.copy2clipboard.gwt.client.VCopy2ClipboardButton;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Krzysztof Suszyński <krzysztof.suszynski@wavesoftware.pl>
 */
public class Copy2ClipboardButtonTest {

	/**
	 * Test of setClipboardText method, of class Copy2ClipboardButton.
	 */
	@Test
	public void testSetClipboardText() {
		String clipboardText = "to set";
		Copy2ClipboardButton instance = new Copy2ClipboardButton();
		instance.setClipboardText(clipboardText);
		assertEquals(clipboardText, instance.getClipboardText());
	}

	/**
	 * Test of getClipboardText method, of class Copy2ClipboardButton.
	 */
	@Test
	public void testGetClipboardText() {
		String clipboardText = "to set";
		Copy2ClipboardButton instance = new Copy2ClipboardButton();
		instance.setClipboardText(clipboardText);
		String expResult = "to set";
		String result = instance.getClipboardText();
		assertEquals(expResult, result);
	}

	/**
	 * Test of paintContent method, of class Copy2ClipboardButton.
	 */
	@Test
	public void testPaintContent() throws Exception {
		PaintTarget target = mock(PaintTarget.class);
		Copy2ClipboardButton instance = new Copy2ClipboardButton();
		instance.setClipboardText("text to clipboard");
		instance.paintContent(target);
		// target.addAttribute(VCopy2ClipboardButton.VAR_CLIPBOARD_TEXT, clipboardText);
		verify(target, times(1)).addAttribute(VCopy2ClipboardButton.VAR_CLIPBOARD_TEXT, "text to clipboard");
	}

	/**
	 * Test of changeVariables method, of class Copy2ClipboardButton.
	 */
	@Test
	public void testChangeVariables() {
		Object source = new Object();
		Map<String, Object> variables = new HashMap<String, Object>();
		Copy2ClipboardButton instance = new Copy2ClipboardButton();
		final StringBuilder sb = new StringBuilder();
		instance.addListener(new Copy2ClipboardButton.ClipboardListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void copiedToClipboard(Copy2ClipboardButton.ClipboardEvent event) {
				sb.append("success");
			}
		});
		variables.put(VCopy2ClipboardButton.VAR_TEXT_COPIED, true);
		instance.changeVariables(source, variables);
		assertEquals(sb.toString(), "success");
	}

	/**
	 * Test of addListener method, of class Copy2ClipboardButton.
	 */
	@Test
	public void testAddListener() {
		Copy2ClipboardButton.ClipboardListener listener = new Copy2ClipboardButton.ClipboardListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void copiedToClipboard(Copy2ClipboardButton.ClipboardEvent event) {
				throw new UnsupportedOperationException("Not supported yet.");
			}
		};
		Copy2ClipboardButton instance = new Copy2ClipboardButton();
		instance.addListener(listener);
		Collection<?> list = instance.getListeners(Copy2ClipboardButton.ClipboardEvent.class);
		assertThat(list.size(), equalTo(1));
	}

	/**
	 * Test of removeListener method, of class Copy2ClipboardButton.
	 */
	@Test
	public void testRemoveListener() {
		Copy2ClipboardButton.ClipboardListener listener = new Copy2ClipboardButton.ClipboardListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void copiedToClipboard(Copy2ClipboardButton.ClipboardEvent event) {
				throw new UnsupportedOperationException("Not supported yet.");
			}
		};
		Copy2ClipboardButton instance = new Copy2ClipboardButton();
		instance.addListener(listener);
		Collection<?> list = instance.getListeners(Copy2ClipboardButton.ClipboardEvent.class);
		assertThat(list.size(), equalTo(1));
		instance.removeListener(listener);
		list = instance.getListeners(Copy2ClipboardButton.ClipboardEvent.class);
		assertThat(list.size(), equalTo(0));
	}
}