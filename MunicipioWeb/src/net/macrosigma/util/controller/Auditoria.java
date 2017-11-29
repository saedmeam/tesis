package net.macrosigma.util.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.zkoss.zk.ui.Executions;

public class Auditoria {

	private static String ipequipo;
	private static String nombremaquina;
		@Deprecated
		public Auditoria() {
			ipequipo = Executions.getCurrent().getDesktop().getSession().getRemoteAddr();
			try {
				nombremaquina = nombrePC(ipequipo);
			} catch (UnknownHostException e) {
				nombremaquina = "Error al resolver nombre";
				e.printStackTrace();
			}
		}

		@SuppressWarnings("deprecation")
		public static void gotAudit() {
			ipequipo = Executions.getCurrent().getDesktop().getSession().getRemoteAddr();
			try {
				nombremaquina = nombrePC(ipequipo);
			} catch (UnknownHostException e) {
				nombremaquina = "Error al resolver nombre";
				e.printStackTrace();
			}
		}
		private static String nombrePC(String ip) throws UnknownHostException {
			String ipoct[] = ip.split("\\.");
			byte dirip[] = new byte[4];
			dirip[0] = new Integer(ipoct[0]).byteValue();
			dirip[1] = new Integer(ipoct[1]).byteValue();
			dirip[2] = new Integer(ipoct[2]).byteValue();
			dirip[3] = new Integer(ipoct[3]).byteValue();
			InetAddress direquip = InetAddress.getByAddress(dirip);
			return direquip.getHostName();
		}

		public static String getIpequipo() {
			return ipequipo;
		}

		public static void setIpequipo(String ipequipo) {
			Auditoria.ipequipo = ipequipo;
		}

		public static String getNombremaquina() {
			return nombremaquina;
		}

		public static void setNombremaquina(String nombremaquina) {
			Auditoria.nombremaquina = nombremaquina;
		}

		
}
