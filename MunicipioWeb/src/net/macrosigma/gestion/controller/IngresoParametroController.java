package net.macrosigma.gestion.controller;

import net.macrosigma.parametro.dao.GmParParametroDao;
import net.macrosigma.parametro.ent.GmParParametros;
import net.macrosigma.util.controller.BaseController;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Textbox;

public class IngresoParametroController extends BaseController {

	// @Wire
	// Intbox intanio;
	@Wire
	Doublebox intanio, intvalor;
	@Wire
	Textbox txtdes;
	@Wire
	Combobox cmbtestado;

	// String tipop;

	GmParParametros param = new GmParParametros();
	GmParParametroDao paraDao = new GmParParametroDao();

	public GmParParametros getInteres() {
		return param;
	}

	public void setInteres(GmParParametros interes) {
		this.param = interes;
	}

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		// tipop = ((String) Sessions.getCurrent().getAttribute("tip_op"));
		// if (tipop == "M") {
		// param = ((GmGesParametro) Sessions.getCurrent().getAttribute(
		// "cod_int"));
		// cargar();
	}

	// }

	@Command
	public void createUsuario() {

		// campos para validar los si estan vacio

		if (intanio.intValue() == 0) {
			intanio.setErrorMessage("campo obligatorio");
			return;
		}

		if (intvalor.doubleValue() == 0) {
			intvalor.setErrorMessage("campo obligatorio");
			return;

		}

		if (txtdes.getText().isEmpty()) {
			txtdes.setErrorMessage("campo obligatorio");
			return;

		}

		if (cmbtestado.getText().isEmpty()) {
			cmbtestado.setErrorMessage("campo obligatorio");
			return;

		}
		paraDao.crear(param);
		limpiar();
		Messagebox.show("Parametro Ingresado");

	}

	public void limpiar() {
		param = new GmParParametros();

		intanio.setText("");
		intvalor.setText("");
		txtdes.setText("");
		cmbtestado.setText("");
	}

}
