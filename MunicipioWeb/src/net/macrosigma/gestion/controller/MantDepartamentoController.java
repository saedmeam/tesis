package net.macrosigma.gestion.controller;

import java.util.ArrayList;
import java.util.List;

import net.macrosigma.gestion.dao.GmGesDepartamentoDao;
import net.macrosigma.gestion.ent.GmGesDepartamento;
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

public class MantDepartamentoController extends BaseController {

	@Wire
	Window winmantrub;
	Window window;
	// llenar tabla
	List<GmGesDepartamento> listaInte = new ArrayList<GmGesDepartamento>();
	GmGesDepartamentoDao intDao = new GmGesDepartamentoDao();
	GmGesDepartamento intereselect = new GmGesDepartamento();
	@Wire
	Bandbox bndanio;

	public GmGesDepartamento getIntereselect() {
		return intereselect;
	}

	public void setIntereselect(GmGesDepartamento intereselect) {
		this.intereselect = intereselect;
	}

	@Command
	public void nuevo() {

		Sessions.getCurrent().setAttribute("opcion", 0);
		if (window == null) {
			window = (Window) Executions.createComponents(
					"/catastroadm/cat_002_A.zul", null, null);
			window.doModal();
			window.setMaximizable(true);
			window.setClosable(true);
			window.setWidth("60%");
			window.setHeight("60%");
			window.addEventListener(Events.ON_CLOSE,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							window = null;
							buscar();
							BindUtils.postNotifyChange(null, null,
									MantDepartamentoController.this,
									"listaInte");
						}
					});
		}
	}

	@Command
	public void modificar() {
		// @BindingParam("objeto") GmParInteres interes) {
		if (intereselect != null)
			if (intereselect.getDepId() != null) {

				Sessions.getCurrent().setAttribute("opcion", 1);
				Sessions.getCurrent().setAttribute("cod_int", intereselect);
				if (window == null) {
					window = (Window) Executions.createComponents(
							"/catastroadm/cat_002_A.zul", null, null);
					window.doModal();
					window.setMaximizable(true);
					window.setClosable(true);
					window.setWidth("60%");
					window.setHeight("60%");
					window.addEventListener(Events.ON_CLOSE,
							new EventListener<Event>() {
								@Override
								public void onEvent(Event arg0)
										throws Exception {
									window = null;
									buscar();
									BindUtils.postNotifyChange(null, null,
											MantDepartamentoController.this,
											"listaInte");
								}
							});
				}
			} else
				Messagebox.show(
						"Debe Seleccionar el registro que desea modificar",
						"Informe", Messagebox.OK, Messagebox.EXCLAMATION,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event e) throws Exception {

							}
						});
		else
			Messagebox.show("Debe Seleccionar el registro que desea modificar",
					"Informe", Messagebox.OK, Messagebox.EXCLAMATION,
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
		BindUtils.postNotifyChange(null, null, MantDepartamentoController.this,
				"listaInte");
	}

	public List<GmGesDepartamento> getListaInte() {
		return listaInte;
	}

	public void setListaInte(List<GmGesDepartamento> listaInte) {
		this.listaInte = listaInte;
	}

	@SuppressWarnings("static-access")
	@NotifyChange("listaInte")
	@Command
	public void getPorRubro() {
		intDao.newManager();
		if (bndanio.getText().isEmpty()) {
			buscar();
		} else {
			if (bndanio.getText() != null) {
				listaInte = intDao.getValPrebyPre(bndanio.getText()
						.toUpperCase());
			} else
				buscar();
		}
	}

	// eliminar
	@Command
	public void eliminar() {
		intDao.newManager();
		// @BindingParam("objeto") GmParInteres interes) {
		if (intereselect != null)
			if (intereselect.getDepId() != null) {
				intereselect.setEstado("INA");
				intDao.actualizar(intereselect);
				buscar();
				BindUtils.postNotifyChange(null, null,
						MantDepartamentoController.this, "listaInte");
			} else
				Messagebox.show(
						"Debe Seleccionar el registro que desea Eliminar",
						"Informe", Messagebox.OK, Messagebox.EXCLAMATION,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event e) throws Exception {

							}
						});
		else
			Messagebox.show("Debe Seleccionar el registro que desea Eliminar",
					"Informe", Messagebox.OK, Messagebox.EXCLAMATION,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {

						}
					});
	}
}
