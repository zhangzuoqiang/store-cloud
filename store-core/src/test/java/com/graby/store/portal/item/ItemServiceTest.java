//package com.graby.store.portal.item;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.test.context.ContextConfiguration;
//import org.springside.modules.test.spring.SpringTransactionalTestCase;
//
//import com.graby.store.entity.Item;
//import com.graby.store.portal.service.ItemService;
//
//@ContextConfiguration(locations = { "/applicationContext.xml" })
//public class ItemServiceTest extends SpringTransactionalTestCase{
//	
//	@Autowired
//	private ItemService itemService;
//	
//	@Test
//	public void testPage() {
//		Page<Item> page = itemService.findPageUserItems(2L, 0, 5);
//		testPage(page);
//	}
//
//	private void testPage(Page<Item> page) {
//		System.out.println("--------------");
//		System.out.println("totalNums:" + page.getTotalElements());
//		System.out.println("totalPages:" + page.getTotalPages());
//		System.out.println("pageSize:" + page.getSize());
//		for (Item item : page) {
//			System.out.println(item.getCode() + "," + item.getTitle() + ", userid=" + item.getUserid());
//		}
//	}
//}
