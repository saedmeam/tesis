package net.macrosigma.parametro.controller;

import net.macrosigma.parametro.dao.GmParPolitSeguridadDao;
import net.macrosigma.parametro.ent.GmParPolitSeguridadBean;
import net.macrosigma.util.controller.BaseController;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;

public class PoliticaSeguridadController extends BaseController {

	GmParPolitSeguridadDao polSegDao = new GmParPolitSeguridadDao();
	GmParPolitSeguridadBean polSegBean = new GmParPolitSeguridadBean();
	@Wire
	Checkbox chkpersim, chkpernum, chkpermin, chkpermay;

	String strPerSim, StrPerNum, StrPerMin, StrPerMay;

	public GmParPolitSeguridadBean getPolSegBean() {
		return polSegBean;
	}

	public void setPolSegBean(GmParPolitSeguridadBean polSegBean) {
		this.polSegBean = polSegBean;
	}

	@Override
	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		cargaDatos();
	}

	public void cargaDatos() {
		String cont = polSegDao.getContPolitSeg();

		if (!cont.equals("[0]")) {
			polSegBean = polSegDao.getPolSegAct();
			if (!polSegBean.getPolSegPerMay().toString().isEmpty()) {

				if (polSegBean.getPolSegPerMay().equals("S"))
					chkpermay.setChecked(true);
				if (polSegBean.getPolSegPerMin().equals("S"))
					chkpermin.setChecked(true);
				if (polSegBean.getPolSegPerNum().equals("S"))
					chkpernum.setChecked(true);
				if (polSegBean.getPolSegPerSim().equals("S"))
					chkpersim.setChecked(true);
			}
		}

	}

	@Command
	public void nuevo() {
		if (chkpermay.isChecked())
			polSegBean.setPolSegPerMay("S");
		else
			polSegBean.setPolSegPerMay("N");

		if (chkpermin.isChecked())
			polSegBean.setPolSegPerMin("S");
		else
			polSegBean.setPolSegPerMin("N");

		if (chkpernum.isChecked())
			polSegBean.setPolSegPerNum("S");
		else
			polSegBean.setPolSegPerNum("N");

		if (chkpersim.isChecked())
			polSegBean.setPolSegPerSim("S");
		else
			polSegBean.setPolSegPerSim("N");
		if (polSegBean.getPolSegId() > 0)
			polSegDao.actualizar(polSegBean);
		else
			polSegDao.crear(polSegBean);
		Messagebox.show("Política Guardada", "Informe", Messagebox.OK,
				Messagebox.ERROR, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {

					}
				});

	}
}
