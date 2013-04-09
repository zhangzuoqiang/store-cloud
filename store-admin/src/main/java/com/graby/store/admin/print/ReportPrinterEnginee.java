package com.graby.store.admin.print;

import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.attribute.Attribute;
import javax.print.event.PrintJobEvent;
import javax.print.event.PrintJobListener;
import javax.print.event.PrintServiceAttributeEvent;
import javax.print.event.PrintServiceAttributeListener;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.view.JasperViewer;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrderDetail;

/**
 * Jasper打印引擎
 * 
 * @author colt
 */
public class ReportPrinterEnginee {

	public ReportPrinterEnginee(String printerName) {
		this.printerName = printerName;
	}

	// 参数
	private Map parameters = new HashMap();

	// 数据
	private JRDataSource datas;

	// 打印机名称
	private String printerName;

	// 系统打印机对象
	private PrintService printService;

	private void prepare() {
		PrintService[] pss = PrinterJob.lookupPrintServices();
		if (pss == null || pss.length == 0) {
			throw new RuntimeException("不存在系统打印机!");
		}
		for (PrintService svc : pss) {
			if (svc.getName().equals(printerName)) {
				printService = svc;
			}
		}
		if (printService == null) {
			throw new RuntimeException("未找到系统打印机：" + printerName);
		}

		printService.addPrintServiceAttributeListener(new PrintServiceAttributeListener() {

			public void attributeUpdate(PrintServiceAttributeEvent event) {
				Attribute[] attrs = event.getAttributes().toArray();
				for (int i = 0; i < attrs.length; i++) {
					String attrName = attrs[i].getName();
					String attrValue = attrs[i].toString();
					System.out.println(attrName + ":" + attrValue);
				}
			}
		});

	}

	private void printJapser(JasperPrint printFile) throws JRException {
		prepare();

		DocPrintJob printJob = printService.createPrintJob();
		printJob.addPrintJobListener(new PrintJobListener() {

			public void printJobRequiresAttention(PrintJobEvent arg0) {
				System.out.println("RequiresAttention:" + arg0);
			}

			public void printJobNoMoreEvents(PrintJobEvent arg0) {
				System.out.println("printJobNoMoreEvents:" + arg0);
			}

			public void printJobFailed(PrintJobEvent arg0) {
				System.out.println("printJobFailed:" + arg0);
			}

			public void printJobCompleted(PrintJobEvent arg0) {
				System.out.println("printJobCompleted:" + arg0);
			}

			public void printJobCanceled(PrintJobEvent arg0) {
				System.out.println("printJobCanceled:" + arg0);
			}

			public void printDataTransferCompleted(PrintJobEvent arg0) {
				System.out.println("printDataTransferCompleted:" + arg0);
			}
		});

		JRAbstractExporter je = new JRPrintServiceExporter();
		je.setParameter(JRExporterParameter.JASPER_PRINT, printFile);
		// 设置指定打印机
		je.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, printService);
		
		je.exportReport();

	}

	/**
	 * 后厨打印
	 * 
	 * @param params
	 * @param lines
	 * @throws JRException
	 * @throws IOException
	 */
	public void printCookRoom(Map params, Set<ShipOrder> lines) throws JRException, IOException {
		fillDate(params, lines);
		Resource res = new ClassPathResource("com/graby/store/admin/print/CookRoomReport.jasper");
		File file = new File("d:/CookRoomReport.jasper");
//		File jasperFile = res.getFile();
		JasperPrint jasperPrint = JasperFillManager.fillReport(file.getPath(), parameters, datas);
		//printJapser(jasperPrint);
		JasperViewer.viewReport(jasperPrint);
	}

	private void fillDate(Map parames, Set<ShipOrder> lines) {
		parameters = parames;
		HashMap[] reportRows = new HashMap[lines.size()];
		int i = 0;
//		for (OrderItem item : lines) {
//			HashMap rowMap = new HashMap();
//			rowMap.put("name", item.getFood().getCnname());
//			rowMap.put("modus", item.getModusDesc());
//			rowMap.put("count", item.getCount() + "份");
//			reportRows[i++] = rowMap;
//		}
		datas = new JRMapArrayDataSource(reportRows);
	}

	public static void main(String[] args) throws JRException, IOException {
		ReportPrinterEnginee printerEngine = new ReportPrinterEnginee("P1");

		Set<ShipOrder> items = new HashSet<ShipOrder>();
		ShipOrderDetail item = new ShipOrderDetail();
		printerEngine.printCookRoom(null, items);

	}

}
