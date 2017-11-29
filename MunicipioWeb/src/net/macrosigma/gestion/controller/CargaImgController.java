package net.macrosigma.gestion.controller;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

public class CargaImgController {

	@Wire
	Window winmantper;

	@Command
	@NotifyChange("listaPersonas")
	public void buscar() {

	}

	@Command
	public void nuevo() {

	}

	@Command
	public void modificar() {

	}

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		buscar();
	}

}
