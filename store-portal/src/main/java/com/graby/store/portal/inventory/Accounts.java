package com.graby.store.portal.inventory;

/**
 * 科目 
 */
public class Accounts {


	public static final String CODE_SHOP_INVENTORY = "201";

	public static final String CODE_SALED = "105";

	public static final String CODE_FROZEN = "104";

	public static final String CODE_BADNESS_DEMAGE_BOX = "103_003";

	public static final String CODE_BADNESS_DEMAGE_MACHINE = "103_002";

	public static final String CODE_BADNESS_DEFECT = "103_001";

	public static final String CODE_SALEABLE = "102";

	public static final String CODE_ONTHEWAY = "101";

	/* ----------- 借方 ----------- */
	
	/**
	 * 在途
	 */
	public static Account ONTHEWAY = new Account(Direction.CREDIT, CODE_ONTHEWAY);

	/**
	 * 可销售
	 */
	public static Account SALEABLE = new Account(Direction.CREDIT, CODE_SALEABLE);

	/**
	 * 不良品：残次
	 */
	public static Account BADNESS_DEFECT = new Account(Direction.CREDIT, CODE_BADNESS_DEFECT);

	/**
	 * 不良品：机损
	 */
	public static Account BADNESS_DEMAGE_MACHINE = new Account(Direction.CREDIT, CODE_BADNESS_DEMAGE_MACHINE);

	/**
	 * 不良品：箱损
	 */
	public static Account BADNESS_DEMAGE_BOX = new Account(Direction.CREDIT, CODE_BADNESS_DEMAGE_BOX);

	/**
	 * 冻结
	 */
	public static Account FROZEN = new Account(Direction.CREDIT, CODE_FROZEN);

	/**
	 * 已售出
	 */
	public static Account SALED = new Account(Direction.DEBIT, CODE_SALED);

	/* ----------- 贷方 ----------- */

	/**
	 * 商铺方库存
	 */
	public static Account SHOP_INVENTORY = new Account(Direction.CREDIT, CODE_SHOP_INVENTORY);

	/**
	 * 库存科目条目
	 * 
	 * @author huabiao.mahb
	 */
	public static class Account {

		public Account(Direction direction, String code) {
			this.direction = direction;
			this.code = code;
		}

		private Direction direction;
		private String code;

		public Direction getDirection() {
			return direction;
		}

		public String getCode() {
			return code;
		}

		public void setDirection(Direction direction) {
			this.direction = direction;
		}

		public void setCode(String code) {
			this.code = code;
		}
	}

	/**
	 * 借贷方向
	 */
	public static enum Direction {

		/**
		 * 借方
		 */
		DEBIT("DEBIT"),

		/**
		 * 贷方
		 */
		CREDIT("CREDIT");

		private final String value;

		private Direction(String val) {
			value = val;
		}
		
		public boolean isDebit() {
			return value.equals("DEBIT");
		}
		
		public boolean isCredit() {
			return value.equals("CREDIT");
		}
		
		public boolean equalsName(String otherName) {
			return (otherName == null) ? false : value.equals(otherName);
		}

		public String toString() {
			return value;
		}

	}

}
