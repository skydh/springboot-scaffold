package com.dh.demo;

public class Test {
	public static void helper(int b, int c) {
		boolean is = true;
		try {
			if (b == 2)
				throw new RuntimeException("hello");
			else
				is = false;
		} finally {
			if (is)
				if (c == 2)
					throw new RuntimeException("world");
		}

	}

	public static void main(String[] arg) {
		helper(2,3);
	}

}
