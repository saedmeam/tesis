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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.West;
import org.zkoss.zul.Window;

public class MantenimientoRollController extends BaseController {

	@Wire
	West westmodulos;
	Window window;
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
		if (window == null) {
			window = (Window) Executions.createComponents(
					"/seguridad/rol/rol_001.zul", null, null);
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
									MantenimientoRollController.this,
									"listaRol");
						}
					});
		}
	}

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		buscar();
		BindUtils.postNotifyChange(null, null,
				MantenimientoRollController.this, "listaRol");
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
			if (window == null) {
				window = (Window) Executions.createComponents(
						"/seguridad/rol/rol_001.zul", null, null);
				window.doModal();
				window.setMaximizable(true);
				window.setWidth("60%");
				window.setHeight("60%");
				window.setClosable(true);
				window.addEventListener(Events.ON_CLOSE,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								window = null;
								buscar();
								BindUtils.postNotifyChange(null, null,
										MantenimientoRollController.this,
										"listaRol");
							}
						});
			}
		} else {
			Messagebox.show("Debe Seleccionar el registro que desea modificar",
					"Informe", Messagebox.OK, Messagebox.EXCLAMATION,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {

						}
					});
		}
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
		Messagebox.show("Registros Procesados exitosamente", "Informe",
				Messagebox.OK, Messagebox.INFORMATION,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						buscar();
						BindUtils.postNotifyChange(null, null,
								MantenimientoRollController.this, "listaRol");
					}
				});

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
				}
			}
			cell.appendChild(lbxListaInterna);
			item.appendChild(cell);
			lbxModulos.appendChild(item);
		}
	}
}
