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

public class MantPreguntasSeguridadController extends BaseController {

	@Wire
	Window winmantrub;
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
		Tabbox tabs = (Tabbox) winmantrub.getParent().getParent().getParent()
				.getParent().getParent().getParent();
		Tabpanels tabpanels = (Tabpanels) winmantrub.getParent().getParent()
				.getParent().getParent().getParent();
		Borderlayout bl = new Borderlayout();
		if (tabs.hasFellow("/catastroadm/cat_014_A.zul")) {
			Tab tab2 = (Tab) tabs.getFellow("/catastroadm/cat_014_A.zul");
			tab2.close();
		}
		// Nombre del tab
		Tab tab = new Tab("INGRESO DE RECEPCION DE PAQUETE");
		tab.setClosable(true);
		tab.setSelected(true);
		// Id del tab
		tab.setId("/catastroadm/cat_014_A.zul");
		tabs.getTabs().appendChild(tab);
		Tabpanel tabpanel = new Tabpanel();
		tabpanels.appendChild(tabpanel);
		Include include = new Include("/catastroadm/cat_014_A.zul");
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
			if (intereselect.getInsId() != null) {

				Sessions.getCurrent().setAttribute("tip_op", "M");
				Sessions.getCurrent().setAttribute("cod_int", intereselect);
				Tabbox tabs = (Tabbox) winmantrub.getParent().getParent()
						.getParent().getParent().getParent().getParent();
				Tabpanels tabpanels = (Tabpanels) winmantrub.getParent()
						.getParent().getParent().getParent().getParent();
				Borderlayout bl = new Borderlayout();
				if (tabs.hasFellow("/catastroadm/cat_014_A.zul")) {
					Tab tab2 = (Tab) tabs.getFellow("/catastroadm/cat_014_A.zul");
					tab2.close();
				}
				// Nombre del tab
				Tab tab = new Tab("MODIFICACION DE RECEPCION DE PAQUETE");
				tab.setClosable(true);
				tab.setSelected(true);
				// Id del tab
				tab.setId("/catastroadm/cat_014_A.zul");
				tabs.getTabs().appendChild(tab);
				Tabpanel tabpanel = new Tabpanel();
				tabpanels.appendChild(tabpanel);
				Include include = new Include("/catastroadm/cat_014_A.zul");
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

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		buscar();
	}

	@NotifyChange("listaInte")
	public void buscar() {
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
		// @BindingParam("objeto") GmParInteres interes) {
		if (intereselect != null)
			if (intereselect.getInsId() != null) {
				intereselect.setEstado("INA");
				intDao.actualizar(intereselect);
				buscar();
				BindUtils.postNotifyChange(null, null,
						MantPreguntasSeguridadController.this, "listaInte");
			} else
				Messagebox.show("Debe Seleccionar el Item que desea Eliminar");
		else
			Messagebox.show("Debe Seleccionar el Item que desea Eliminar");
	}
}
