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

import com.vaadin.ui.ClientWidget;
import com.vaadin.ui.Window;

import de.codecentric.vaadin.copy2clipboard.gwt.client.VC2CModalWindow;

/**
 * A special modal window which allows copy to clipboard actions. See
 * http://dev.vaadin.com/ticket/9001 for reasons.
 * 
 * @author henning.treu@codecentric.de
 * 
 */
@ClientWidget(VC2CModalWindow.class)
public class C2CModalWindow extends Window {

    /**
     * Generated UID.
     */
    private static final long serialVersionUID = 4791219156445746010L;

    /**
     * Constructor. Creates a modal window with the given caption.
     * 
     * @param caption
     *            the caption for the window.
     */
    public C2CModalWindow(String caption) {
	super(caption);
	setModal(true);
    }

}
