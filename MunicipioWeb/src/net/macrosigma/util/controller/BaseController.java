package net.macrosigma.util.controller;


import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

public abstract class BaseController {

    public abstract void init(@ContextParam(ContextType.VIEW) Component view);

    public void reloadAll() {
	BindUtils.postNotifyChange(null, null, this, "*");
    }

    protected void growl(String msg) {
	String call = "doGrowl(\"" + msg + "\")";
	Clients.evalJavaScript(call);
    }

    protected void cerrarVentana(Window window) {
	Event event = new Event(Events.ON_CLOSE, window);
	Events.postEvent(event);
    }

}
