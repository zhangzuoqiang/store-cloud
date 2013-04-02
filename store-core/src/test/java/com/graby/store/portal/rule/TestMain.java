//package com.graby.store.portal.rule;
//
//import org.drools.RuleBase;
//import org.drools.RuleBaseFactory;
//import org.drools.StatefulSession;
//
//public class TestMain {
//	
//	/**
//	 * @param args
//	 */
//	public static void main(final String[] args) {
//
////		final PackageBuilder builder = new PackageBuilder();
////		try {
////			builder.addPackageFromDrl(new InputStreamReader(StateExampleUsingAgendaGroup.class
////					.getResourceAsStream("StateExampleUsingAgendaGroup.drl")));
////		} catch (DroolsParserException e) {
////			throw new IllegalArgumentException("Invalid drl", e);
////		} catch (IOException e) {
////			throw new IllegalArgumentException("Could not read drl", e);
////		}
//
//		final RuleBase ruleBase = RuleBaseFactory.newRuleBase();
////		ruleBase.addPackage(builder.getPackage());
//
//		final StatefulSession session = ruleBase.newStatefulSession();
//
//		
////
////		// final WorkingMemoryFileLogger logger = new WorkingMemoryFileLogger(
////		// session );
////		// logger.setFileName( "log/state.log" );
////
////		// PropertyChangeListeners so you don't have to call update().
////		final boolean dynamic = true;
////
////		session.insert(a, dynamic);
////		session.insert(b, dynamic);
////		// session.insert(c, dynamic);
////		session.insert(d, dynamic);
////
////		session.fireAllRules();
////		session.dispose();
//
//		// logger.writeToDisk();
//	}
//}
