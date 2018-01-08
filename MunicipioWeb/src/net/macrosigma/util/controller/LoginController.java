package net.macrosigma.util.controller;

import java.util.Date;

import net.macrosigma.parametro.dao.GmParPolitSeguridadDao;
import net.macrosigma.parametro.ent.GmParPolitSeguridadBean;
import net.macrosigma.seguridad.dao.GmSegUsuarioDao;
import net.macrosigma.seguridad.ent.GmSegUsuario;
import net.macrosigma.util.dao.GmHistorialAccesoDao;
import net.macrosigma.util.ent.Encriptacion;
import net.macrosigma.util.ent.GmHistorialAcceso;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class LoginController extends BaseController {

	GmSegUsuarioDao usuarioDao = new GmSegUsuarioDao();
	GmSegUsuario usuario = new GmSegUsuario();
	GmParPolitSeguridadDao polSegDao = new GmParPolitSeguridadDao();
	GmParPolitSeguridadBean polSeg = polSegDao.getPolSegAct();

	GmHistorialAcceso historialAcceso = new GmHistorialAcceso();
	GmHistorialAccesoDao historialAccesoDao = new GmHistorialAccesoDao();

	@SuppressWarnings("deprecation")
	Auditoria aud = new Auditoria();

	Date hoy = new Date();
	long diasRestantes;
	Window window;

	@Wire
	Textbox usu, pass;

	@SuppressWarnings("static-access")
	@Command
	public void doLogin(@BindingParam("objeto") int log) {

		if (usu.getText().isEmpty()) {
			Messagebox.show("Por favor Ingrese un usuario ", "Aviso",
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}

		if (pass.getText().isEmpty()) {
			Messagebox.show("Por favor Ingrese su clave ", "Aviso",
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		usuario = usuarioDao.getUsuarioPorUsuario(usu.getText().toUpperCase()
				.trim());

		if (usuario == null) {
			Messagebox.show("El usuario ingresado no existe", "Aviso",
					Messagebox.OK, Messagebox.EXCLAMATION);
			historialAcceso = new GmHistorialAcceso();
			historialAcceso.setUsuario("");
			historialAcceso.setHisAccTipoAcceso("Fallido");
			historialAcceso.setHisAccDetalle("Usuario ingresado no existe");
			historialAcceso.setHisAccIp(aud.getIpequipo());
			historialAcceso.setHisAccNombreMaquina(aud.getNombremaquina());
			historialAccesoDao.crear(historialAcceso);
			return;
		}
		if (usuario.getEstado().equals("INA")) {
			Messagebox
					.show("El usuario ingresado se encuentra inactivo, por favor contáctese con el administrador del sistema",
							"Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			historialAcceso = new GmHistorialAcceso();
			historialAcceso.setUsuario(usuario.getUsuUsuario());
			historialAcceso.setHisAccTipoAcceso("Fallido");
			historialAcceso.setHisAccDetalle("Usuario Inactivo");
			historialAcceso.setHisAccIp(aud.getIpequipo());
			historialAcceso.setHisAccNombreMaquina(aud.getNombremaquina());
			historialAccesoDao.crear(historialAcceso);
			return;
		}
		if (usuario.getEstado().equals("BLO")) {
			Messagebox
					.show("Su usuario se encuentra bloqueado, por favor contáctese con el administrador del sistema",
							"Atención", Messagebox.OK, Messagebox.EXCLAMATION);
			historialAcceso = new GmHistorialAcceso();
			historialAcceso.setUsuario(usuario.getUsuUsuario());
			historialAcceso.setHisAccTipoAcceso("Fallido");
			historialAcceso.setHisAccDetalle("Usuario bloqueado");
			historialAcceso.setHisAccIp(aud.getIpequipo());
			historialAcceso.setHisAccNombreMaquina(aud.getNombremaquina());
			historialAccesoDao.crear(historialAcceso);
			return;
		}

		String clave = Encriptacion.toMD5(pass.getText());

		if (clave.equals(usuario.getUsuClave())) {
			usuario.setUsuIntentosFallidos(0);
			usuarioDao.actualizar(usuario);
			diasRestantes = (hoy.getTime() - usuario.getUsuFechaCambioClave()
					.getTime()) / (86400000);

			if (diasRestantes < polSeg.getPolSegVigCon()) {
				Sessions.getCurrent().setAttribute("diasRestantes",
						polSeg.getPolSegVigCon() - diasRestantes);
				Sessions.getCurrent().setAttribute("diasMensaje",
						polSeg.getPolSegDiasRecCamCon());
			} else {
				Messagebox
						.show("Su usuario se encuentra bloqueado por que su contraseña caducó, por favor actualice su contraseña para poder ingresar al sistema",
								"Atención", Messagebox.OK,
								Messagebox.EXCLAMATION);
				Sessions.getCurrent().setAttribute("usuario", usuario);
				cambioClave();
				historialAcceso = new GmHistorialAcceso();
				historialAcceso.setUsuario(usuario.getUsuUsuario());
				historialAcceso.setHisAccTipoAcceso("Fallido");
				historialAcceso
						.setHisAccDetalle("Clave caducada, redireccion a cambio de Clave");
				historialAcceso.setHisAccIp(aud.getIpequipo());
				historialAcceso.setHisAccNombreMaquina(aud.getNombremaquina());
				historialAccesoDao.crear(historialAcceso);
				return;
			}
			if (log == 1) {
				Executions.sendRedirect("/Pantalla_Principal.zul");
			} else {
				Executions.sendRedirect("/MenuNuevo.zul");
			}
			Sessions.getCurrent().setAttribute("usuario", usuario);
			historialAcceso = new GmHistorialAcceso();
			historialAcceso.setUsuario(usuario.getUsuUsuario());
			historialAcceso.setHisAccTipoAcceso("Exitoso");
			historialAcceso.setHisAccDetalle("Acceso Exitoso al sistema");
			historialAcceso.setHisAccIp(aud.getIpequipo());
			historialAcceso.setHisAccNombreMaquina(aud.getNombremaquina());
			historialAccesoDao.crear(historialAcceso);
		} else {
			usuario.setUsuIntentosFallidos(usuario.getUsuIntentosFallidos() + 1);
			usuarioDao.actualizar(usuario);

			if (usuario.getUsuIntentosFallidos() >= polSeg
					.getPolSegNumIntFalliCla()) {
				usuario.setEstado("BLO");
				usuario.setUsuIntentosFallidos(0);
				usuarioDao.actualizar(usuario);
				Messagebox
						.show("Su usuario se encuentra bloqueado por que ha excedido el número de intentos fallidos permitidos, por favor contáctese con el administrador del sistema",
								"Atención", Messagebox.OK,
								Messagebox.EXCLAMATION);
				historialAcceso = new GmHistorialAcceso();
				historialAcceso.setUsuario(usuario.getUsuUsuario());
				historialAcceso.setHisAccTipoAcceso("Fallido");
				historialAcceso
						.setHisAccDetalle("Bloque por limite de intentos fallidos");
				historialAcceso.setHisAccIp(aud.getIpequipo());
				historialAcceso.setHisAccNombreMaquina(aud.getNombremaquina());
				historialAccesoDao.crear(historialAcceso);
				return;
			}
			Messagebox.show(
					"La contraseña ingresada no es correcta le quedan "
							+ (polSeg.getPolSegNumIntFalliCla() - usuario
									.getUsuIntentosFallidos()) + " de "
							+ polSeg.getPolSegNumIntFalliCla() + " intentos.",
					"Aviso", Messagebox.OK, Messagebox.ERROR);
			historialAcceso = new GmHistorialAcceso();
			historialAcceso.setUsuario(usuario.getUsuUsuario());
			historialAcceso.setHisAccTipoAcceso("Fallido");
			historialAcceso
					.setHisAccDetalle("Clave incorrecta conteo de intentos fallidos");
			historialAcceso.setHisAccIp(aud.getIpequipo());
			historialAcceso.setHisAccNombreMaquina(aud.getNombremaquina());
			historialAccesoDao.crear(historialAcceso);
			return;
		}
	}

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	@Command
	public void crearUsuario() {
		if (window == null) {

			window = (Window) Executions.createComponents(
					"/seguridad/usuario/usu_003.zul", null, null);
			window.setClosable(true);
			window.doModal();
			window.addEventListener(Events.ON_CLOSE,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							if ("onOK".equals(arg0.getName())) {
								Events.postEvent(new Event(Events.ON_CLOSE,
										window));
							}
							window = null;
						}
					});
		}
	}

	@Command
	public void recuperar() {
		if (window == null) {

			window = (Window) Executions.createComponents(
					"/seguridad/usuario/usu_004.zul", null, null);
			window.setClosable(true);
			window.doModal();
			window.addEventListener(Events.ON_CLOSE,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							if ("onOK".equals(arg0.getName())) {
								Events.postEvent(new Event(Events.ON_CLOSE,
										window));
							}
							window = null;
						}
					});
		}
	}

	@Command
	public void requisitos() {
		if (window == null) {

			window = (Window) Executions.createComponents(
					"/parametro/par_001.zul", null, null);
			window.setClosable(true);
			window.doModal();
			window.addEventListener(Events.ON_CLOSE,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							if ("onOK".equals(arg0.getName())) {
								Events.postEvent(new Event(Events.ON_CLOSE,
										window));
							}
							window = null;

						}
					});
		}
	}

	public void cambioClave() {
		if (window == null) {
			window = (Window) Executions.createComponents(
					"/parametro/par_026.zul", null, null);
			window.doModal();
			window.addEventListener(Events.ON_CLOSE,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							if ("onOK".equals(arg0.getName())) {
								Events.postEvent(new Event(Events.ON_CLOSE,
										window));
							}
							window = null;
							Sessions.getCurrent().invalidate();
							Executions.sendRedirect("/Login.zul");

						}
					});
		}
	}
}
