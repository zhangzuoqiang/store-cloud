package com.graby.store.print;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.graby.store.admin.print.Pick;
import com.graby.store.admin.print.PickTestData;

public class JasperTest {
	
	public static void compile(String src, String dist) throws JRException {
		JasperCompileManager.compileReportToFile(src, dist);
	}
	
	public static void main(String[] args) throws JRException, IOException {
		Resource res = new ClassPathResource("/report/pick_main.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(res.getInputStream());
		
		List<Pick> picks = PickTestData.geneDatas();
		JRDataSource ds = new JRBeanCollectionDataSource(picks);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(), ds);
		JasperViewer.viewReport(jasperPrint);
	}
	
}
