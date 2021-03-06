package net.macrosigma.seguridad.controller;

import java.util.Date;

import net.macrosigma.seguridad.dao.GmSegRolDao;
import net.macrosigma.seguridad.ent.GmSegRol;
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
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class IngresoRolController extends BaseController {

	@Wire
	Textbox txtnombre, txtdescripcion;

	@Wire
	Window winMantRol;
	@Wire
	Combobox cmbestado;

	GmSegRol rol = new GmSegRol();
	GmSegRolDao rolDao = new GmSegRolDao();
	String tipop;

	public GmSegRol getUsuario() {
		return rol;
	}

	public void setUsuario(GmSegRol rol) {
		this.rol = rol;
	}

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		tipop = ((String) Sessions.getCurrent().getAttribute("tip_op"));
		if (tipop == "M") {
			rol = ((GmSegRol) Sessions.getCurrent().getAttribute("rol"));
			
		}
		BindUtils.postNotifyChange(null, null,
				IngresoRolController.this, "rol");
		if(rol.getEstado().equals("INA"))
			cmbestado.setSelectedIndex(1);
		else
			cmbestado.setSelectedIndex(0);
		// cargar();
		

	}

	@Command
	public void createUsuario() {
		rolDao.newManager();
		// campos para validar los si estan vacio

		if (txtnombre.getText().isEmpty()) {
			txtnombre.setErrorMessage("campo obligatorio");
			return;
		}

		if (txtdescripcion.getText().isEmpty()) {
			txtdescripcion.setErrorMessage("campo obligatorio");
			return;

		}

		if (cmbestado.getText().isEmpty()) {
			cmbestado.setErrorMessage("campo obligatorio");
			return;

		}

		rol.setRolNombre(txtnombre.getText());
		rol.setRolDescripcion(txtdescripcion.getText());
		rol.setEstado(cmbestado.getSelectedItem().getValue().toString());
		rol.setUsuario(((GmSegUsuario) Sessions.getCurrent().getAttribute(
				"usuario")).getUsuUsuario());
		rol.setFechaModificacion(new Date());
		String msg ="";
		if (tipop == "N"){
			rolDao.crear(rol);
		msg="Rol ingresado con �xito";
		}
		else{
			rolDao.actualizar(rol);
			msg="Rol actualizado con �xito";
		}
		Messagebox.show(msg, "Informe",
				Messagebox.OK, Messagebox.INFORMATION,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						if ("onOK".equals(e.getName())) {
							Events.postEvent(new Event(Events.ON_CLOSE,
									winMantRol));
						}
						
					}
				});

	}

	public void limpiar() {
		rol = new GmSegRol();

		txtnombre.setText("");
		txtdescripcion.setText("");
		cmbestado.setText("");

	}

}
