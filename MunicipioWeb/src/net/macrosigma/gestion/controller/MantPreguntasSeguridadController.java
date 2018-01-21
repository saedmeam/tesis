package net.macrosigma.gestion.controller;

import java.util.ArrayList;
import java.util.List;

import net.macrosigma.gestion.dao.GmGesPreguntaFrecuenteDao;
import net.macrosigma.gestion.ent.GmGesPreguntaFrecuente;
import net.macrosigma.util.controller.BaseController;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Window;

public class MantPreguntasSeguridadController extends BaseController {

	@Wire
	Window winmantrub;
	Window window;
	// llenar tabla
	List<GmGesPreguntaFrecuente> listaInte = new ArrayList<GmGesPreguntaFrecuente>();
	GmGesPreguntaFrecuenteDao intDao = new GmGesPreguntaFrecuenteDao();
	GmGesPreguntaFrecuente intereselect = new GmGesPreguntaFrecuente();
	@Wire
	Bandbox bndanio;

	public GmGesPreguntaFrecuente getIntereselect() {
		return intereselect;
	}

	public void setIntereselect(GmGesPreguntaFrecuente intereselect) {
		this.intereselect = intereselect;
	}

	@Command
	public void nuevo() {

		Sessions.getCurrent().setAttribute("tip_op", "N");
		if (window == null) {
			window = (Window) Executions.createComponents(
					"/catastroadm/cat_014_A.zul", null, null);
			window.doModal();
			window.setMaximizable(true);
			window.setWidth("60%");
			window.setClosable(true);
			window.setHeight("60%");
			window.addEventListener(Events.ON_CLOSE,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							window = null;
							buscar();
							BindUtils.postNotifyChange(null, null,
									MantPreguntasSeguridadController.this,
									"listaInte");
						}
					});
		}
	}

	@Command
	public void modificar() {
		intDao.newManager();
		// @BindingParam("objeto") GmParInteres interes) {
		if (intereselect != null)
			if (intereselect.getInsId() != null) {

				Sessions.getCurrent().setAttribute("tip_op", "M");
				Sessions.getCurrent().setAttribute("cod_int", intereselect);
				if (window == null) {
					window = (Window) Executions.createComponents(
							"/catastroadm/cat_014_A.zul", null, null);
					window.doModal();
					window.setMaximizable(true);
					window.setWidth("60%");
					window.setClosable(true);
					window.setHeight("60%");
					window.addEventListener(Events.ON_CLOSE,
							new EventListener<Event>() {
								@Override
								public void onEvent(Event arg0)
										throws Exception {
									window = null;
									buscar();
									BindUtils
											.postNotifyChange(
													null,
													null,
													MantPreguntasSeguridadController.this,
													"listaInte");
								}
							});
				}
			} else
				Messagebox.show(
						"Debe Seleccionar el registro que desea modificar",
						"Informe", Messagebox.OK, Messagebox.ERROR,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event e) throws Exception {

							}
						});
		else
			Messagebox.show("Debe Seleccionar el registro que desea modificar",
					"Informe", Messagebox.OK, Messagebox.ERROR,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {

						}
					});
	}

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		buscar();
	}

	@NotifyChange("listaInte")
	public void buscar() {
		intDao.newManager();
		listaInte = intDao.getPreFreAct();

	}

	public List<GmGesPreguntaFrecuente> getListaInte() {
		return listaInte;
	}

	public void setListaInte(List<GmGesPreguntaFrecuente> listaInte) {
		this.listaInte = listaInte;
	}

	@NotifyChange("listaInte")
	@Command
	public void InteresPorAño() {
		intDao.newManager();
		if (bndanio.getText().isEmpty()) {
			buscar();
		} else {
			if (bndanio.getText() != null) {
				listaInte = GmGesPreguntaFrecuenteDao.getValPrebyPre(bndanio
						.getText().toUpperCase());
			} else
				buscar();
		}
	}

	// eliminar
	@Command
	public void eliminar() {
		intDao.newManager();
		// @BindingParam("objeto") GmParInteres interes) {
		if (intereselect != null) {
			if (intereselect.getInsId() != null) {
				if (intereselect.getPreFreUsu().size() == 0) {

					intereselect.setEstado("INA");
					intDao.actualizar(intereselect);
					buscar();
					BindUtils.postNotifyChange(null, null,
							MantPreguntasSeguridadController.this, "listaInte");

				} else {
					Messagebox
							.show("No se puede eliminar la pregunta si ya se relacionó con algún usuario ",
									"Información", Messagebox.OK,
									Messagebox.ERROR);
				}
			} else
				Messagebox.show("Debe Seleccionar el Ítem que desea Eliminar",
						"Información", Messagebox.OK, Messagebox.INFORMATION);
		} else
			Messagebox.show("Debe Seleccionar el Ítem que desea Eliminar",
					"Información", Messagebox.OK, Messagebox.INFORMATION);
	}
}
