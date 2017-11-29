package net.macrosigma.parametro.controller;

import java.util.Date;

import net.macrosigma.parametro.dao.GmParPolitSeguridadDao;
import net.macrosigma.parametro.ent.GmParPolitSeguridadBean;
import net.macrosigma.seguridad.dao.GmSegUsuarioDao;
import net.macrosigma.seguridad.ent.GmSegUsuario;
import net.macrosigma.util.controller.BaseController;
import net.macrosigma.util.ent.Encriptacion;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class CambiarClaveController extends BaseController {

	GmSegUsuarioDao usuarioDao = new GmSegUsuarioDao();
	GmParPolitSeguridadDao polSegDao = new GmParPolitSeguridadDao();
	GmParPolitSeguridadBean polSeg = polSegDao.getPolSegAct();

	public String CARACTERES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzñÑ";
	String mensaje = "";

	@Wire
	Window winCambioClave;
	@Wire
	Textbox claveActual, nuevaClave, confirmarClave;
	@Wire
	Button btnAceptar;
	@Wire
	Image img0, img1, img2;

	int ban1 = 0, ban2 = 0, ban3 = 0, ban4 = 0, ban5 = 0;
	String codigo;
	GmSegUsuario usuario = (GmSegUsuario) Sessions.getCurrent().getAttribute(
			"usuario");

	public GmSegUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(GmSegUsuario usuario) {
		this.usuario = usuario;
	}

	public String getGeneraClaves(String key, int length) {
		String pswd = "";
		for (int i = 0; i < length; i++) {
			pswd += (key.charAt((int) (Math.random() * key.length())));
		}
		return pswd;
	}

	@Command
	public void doCambiar() {
		if (claveActual.getText().isEmpty()) {
			claveActual.setErrorMessage("Debe ingresar su clave actual.");
			return;
		}
		if (nuevaClave.getText().isEmpty()) {
			nuevaClave.setErrorMessage("Debe ingresar una nueva clave.");
			return;
		}
		if (confirmarClave.getText().isEmpty()) {
			confirmarClave
					.setErrorMessage("Debe ingresar la confirmación de clave.");
			return;
		}
		if (ban1 == 1) {
			confirmarClave.setErrorMessage("Las claves no coinciden.");
			return;
		}
		if (ban2 == 1) {
			nuevaClave
					.setErrorMessage("La clave debe tener minimo 8 caracters.");
			return;
		}
		if (ban3 == 1) {
			nuevaClave
					.setErrorMessage(mensaje);
			return;
		}
		if (ban4 == 1) {
			nuevaClave
					.setErrorMessage("La nueva clave debe ser diferente a la actual.");
			return;
		}
		if (ban5 == 1) {
			claveActual.setErrorMessage("La clave ingresada no es correcta.");
			return;
		}
		usuario.setUsuClave(Encriptacion.toMD5(nuevaClave.getText()));
		usuario.setUsuFechaCambioClave(new Date());
		usuarioDao.actualizar(usuario);
		Sessions.getCurrent().removeAttribute("usuario");
		Sessions.getCurrent().setAttribute("usuario", usuario);
		Messagebox.show("su clave fue cambiada exitosamente", "Informe",
				Messagebox.OK, Messagebox.INFORMATION,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						if ("onOK".equals(e.getName())) {
						}
						Events.postEvent(new Event(Events.ON_CLOSE,
								winCambioClave));
					}
				});
	}

	@Override
	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);

	}

	@Command
	public void validarClave() {
		if (polSeg.getPolSegPerMay().equals("S")) {
			if (polSeg.getPolSegPerMin().equals("S")) {
				if (polSeg.getPolSegPerNum().equals("S")) {
					if (polSeg.getPolSegPerSim().equals("S")) {
						mensaje = "La clave debe contener los siguientes elementos: mayusculas, minusculas, números y símbolos.";
					} else {
						mensaje = "La clave debe contener los siguientes elementos: mayusculas, minusculas y números.";
					}
				} else {
					if (polSeg.getPolSegPerSim().equals("S")) {
						mensaje = "La clave debe contener los siguientes elementos: mayusculas, minusculas y símbolos.";
					} else {
						mensaje = "La clave debe contener los siguientes elementos: mayusculas y minusculas.";
					}
				}
			} else {
				if (polSeg.getPolSegPerNum().equals("S")) {
					if (polSeg.getPolSegPerSim().equals("S")) {
						mensaje = "La clave debe contener los siguientes elementos: mayusculas, números y símbolos.";
					} else {
						mensaje = "La clave debe contener los siguientes elementos: mayusculas y números.";
					}
				} else {
					if (polSeg.getPolSegPerSim().equals("S")) {
						mensaje = "La clave debe contener los siguientes elementos: mayusculas y símbolos.";
					} else {
						mensaje = "La clave debe contener al menos: mayusculas.";
					}
				}
			}
		}
		else{
			if (polSeg.getPolSegPerMin().equals("S")) {
				if (polSeg.getPolSegPerNum().equals("S")) {
					if (polSeg.getPolSegPerSim().equals("S")) {
						mensaje = "La clave debe contener los siguientes elementos: minusculas, números y símbolos.";
					} else {
						mensaje = "La clave debe contener los siguientes elementos: minusculas y números.";
					}
				} else {
					if (polSeg.getPolSegPerSim().equals("S")) {
						mensaje = "La clave debe contener los siguientes elementos: minusculas y símbolos.";
					} else {
						mensaje = "La clave debe contener almenos: minusculas.";
					}
				}
			} else {
				if (polSeg.getPolSegPerNum().equals("S")) {
					if (polSeg.getPolSegPerSim().equals("S")) {
						mensaje = "La clave debe contener los siguientes elementos: números y símbolos.";
					} else {
						mensaje = "La clave debe contener almenos: números.";
					}
				} else {
					if (polSeg.getPolSegPerSim().equals("S")) {
						mensaje = "La clave debe contener al menos: símbolos.";
					} else {
						mensaje = "";
					}
				}
			}
		}

		if (!nuevaClave.getText().isEmpty()) {
			if (nuevaClave.getText().length() < polSeg.getPolSegLongMinCon()
					|| nuevaClave.getText().length() > polSeg
							.getPolSegLongMaxCon()) {
				nuevaClave.setErrorMessage("La clave debe tener minimo "
						+ polSeg.getPolSegLongMinCon() + " caracters y maximo "
						+ polSeg.getPolSegLongMaxCon() + " caracteres.");
				ban2 = 1;
			} else {
				ban2 = 0;
			}
			if (!validarLetrasClave(nuevaClave.getText())) {
				nuevaClave.setErrorMessage(mensaje);
				ban3 = 1;
			} else {
				ban3 = 0;
			}
			if (!claveActual.getText().isEmpty()
					&& !nuevaClave.getText().isEmpty() && ban3 == 0) {
				if (usuario.getUsuClave().equals(
						Encriptacion.toMD5(nuevaClave.getText()))) {
					nuevaClave
							.setErrorMessage("La nueva clave debe ser diferente a la actual.");
					ban4 = 1;
				}
				else{
					ban4 = 0;
				}
			}

		}
		if (!nuevaClave.getText().isEmpty()
				&& !confirmarClave.getText().isEmpty()) {
			if (!nuevaClave.getText().equals(confirmarClave.getText())) {
				confirmarClave.setErrorMessage("Las claves no coinciden.");
				ban1 = 1;
				img1.setVisible(false);
				img2.setVisible(false);
			} else {
				ban1 = 0;
				img1.setVisible(true);
				img2.setVisible(true);
			}
		} else {
			img1.setVisible(false);
			img2.setVisible(false);
		}

	}

	public Boolean validarLetrasClave(String clave) {
		int cmay = 0, cmin = 0, cnum = 0, cces = 0, cgen = 0, csel = 0;
		String lMay = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ", lMin = "abcdefghijklmnñopqrstuvwxyz", num = "0123456789";

		for (int i = 0; i < clave.length(); i++) {

			if (lMay.indexOf(clave.charAt(i), 0) != -1) {
				cmay++;
			} else {
				if (lMin.indexOf(clave.charAt(i), 0) != -1) {
					cmin++;
				} else {
					if (num.indexOf(clave.charAt(i), 0) != -1) {
						cnum++;
					} else {
						cces++;
					}
				}
			}
		}
		if (cmay > 0 && polSeg.getPolSegPerMay().equals("S"))
			cgen++;
		if (cmin > 0 && polSeg.getPolSegPerMin().equals("S"))
			cgen++;
		if (cnum > 0 && polSeg.getPolSegPerNum().equals("S"))
			cgen++;
		if (cces > 0 && polSeg.getPolSegPerSim().equals("S"))
			cgen++;

		if (polSeg.getPolSegPerMay().equals("S"))
			csel++;
		if (polSeg.getPolSegPerMin().equals("S"))
			csel++;
		if (polSeg.getPolSegPerNum().equals("S"))
			csel++;
		if (polSeg.getPolSegPerSim().equals("S"))
			csel++;

		if (cgen >= csel)
			return true;
		else
			return false;

	}

	@Command
	public void doRedirecionar() {
		Executions.sendRedirect("/");
	}

	@Command
	public void claveActual() {
		if (!claveActual.getText().isEmpty()) {
			if (usuario.getUsuClave().equals(
					Encriptacion.toMD5(claveActual.getText()))) {
				img0.setVisible(true);
				ban5 = 0;
			} else {
				claveActual
						.setErrorMessage("La clave ingresada no es correcta");
				ban5 = 5;
				img0.setVisible(false);
			}
		} else {
			img0.setVisible(false);
		}
	}
}
