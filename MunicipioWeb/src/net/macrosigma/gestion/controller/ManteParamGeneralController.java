package net.macrosigma.gestion.controller;

import java.util.ArrayList;
import java.util.List;

import net.macrosigma.parametro.dao.GmParParametroDao;
import net.macrosigma.parametro.ent.GmParParametros;
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
import org.zkoss.zul.Label;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;

public class ManteParamGeneralController extends BaseController {

	@Wire
	Window winmantparamgen;

	List<GmParParametros> listaPara = new ArrayList<GmParParametros>();
	GmParParametros Paraselect = new GmParParametros();
	GmParParametroDao paraDao = new GmParParametroDao();
	Treechildren children = new Treechildren();
	long nomval = 0;

	@Wire
	Tree trlistpara;

	@Wire
	Bandbox txtbusqueda;

	public List<GmParParametros> getlistaPara() {
		return listaPara;
	}

	public void setlistaPara(List<GmParParametros> listaPara) {
		this.listaPara = listaPara;
	}

	public GmParParametros getParaselect() {
		return Paraselect;
	}

	public void setParaselect(GmParParametros paraselect) {
		Paraselect = paraselect;
	}

	public void listdetpara() {
		cargaarbol();
	}

	public void cargaarbol() {
		trlistpara.removeChild(children);
		children = new Treechildren();
		Treeitem item = new Treeitem();
		Treerow row = new Treerow();
		Treecell cell = new Treecell();
		if (listaPara != null) {
			for (int i = 0; i < listaPara.size(); i++) {
				item = new Treeitem();
				row = new Treerow();
				Label lbl = new Label();
				lbl.setId(listaPara.get(i).getPar_id() + "");
				lbl.setValue(listaPara.get(i).getParDes());
				cell = new Treecell();
				cell.appendChild(lbl);
				row.appendChild(cell);
				cell = new Treecell();
				cell.setLabel(listaPara.get(i).getParValor());
				row.appendChild(cell);
				cell = new Treecell();
				cell.setLabel(listaPara.get(i).getEstado().equals("ACT") ? "ACTIVO"
						: "INACTIVO");
				row.appendChild(cell);
				item.appendChild(row);
				if (listaPara.get(i).getCarIdHij() != null) {
					if (listaPara.get(i).getCarIdHij().size() > 0) {
						cargahijos(item, listaPara.get(i).getCarIdHij());
					}
				}

				children.appendChild(item);
			}
			trlistpara.appendChild(children);
		}
	}

	public void cargahijos(Treeitem pad, List<GmParParametros> hij) {
		Treechildren children = new Treechildren();
		Treeitem item = new Treeitem();
		Treerow row = new Treerow();
		Treecell cell = new Treecell();
		for (int i = 0; i < hij.size(); i++) {
			item = new Treeitem();
			row = new Treerow();
			cell = new Treecell();
			row = new Treerow();
			cell = new Treecell();
			Label lbl = new Label();
			lbl.setId(hij.get(i).getPar_id() + "");
			lbl.setValue(hij.get(i).getParDes());
			cell = new Treecell();
			cell.appendChild(lbl);
			row.appendChild(cell);
			cell = new Treecell();
			cell.setLabel(hij.get(i).getParValor());
			row.appendChild(cell);
			cell = new Treecell();
			cell.setLabel(hij.get(i).getEstado().equals("ACT") ? "ACTIVO"
					: "INACTIVO");
			row.appendChild(cell);
			item.appendChild(row);
			children.appendChild(item);
			if (hij.get(i).getCarIdHij() != null) {
				if (hij.get(i).getCarIdHij().size() > 0) {
					cargahijos(item, hij.get(i).getCarIdHij());
				}
			}

		}

		pad.appendChild(children);

	}

	@Command
	public void nuevo() {

		Sessions.getCurrent().setAttribute("tip_op", "N");
		Tabbox tabs = (Tabbox) winmantparamgen.getParent().getParent()
				.getParent().getParent().getParent().getParent();
		Tabpanels tabpanels = (Tabpanels) winmantparamgen.getParent()
				.getParent().getParent().getParent().getParent();
		Borderlayout bl = new Borderlayout();
		if (tabs.hasFellow("/parametro/par_019_B.zul")) {
			Tab tab2 = (Tab) tabs.getFellow("/parametro/par_019_B.zul");
			tab2.close();
		}
		// Nombre del tab
		Tab tab = new Tab("INGRESO DE PARAMETROS GENERAL");
		tab.setClosable(true);
		tab.setSelected(true);
		// Id del tab
		tab.setId("/parametro/par_019_B.zul");
		tabs.getTabs().appendChild(tab);
		Tabpanel tabpanel = new Tabpanel();
		tabpanels.appendChild(tabpanel);
		Include include = new Include("/parametro/par_019_B.zul");
		Center c = new Center();
		c.setAutoscroll(true);
		c.appendChild(include);
		bl.appendChild(c);
		tabpanel.appendChild(bl);
	}

	@Command
	public void modificar() {
		if (Paraselect == null) {
			Messagebox.show("Debe selccionar un registro a modificar");
			return;
		}

		Sessions.getCurrent().setAttribute("tip_op", "M");
		Sessions.getCurrent().setAttribute("pargen", Paraselect);
		Tabbox tabs = (Tabbox) winmantparamgen.getParent().getParent()
				.getParent().getParent().getParent().getParent();
		Tabpanels tabpanels = (Tabpanels) winmantparamgen.getParent()
				.getParent().getParent().getParent().getParent();
		Borderlayout bl = new Borderlayout();
		if (tabs.hasFellow("/parametro/par_019_B.zul")) {
			Tab tab2 = (Tab) tabs.getFellow("/parametro/par_019_B.zul");
			tab2.close();
		}
		// Nombre del tab
		Tab tab = new Tab("MODIFICACION DE PARAMETROS GENERAL");
		tab.setClosable(true);
		tab.setSelected(true);
		// Id del tab
		tab.setId("/parametro/par_019_B.zul");
		tabs.getTabs().appendChild(tab);
		Tabpanel tabpanel = new Tabpanel();
		tabpanels.appendChild(tabpanel);
		Include include = new Include("/parametro/par_019_B.zul");
		Center c = new Center();
		c.setAutoscroll(true);
		c.appendChild(include);
		bl.appendChild(c);
		tabpanel.appendChild(bl);
	}

	@Command
	public void eliminar() {
		if (Paraselect == null) {
			Messagebox.show("Debe selccionar un registro a eliminar");
			return;
		}
//		Paraselect.setEstado("I");
		try {
			paraDao.eliminar(Paraselect);
		} catch (Exception e) {
			Messagebox.show("No se puede eliminar");
		}

		BindUtils.postNotifyChange(null, null,
				ManteParamGeneralController.this, "listaPara");

	}

	@Command
	public void agregardet() {
		if (Paraselect.getParDes() == null || Paraselect.getParDes() == "") {
			Messagebox.show("Debe selccionar un registro a modificar");
			return;
		}

		GmParParametros par = new GmParParametros();
		par.setCarIdPad(Paraselect);

		Sessions.getCurrent().setAttribute("tip_op", "AD");
		Sessions.getCurrent().setAttribute("pargen", par);
		Tabbox tabs = (Tabbox) winmantparamgen.getParent().getParent()
				.getParent().getParent().getParent().getParent();
		Tabpanels tabpanels = (Tabpanels) winmantparamgen.getParent()
				.getParent().getParent().getParent().getParent();
		Borderlayout bl = new Borderlayout();
		if (tabs.hasFellow("/parametro/par_019_B.zul")) {
			Tab tab2 = (Tab) tabs.getFellow("/parametro/par_019_B.zul");
			tab2.close();
		}
		// Nombre del tab
		Tab tab = new Tab("MANTENIMIENTO PARAMETROS GENERAL");
		tab.setClosable(true);
		tab.setSelected(true);
		// Id del tab
		tab.setId("/parametro/par_019_B.zul");
		tabs.getTabs().appendChild(tab);
		Tabpanel tabpanel = new Tabpanel();
		tabpanels.appendChild(tabpanel);
		Include include = new Include("/parametro/par_019_B.zul");
		Center c = new Center();
		c.setAutoscroll(true);
		c.appendChild(include);
		bl.appendChild(c);
		tabpanel.appendChild(bl);
	}

	@Command
	public void selec() {
		nomval = 0;
		nomval = Long
				.parseLong(((Label) (trlistpara.getSelectedItem().getChildren()
						.get(0).getChildren().get(0).getChildren().get(0)))
						.getId());
		buscaarbol();
	}

	public void buscaarbol() {
		for (int i = 0; i < listaPara.size(); i++) {
			if (nomval == listaPara.get(i).getPar_id())
				Paraselect = listaPara.get(i);
			if (listaPara.get(i).getCarIdHij() != null) {
				if (listaPara.get(i).getCarIdHij().size() > 0) {
					buscadet(listaPara.get(i).getCarIdHij());
				}
			}
		}
	}

	public void buscadet(List<GmParParametros> hij) {
		for (int i = 0; i < hij.size(); i++) {
			if (nomval == hij.get(i).getPar_id()) {
				Paraselect = hij.get(i);
				break;
			}
			if (hij.get(i).getCarIdHij() != null) {
				if (hij.get(i).getCarIdHij().size() > 0) {
					buscadet(hij.get(i).getCarIdHij());
				}
			}
		}
	}

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		listaPara();
	}

	@SuppressWarnings("static-access")
	@NotifyChange("listaPara")
	public void buscar() {
		listaPara = paraDao.getParametropad();
	}

	@SuppressWarnings("static-access")
	@NotifyChange("listaPara")
	@Command
	public void listaPara() {
		txtbusqueda.setText(txtbusqueda.getText().toUpperCase());
		if (txtbusqueda.getText().isEmpty()) {
			buscar();
		} else {
			listaPara = paraDao.getParametroByDes(txtbusqueda.getText());
		}
		listdetpara();
	}

}