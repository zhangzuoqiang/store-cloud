package com.graby.store.util;

public class Seqence {
	
	private Seqence() {
	}
	
	private int seq = 100000;

	private static class Seqence6bitContainer {
		private static Seqence instance = new Seqence();
	}

	public static Seqence getInstance() {
		return Seqence6bitContainer.instance;
	}

	public synchronized String next() {
		this.seq++;
		if (this.seq >= 1000000) {
			this.seq = 100000;
			return "100000";
		}
		return String.valueOf(seq);
	}
	
	public static void main(String[] args) {
		System.out.println(Seqence.getInstance().next());
		System.out.println(Seqence.getInstance().next());
	}
}
