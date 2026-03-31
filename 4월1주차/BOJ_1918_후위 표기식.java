package b1918;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Deque;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		Deque<Character> ad = new ArrayDeque<>();
		String infix = br.readLine();
		int index = 0;
		
		while (index < infix.length()) {
			char c = infix.charAt(index);
			
			if (Character.isAlphabetic(c))
				sb.append(c);
			else if (c == '(')
				ad.push(c);
			else if (c == ')') {
				while (!ad.isEmpty() && ad.peek() != '(')
					sb.append(ad.pop());
				
				ad.pop();
			}
			else {
				while (!ad.isEmpty() && rank(c) <= rank(ad.peek())) {
					sb.append(ad.pop());
				}
				
				ad.push(c);
			}
			
			index++;
		}

		while (!ad.isEmpty())
			sb.append(ad.pop());
		
		bw.write(sb.toString());
		bw.flush();
		bw.close();
	}
	
	static int rank(char c) {
		switch (c) {
		case '+':
		case '-':
			return 1;
		case '*':
		case '/':
			return 2;
		}
		
		return -1;
	}
}
