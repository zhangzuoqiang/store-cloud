package com.graby.store.portal.tick;

import java.util.ArrayList;
import java.util.List;

import com.graby.store.base.Page;
import com.taobao.api.domain.Item;


public class TickTest {
	
	public static void main(String[] args) {
		List<Item> items = new ArrayList<Item>();
		for (int i = 0; i < 2; i++) {
			Item item = new Item();
			item.setNumIid(Long.valueOf(i) + 100000);
			items.add(item);
		}
		
		Page<Item> page = new Page<Item>(20);
		page.setTotalCount(items.size());
		
		System.out.println("total page : " + page.getTotalPages());
		
		StringBuffer line = new StringBuffer();
		for (int cur  = page.getFirst(); page.isHasNext() ;) {
			page.setPageNo(cur);
			int start = page.getPageSize()* (page.getPageNo()-1);
			long end = page.isHasNext()? page.getPageSize()*page.getPageNo() : page.getTotalCount();
			for (int i = start; i < end; i++) {
				line.append(items.get(i).getNumIid());
				line.append(i < (end-1) ? "," : "");
			}
			System.out.println(line);
			line = new StringBuffer();
			cur++;			
			
		}
		
//		StringBuffer buf = new StringBuffer();
//		for (int i = 0; i < items.size(); i++) {
//			buf.append(items.get(i).getNumIid());
//			int mod = (i+1) % 20;
//			buf.append(mod != 0 && i < items.size()? "," : "");
//			if (mod == 0) {
//				System.out.println(buf);
//				buf = new StringBuffer();
//				continue;
//			}
//			
//		}
	}
	
}
