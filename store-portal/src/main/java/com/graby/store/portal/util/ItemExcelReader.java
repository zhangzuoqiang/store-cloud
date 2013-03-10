package com.graby.store.portal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.graby.store.entity.Item;

public class ItemExcelReader {
	
	private Sheet sheet;
	
	public ItemExcelReader(InputStream in) throws FileNotFoundException, IOException {
		Workbook wb = new HSSFWorkbook(in);
		sheet = wb.getSheetAt(0);
	}
	
	public ItemExcelReader(File file) throws FileNotFoundException, IOException {
		Workbook wb = new HSSFWorkbook(new FileInputStream(file));
		sheet = wb.getSheetAt(0);
	}
	
	private List<Item> read(Sheet sheet) {
		List<Item> items = new ArrayList<Item>();
		int totalRownums = sheet.getPhysicalNumberOfRows();
		for (int i = 1; i < totalRownums; i++) {
			Item item = new Item();
			Row row = sheet.getRow(i);
			Cell codeCell = row.getCell(0);
			Cell nameCell = row.getCell(1);
			Cell weightCell = row.getCell(2);
			Cell typeCell = row.getCell(3);
			Cell descCell = row.getCell(4);
			// 编码
			item.setCode(codeCell.getStringCellValue());
			// 标题
			item.setTitle(nameCell.getStringCellValue());
			// 重量
			Double widght = weightCell.getNumericCellValue();
			item.setWeight(widght.longValue());
			// 类型
			String type = typeCell.getStringCellValue();
			item.setType(type.equals("普通商品")? "normal" : "combine");
			// 描述
			item.setDescription(descCell.getStringCellValue());
			items.add(item);
		}
		return items;
	}
	
	public List<Item> getItems() {
		return read(sheet);
	}
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String path = "d:/商品模板.xls";
		File file = new File(path);
		ItemExcelReader excel = new ItemExcelReader(file);
		List<Item> items = excel.getItems();
		for (Item item : items) {
			System.out.println(item.getTitle() + "," + item.getType());
		}
	}
	
}
