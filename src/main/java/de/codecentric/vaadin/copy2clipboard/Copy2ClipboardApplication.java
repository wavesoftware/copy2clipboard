package de.codecentric.vaadin.copy2clipboard;

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Window;

import de.codecentric.vaadin.copy2clipboard.Copy2ClipboardButton.ClipboardEvent;
import de.codecentric.vaadin.copy2clipboard.Copy2ClipboardButton.ClipboardListener;
import de.codecentric.vaadin.popup.PopupButton;

/**
 * Test application for the FlexPaper Viewer.
 * 
 * @author alexander.berresch
 */
public class Copy2ClipboardApplication extends Application {

    /**
     * Generated UID.
     */
    private static final long serialVersionUID = 1198903699329175622L;

    /**
     * (non-Javadoc)
     * 
     * @see com.vaadin.Application#init()
     */
    @Override
    public void init() {
	final Window mainWindow = new Window();
	setMainWindow(mainWindow);

	Button b = new Button("show/hide button");
	b.addListener(new Button.ClickListener() {

	    Copy2ClipboardButton button = null;

	    @Override
	    public void buttonClick(ClickEvent event) {
		if (button == null) {
		    button = new Copy2ClipboardButton();
		    button.setCaption("Copy");
		    mainWindow.addComponent(button);
		    button.setClipboardText("copy this to the clipboard");

		    button.addListener(new ClipboardListener() {

			@Override
			public void copiedToClipboard(ClipboardEvent event) {
			    mainWindow.showNotification("copied to clipboard");
			}
		    });
		} else {
		    mainWindow.removeComponent(button);
		    button = null;
		}
	    }
	});
	mainWindow.addComponent(b);

	Button b2 = new Button("add/remove panel");
	b2.addListener(new Button.ClickListener() {

	    TextArea p = null;

	    @Override
	    public void buttonClick(ClickEvent event) {
		if (p == null) {
		    p = new TextArea("test");
		    mainWindow.addComponent(p);
		} else {
		    mainWindow.removeComponent(p);
		    p = null;
		}
	    }
	});
	mainWindow.addComponent(b2);

	Copy2ClipboardButton c2c = new Copy2ClipboardButton();
	c2c.setCaption("panelTest");
	c2c.setClipboardText("clipbiard text from panel");
	c2c.addListener(new ClipboardListener() {

	    @Override
	    public void copiedToClipboard(ClipboardEvent event) {
		mainWindow.showNotification("from panel: copied to clipboard");
	    }
	});

	Panel panel = new Panel();
	panel.addComponent(c2c);
	mainWindow.addComponent(panel);

	Copy2ClipboardButton popupCopy = new Copy2ClipboardButton("copy", true);
	popupCopy.addListener(new ClipboardListener() {

	    @Override
	    public void copiedToClipboard(ClipboardEvent event) {
		mainWindow.showNotification("from popup: copied to clipboard");
	    }
	});

	final PopupButton popup = new PopupButton("click me");
	popupCopy.setClipboardText("popup copy!!!");
	popup.addComponent(popupCopy);

	popupCopy.addListener(new ClipboardListener() {

	    @Override
	    public void copiedToClipboard(ClipboardEvent event) {
		popup.setPopupVisible(true);
	    }
	});

	mainWindow.addComponent(popup);

    }
}