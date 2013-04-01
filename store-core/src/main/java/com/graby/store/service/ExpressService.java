package com.graby.store.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.drools.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.graby.store.base.AppException;
import com.graby.store.entity.Express;

@Component
public class ExpressService {
	
	@Value("${express.validate}")
	private  boolean validate;
	
	@Value("classpath:data/express.xml")
	private Resource express;
	
	/**
	 * 快递公司列表
	 * key=编码， value=快递公司实体
	 */
	private Map<String, Express> expresses = new HashMap<String, Express>();
	
	/**
	 * 根据编码获取快递公司名称
	 * @param code
	 * @return
	 */
	public String getExpressCompanyName(String code) {
		Express company = expresses.get(code);
		return company == null ? "" : company.getName();
	}
	
	/**
	 * 运单规则校验
	 * @param code
	 * @param orderno
	 * @return
	 */
	public boolean validate(String code, String orderno) {
		if (!validate) {
			return true;
		}
		Express e = expresses.get(code);
		if (e == null) {
			throw new AppException("未找到快递公司,CODE=" + code);
		}
		if (StringUtils.isEmpty(orderno)) {
			throw new AppException("运单规则校验未运行，运单号为空");
		}
		String reg = e.getRegMailNo();
		if (StringUtils.isEmpty(reg)) {
			return true;
		}
		return orderno.matches(reg);
	}
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() throws IOException, DocumentException {
		SAXReader reader = new SAXReader();
		Document doc = reader.read(express.getFile());
		String xpath ="/logistics_companies_get_response/logistics_companies/logistics_company";
		List<Element> companys = doc.selectNodes(xpath);
		for (Element eleCompany : companys) {
			String code = eleCompany.elementText("code");
			String name = eleCompany.elementText("name");
			String reg = eleCompany.elementText("reg_mail_no");
			Express e = gene(code, name, reg);
			expresses.put(code, e);
		}
	}

	private Express gene(String code, String name, String reg) {
		Express e = new Express();
		e.setCode(code);
		e.setName(name);
		e.setRegMailNo(reg);
		return e;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

}
