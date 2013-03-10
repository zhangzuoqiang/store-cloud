package com.graby.store.portal.top;

import java.util.List;

import org.springside.modules.mapper.JsonMapper;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Product;
import com.taobao.api.request.ItemsOnsaleGetRequest;
import com.taobao.api.request.ProductsGetRequest;
import com.taobao.api.request.ShopGetRequest;
import com.taobao.api.request.UserGetRequest;
import com.taobao.api.response.ProductsGetResponse;
import com.taobao.api.response.ShopGetResponse;
import com.taobao.api.response.UserGetResponse;

public class HttpCallTest {

	private static final String appKey = "test";
	private static final String appSecret = "test";
	private static final String serverUrl = "http://gw.api.tbsandbox.com/router/rest";
	private static final String userNickname = "sandbox_b_01";

	private static DefaultTaobaoClient conn() {
		/** 创建client **/
		DefaultTaobaoClient client = new DefaultTaobaoClient(serverUrl, appKey, appSecret, "json");
		return client;
	}

	// 测试获取用户
	private static void testGetUser() throws ApiException {
		DefaultTaobaoClient client = conn();
		UserGetRequest req = new UserGetRequest();
		/** 设置API业务入参 **/
		req.setFields("nick,email");
		req.setNick(userNickname);
		UserGetResponse resp = client.execute(req);
		/** 正常请求，获取用户信息，由于email是需要用户授权才能获取，因此返回的信息中不包含emaill信息 **/
		System.out.println(resp.getBody());
	}

	// 测试获取商铺
	private static void testShopInfo() throws ApiException {
		DefaultTaobaoClient client = conn();
		ShopGetRequest req = new ShopGetRequest();
		req.setFields("sid,cid,title,nick,desc,bulletin,pic_path,created,modified");
		req.setNick(userNickname);
		ShopGetResponse response = client.execute(req);
		System.out.println(response.getBody());
	}

	// 获取用户产品列表
	private static void testGetProduct() throws ApiException {
		DefaultTaobaoClient client = conn();
		ProductsGetRequest req = new ProductsGetRequest();
		req.setFields("product_id,outer_id,created,cid,cat_name,props,props_str,name,binds,binds_str,sale_props,desc,pic_url,modified");
		req.setNick(userNickname);
		req.setPageNo(1L);
		req.setPageSize(40L);
		ProductsGetResponse response = client.execute(req);
		List<Product> prods = response.getProducts();
		JsonMapper json = new JsonMapper();
		for (Product product : prods) {
			System.out.println(json.toJson(product));
		}
	}

	public static void testItemGet() {
		DefaultTaobaoClient client = conn();
		ItemsOnsaleGetRequest req = new ItemsOnsaleGetRequest();
		req.setFields("num_iid,title,price");
		req.setQ("N97");
		req.setCid(1512L);
		req.setSellerCids("11");
		req.setPageNo(10L);
		req.setHasDiscount(true);
		req.setHasShowcase(true);
		req.setOrderBy("list_time:desc");
	}

	public static void main(String[] args) throws ApiException {
		testGetUser();
		testShopInfo();
		testGetProduct();
	}

}
