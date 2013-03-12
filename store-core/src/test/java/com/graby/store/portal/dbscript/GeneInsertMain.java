package com.graby.store.portal.dbscript;

public class GeneInsertMain {
	
	
	public static String gene(String tablename, String[] props) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ").append(tablename).append(" ( \n");
		for (int i = 0; i < props.length; i++) {
			sql.append(props[i]);
			if ( i < (props.length-1)) {
				sql.append(", ");
			}
		}
		sql.append(")\n");
		sql.append("values (\n");
		for (int i = 0; i < props.length; i++) {
			sql.append("#{").append(props[i]).append("}");
			if ( i < (props.length-1)) {
				sql.append(", ");
			}
		}
		sql.append(")\n");
		return sql.toString();
	}
	
	
	public static void main(String[] args) {
		String ss  = "itemid, ok, bad, defect, demage1, demage2";
		String[] props = ss.split(",\\s++");
		System.out.println(gene("sc_item_inventory", props));
	}
}
