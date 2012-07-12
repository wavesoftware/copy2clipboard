package de.codecentric.vaadin.copy2clipboard;

import com.vaadin.ui.ClientWidget;
import com.vaadin.ui.Window;

import de.codecentric.vaadin.copy2clipboard.gwt.client.VC2CModalWindow;

@ClientWidget(VC2CModalWindow.class)
public class C2CModalWindow extends Window {

    public C2CModalWindow(String caption) {
	super(caption);
	setModal(true);
    }

}
