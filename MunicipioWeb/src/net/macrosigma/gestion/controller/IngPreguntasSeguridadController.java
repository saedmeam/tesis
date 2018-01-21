package net.macrosigma.gestion.controller;

import net.macrosigma.gestion.dao.GmGesPreguntaFrecuenteDao;
import net.macrosigma.gestion.ent.GmGesPreguntaFrecuente;
import net.macrosigma.util.controller.BaseController;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class IngPreguntasSeguridadController extends BaseController {

	@Wire
	Textbox txtpreg;
	String tipop;
	
	@Wire
	Window winpregseg;

	GmGesPreguntaFrecuente interes = new GmGesPreguntaFrecuente();
	GmGesPreguntaFrecuenteDao intDao = new GmGesPreguntaFrecuenteDao();

	public GmGesPreguntaFrecuenteDao getIntDao() {
		return intDao;
	}

	public void setIntDao(GmGesPreguntaFrecuenteDao intDao) {
		this.intDao = intDao;
	}

	public GmGesPreguntaFrecuente getInteres() {
		return interes;
	}

	public void setInteres(GmGesPreguntaFrecuente interes) {
		this.interes = interes;
	}

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		tipop = ((String) Sessions.getCurrent().getAttribute("tip_op"));
		if (tipop == "M") {
			interes = ((GmGesPreguntaFrecuente) Sessions.getCurrent()
					.getAttribute("cod_int"));
		}

		BindUtils.postNotifyChange(null, null,
				IngPreguntasSeguridadController.this, "interes");
	}

	@Command
	public void createUsuario() {
		// campos para validar los si estan vacio
		intDao.newManager();
		if (txtpreg.getValue() == null) {
			txtpreg.setErrorMessage("campo obligatorio");
			return;
		}
		interes.setEstado("ACT");
		if (tipop == "M"){
			intDao.actualizar(interes);
			Messagebox.show("Pregunta Frecuente Modificada", "Informe",
					Messagebox.OK, Messagebox.INFORMATION,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {
							Events.postEvent(new Event(Events.ON_CLOSE,
									winpregseg));
						}
					});
		}
		else{
			intDao.crear(interes);
			Messagebox.show("Pregunta Frecuente Ingresada", "Informe",
					Messagebox.OK, Messagebox.INFORMATION,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {
							Events.postEvent(new Event(Events.ON_CLOSE,
									winpregseg));
						}
					});
		}
		limpiar();
		

	}

	public void limpiar() {
		interes = new GmGesPreguntaFrecuente();
		txtpreg.setValue("");

	}

}
