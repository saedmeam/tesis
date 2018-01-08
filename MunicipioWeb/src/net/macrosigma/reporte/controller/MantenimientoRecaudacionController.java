package net.macrosigma.reporte.controller;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.macrosigma.db.Conexion;
import net.macrosigma.gestion.dao.GmGesPreguntaFrecuenteDao;
import net.macrosigma.gestion.ent.GmGesPreguntaFrecuente;
import net.macrosigma.util.controller.BaseController;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Iframe;

public class MantenimientoRecaudacionController extends BaseController {

	@Wire("#iframerep")
	Iframe iframerep;

	String path = Sessions.getCurrent().getWebApp().getRealPath("/report");

	@Command
	public void imprimir() {
		ByteArrayOutputStream baos = getReport();

		AMedia amedia = new AMedia("preguntas_frecuentes", "pdf", null,
				baos.toByteArray());
		iframerep.setContent(amedia);

		getReportExcel();
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	public ByteArrayOutputStream getReport() throws NumberFormatException {

		ByteArrayOutputStream printStream = null;

		Map<String, Object> paramRpt = new HashMap<String, Object>();
		paramRpt.put("id", 1);
		Connection cn = new Conexion().getConexion();
		try {

			JasperPrint jprint = JasperFillManager.fillReport(path
					+ "/preguntas_frecuentes.jasper", paramRpt, cn);
			printStream = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jprint, printStream);
		} catch (Exception e) {
			System.err.println("Error:No fue posible elaborar el reporte :"
					+ e.getMessage());
		} finally {
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return printStream;
	}

	@Override
	public void init(Component view) {
		Selectors.wireComponents(view, this, false);
	}

	public void getReportExcel() {
		org.apache.poi.ss.usermodel.Workbook workbook = new HSSFWorkbook();
		Sheet listSheet = workbook.createSheet("preguntas");
		List<GmGesPreguntaFrecuente> lprefre = new ArrayList<>();
		lprefre = new GmGesPreguntaFrecuenteDao().getPreFreAct();
		int rowIndex = 0;
		for (GmGesPreguntaFrecuente kp : lprefre) {
			Row row = listSheet.createRow(rowIndex++);
			int cellIndex = 0;
			row.createCell(cellIndex++).setCellValue(kp.getInsId());
			row.createCell(cellIndex++).setCellValue(kp.getDesPregunta());
		}

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			workbook.write(baos);
			AMedia amedia = new AMedia("preguntas_frecuentes.xls", "xls",
					"application/file", baos.toByteArray());
			Filedownload.save(amedia);
			baos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
