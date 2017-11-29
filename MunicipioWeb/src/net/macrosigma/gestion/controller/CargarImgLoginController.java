package net.macrosigma.gestion.controller;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;

public class CargarImgLoginController {

	//private Window window;

	@Command
	public void nuevo() {
		// if (window == null) {
		// window = (Window) Executions.createComponents(
		// "/seguridad/rol/rol_001.zul", null, null);
		// window.doModal();
		// window.addEventListener(Events.ON_CLOSE,
		// new EventListener<Event>() {
		// @Override
		// public void onEvent(Event arg0) throws Exception {
		// window = null;
		// }
		// });
		// }
	}

	@Command
	public void limpiar() {
		// if (window == null) {
		// window = (Window) Executions.createComponents(
		// "/seguridad/rol/rol_001.zul", null, null);
		// window.doModal();
		// window.addEventListener(Events.ON_CLOSE,
		// new EventListener<Event>() {
		// @Override
		// public void onEvent(Event arg0) throws Exception {
		// window = null;
		// }
		// });
		// }
	}

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

}
