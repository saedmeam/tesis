package net.macrosigma.seguridad.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.macrosigma.seguridad.dao.GmSegAccionesDao;
import net.macrosigma.seguridad.dao.GmSegMenuDao;
import net.macrosigma.seguridad.dao.GmSegRolDao;
import net.macrosigma.seguridad.dao.GmSegRolMenuAccionDao;
import net.macrosigma.seguridad.dao.GmSegRolMenuDao;
import net.macrosigma.seguridad.ent.GmSegAcciones;
import net.macrosigma.seguridad.ent.GmSegMenu;
import net.macrosigma.seguridad.ent.GmSegRol;
import net.macrosigma.seguridad.ent.GmSegRolMenu;
import net.macrosigma.seguridad.ent.GmSegRolMenuAccion;
import net.macrosigma.seguridad.ent.GmSegUsuario;
import net.macrosigma.util.controller.BaseController;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.East;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.West;
import org.zkoss.zul.Window;

public class MantenimientoRollController extends BaseController {
	@Wire
	East eastacciones;
	@Wire
	West westmodulos;
	@Wire
	Listbox lbxRoles, lbxModulos = new Listbox(), lbxacciones = new Listbox();
	@Wire
	Window winMonRol;

	// llenar tabla
	List<GmSegRol> listaRol = new ArrayList<GmSegRol>();
	GmSegRolDao rolDao = new GmSegRolDao();
	List<GmSegMenu> listModulos = new ArrayList<GmSegMenu>();
	GmSegMenuDao menuDao = new GmSegMenuDao();
	List<GmSegRolMenu> listaRolMenuGuardar = new ArrayList<>();
	GmSegRolMenuDao gmSegRolMenuDao = new GmSegRolMenuDao();
	GmSegRol rolSeleccionado = new GmSegRol();
	List<GmSegRolMenuAccion> listaRolMenuAccionGuardar = new ArrayList<>();
	GmSegRolMenuAccionDao rolMenuAccionDao = new GmSegRolMenuAccionDao();
	GmSegRolMenu rolMenuSeleccionado = new GmSegRolMenu();
	GmSegMenu menuAntes = new GmSegMenu();

	// modificar
	GmSegRol rolselect = null;

	List<GmSegAcciones> listaAcciones = new ArrayList<GmSegAcciones>();
	GmSegAccionesDao accionesDao = new GmSegAccionesDao();

	@SuppressWarnings("static-access")
	public List<GmSegMenu> getListModulos() {
		listModulos = menuDao.getMenuPrincipal();
		return listModulos;
	}

	public List<GmSegAcciones> getListaAcciones() {
		listaAcciones = accionesDao.listarAccesos();
		return listaAcciones;
	}

	public void setListaAcciones(List<GmSegAcciones> listaAcciones) {
		this.listaAcciones = listaAcciones;
	}

	public void setListModulos(List<GmSegMenu> listModulos) {
		this.listModulos = listModulos;
	}

	public GmSegRol getRolselect() {
		return rolselect;
	}

	public void setRolselect(GmSegRol rolselect) {
		this.rolselect = rolselect;
	}

	@Wire
	Bandbox txtbusqueda;

	@Command
	public void nuevo() {
		Sessions.getCurrent().setAttribute("tip_op", "N");
		Tabbox tabs = (Tabbox) winMonRol.getParent().getParent().getParent()
				.getParent().getParent().getParent();
		Tabpanels tabpanels = (Tabpanels) winMonRol.getParent().getParent()
				.getParent().getParent().getParent();
		Borderlayout bl = new Borderlayout();
		if (tabs.hasFellow("/seguridad/rol/rol_001.zul")) {
			Tab tab2 = (Tab) tabs.getFellow("/seguridad/rol/rol_001.zul");
			tab2.close();
		}
		// Nombre del tab
		Tab tab = new Tab("INGRESO DE ROLES");
		tab.setClosable(true);
		tab.setSelected(true);
		// Id del tab
		tab.setId("/seguridad/rol/rol_001.zul");
		tabs.getTabs().appendChild(tab);
		Tabpanel tabpanel = new Tabpanel();
		tabpanels.appendChild(tabpanel);
		Include include = new Include("/seguridad/rol/rol_001.zul");
		Center c = new Center();
		c.setAutoscroll(true);
		c.appendChild(include);
		bl.appendChild(c);
		tabpanel.appendChild(bl);

	}

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		buscar();
	}

	@NotifyChange("listaRol")
	public void buscar() {
		listaRol = rolDao.obtenerTodos();

	}

	public List<GmSegRol> getListaRol() {
		return listaRol;
	}

	public void setListaRol(List<GmSegRol> listaRol) {
		this.listaRol = listaRol;
	}

	// hacer consulta
	@NotifyChange("listaRol")
	@Command
	public void listaRol() {
		if (txtbusqueda.getText().isEmpty()) {
			buscar();
		} else {
			listaRol = rolDao.getPorRol(txtbusqueda.getText());
		}
	}

	@Command
	public void modificar() {
		if (rolselect != null) {
			Sessions.getCurrent().setAttribute("tip_op", "M");
			Sessions.getCurrent().setAttribute("rol", rolselect);
			Tabbox tabs = (Tabbox) winMonRol.getParent().getParent()
					.getParent().getParent().getParent().getParent();
			Tabpanels tabpanels = (Tabpanels) winMonRol.getParent().getParent()
					.getParent().getParent().getParent();
			Borderlayout bl = new Borderlayout();
			if (tabs.hasFellow("/seguridad/rol/rol_001.zul")) {
				Tab tab2 = (Tab) tabs.getFellow("/seguridad/rol/rol_001.zul");
				tab2.close();
			}
			// Nombre del tab
			Tab tab = new Tab("MODIFICACION DE ROLES");
			tab.setClosable(true);
			tab.setSelected(true);
			// Id del tab
			tab.setId("/seguridad/rol/rol_001.zul");
			tabs.getTabs().appendChild(tab);
			Tabpanel tabpanel = new Tabpanel();
			tabpanels.appendChild(tabpanel);
			Include include = new Include("/seguridad/rol/rol_001.zul");
			Center c = new Center();
			c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} else {
			Messagebox.show("Debe Seleccionar el registro que desea modificar");
		}
	}

	@Command
	public void acciones(GmSegMenu objeto) {
		eastacciones.setVisible(true);
		dibujarAcciones(objeto);
	}

	@Command
	public void modulos() {
		rolSeleccionado = (GmSegRol) lbxRoles.getSelectedItem().getValue();
		listaRolMenuGuardar = gmSegRolMenuDao.getPorRol(rolSeleccionado);
		listaRolMenuAccionGuardar = rolMenuAccionDao.getTodosActivos();
		getListModulos();
		if (listaRolMenuGuardar == null) {
			listaRolMenuGuardar = new ArrayList<>();
		}
		if (listaRolMenuAccionGuardar == null) {
			listaRolMenuAccionGuardar = new ArrayList<>();
		}
		dibujarOpcionesRol();
		westmodulos.setVisible(true);
		eastacciones.setVisible(false);
	}

	@Command
	public void guardarRolMenu() {
		for (GmSegRolMenu rolmenu : listaRolMenuGuardar) {
			rolmenu.setUsuario(((GmSegUsuario) Sessions.getCurrent()
					.getAttribute("usuario")).getUsuUsuario());
			rolmenu.setFechaModificacion(new Date());
			GmSegRolMenu rm = gmSegRolMenuDao.getPorRolyMenu(rolmenu.getRol(),
					rolmenu.getMenu());
			if (rm != null) {
				gmSegRolMenuDao.actualizar(rolmenu);
			} else {
				gmSegRolMenuDao.crear(rolmenu);
			}
		}

		for (GmSegRolMenuAccion rmaGuardar : listaRolMenuAccionGuardar) {
			rmaGuardar.setUsuario(((GmSegUsuario) Sessions.getCurrent()
					.getAttribute("usuario")).getUsuUsuario());
			rmaGuardar.setFechaModificacion(new Date());
			GmSegRolMenuAccion rmaComp = rolMenuAccionDao.getPorRolMenuyAccion(
					rmaGuardar.getRolMenu().getRol(), rmaGuardar.getRolMenu()
							.getMenu(), rmaGuardar.getAccion());
			GmSegRolMenu rmExtraerInsert = gmSegRolMenuDao.getPorRolyMenu(
					rmaGuardar.getRolMenu().getRol(), rmaGuardar.getRolMenu()
							.getMenu());
			if (rmExtraerInsert != null) {
				rmaGuardar.setRolMenu(rmExtraerInsert);
			}
			if (rmaComp != null) {
				rolMenuAccionDao.actualizar(rmaGuardar);
			} else {
				rolMenuAccionDao.crear(rmaGuardar);
			}
		}
		Messagebox.show("Registros Guardados exitosamente", "Informe",
				Messagebox.OK, Messagebox.INFORMATION);
	}

	@Command
	public void check(GmSegMenu objeto) {
		int band = 0;
		GmSegRolMenu rm = gmSegRolMenuDao.getPorRolyMenu(rolSeleccionado,
				objeto);
		if (rm != null) {
			for (GmSegRolMenu rmcomp : listaRolMenuGuardar) {
				if (rm.equals(rmcomp)) {
					if (rmcomp.getEstado().equals("ACT")) {
						rmcomp.setEstado("INA");
						rolMenuSeleccionado = rmcomp;
					} else {
						rmcomp.setEstado("ACT");
						rolMenuSeleccionado = rmcomp;
					}
				}
			}
		} else {
			for (GmSegRolMenu rmcomp : listaRolMenuGuardar) {
				if (objeto.equals(rmcomp.getMenu())) {
					if (rmcomp.getEstado().equals("ACT")) {
						rmcomp.setEstado("INA");
						rolMenuSeleccionado = rmcomp;
						band = 1;
					} else {
						rmcomp.setEstado("ACT");
						rolMenuSeleccionado = rmcomp;
						band = 1;
					}
				}
			}
			if (band == 0) {
				GmSegRolMenu rmnuevo = new GmSegRolMenu();
				rmnuevo.setMenu(objeto);
				rmnuevo.setRol(rolSeleccionado);
				listaRolMenuGuardar.add(rmnuevo);
				rolMenuSeleccionado = rmnuevo;
			}

		}
	}

	public void dibujarOpcionesRol() {
		lbxModulos.getItems().clear();
		for (GmSegMenu modulo : listModulos) {
			Listitem item = new Listitem();
			Listcell cell = new Listcell();
			Listbox lbxListaInterna = new Listbox();
			Listheader header = new Listheader();
			Listhead head = new Listhead();
			header.setLabel(modulo.getMenNombre());
			head.appendChild(header);
			lbxListaInterna.appendChild(head);
			for (GmSegMenu opcion : modulo.getListPadreMenu()) {
				if (opcion.getEstado().equals("ACT")) {
					final Listitem itemInterno = new Listitem();
					itemInterno.setValue(opcion);
					Listcell cellInterno = new Listcell();
					Checkbox check = new Checkbox();
					Label label = new Label();
					label.setValue(opcion.getMenNombre());
					for (GmSegRolMenu rmg : listaRolMenuGuardar) {
						if (rmg.getMenu().equals(opcion)
								&& rmg.getEstado().equals("ACT")) {
							check.setChecked(true);
						}
					}
					check.addEventListener(Events.ON_CLICK,
							new EventListener<Event>() {
								@Override
								public void onEvent(Event arg0)
										throws Exception {
									check((GmSegMenu) itemInterno.getValue());
								}
							});
					cellInterno.appendChild(check);
					cellInterno.appendChild(label);
					itemInterno.appendChild(cellInterno);
					lbxListaInterna.appendChild(itemInterno);
					itemInterno.addEventListener(Events.ON_CLICK,
							new EventListener<Event>() {
								@Override
								public void onEvent(Event arg0)
										throws Exception {
									acciones((GmSegMenu) itemInterno.getValue());
								}
							});
				}
			}
			cell.appendChild(lbxListaInterna);
			item.appendChild(cell);
			lbxModulos.appendChild(item);
		}
	}

	public void dibujarAcciones(GmSegMenu objeto) {
		rolMenuSeleccionado.setMenu(objeto);
		rolMenuSeleccionado.setRol(rolSeleccionado);
		List<GmSegRolMenuAccion> accionesOpcion = new ArrayList<>();
		menuAntes = objeto;
		for (GmSegRolMenuAccion ac : listaRolMenuAccionGuardar) {
			if (ac.getRolMenu().getRol().equals(rolMenuSeleccionado.getRol())
					&& ac.getRolMenu().getMenu().equals(objeto)) {
				accionesOpcion.add(ac);
			}
		}
		getListaAcciones();
		lbxacciones.getItems().clear();
		for (GmSegAcciones acc : listaAcciones) {
			final Listitem item = new Listitem();
			Listcell cell = new Listcell();
			Checkbox check = new Checkbox();
			Label label = new Label();
			label.setValue(acc.getAccionEnum().getDescripcion());
			cell.appendChild(check);
			cell.appendChild(label);
			item.appendChild(cell);
			item.setValue(acc);
			lbxacciones.appendChild(item);
			for (GmSegRolMenuAccion acccheck : accionesOpcion) {
				if (acccheck.getAccion().equals(acc)
						&& acccheck.getEstado().equals("ACT")) {
					check.setChecked(true);
				}
			}
			check.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					checkAccion((GmSegAcciones) item.getValue());
				}
			});
		}
	}

	@Command
	public void checkAccion(GmSegAcciones objeto) {
		int band = 0;
		GmSegRolMenuAccion rma = rolMenuAccionDao.getPorRolMenuyAccion(
				rolMenuSeleccionado.getRol(), rolMenuSeleccionado.getMenu(),
				objeto);
		if (rma != null) {
			for (GmSegRolMenuAccion rmacomp : listaRolMenuAccionGuardar) {
				if (rma.getRolMenu().getRol()
						.equals(rmacomp.getRolMenu().getRol())
						&& rma.getRolMenu().getMenu()
								.equals(rmacomp.getRolMenu().getMenu())
						&& rma.getAccion().equals(rmacomp.getAccion())) {
					if (rmacomp.getEstado().equals("ACT")) {
						rmacomp.setEstado("INA");
					} else {
						rmacomp.setEstado("ACT");
					}
				}
			}
		} else {
			for (GmSegRolMenuAccion rmacomp : listaRolMenuAccionGuardar) {
				if (rmacomp.getRolMenu().getRol()
						.equals(rolMenuSeleccionado.getRol())
						&& rmacomp.getRolMenu().getMenu()
								.equals(rolMenuSeleccionado.getMenu())
						&& rmacomp.getAccion().equals(objeto)) {
					if (rmacomp.getEstado().equals("ACT")) {
						rmacomp.setEstado("INA");
						band = 1;
					} else {
						rmacomp.setEstado("ACT");
						band = 1;
					}
				}

			}
			if (band == 0) {
				GmSegRolMenuAccion rmanuevo = new GmSegRolMenuAccion();
				rmanuevo.setAccion(objeto);
				GmSegRolMenu rmnuevo = new GmSegRolMenu();
				rmnuevo.setRol(rolSeleccionado);
				rmnuevo.setMenu(menuAntes);
				rmanuevo.setRolMenu(rmnuevo);
				listaRolMenuAccionGuardar.add(rmanuevo);
			}
		}
	}
}
