package com.graby.store.portal.page;

import java.util.ArrayList;
import java.util.List;

import com.graby.store.base.Pagination;
import com.taobao.api.domain.Item;


public class TickTest {
	
	public static void main(String[] args) {
		List<Item> items = new ArrayList<Item>();
		for (int i = 0; i < 332; i++) {
			Item item = new Item();
			item.setNumIid(Long.valueOf(i) + 100000);
			items.add(item);
		}
		
		Pagination<Item> page = new Pagination<Item>(20);
		page.setTotalCount(items.size());
		
		System.out.println("total page : " + page.getTotalPages());
		
		StringBuffer line = new StringBuffer();
		int cur  = page.getFirst();
		do {
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
		} while (page.isHasNext());

	}
	
}
