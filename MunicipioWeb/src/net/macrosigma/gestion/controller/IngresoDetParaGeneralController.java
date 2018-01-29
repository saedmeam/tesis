package net.macrosigma.gestion.controller;

import net.macrosigma.parametro.dao.GmParParametroDao;
import net.macrosigma.parametro.ent.GmParParametros;
import net.macrosigma.util.controller.BaseController;

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

public class IngresoDetParaGeneralController extends BaseController {

	@Wire
	Textbox txtnom, txtdes;
	@Wire
	Window winpar;

	@Wire
	Combobox cmbtestado;
	String tipop;

	GmParParametros para = new GmParParametros();
	GmParParametroDao paraDao = new GmParParametroDao();

	public GmParParametros getPara() {
		return para;
	}

	public void setPara(GmParParametros para) {
		this.para = para;
	}

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		tipop = ((String) Sessions.getCurrent().getAttribute("tip_op"));
		if (!tipop.equals("N"))
			para = ((GmParParametros) Sessions.getCurrent().getAttribute(
					"pargen"));
	}

	@Command
	public void createUsuario() {
		paraDao.newManager();
		if (txtnom.getText().isEmpty()) {
			txtnom.setErrorMessage("campo obligatorio");
			return;
		}

		if (txtdes.getText().isEmpty()) {
			txtdes.setErrorMessage("campo obligatorio");
			return;

		}

		if (cmbtestado.getText().isEmpty()) {
			cmbtestado.setErrorMessage("campo obligatorio");
			return;

		}

		para.setParValor(txtnom.getText().toUpperCase());
		para.setParDes(txtdes.getText().toUpperCase());
		para.setEstado(cmbtestado.getSelectedItem().getValue().toString());
		if (tipop.equals("M")) {
			paraDao.actualizar(para);
			Messagebox.show("Parámetro Modificado", "Informe", Messagebox.OK,
					Messagebox.INFORMATION, new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {
							Events.postEvent(new Event(Events.ON_CLOSE, winpar));
						}
					});

		} else {
			if (!paraDao.valParametroByDes(para.getParDes())) {
				paraDao.crear(para);
				Messagebox.show("Parámetro Ingresado", "Informe",
						Messagebox.OK, Messagebox.INFORMATION,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event e) throws Exception {
								Events.postEvent(new Event(Events.ON_CLOSE,
										winpar));
							}
						});
			}
			else{
				Messagebox.show("Este parámetro ya ha sido ingresado", "Informe",
						Messagebox.OK, Messagebox.INFORMATION,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event e) throws Exception {
								
							}
						});
			}
		}
		limpiar();

	}

	public void limpiar() {
		GmParParametros para1 = para;
		para = new GmParParametros();
		para.setCarIdPad(para1.getCarIdPad());
		// para = new GmGesParametros();
		txtnom.setText("");
		txtdes.setText("");
		txtdes.setFocus(true);
		// cmbtestado.setText("");
	}

}