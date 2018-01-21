package net.macrosigma.util.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import net.macrosigma.seguridad.dao.GmSegMenuDao;
import net.macrosigma.seguridad.ent.GmSegMenu;
import net.macrosigma.seguridad.ent.GmSegUsuario;

import org.zkoss.bind.annotation.AfterCompose;
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
import org.zkoss.zkmax.zul.Navbar;
import org.zkoss.zkmax.zul.Navitem;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Center;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

public class MenuNuevoController extends BaseController {

	GmSegMenu idmenu = new GmSegMenu();
	Window window;
	@Wire
	Tabbox tabs;
	@Wire
	Vlayout vlayout;
	@Wire
	Tabpanels tabpanels;

	@Wire
	Navbar navbar;
	@Wire
	Label lbNombreModulo;
	@Wire("#incCont")
	private Include incCont;

	GmSegMenuDao menuDao = new GmSegMenuDao();
	List<GmSegMenu> listaModulos = new ArrayList<GmSegMenu>();
	List<GmSegMenu> listaMenu = new ArrayList<GmSegMenu>();
	GmSegUsuario usu = (GmSegUsuario) Sessions.getCurrent().getAttribute(
			"usuario");
	long diasRestantes = (Long) Sessions.getCurrent().getAttribute(
			"diasRestantes");
	long diasMensaje = (Long) Sessions.getCurrent().getAttribute("diasMensaje");

	String fecha = "";

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		menuDao.newManager();
		idmenu = (GmSegMenu) Sessions.getCurrent().getAttribute("IdMenu");

		SimpleDateFormat formateador = new SimpleDateFormat(
				"EEEEEEEEE dd 'de' MMMM 'de' yyyy", new Locale("es"));

		Date fechaDate = new Date();
		fecha = formateador.format(fechaDate);
		fecha = fecha.toUpperCase();
		// llenarNavbar(idmenu);
		// cargarNombreMenu();
		// llenarNavbar(idmenu);
		// cargarNombreMenu();
		getModulos();
		cargaMensaje();

		// List<GmParInteres> lint = new ArrayList<>();
		// GmParInteresDao intDao = new GmParInteresDao();
		// lint = intDao.getPaqAlertaEnt();
		// String mensaje = "";
		// if (lint != null) {
		// for (int i = 0; i < lint.size(); i++) {
		// Date fecprueba = new Date();
		// fecprueba = sumarRestarDiasFecha(lint.get(i).getFechaIngreso(),
		// lint.get(i).getClaCatIdTipEnv().getRubAanio());
		// if (fecprueba.compareTo(new Date()) <= 0) {
		// mensaje += "Código del paquete"
		// + lint.get(i).getClaCatCodPaq();
		// }
		// if (!mensaje.equals(""))
		// if (i + 1 < lint.size())
		// mensaje += ", ";
		// else
		// mensaje += ".";
		// }
		// if (!mensaje.equals(""))
		// Messagebox
		// .show("Los siguentes paquetes no se ha recibido respuesta si entregados o no:"
		// + mensaje);
		// }
	}

	public Date sumarRestarDiasFecha(Date fecha, long dias) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.DAY_OF_YEAR, Integer.parseInt(dias + ""));
		return calendar.getTime();
	}

	public void cargaMensaje() {
		if (diasRestantes >= 0 && diasRestantes <= diasMensaje) {
			if (diasRestantes == 1) {
				Messagebox
						.show("Por seguridad se recomienda cambiar su clave de acceso al sistema, Le queda "
								+ diasRestantes
								+ " dia para cambiar su clave, ¡ recuerde que solo hasta hoy puede cambiar su clave, si no la cambia se bloqueará !",
								"Informe", Messagebox.OK,
								Messagebox.INFORMATION);
			} else {
				Messagebox
						.show("Por seguridad se recomienda cambiar su clave de acceso al sistema, Le quedan "
								+ diasRestantes + " dias para cambiar su clave",
								"Informe", Messagebox.OK,
								Messagebox.INFORMATION);
			}
		}
		Sessions.getCurrent().setAttribute("diasRestantes", -1L);
		Sessions.getCurrent().setAttribute("diasMensaje", 10L);
	}

	public GmSegUsuario getUsu() {
		return usu;
	}

	public void setUsu(GmSegUsuario usu) {
		this.usu = usu;
	}

	@SuppressWarnings("static-access")
	public void llenarNavbar(GmSegMenu Id) {
		listaMenu = menuDao.getMenuPorModulo(usu, Id);
		if (listaMenu == null)
			return;
		for (final GmSegMenu padre : listaMenu) {
			Navitem item = new Navitem();
			item.setLabel(padre.getMenNombre());
			item.setImage(padre.getMenIcono());
			item.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					incCont.setSrc(padre.getMenRuta());
				}
			});
			navbar.appendChild(item);
		}

	}

	@Command
	public void doLogoff() {
		Sessions.getCurrent().invalidate();
		Executions.sendRedirect("/Login.zul");
	}

	public void cargarNombreMenu() {
		lbNombreModulo.setValue("MODULO " + idmenu.getMenDescripcionMenu());
	}

	@Command
	public void cambioClave() {
		if (window == null) {
			window = (Window) Executions.createComponents(
					"/parametro/par_026.zul", null, null);
			window.doModal();
			window.addEventListener(Events.ON_CLOSE,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							window = null;
						}
					});
		}
	}

	protected void addTab(GmSegMenu opcion) {
		Borderlayout bl = new Borderlayout();
		if (tabs.hasFellow("tab:" + String.valueOf(opcion.getMenId()))) {
			Tab tab2 = (Tab) tabs.getFellow("tab:"
					+ String.valueOf(opcion.getMenId()));
			tab2.focus();
			tab2.setSelected(true);
			return;
		}
		Tab tab = new Tab(opcion.getMenNombre());
		tab.setClosable(true);
		tab.setSelected(true);
		tab.setId("tab:" + String.valueOf(opcion.getMenId()));
		tabs.getTabs().appendChild(tab);
		Tabpanel tabpanel = new Tabpanel();
		tabpanels.appendChild(tabpanel);
		Include include = new Include(opcion.getMenRuta());
		Center c = new Center();
		c.setAutoscroll(true);
		c.appendChild(include);
		bl.appendChild(c);
		tabpanel.appendChild(bl);

	}

	@SuppressWarnings("static-access")
	public void getModulos() {
		listaModulos = menuDao.getMenuPorUsuario(usu);
		if (listaModulos != null) {
			if (!listaModulos.isEmpty()) {
				for (GmSegMenu modulo : listaModulos) {
					Groupbox gb = new Groupbox();
					gb.setId("gb" + modulo.getMenId());
					gb.setMold("3d");
					gb.setOpen(false);
					Caption caption = new Caption(modulo.getMenNombre());
					caption.setStyle("width:100% !important");
					caption.setImage("/img/icon_1.png");
					gb.appendChild(caption);
					vlayout.appendChild(gb);
					Vlayout vl = new Vlayout();
					vl.setSpacing("0px");
					vlayout.setWidth("100%");
					gb.appendChild(vl);
					Image div = new Image();
					div.setId("arrow" + modulo.getMenId());
					div.setSrc("img/flecha_arriba1.png");
					div.setWidth("14px");
					div.setHeight("12px");
					div.setStyle("float:right");
					caption.appendChild(div);
					gb.addEventListener(Events.ON_CLICK,
							new EventListener<Event>() {
								@Override
								public void onEvent(Event event)
										throws Exception {
									Groupbox gb = (Groupbox) event.getTarget();
									for (Component comp : vlayout.getChildren()) {
										if (comp instanceof Groupbox) {
											Groupbox gba = (Groupbox) comp;
											if (!gba.getId().equals(gb.getId())) {
												for (Component comp1 : gba
														.getChildren()) {
													if (comp instanceof Caption) {
														for (Component comp2 : comp
																.getChildren()) {
															if (comp2 instanceof Image) {
																Image div = (Image) comp1;
																div.setStyle("float:right");
																div.setSrc("img/flecha_arriba1.png");
															}
														}
													}
												}
											}
										}
									}
									if (gb.isOpen()) {
										for (Component comp : gb.getChildren()) {
											if (comp instanceof Caption) {
												for (Component comp1 : comp
														.getChildren()) {
													if (comp1 instanceof Image) {
														Image div = (Image) comp1;
														div.setSrc("img/flecha_abajo1.png");
														div.setStyle("float:right");
													}
												}
											}
										}
									} else {
										gb.setSclass("menu-grupo-open-false");
										for (Component comp : gb.getChildren()) {
											if (comp instanceof Caption) {
												for (Component comp1 : comp
														.getChildren()) {
													if (comp1 instanceof Image) {
														Image div = (Image) comp1;
														div.setSrc("img/flecha_arriba1.png");
														div.setStyle("float:right");
													}
												}
											}
										}
									}
								}

							});
					listaMenu = menuDao.getMenuPorModulo(usu, modulo);
					if (listaMenu.size() > 0) {
						for (GmSegMenu op : listaMenu) {
							if (op.getPadreMenu().getMenId() != 0) {
								Button button = new Button();
								button.setAttribute("opcion", op);
								button.setWidth("290px");
								button.setImage("/img/circle_orange.png");
								button.setLabel(op.getMenNombre());
								button.setSclass("boton-menu");
								button.addEventListener(Events.ON_CLICK,
										new EventListener<Event>() {
											@Override
											public void onEvent(Event event)
													throws Exception {
												Button button = (Button) event
														.getTarget();
												GmSegMenu opcion = (GmSegMenu) button
														.getAttribute("opcion");
												incCont.setSrc(opcion
														.getMenRuta());
											}
										});
								vl.appendChild(button);
							}

						}
					}

				}
			}
		}
	}

}
