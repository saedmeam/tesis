package net.macrosigma.general;

import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;

public class Validador implements Initiator{
	
	public void doInit(Page page, Map<String, Object> args) throws Exception {
		if (!page.getRequestPath().equals("/Menu.zul") && !page.getRequestPath().equals("/Pantalla_Principal.zul") && !page.getRequestPath().equals("/MenuNuevo.zul"))
			Executions.getCurrent().sendRedirect("/Pantalla_Principal.zul");
//		if (!page.getRequestPath().equals("/MenuNuevo.zul"))
//			Executions.getCurrent().sendRedirect("/MenuNuevo.zul");
	}
}
