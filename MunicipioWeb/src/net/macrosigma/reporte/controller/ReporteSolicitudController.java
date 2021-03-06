package net.macrosigma.reporte.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.macrosigma.db.Conexion;
import net.macrosigma.gestion.dao.GmGesPreguntaFrecuenteDao;
import net.macrosigma.gestion.ent.GmGesPreguntaFrecuente;
import net.macrosigma.parametro.dao.GmParParametroDao;
import net.macrosigma.parametro.ent.GmParParametros;
import net.macrosigma.seguridad.dao.GmSegUsuarioDao;
import net.macrosigma.seguridad.ent.GmSegUsuario;
import net.macrosigma.util.controller.BaseController;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Iframe;

public class ReporteSolicitudController extends BaseController {

	@Wire("#iframerep")
	Iframe iframerep;
	@Wire
	Combobox cmbususol, cmbusuasig;

	GmParParametros parCarreraSel = new GmParParametros();
	GmParParametros parSolSel = new GmParParametros();
	GmSegUsuario usuAsigSel = new GmSegUsuario();
	GmSegUsuario usuSolSel = new GmSegUsuario();
	List<GmSegUsuario> listUsuAsig = new ArrayList<GmSegUsuario>();
	List<GmParParametros> listparSol = new ArrayList<GmParParametros>();
	List<GmParParametros> listparCarrera = new ArrayList<GmParParametros>();
	List<GmSegUsuario> listUsuSol = new ArrayList<GmSegUsuario>();
	@Wire
	Datebox dtbfecdesd, dtbfechast;
	// Date fecDesd = new Date();
	// Date fecHast = new Date();

	// public Date getFecDesd() {
	// return fecDesd;
	// }
	//
	// public void setFecDesd(Date fecDesd) {
	// this.fecDesd = fecDesd;
	// }
	//
	// public Date getFecHast() {
	// return fecHast;
	// }
	//
	// public void setFecHast(Date fecHast) {
	// this.fecHast = fecHast;
	// }

	GmSegUsuarioDao usuDao = new GmSegUsuarioDao();
	GmParParametroDao parDao = new GmParParametroDao();

	String path = Sessions.getCurrent().getWebApp().getRealPath("/report");
	String img = Sessions.getCurrent().getWebApp().getRealPath("/img");

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

	public List<GmParParametros> getListparSol() {
		return listparSol;
	}

	public void setListparSol(List<GmParParametros> listparSol) {
		this.listparSol = listparSol;
	}

	public GmParParametros getParSolSel() {
		return parSolSel;
	}

	public void setParSolSel(GmParParametros parSolSel) {
		this.parSolSel = parSolSel;
	}

	public GmSegUsuario getUsuAsigSel() {
		return usuAsigSel;
	}

	public void setUsuAsigSel(GmSegUsuario usuAsigSel) {
		this.usuAsigSel = usuAsigSel;
	}

	public List<GmSegUsuario> getListUsuAsig() {
		return listUsuAsig;
	}

	public void setListUsuAsig(List<GmSegUsuario> listUsuAsig) {
		this.listUsuAsig = listUsuAsig;
	}

	public GmSegUsuario getUsuSolSel() {
		return usuSolSel;
	}

	public void setUsuSolSel(GmSegUsuario usuSolSel) {
		this.usuSolSel = usuSolSel;
	}

	public List<GmSegUsuario> getListUsuSol() {
		return listUsuSol;
	}

	public void setListUsuSol(List<GmSegUsuario> listUsuSol) {
		this.listUsuSol = listUsuSol;
	}

	@Command
	public void imprimirpdf() {
		ByteArrayOutputStream baos = getReport();

		AMedia amedia = new AMedia("solicitud", "pdf", null, baos.toByteArray());
		iframerep.setContent(amedia);

		// getReportExcel();
	}

	@Command
	public void limpiar() {
		parCarreraSel = new GmParParametros();
		parSolSel = new GmParParametros();
		usuAsigSel = new GmSegUsuario();
		usuSolSel = new GmSegUsuario();

		dtbfecdesd.setValue(null);
		dtbfechast.setValue(null);
		BindUtils.postNotifyChange(null, null, ReporteSolicitudController.this,
				"usuSolSel");
		BindUtils.postNotifyChange(null, null, ReporteSolicitudController.this,
				"usuAsigSel");
		BindUtils.postNotifyChange(null, null, ReporteSolicitudController.this,
				"parSolSel");
		BindUtils.postNotifyChange(null, null, ReporteSolicitudController.this,
				"parCarreraSel");
	}

	@Command
	public void imprimirexcel() {
		ByteArrayOutputStream baos = getReportXls();

		AMedia amedia = new AMedia("solicitud.xls", "xls", null,
				baos.toByteArray());
		iframerep.setContent(amedia);

	}

	GmSegUsuario usu = (GmSegUsuario) Sessions.getCurrent().getAttribute(
			"usuario");

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		cargaCombo();
	}

	public ByteArrayOutputStream getReport() throws NumberFormatException {

		ByteArrayOutputStream printStream = null;

		Map<String, Object> paramRpt = new HashMap<String, Object>();
		paramRpt.put("pv_img", img + File.separator + "ug.jpg");
		paramRpt.put("pv_usuario", usu.getUsuario());
		// paramRpt.put("id", 1);
		if (parCarreraSel.getPar_id() != null)
			paramRpt.put("pnidcarrera", parCarreraSel.getPar_id());
		else
			paramRpt.put("pnidcarrera", 0L);

		if (parSolSel.getPar_id() != null)
			paramRpt.put("pnidtipsol", parSolSel.getPar_id());
		else
			paramRpt.put("pnidtipsol", 0L);

		if (usuAsigSel.getUsuId() != null)
			paramRpt.put(
					"pnidusuasig",
					Long.parseLong(cmbusuasig.getSelectedItem().getValue()
							.toString()));
		else
			paramRpt.put("pnidusuasig", 0L);

		if (usuSolSel.getUsuId() != null)
			paramRpt.put(
					"pnidsolsel",
					Long.parseLong(cmbususol.getSelectedItem().getValue()
							.toString()));
		else
			paramRpt.put("pnidsolsel", 0L);
		DateFormat df = new SimpleDateFormat("ddMMyyyy");
		if (dtbfecdesd.getValue() != null) {
			String reportDate = df.format(dtbfecdesd.getValue());
			paramRpt.put("pdfecdesd", reportDate);
		} else {
			paramRpt.put("pdfecdesd", null);
		}
		if (dtbfechast.getValue() != null) {
			String reportDate = df.format(dtbfechast.getValue());
			paramRpt.put("pdfechast", reportDate);
		} else
			paramRpt.put("pdfechast", null);
		// dtbfecdesd, dtbfechast
		Connection cn = new Conexion().getConexion();
		try {

			JasperPrint jprint = JasperFillManager.fillReport(path
					+ "/solicitud.jasper", paramRpt, cn);
			printStream = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jprint, printStream);
		} catch (Exception e) {
			System.err.println("Error:No fue posible elaborar el reporte :"
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return printStream;
	}

	public ByteArrayOutputStream getReportXls() throws NumberFormatException {

		ByteArrayOutputStream printStream = null;

		Map<String, Object> paramRpt = new HashMap<String, Object>();
		paramRpt.put("pv_img", img + File.separator + "ug.jpg");
		paramRpt.put("pv_usuario", usu.getUsuario());
		// paramRpt.put("id", 1);
		if (parCarreraSel.getPar_id() != null)
			paramRpt.put("pnidcarrera", parCarreraSel.getPar_id());
		else
			paramRpt.put("pnidcarrera", 0L);

		if (parSolSel.getPar_id() != null)
			paramRpt.put("pnidtipsol", parSolSel.getPar_id());
		else
			paramRpt.put("pnidtipsol", 0L);

		if (usuAsigSel.getUsuId() != null)
			paramRpt.put(
					"pnidusuasig",
					Long.parseLong(cmbusuasig.getSelectedItem().getValue()
							.toString()));
		else
			paramRpt.put("pnidusuasig", 0L);

		if (usuSolSel.getUsuId() != null)
			paramRpt.put(
					"pnidsolsel",
					Long.parseLong(cmbususol.getSelectedItem().getValue()
							.toString()));
		else
			paramRpt.put("pnidsolsel", 0L);
		DateFormat df = new SimpleDateFormat("ddMMyyyy");
		if (dtbfecdesd.getValue() != null) {
			String reportDate = df.format(dtbfecdesd.getValue());
			paramRpt.put("pdfecdesd", reportDate);
		} else {
			paramRpt.put("pdfecdesd", null);
		}
		if (dtbfechast.getValue() != null) {
			String reportDate = df.format(dtbfechast.getValue());
			paramRpt.put("pdfechast", reportDate);
		} else
			paramRpt.put("pdfechast", null);
		// dtbfecdesd, dtbfechast
		Connection cn = new Conexion().getConexion();
		try {

			JasperPrint jprint = JasperFillManager.fillReport(path
					+ "/solicitud.jasper", paramRpt, cn);
			printStream = new ByteArrayOutputStream();

			JRXlsExporter xlsExporter = new JRXlsExporter();
			xlsExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,
					jprint);
			xlsExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
					printStream);
			xlsExporter.setParameter(
					JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
			xlsExporter.setParameter(
					JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
					Boolean.FALSE);
			xlsExporter.setParameter(
					JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
					Boolean.TRUE);
			xlsExporter.exportReport();

		} catch (Exception e) {
			System.err.println("Error:No fue posible elaborar el reporte :"
					+ e.getMessage());
			e.printStackTrace();
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
		cargaCombo();
	}

	@SuppressWarnings("static-access")
	public void cargaCombo() {
		usuDao.newManager();
		parDao.newManager();
		listparCarrera = parDao.getParametroByDesPad("CARRERAS");
		listparSol = parDao.getParametroByDesPad("TIPOS DE SOLICITUD");
		listUsuSol = usuDao.getUsuarioEstudiante();
		listUsuAsig = usuDao.getUsuarioACT();
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
			baos.close();
			AMedia amedia = new AMedia("solicitud.xls", "xls",
					"application/file", baos.toByteArray());
			Filedownload.save(amedia);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
