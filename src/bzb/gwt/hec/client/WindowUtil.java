package bzb.gwt.hec.client;

import com.google.gwt.core.client.JavaScriptObject;

public abstract class WindowUtil {
	
	public static native JavaScriptObject newWindow(String url, String   
			name, String features)/*-{ 
			       var window = $wnd.open(url, name, features); 
			        return window; 
			    }-*/; 
	
	public static native void setWindowTarget(JavaScriptObject window,   
			String target)/*-{ 
			       window.location = target; 
			    }-*/; 

}
