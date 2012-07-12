package de.codecentric.vaadin.copy2clipboard;

import com.vaadin.ui.ClientWidget;
import com.vaadin.ui.Window;

import de.codecentric.vaadin.copy2clipboard.gwt.client.GWindow;

@ClientWidget(GWindow.class)
public class C2CWindow extends Window {

    public C2CWindow(String caption) {
	super(caption);
    }
}
