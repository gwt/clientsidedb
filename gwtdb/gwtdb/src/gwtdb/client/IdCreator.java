package gwtdb.client;

import java.util.Random;

public class IdCreator {
	private final static int LEN = 64;
	private final static String CHARS = "abcdefghijklmopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private final static Random r = new Random();
	
	public static String get() {
		String id = "";
		for (int i=0; i<LEN; i++) {
			id += CHARS.charAt(r.nextInt(CHARS.length()));
		}
		return id;
	}
}
