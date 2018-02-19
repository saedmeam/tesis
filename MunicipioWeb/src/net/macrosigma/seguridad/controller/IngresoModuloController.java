package net.macrosigma.seguridad.controller;

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
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class IngresoModuloController extends BaseController {

	@Wire
	Textbox txtnombre, txtdescripcion,txtRutaIcono;
	
	@Wire
	Intbox inbNumroModulo;

	@Wire
	Combobox cmbtestado;
	
	@Wire
	Window winMod;

	GmSegMenu modulo = new GmSegMenu();
	GmSegMenuDao menuDao = new GmSegMenuDao();
	
	int opcion;

	public GmSegMenu getModulo() {
		return modulo;
	}

	public void setModulo(GmSegMenu modulo) {
		this.modulo = modulo;
	}

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		cargarPagina();
	}
	
	public void cargarPagina(){
		opcion=(Integer)Sessions.getCurrent().getAttribute("opcion");
		if(opcion==1){
		   modulo = (GmSegMenu)Sessions.getCurrent().getAttribute("modulo");
		   winMod.setTitle("Modificación de_Modulo[mod_001]");
		   BindUtils.postNotifyChange(null, null,
					IngresoModuloController.this,
					"modulo");
		}
	}

	@Command
	public void crearModulo() {
		menuDao.newManager();
		// campos para validar los si estan vacio

		if (txtnombre.getText().isEmpty()) {
			txtnombre.setErrorMessage("Debe ingresar un Nombre de Módulo");
			return;
		}

		if (txtdescripcion.getText().isEmpty()) {
			txtdescripcion.setErrorMessage("Debe ingresar la descripción del Módulo ");
			return;

		}
		
//		if(txtRutaIcono.getText().isEmpty()){
//			txtRutaIcono.setErrorMessage("Debe Ingresar la ruta del Ícono");
//			return;
//		}
		
		if(inbNumroModulo.getText().equals("0") || inbNumroModulo.getText().isEmpty()){
			inbNumroModulo.setText("");
			inbNumroModulo.setErrorMessage("Debe ingresar el numero de módulo diferente a 0");
			return;
		}
		modulo.setUsuario(((GmSegUsuario) Sessions.getCurrent().getAttribute(
				"usuario")).getUsuUsuario());
		if(opcion==0){
			menuDao.crear(modulo);
			Messagebox.show("Módulo ingresado con éxito", "Informe",
					Messagebox.OK, Messagebox.INFORMATION,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {
							if ("onOK".equals(e.getName())) {
							}
							Events.postEvent(new Event(Events.ON_CLOSE,
									winMod));
						}
					});
		}
		else{
			menuDao.actualizar(modulo);
			Messagebox.show("Módulo actualizado con éxito", "Informe",
					Messagebox.OK, Messagebox.INFORMATION,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {
							if ("onOK".equals(e.getName())) {
							}
							Events.postEvent(new Event(Events.ON_CLOSE,
									winMod));
						}
					});
		}
	}

	public void limpiar() {
		modulo = new GmSegMenu();
		txtnombre.setText("");
		txtdescripcion.setText("");
		cmbtestado.setText("");

	}

}
