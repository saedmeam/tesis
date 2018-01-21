package net.macrosigma.seguridad.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.macrosigma.gestion.dao.GmGesPreguntaFrecuenteDao;
import net.macrosigma.gestion.dao.GmGesPreguntaUsuarioDao;
import net.macrosigma.gestion.ent.GmGesDepartamento;
import net.macrosigma.gestion.ent.GmGesPreguntaFrecuente;
import net.macrosigma.gestion.ent.GmGesPreguntasUsuario;
import net.macrosigma.parametro.dao.GmParParametroDao;
import net.macrosigma.parametro.dao.GmParPolitSeguridadDao;
import net.macrosigma.parametro.ent.GmParParametros;
import net.macrosigma.parametro.ent.GmParPolitSeguridadBean;
import net.macrosigma.seguridad.dao.GmSegRolDao;
import net.macrosigma.seguridad.dao.GmSegRolUsuarioDao;
import net.macrosigma.seguridad.dao.GmSegUsuarioDao;
import net.macrosigma.seguridad.ent.GmSegRol;
import net.macrosigma.seguridad.ent.GmSegRolUsuario;
import net.macrosigma.seguridad.ent.GmSegUsuario;
import net.macrosigma.util.controller.BaseController;
import net.macrosigma.util.ent.Encriptacion;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
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
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Center;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class IngresoUsuarioController extends BaseController {

	@Wire
	Window winNuevoUsu, window;
	@Wire
	Listbox lbxRolesAgregados, lbxAgregarRoles;
	@Wire
	Textbox txtusuario, txtClave, txtClave2, txtnombre, txtapellido,
			txtdepartamento, txtemail, txtrol, txtres;
	@Wire
	Combobox cmbtestado, cmbtnombrrol, cmbdpto, cmbdesc;
	@Wire
	Image img0, img1, img2, img3;
	@Wire
	Checkbox chkClave;
	@Wire
	Cell cellClave;
	@Wire
	Popup popRoles, nombrerol;

	int opcion = (Integer) Sessions.getCurrent().getAttribute("opcion");

	private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	GmSegUsuario usuario = new GmSegUsuario();
	GmSegUsuario usuarioModificar = new GmSegUsuario();
	GmSegUsuarioDao usuarioDao = new GmSegUsuarioDao();
	GmParPolitSeguridadDao polSegDao = new GmParPolitSeguridadDao();
	GmParPolitSeguridadBean polSeg = polSegDao.getPolSegAct();
	List<GmSegRol> listaRoles = new ArrayList<GmSegRol>();
	GmSegRolDao rolDao = new GmSegRolDao();
	List<GmSegRolUsuario> listaRolUsuarioGuardar = new ArrayList<GmSegRolUsuario>();
	List<GmSegRolUsuario> listaRolUsuarioBorrar = new ArrayList<GmSegRolUsuario>();
	GmSegRolUsuarioDao gmSegRolUsuarioDao = new GmSegRolUsuarioDao();

	List<GmParParametros> listparCarrera = new ArrayList<GmParParametros>();
	GmParParametros parCarreraSel = new GmParParametros();
	GmParParametroDao parDao = new GmParParametroDao();
	List<GmGesDepartamento> listparSol = new ArrayList<GmGesDepartamento>();
	GmGesDepartamento parSolSel = new GmGesDepartamento();

	List<GmGesPreguntaFrecuente> listpardes = new ArrayList<GmGesPreguntaFrecuente>();
	GmGesPreguntaFrecuente pregFreSel = new GmGesPreguntaFrecuente();
	GmGesPreguntaFrecuenteDao pregFreDao = new GmGesPreguntaFrecuenteDao();
	List<GmGesPreguntasUsuario> listPreguntaUsuario = new ArrayList<GmGesPreguntasUsuario>();
	List<GmGesPreguntasUsuario> listPreguntaUsuarioElim = new ArrayList<GmGesPreguntasUsuario>();
	GmGesPreguntasUsuario pregUsuSel = new GmGesPreguntasUsuario();
	GmGesPreguntasUsuario pregUsuElim = new GmGesPreguntasUsuario();
	GmGesPreguntaUsuarioDao pregUsuDao = new GmGesPreguntaUsuarioDao();

	public List<GmGesPreguntaFrecuente> getListpardes() {
		return listpardes;
	}

	public void setListpardes(List<GmGesPreguntaFrecuente> listpardes) {
		this.listpardes = listpardes;
	}

	public GmGesPreguntaFrecuente getPregFreSel() {
		return pregFreSel;
	}

	public void setPregFreSel(GmGesPreguntaFrecuente pregFreSel) {
		this.pregFreSel = pregFreSel;
	}

	public List<GmGesPreguntasUsuario> getListPreguntaUsuario() {
		return listPreguntaUsuario;
	}

	public void setListPreguntaUsuario(
			List<GmGesPreguntasUsuario> listPreguntaUsuario) {
		this.listPreguntaUsuario = listPreguntaUsuario;
	}

	public GmGesPreguntasUsuario getPregUsuSel() {
		return pregUsuSel;
	}

	public void setPregUsuSel(GmGesPreguntasUsuario pregUsuSel) {
		this.pregUsuSel = pregUsuSel;
	}

	public GmGesPreguntasUsuario getPregUsuElim() {
		return pregUsuElim;
	}

	public void setPregUsuElim(GmGesPreguntasUsuario pregUsuElim) {
		this.pregUsuElim = pregUsuElim;
	}

	public List<GmParParametros> getListparCarrera() {
		return listparCarrera;
	}

	public void setListparCarrera(List<GmParParametros> listparCarrera) {
		this.listparCarrera = listparCarrera;
	}

	public GmParParametros getParCarreraSel() {
		return parCarreraSel;
	}

	public void setParCarreraSel(GmParParametros parCarreraSel) {
		this.parCarreraSel = parCarreraSel;
	}

	public List<GmGesDepartamento> getListparSol() {
		return listparSol;
	}

	public void setListparSol(List<GmGesDepartamento> listparSol) {
		this.listparSol = listparSol;
	}

	public GmGesDepartamento getParSolSel() {
		return parSolSel;
	}

	public void setParSolSel(GmGesDepartamento parSolSel) {
		this.parSolSel = parSolSel;
	}

	public List<GmSegRolUsuario> getListaRolUsuarioGuardar() {
		return listaRolUsuarioGuardar;
	}

	public void setListaRolUsuarioGuardar(
			List<GmSegRolUsuario> listaRolUsuarioGuardar) {
		this.listaRolUsuarioGuardar = listaRolUsuarioGuardar;
	}

	@SuppressWarnings("static-access")
	public void cargarRoles() {
		listpardes = pregFreDao.getPreFreAct();
		listaRoles = rolDao.getRoles();
		listparCarrera = parDao.getParametroByDesPad("CARRERAS");
	}

	public List<GmSegRol> getListaRoles() {
		return listaRoles;
	}

	public void setListaRoles(List<GmSegRol> listaRoles) {
		this.listaRoles = listaRoles;
	}

	int ban1 = 0, ban2 = 0, ban3 = 0, ban4 = 0, ban5 = 0, ban6 = 0;
	String mensaje = "";
	String claveAnterior = "";

	public String CARACTERES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzñÑ_@";

	public GmSegUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(GmSegUsuario usuario) {
		this.usuario = usuario;
	}

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		Sessions.getCurrent().removeAttribute("opcion");
		cargarUsuarioMod();
		cargarRoles();
	}

	public void cargarUsuarioMod() {
		if (opcion == 1) {
			usuarioModificar = (GmSegUsuario) Sessions.getCurrent()
					.getAttribute("usuarioModificar");
			winNuevoUsu.setTitle("Modificar_Usuario [usu_001]");
			Sessions.getCurrent().removeAttribute("opcion");
			Sessions.getCurrent().removeAttribute("usuarioModificar");
			usuario = usuarioModificar;
			claveAnterior = usuario.getUsuClave();
			usuario.setUsuClave("");
			txtusuario.setDisabled(true);
			txtClave.setDisabled(true);
			txtClave2.setDisabled(true);
			cellClave.setVisible(true);
			listaRolUsuarioGuardar = usuarioModificar.getUsuRolUsuId();
			parCarreraSel = usuarioModificar.getUsuCarrId();
			parSolSel = usuarioModificar.getUsuDepId();
			listPreguntaUsuario = usuarioModificar.getPreUsu();
			BindUtils.postNotifyChange(null, null,
					IngresoUsuarioController.this, "usuario");
			BindUtils.postNotifyChange(null, null,
					IngresoUsuarioController.this, "listPreguntaUsuario");
		}
	}

	@Command
	public void crearUsuario() {
		usuarioDao.newManager();
		if (usuario.getUsuNombres() == null) {
			txtnombre.setErrorMessage("Por favor ingrese Nombres");
			return;
		}
		if (usuario.getUsuApellidos() == null) {
			txtapellido.setErrorMessage("Por favor ingrese Apellidos");
			return;
		}
		if (usuario.getUsuNombres() == null) {
			txtnombre.setErrorMessage("Por favor ingrese Nombres");
			return;
		}
		if (cmbdpto.getValue() == null) {
			cmbdpto.setErrorMessage("Por favor seleccione un Departamento");
			return;
		} else
			usuario.setUsuDepId(parSolSel);

		if (cmbdesc.getValue() == null) {
			cmbdesc.setErrorMessage("Por favor seleccione una Carrera");
			return;
		} else
			usuario.setUsuCarrId(parCarreraSel);
		if (usuario.getUsuEmail() == null) {
			txtemail.setErrorMessage("Por favor ingrese Email");
			return;
		}
		if (usuario.getUsuUsuario() == null) {
			txtusuario.setErrorMessage("Por favor ingrese Usuario");
			return;
		}
		if (chkClave.isChecked() || opcion == 0) {
			if (txtClave.getText().isEmpty()) {
				txtClave.setErrorMessage("Por favor ingrese Clave");
				return;
			}
			if (txtClave2.getText().isEmpty()) {
				txtClave2
						.setErrorMessage("Por favor ingrese Clave de confirmación");
				return;
			}

			if (ban1 == 1) {
				txtClave2.setErrorMessage("Las claves no coinciden.");
				return;
			}
			if (ban2 == 1) {
				txtClave.setErrorMessage("La clave debe tener minimo 8 caracters.");
				return;
			}
			if (ban3 == 1) {
				txtClave.setErrorMessage(mensaje);
				return;
			}
			if (ban4 == 1) {
				txtusuario
						.setErrorMessage("Nombre de usuario demasiado grande, el límite de caracteres asignado para usuarios es máximo "
								+ polSeg.getPolSegLonMaxUsu());
				return;
			}
			if (ban5 == 1) {
				txtusuario
						.setErrorMessage("El usuario ingresado ya existe, por favor ingrese otro.");
				return;
			}
		}
		if (ban6 == 1) {
			txtemail.setErrorMessage("La dirección de correo no es válida");
			ban6 = 1;
			return;
		}
		if (chkClave.isChecked() || opcion == 0) {
			usuario.setUsuClave(Encriptacion.toMD5(usuario.getUsuClave()));
			usuario.setUsuFechaCambioClave(new Date());
		} else {
			usuario.setUsuClave(claveAnterior);
		}
		if (listaRolUsuarioGuardar.size() == 0) {
			Messagebox.show("Debe escoger almenos un ROL", "Información",
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		} else {
			usuario.setUsuRolUsuId(listaRolUsuarioGuardar);
		}
		usuario.setUsuUsuario(usuario.getUsuUsuario().toUpperCase());
		usuario.setUsuario(((GmSegUsuario) Sessions.getCurrent().getAttribute(
				"usuario")).getUsuUsuario());

		if (opcion == 0) {
			usuarioDao.crear(usuario);
			if (listPreguntaUsuario.size() < 3) {
				Messagebox.show("Debe escoger al menos tres preguntas ",
						"Información", Messagebox.OK, Messagebox.INFORMATION);
				return;
			} else {
				for (int i = 0; i < listPreguntaUsuario.size(); i++) {
					listPreguntaUsuario.get(i).setPreUsu(usuario);
					if (listPreguntaUsuario.get(i).getInsId() > 0)
						pregUsuDao.actualizar(listPreguntaUsuario.get(i));
					else
						pregUsuDao.crear(listPreguntaUsuario.get(i));
					pregUsuDao.newManager();
				}
			}
			Messagebox.show("Usuario ingresado con éxito", "Informe",
					Messagebox.OK, Messagebox.INFORMATION,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {
							Events.postEvent(new Event(Events.ON_CLOSE,
									winNuevoUsu));
						}
					});

		} else {
			for (GmSegRolUsuario rolUsuBorrar : listaRolUsuarioBorrar) {
				// usuario.getUsuRolUsuId().add(rolUsuBorrar);
				gmSegRolUsuarioDao.newManager();
				if (rolUsuBorrar.getRolUsuId() > 0)
					gmSegRolUsuarioDao.actualizar(rolUsuBorrar);
				else
					gmSegRolUsuarioDao.crear(rolUsuBorrar);
			}
			for (int i = 0; i < listPreguntaUsuarioElim.size(); i++) {
				listPreguntaUsuarioElim.get(i).setEstado("INA");
				pregUsuDao.newManager();
				if (listPreguntaUsuarioElim.get(i).getInsId() > 0)
					pregUsuDao.actualizar(listPreguntaUsuarioElim.get(i));
				else
					pregUsuDao.crear(listPreguntaUsuario.get(i));

			}
			if (listPreguntaUsuario.size() < 3) {
				Messagebox.show("Debe escoger al menos tres preguntas ",
						"Información", Messagebox.OK, Messagebox.INFORMATION);
				return;
			} else {
				for (int i = 0; i < listPreguntaUsuario.size(); i++) {
					listPreguntaUsuario.get(i).setPreUsu(usuario);
					pregUsuDao.newManager();
					if (listPreguntaUsuario.get(i).getInsId() > 0)
						pregUsuDao.actualizar(listPreguntaUsuario.get(i));
					else
						pregUsuDao.crear(listPreguntaUsuario.get(i));

				}
			}
			usuario.setFechaModificacion(new Date());

			usuarioDao.actualizar(usuario);
			Messagebox.show("Usuario modificado con éxito", "Informe",
					Messagebox.OK, Messagebox.INFORMATION,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {
							if ("onOK".equals(e.getName())) {
								Events.postEvent(new Event(Events.ON_CLOSE,
										winNuevoUsu));
							}
						}
					});
		}
	}

	@Command
	public void validarClave() {
		if (polSeg.getPolSegPerMay().equals("S")) {
			if (polSeg.getPolSegPerMin().equals("S")) {
				if (polSeg.getPolSegPerNum().equals("S")) {
					if (polSeg.getPolSegPerSim().equals("S")) {
						mensaje = "La clave debe contener los siguientes elementos: mayúsculas, minúsculas, números y simbolos.";
					} else {
						mensaje = "La clave debe contener los siguientes elementos: mayúsculas, minúsculas y números.";
					}
				} else {
					if (polSeg.getPolSegPerSim().equals("S")) {
						mensaje = "La clave debe contener los siguientes elementos: mayúsculas, minúsculas y simbolos.";
					} else {
						mensaje = "La clave debe contener los siguientes elementos: mayúsculas y minúsculas.";
					}
				}
			} else {
				if (polSeg.getPolSegPerNum().equals("S")) {
					if (polSeg.getPolSegPerSim().equals("S")) {
						mensaje = "La clave debe contener los siguientes elementos: mayúsculas, números y simbolos.";
					} else {
						mensaje = "La clave debe contener los siguientes elementos: mayúsculas y números.";
					}
				} else {
					if (polSeg.getPolSegPerSim().equals("S")) {
						mensaje = "La clave debe contener los siguientes elementos: mayúsculas y simbolos.";
					} else {
						mensaje = "La clave debe contener al menos: mayúsculas.";
					}
				}
			}
		} else {
			if (polSeg.getPolSegPerMin().equals("S")) {
				if (polSeg.getPolSegPerNum().equals("S")) {
					if (polSeg.getPolSegPerSim().equals("S")) {
						mensaje = "La clave debe contener los siguientes elementos: minúsculas, números y simbolos.";
					} else {
						mensaje = "La clave debe contener los siguientes elementos: minúsculas y números.";
					}
				} else {
					if (polSeg.getPolSegPerSim().equals("S")) {
						mensaje = "La clave debe contener los siguientes elementos: minúsculas y simbolos.";
					} else {
						mensaje = "La clave debe contener almenos: minúsculas.";
					}
				}
			} else {
				if (polSeg.getPolSegPerNum().equals("S")) {
					if (polSeg.getPolSegPerSim().equals("S")) {
						mensaje = "La clave debe contener los siguientes elementos: números y simbolos.";
					} else {
						mensaje = "La clave debe contener almenos: números.";
					}
				} else {
					if (polSeg.getPolSegPerSim().equals("S")) {
						mensaje = "La clave debe contener al menos: simbolos.";
					} else {
						mensaje = "";
					}
				}
			}
		}

		if (!txtClave.getText().isEmpty()) {
			if (txtClave.getText().length() < polSeg.getPolSegLongMinCon()
					|| txtClave.getText().length() > polSeg
							.getPolSegLongMaxCon()) {
				txtClave.setErrorMessage("La clave debe tener mínimo "
						+ polSeg.getPolSegLongMinCon() + " caracters y maximo "
						+ polSeg.getPolSegLongMaxCon() + " caracteres.");
				ban2 = 1;
			} else {
				ban2 = 0;
			}
			if (!validarLetrasClave(txtClave.getText())) {
				txtClave.setErrorMessage(mensaje);
				ban3 = 1;
			} else {
				ban3 = 0;
			}

		}
		if (!txtClave.getText().isEmpty() && !txtClave2.getText().isEmpty()) {
			if (!txtClave.getText().equals(txtClave2.getText())) {
				txtClave2.setErrorMessage("Las claves no coinciden.");
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

	@SuppressWarnings("static-access")
	@Command
	public void validarUsuario() {
		if (!txtusuario.getText().isEmpty())
			txtusuario.setText(quitarSignos(txtusuario.getText()));
		if (!txtusuario.getText().isEmpty()) {
			if (txtusuario.getText().length() > polSeg.getPolSegLonMaxUsu()) {
				txtusuario
						.setErrorMessage("Nombre de usuario demasiado grande, el límite de caracteres asignado para usuarios es máximo "
								+ polSeg.getPolSegLonMaxUsu());
				img0.setVisible(false);
				ban4 = 1;
				return;
			} else {
				ban4 = 0;
			}
			GmSegUsuario contUsu = usuarioDao.getUsuarioPorUsuario(txtusuario
					.getText().toUpperCase());
			if (contUsu != null) {
				txtusuario
						.setErrorMessage("El usuario ingresado ya existe, por favor ingrese otro.");
				ban5 = 1;
				img0.setVisible(false);
				return;
			} else {
				ban5 = 0;
				img0.setVisible(true);
			}

		} else {
			img0.setVisible(false);
		}
	}

	public String quitarSignos(String quitar) {
		String usuFiltrado = quitar;
		String cambiar = "";
		for (int i = 0; i < quitar.length(); i++) {
			if (CARACTERES.indexOf(quitar.charAt(i), 0) == -1) {
				cambiar = String.valueOf(quitar.charAt(i));
				usuFiltrado = usuFiltrado.replace(cambiar, "");
			}
		}
		return usuFiltrado;
	}

	@Command
	public void validarCorreo() {
		if (!txtemail.getText().isEmpty()) {
			if (!validarEmail(txtemail.getText())) {
				txtemail.setErrorMessage("La dirección de correo no es valida");
				ban6 = 1;
				img3.setVisible(false);
				return;
			} else {
				ban6 = 0;
				img3.setVisible(true);
			}
		} else {
			img3.setVisible(false);
		}
	}

	public static boolean validarEmail(String email) {

		// Compiles the given regular expression into a pattern.
		Pattern pattern = Pattern.compile(PATTERN_EMAIL);

		// Match the given input against this pattern
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	@Command
	public void check() {
		if (chkClave.isChecked()) {
			txtClave.setDisabled(false);
			txtClave2.setDisabled(false);
		} else {
			txtClave.setDisabled(true);
			txtClave2.setDisabled(true);
			txtClave.setText("");
			txtClave2.setText("");
			ban1 = 0;
			ban2 = 0;
			ban3 = 0;
		}
	}

	public void limpiar() {
		usuario = new GmSegUsuario();
		txtusuario.setText("");
		txtClave.setText("");
		txtnombre.setText("");
		txtapellido.setText("");
		txtdepartamento.setText("");
		txtemail.setText("");
		cmbtestado.setText("");
		cmbtnombrrol.setText("");
	}

	@Command
	public void agregarRoles() {
		GmSegRol rol = new GmSegRol();
		GmSegRolUsuario ru = new GmSegRolUsuario();
		for (Listitem item : lbxAgregarRoles.getItems()) {
			int band = 0;
			if (item.isSelected()) {
				rol = (GmSegRol) item.getValue();
				if (listaRolUsuarioGuardar.size() > 0) {
					for (GmSegRolUsuario r : listaRolUsuarioGuardar) {
						if (rol.getRolId() == r.getGmSegRol().getRolId()
								|| rol.equals(r.getGmSegRol())) {
							band = 1;
						}
					}
				}
				if (band == 0) {
					ru = new GmSegRolUsuario();
					ru.setGmSegRol(rol);
					ru.setGmSegUsuario(usuario);
					ru.setUsuario(((GmSegUsuario) Sessions.getCurrent()
							.getAttribute("usuario")).getUsuUsuario());
					listaRolUsuarioGuardar.add(ru);
				}
			}

		}
		popRoles.close();
		lbxAgregarRoles.clearSelection();
		BindUtils.postNotifyChange(null, null, IngresoUsuarioController.this,
				"listaRolUsuarioGuardar");

	}

	@Command
	public void removerRol(@BindingParam("objeto") GmSegRolUsuario objeto) {
		listaRolUsuarioGuardar.remove(objeto);
		listaRolUsuarioBorrar.remove(objeto);
		objeto.setEstado("INA");
		listaRolUsuarioBorrar.add(objeto);
		BindUtils.postNotifyChange(null, null, IngresoUsuarioController.this,
				"listaRolUsuarioGuardar");

	}

	@Command
	public void nuevo() {
		Sessions.getCurrent().setAttribute("tip_op", "N");
		Tabbox tabs = (Tabbox) winNuevoUsu.getParent().getParent().getParent()
				.getParent().getParent().getParent();
		Tabpanels tabpanels = (Tabpanels) winNuevoUsu.getParent().getParent()
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

	@Command
	public void cargalistado() {
		listparSol = new ArrayList<>();
		if (parCarreraSel != null) {
			for (int i = 0; i < parCarreraSel.getDepCarreraId().size(); i++) {
				listparSol.add(parCarreraSel.getDepCarreraId().get(i)
						.getDepCarreraDepId());
			}

		}
		BindUtils.postNotifyChange(null, null, IngresoUsuarioController.this,
				"listparSol");
	}

	@Command
	public void ingresaPregunta() {

		if (pregFreSel == null) {
			cmbdesc.setErrorMessage("Por favor Seleccione una Pregunta");
			return;
		}
		if (pregUsuSel.getResPreg() == null) {
			txtres.setErrorMessage("Por favor ingrese Apellidos");
			return;
		}
		boolean b = false;
		for (int i = 0; i < listPreguntaUsuario.size(); i++) {
			if (listPreguntaUsuario.get(i).getPreFreUsu() == pregFreSel) {
				b = true;
				break;
			}

		}
		if (!b) {
			pregUsuSel.setPreFreUsu(pregFreSel);
			listPreguntaUsuario.add(pregUsuSel);
		} else {
			cmbdesc.setErrorMessage("No se puede Ingresar 2 veces la misma pregunta");
			return;
		}
		pregUsuSel = new GmGesPreguntasUsuario();
		pregFreSel = new GmGesPreguntaFrecuente();
		BindUtils.postNotifyChange(null, null, IngresoUsuarioController.this,
				"listPreguntaUsuario");
		BindUtils.postNotifyChange(null, null, IngresoUsuarioController.this,
				"pregUsuSel");
		BindUtils.postNotifyChange(null, null, IngresoUsuarioController.this,
				"pregFreSel");
	}

	@Command
	public void elim(@BindingParam("obj") GmGesPreguntasUsuario edif) {
		listPreguntaUsuario.remove(edif);
		listPreguntaUsuarioElim.add(edif);
		BindUtils.postNotifyChange(null, null, IngresoUsuarioController.this,
				"listPreguntaUsuario");
	}

}
