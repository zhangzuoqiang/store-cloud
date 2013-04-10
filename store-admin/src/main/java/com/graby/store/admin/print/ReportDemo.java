package com.graby.store.admin.print;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ReportDemo {

	public static void main(String[] args) throws IOException, JRException {
		Resource res = new ClassPathResource("/report/picking_batch.jrxml");
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		JasperCompileManager.compileReportToStream(res.getInputStream(), new FileOutputStream(new File("d:/aa.jasper")));
//		Map<String, Object> data = new HashMap<String, Object>();
//		data.put("code", "User Report");
//		data.put("title", "ddd");
//		data.put("num", 10);
		// jasperPrint = JasperFillManager.fillReport(sourceFileName,
		// parameters, dataSource).fillReport(file.getPath(), parameters,
		// datas);
		// jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
		// connection);
		// JasperExportManager.exportReportToPdfFile(jasperPrint,
		// "src/test.pdf");
		// JasperViewer.viewReport(jasperPrint);

	}
}
