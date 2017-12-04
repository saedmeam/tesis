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
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Include;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Window;

public class MantSolicitudController extends BaseController {

	@Wire
	Window winmantrub;
	// llenar tabla
	List<GmGesSolicitud> listaInte = new ArrayList<GmGesSolicitud>();
	GmGesSolicitudDao intDao = new GmGesSolicitudDao();
	GmGesSolicitud intereselect = new GmGesSolicitud();
	@Wire
	Bandbox bndanio;

	GmSegUsuario usu = (GmSegUsuario) Sessions.getCurrent().getAttribute(
			"usuario");

	GmParParametroDao parDao = new GmParParametroDao();
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
		Tabbox tabs = (Tabbox) winmantrub.getParent().getParent().getParent()
				.getParent().getParent().getParent();
		Tabpanels tabpanels = (Tabpanels) winmantrub.getParent().getParent()
				.getParent().getParent().getParent();
		Borderlayout bl = new Borderlayout();
		if (tabs.hasFellow("/catastroadm/cat_001_A.zul")) {
			Tab tab2 = (Tab) tabs.getFellow("/catastroadm/cat_001_A.zul");
			tab2.close();
		}
		// Nombre del tab
		Tab tab = new Tab("INGRESO DE RECEPCION DE SOLICITUD");
		tab.setClosable(true);
		tab.setSelected(true);
		// Id del tab
		tab.setId("/catastroadm/cat_001_A.zul");
		tabs.getTabs().appendChild(tab);
		Tabpanel tabpanel = new Tabpanel();
		tabpanels.appendChild(tabpanel);
		Include include = new Include("/catastroadm/cat_001_A.zul");
		Center c = new Center();
		c.setAutoscroll(true);
		c.appendChild(include);
		bl.appendChild(c);
		tabpanel.appendChild(bl);
	}

	@Command
	public void modificar() {
		// @BindingParam("objeto") GmParInteres interes) {
		if (intereselect != null)
			if (intereselect.getSolId() != null) {

				Sessions.getCurrent().setAttribute("tip_op", "M");
				Sessions.getCurrent().setAttribute("cod_int", intereselect);
				Tabbox tabs = (Tabbox) winmantrub.getParent().getParent()
						.getParent().getParent().getParent().getParent();
				Tabpanels tabpanels = (Tabpanels) winmantrub.getParent()
						.getParent().getParent().getParent().getParent();
				Borderlayout bl = new Borderlayout();
				if (tabs.hasFellow("/catastroadm/cat_001_A.zul")) {
					Tab tab2 = (Tab) tabs
							.getFellow("/catastroadm/cat_001_A.zul");
					tab2.close();
				}
				// Nombre del tab
				Tab tab = new Tab("MODIFICACION DE SOLICITUD");
				tab.setClosable(true);
				tab.setSelected(true);
				// Id del tab
				tab.setId("/catastroadm/cat_001_A.zul");
				tabs.getTabs().appendChild(tab);
				Tabpanel tabpanel = new Tabpanel();
				tabpanels.appendChild(tabpanel);
				Include include = new Include("/catastroadm/cat_001_A.zul");
				Center c = new Center();
				c.setAutoscroll(true);
				c.appendChild(include);
				bl.appendChild(c);
				tabpanel.appendChild(bl);
			} else
				Messagebox
						.show("Debe Seleccionar el registro que desea modificar");
		else
			Messagebox.show("Debe Seleccionar el registro que desea modificar");
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
		listaInte = intDao.getPreFreAct(usu);

	}

	@SuppressWarnings("static-access")
	@NotifyChange("listaInte")
	@Command
	public void InteresPorAño() {
		if (bndanio.getText().isEmpty()) {
			buscar();
		} else {
			if (bndanio.getText() != null) {
				listaInte = intDao.getSolbyTipSol(usu, parSolSel);
			} else
				buscar();
		}
	}

	// eliminar
	@Command
	public void eliminar() {
		// @BindingParam("objeto") GmParInteres interes) {
		if (intereselect != null)
			if (intereselect.getSolId() != null) {
				if (intereselect.getSolEstado().equals("ACT")
						|| intereselect.getSolEstado().equals("OBS")) {
					intereselect.setEstado("INA");
					intDao.actualizar(intereselect);
					buscar();
					BindUtils.postNotifyChange(null, null,
							MantSolicitudController.this, "listaInte");
				}else{
					Messagebox.show("No se puede eliminar el registro ya que esta siendo procesado");
				}
			} else
				Messagebox.show("Debe Seleccionar el Item que desea Eliminar");
		else
			Messagebox.show("Debe Seleccionar el Item que desea Eliminar");
	}
}
