package net.macrosigma.gestion.controller;

import java.util.ArrayList;
import java.util.List;

import net.macrosigma.gestion.dao.GmGesSolicitudDao;
import net.macrosigma.gestion.ent.GmGesSolicitud;
import net.macrosigma.parametro.dao.GmParParametroDao;
import net.macrosigma.parametro.ent.GmParParametros;
import net.macrosigma.seguridad.ent.GmSegUsuario;
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

public class MantSolicitudController extends BaseController {

	@Wire
	Window winmantrub;
	Window window;
	// llenar tabla
	List<GmGesSolicitud> listaInte = new ArrayList<GmGesSolicitud>();
	GmGesSolicitudDao intDao = new GmGesSolicitudDao();
	GmParParametroDao parDao = new GmParParametroDao();
	GmGesSolicitud intereselect = new GmGesSolicitud();
	@Wire
	Bandbox bndanio;

	GmSegUsuario usu = (GmSegUsuario) Sessions.getCurrent().getAttribute(
			"usuario");

	List<GmParParametros> listparSol = new ArrayList<GmParParametros>();
	GmParParametros parSolSel = new GmParParametros();

	public List<GmGesSolicitud> getListaInte() {
		return listaInte;
	}

	public void setListaInte(List<GmGesSolicitud> listaInte) {
		this.listaInte = listaInte;
	}

	public GmGesSolicitud getIntereselect() {
		return intereselect;
	}

	public void setIntereselect(GmGesSolicitud intereselect) {
		this.intereselect = intereselect;
	}

	public GmSegUsuario getUsu() {
		return usu;
	}

	public void setUsu(GmSegUsuario usu) {
		this.usu = usu;
	}

	public List<GmParParametros> getListparSol() {
		return listparSol;
	}

	public void setListparSol(List<GmParParametros> listparSol) {
		this.listparSol = listparSol;
	}

	public GmParParametros getParSolSel() {
		return parSolSel;
	}

	public void setParSolSel(GmParParametros parSolSel) {
		this.parSolSel = parSolSel;
	}

	@Command
	public void nuevo() {

		Sessions.getCurrent().setAttribute("tip_op", "N");
		if (window == null) {
			window = (Window) Executions.createComponents(
					"/catastroadm/cat_001_A.zul", null, null);
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
							intereselect = new GmGesSolicitud();
							BindUtils.postNotifyChange(null, null,
									MantSolicitudController.this, "listaInte");
						}
					});
		}
	}

	@Command
	public void modificar() {
		intDao.newManager();
		// @BindingParam("objeto") GmParInteres interes) {
		if (intereselect != null) {
			if (intereselect.getSolId() != null) {
				if (intereselect.getSolEstado().equals("ACT")
						|| intereselect.getSolEstado().equals("OBS")
						|| intereselect.getSolEstado().equals("ING")) {
					Sessions.getCurrent().setAttribute("tip_op", "M");
					Sessions.getCurrent().setAttribute("cod_int", intereselect);
					if (window == null) {
						window = (Window) Executions.createComponents(
								"/catastroadm/cat_001_A.zul", null, null);
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
										intereselect = new GmGesSolicitud();
										BindUtils.postNotifyChange(null, null,
												MantSolicitudController.this,
												"listaInte");
									}
								});
					}
				} else {
					Messagebox
							.show("No se puede modificar el registro ya que esta siendo procesado",
									"Informe", Messagebox.OK, Messagebox.ERROR,
									new EventListener<Event>() {
										@Override
										public void onEvent(Event e)
												throws Exception {

										}
									});
				}
			} else
				Messagebox.show("Debe Seleccionar el Ítem que desea Modificar",
						"Informe", Messagebox.OK, Messagebox.ERROR,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event e) throws Exception {

							}
						});
		} else
			Messagebox.show("Debe Seleccionar el Ítem que desea Modificar",
					"Informe", Messagebox.OK, Messagebox.ERROR,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {

						}
					});
	}

	@Command
	public void verdet() {
		intDao.newManager();
		// @BindingParam("objeto") GmParInteres interes) {
		if (intereselect.getSolId() != null) {

			Sessions.getCurrent().setAttribute("tip_op", "M");
			Sessions.getCurrent().setAttribute("cod_int", intereselect);
			if (window == null) {
				window = (Window) Executions.createComponents(
						"/catastroadm/cat_001_B.zul", null, null);
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
								intereselect = new GmGesSolicitud();
								BindUtils.postNotifyChange(null, null,
										MantSolicitudController.this,
										"listaInte");
							}
						});
			}
		} else
			Messagebox.show(
					"Debe Seleccionar el Ítem que desea ver el detalle",
					"Informe", Messagebox.OK, Messagebox.ERROR,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {

						}
					});
	}

	@SuppressWarnings("static-access")
	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		listparSol = parDao.getParametroByDesPad("TIPOS DE SOLICITUD");
		buscar();
	}

	@NotifyChange("listaInte")
	public void buscar() {
		intDao.newManager();
		listaInte = intDao.getPreFreAct(usu);

	}

	@NotifyChange("listaInte")
	@Command
	public void InteresPorAño() {
		intDao.newManager();
		// if (bndanio.getText().isEmpty()) {
		buscar();
		// } else {
		// if (bndanio.getText() != null) {
		// listaInte = intDao.getSolbyTipSol(usu, parSolSel);
		// } else
		// buscar();
		// }
	}

	// eliminar
	@Command
	public void eliminar() {
		intDao.newManager();
		// @BindingParam("objeto") GmParInteres interes) {
		if (intereselect != null)
			if (intereselect.getSolId() != null) {
				if (intereselect.getSolEstado().equals("ING")) {
					intereselect.setEstado("INA");
					intDao.actualizar(intereselect);
					buscar();
					intereselect = new GmGesSolicitud();
					BindUtils.postNotifyChange(null, null,
							MantSolicitudController.this, "listaInte");
				} else {
					Messagebox
							.show("No se puede eliminar el registro ya que esta siendo procesado",
									"Informe", Messagebox.OK, Messagebox.ERROR,
									new EventListener<Event>() {
										@Override
										public void onEvent(Event e)
												throws Exception {

										}
									});
				}
			} else
				Messagebox.show("Debe Seleccionar el Ítem que desea Eliminar",
						"Informe", Messagebox.OK, Messagebox.ERROR,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event e) throws Exception {

							}
						});
		else
			Messagebox.show("Debe Seleccionar el Ítem que desea Eliminar",
					"Informe", Messagebox.OK, Messagebox.ERROR,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {

						}
					});
	}
}
