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
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;

public class ManteParamGeneralController extends BaseController {

	@Wire
	Window winmantparamgen;
	Window window;
	List<GmParParametros> listaPara = new ArrayList<GmParParametros>();
	GmParParametros Paraselect = new GmParParametros();
	GmParParametroDao paraDao = new GmParParametroDao();
	Treechildren children = new Treechildren();
	long nomval = 0;

	@Wire
	Tree trlistpara;

	@Wire
	Textbox txtbusqueda;
	@Wire
	Combobox cmbest;

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
		if (window == null) {
			window = (Window) Executions.createComponents(
					"/parametro/par_019_B.zul", null, null);
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
							listaPara();
							BindUtils.postNotifyChange(null, null,
									ManteParamGeneralController.this,
									"listaPara");
						}
					});
		}

	}

	@Command
	public void modificar() {
		paraDao.newManager();
		if (Paraselect == null) {
			Messagebox.show("Debe selccionar un registro a modificar");
			return;
		}

		Sessions.getCurrent().setAttribute("tip_op", "M");
		Sessions.getCurrent().setAttribute("pargen", Paraselect);
		if (window == null) {
			window = (Window) Executions.createComponents(
					"/parametro/par_019_B.zul", null, null);
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
							listaPara();
							BindUtils.postNotifyChange(null, null,
									ManteParamGeneralController.this,
									"listaPara");
						}
					});
		}
	}

	@Command
	public void eliminar() {
		paraDao.newManager();
		if (Paraselect == null) {
			Messagebox.show("Debe selccionar un registro a eliminar");
			return;
		}
		if (Paraselect.getCarIdHij().size() > 0) {
			Messagebox
					.show("No se puede eliminar el parámetro si ya tiene alguna dependencia ",
							"Información", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (Paraselect.getDepCarreraId().size() > 0) {
			Messagebox
					.show("No se puede eliminar el parámetro si ya tiene alguna dependencia ",
							"Información", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (Paraselect.getSolCarrera().size() > 0) {
			Messagebox
					.show("No se puede eliminar el parámetro si ya tiene alguna dependencia ",
							"Información", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (Paraselect.getSolReqTipSol().size() > 0) {
			Messagebox
					.show("No se puede eliminar el parámetro si ya tiene alguna dependencia ",
							"Información", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		Paraselect.setEstado("INA");
		try {
			paraDao.actualizar(Paraselect);
			Messagebox.show("Registro Eliminado", "Informe", Messagebox.OK,
					Messagebox.ERROR, new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {

						}
					});
		} catch (Exception e) {
			Messagebox.show("No se puede eliminar", "Informe", Messagebox.OK,
					Messagebox.ERROR, new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {

						}
					});
		}
		listaPara();
		BindUtils.postNotifyChange(null, null,
				ManteParamGeneralController.this, "listaPara");

	}

	@Command
	public void agregardet() {
		if (Paraselect.getParDes() == null || Paraselect.getParDes() == "") {

			Messagebox.show("Debe selccionar un registro a modificar",
					"Informe", Messagebox.OK, Messagebox.ERROR,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {

						}
					});
			return;
		}

		GmParParametros par = new GmParParametros();
		par.setCarIdPad(Paraselect);

		Sessions.getCurrent().setAttribute("tip_op", "AD");
		Sessions.getCurrent().setAttribute("pargen", par);
		if (window == null) {
			window = (Window) Executions.createComponents(
					"/parametro/par_019_B.zul", null, null);
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
							listaPara();
							BindUtils.postNotifyChange(null, null,
									ManteParamGeneralController.this,
									"listaPara");
						}
					});
		}
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
		paraDao.newManager();
		listaPara = paraDao.getParametroComp();
	}

	@SuppressWarnings("static-access")
	@NotifyChange("listaPara")
	@Command
	public void listaPara() {
		List<GmParParametros> lpar = new ArrayList<>();
		listaPara = new ArrayList<>();
		txtbusqueda.setText(txtbusqueda.getText().toUpperCase());
		if (txtbusqueda.getText().isEmpty() && cmbest.getSelectedIndex() == -1) {
			buscar();
		} else {
			paraDao.newManager();
			lpar = paraDao.getParametroByDes(txtbusqueda.getText(), cmbest
					.getSelectedItem().getValue().toString());
		}
		for (int i = 0; i < lpar.size(); i++) {
			boolean b = false;
			GmParParametros par = new GmParParametros();
			par = getPadXParam(lpar.get(i));
			for (int j = 0; j < listaPara.size(); j++) {
				if (par.getPar_id() == listaPara.get(j).getPar_id())
					b = true;
			}
			if (!b)
				listaPara.add(par);
		}
		listdetpara();
	}

	public GmParParametros getPadXParam(GmParParametros par) {
		GmParParametros parPad = new GmParParametros();
		if (par.getCarIdPad() != null)
			parPad = getPadXParam(par.getCarIdPad());
		else
			parPad = par;
		return parPad;
	}

}