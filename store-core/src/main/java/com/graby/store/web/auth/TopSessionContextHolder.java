package com.graby.store.web.auth;
//package com.graby.store.auth;
//
//public class TopSessionContextHolder {
//	
//	public static ThreadLocal<TopSessionContext> local = new ThreadLocal<TopSessionContext>();
//	
//	public static void setSessionKey(String sessionKey) {
//		local.set(new TopSessionContextImpl(sessionKey));
//	}
//	
//	public static String getSessionKey() {
//		TopSessionContext context = local.get();
//		if (context != null) {
//			context.getSessionKey();
//		}
//		return null;
//	}
//	
//	public static void cleanup() {
//		local.set(null);
//	}
//	
//}
