package net.macrosigma.seguridad.controller;

import java.util.ArrayList;
import java.util.List;

import net.macrosigma.seguridad.dao.GmSegMenuDao;
import net.macrosigma.seguridad.ent.GmSegMenu;
import net.macrosigma.seguridad.ent.GmSegUsuario;
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
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class IngresoOpcionController extends BaseController {

	@Wire
	Window winNuevaOpc;

	@Wire
	Textbox txtnombreOpcion, txtDes, txtRutaPagina, txtRutaIcono;

	@Wire
	Combobox cmbModulo, cmbEstado;

	List<GmSegMenu> listaModulos = new ArrayList<GmSegMenu>();
	GmSegMenuDao opcionDao = new GmSegMenuDao();
	GmSegMenu opcion = new GmSegMenu();
	int op = (Integer) Sessions.getCurrent().getAttribute("opcion");

	@SuppressWarnings("static-access")
	public List<GmSegMenu> getListaModulos() {
		listaModulos = opcionDao.getMenuModulo();
		return listaModulos;
	}

	public void setListaModulos(List<GmSegMenu> listaModulos) {
		this.listaModulos = listaModulos;
	}

	public GmSegMenuDao getOpcionDao() {
		return opcionDao;
	}

	public void setOpcionDao(GmSegMenuDao opcionDao) {
		this.opcionDao = opcionDao;
	}

	public GmSegMenu getOpcion() {
		return opcion;
	}

	public void setOpcion(GmSegMenu opcion) {
		this.opcion = opcion;
	}

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		if (op == 1) {
			cargarModificacion();
		}
	}

	public void cargarModificacion() {
		opcion = (GmSegMenu) Sessions.getCurrent().getAttribute("opcionModificar");
		BindUtils.postNotifyChange(null, null, IngresoOpcionController.this,
				"opcion");
		winNuevaOpc.setTitle("Modificar_Opciones[opc_001]");
		cmbModulo.setText(opcion.getPadreMenu().getMenNombre());
		Sessions.getCurrent().removeAttribute("opcionModificar");
		Sessions.getCurrent().removeAttribute("opcion");
	}

	@Command
	public void crearOpcion() {
		if (txtnombreOpcion.getText().equals("")) {
			txtnombreOpcion
					.setErrorMessage("Debe ingresar un nombre de opción");
			return;
		}
		if (txtDes.getText().equals("")) {
			txtDes.setErrorMessage("Debe ingresar una descripción de la opcion");
			return;
		}
		if (cmbModulo.getText().equals("")) {
			cmbModulo.setErrorMessage("Debe escojer un módulo");
			return;
		}
		if (txtRutaPagina.getText().equals("")) {
			txtRutaPagina.setErrorMessage("Debe ingresar una ruta de página");
			return;
		}
		if (cmbEstado.getText().equals("")) {
			cmbEstado.setText("Debe escojer un estado");
		}
		opcion.setUsuario(((GmSegUsuario) Sessions.getCurrent().getAttribute(
				"usuario")).getUsuUsuario());
		if (op == 0) {
			opcionDao.crear(opcion);
			Messagebox.show("Opción ingresada con exito", "Informe",
					Messagebox.OK, Messagebox.INFORMATION,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {
							if ("onOK".equals(e.getName())) {
							}
//							Events.postEvent(new Event(Events.ON_CLOSE,
//									winNuevaOpc));
						}
					});
		} else {
			opcionDao.actualizar(opcion);
			Messagebox.show("Opción ingresada con exito", "Informe",
					Messagebox.OK, Messagebox.INFORMATION,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {
							if ("onOK".equals(e.getName())) {
							}
//							Events.postEvent(new Event(Events.ON_CLOSE,
//									winNuevaOpc));
						}
					});
		}

	}
}
