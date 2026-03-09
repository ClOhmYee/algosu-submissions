import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static PriorityQueue<Line> list;
	static int[] roots;
	
	static class Line {
		int start, end, weight;
		
		public Line(int start, int end, int weight) {
			this.start = start;
			this.end = end;
			this.weight = weight;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int V = Integer.parseInt(st.nextToken());
		int E = Integer.parseInt(st.nextToken());
		list = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.weight, o2.weight));
		
		for (int e = 0; e < E; e++) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());
			
			list.add(new Line(start, end, weight));
		}
		
		roots = new int[V + 1];
		for (int i = 1; i <= V; i++)
			roots[i] = i;
		
		int result = 0;
		while (!list.isEmpty()) {
			Line curr = list.poll();
			
			int startRoot = find(curr.start);
			int endRoot = find(curr.end);
			
			if (startRoot != endRoot) {
				result += curr.weight;
				roots[startRoot] = endRoot;
			}
		}
		
		sb.append(result);
		bw.write(sb.toString());
		bw.flush();
		bw.close();
	}
	
	static int find(int x) {
		if (roots[x] == x) return x;
		
		return roots[x] = find(roots[x]);
	}
}
