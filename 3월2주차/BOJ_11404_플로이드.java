import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int N = Integer.parseInt(br.readLine());
		int M = Integer.parseInt(br.readLine());
		int[][] allBus = new int[N][N];
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				allBus[r][c] = 1_000_000_000;
			}
		}
		
		for (int m = 0; m < M; m++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken()) - 1;
			int e = Integer.parseInt(st.nextToken()) - 1;
			int w = Integer.parseInt(st.nextToken());
			
			allBus[s][e] = Math.min(allBus[s][e], w);
		}
		
		for (int mid = 0; mid < N; mid++) {
			for (int start = 0; start < N; start++) {
				for (int end = 0; end < N; end++) {
					if (start == end)
						continue;
					
					allBus[start][end] = Math.min(allBus[start][end], allBus[start][mid] + allBus[mid][end]);
				}
			}
		}
		
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				if (allBus[r][c] == 1_000_000_000)
					sb.append("0 ");
				else
					sb.append(allBus[r][c] + " ");
			}
			
			sb.append("\n");
		}
		
		bw.write(sb.toString());
		bw.flush();
		bw.close();
	}
}
