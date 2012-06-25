package de.codecentric.vaadin.copy2clipboard.gwt.client;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Check if Flash is available in this browser
 * 
 * @author aljosa.klisanic
 * 
 */
public class FlashUtils extends JavaScriptObject {

    /**
     * Protected constructor because GWT compiler needs it
     */
    protected FlashUtils() {
	// MUST have protected constructor (GWT requirement)
    }

    /**
     * @return "true" if Flash is available in this browser
     */
    private static native String hasFlash() /*-{
					    var isIE = (navigator.appVersion.indexOf("MSIE") != -1) ? true : false;
					    var isWindows = (navigator.appVersion.indexOf("Windows") != -1) ? true : false;  
					    var flashInstalled = "false"; 
					    var minFlashVersion = 8;
					    if (isIE && isWindows) {    // Flash check for IE 
					      for(var i=10; i>=minFlashVersion; i--){ 
					      	try{ 
					      		var flash = new ActiveXObject("ShockwaveFlash.ShockwaveFlash." + i); 
					      		flashInstalled = "true"; 
					      	} 
					      	catch(e){ 
					      	} 
					      } 
					    } 
					    else {  // Flash check for other browsers 
					      if (navigator.plugins["Shockwave Flash"] != null) { 
					         	flashInstalled = "true"; 
					      } 
					    } 
					    return flashInstalled;
					    }-*/;

    /**
     * @return true if Flash is available in this browser
     */
    public static boolean isFlashEnabledInBrowser() {
	return hasFlash().equalsIgnoreCase("true");
    }

}
