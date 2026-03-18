import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
	static int N, M;
	static int[][] map;
	static int[] left, right;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		left = new int[M];
		right = new int[M];
		
		for (int r = 0; r < N; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c = 0; c < M; c++)
				map[r][c] = Integer.parseInt(st.nextToken());
		}
		
		// DP 초기값 세팅
		int[][] dp = new int[N][M];
		dp[0][0] = map[0][0];
		for (int c = 1; c < M; c++) {
			dp[0][c] = dp[0][c - 1] + map[0][c];
		}
		
		// DP 시작
		for (int r = 1; r < N; r++) {
			left[0] = dp[r - 1][0] + map[r][0];
			for (int c = 1; c < M; c++)
				left[c] = Math.max(left[c - 1], dp[r - 1][c]) + map[r][c];
			
			// 마지막 경우에는 left만 따지면 된다
			// (맨 오른쪽 아래로 이동하는 것이 목표이므로, 왼쪽에서 오른쪽 search만 해당)
			if (r == N - 1) {
				dp[N - 1][M - 1] = left[M - 1];
				break;
			}
			
			right[M - 1] = dp[r - 1][M - 1] + map[r][M - 1];
			for (int c = M - 2; c >= 0; c--)
				right[c] = Math.max(right[c + 1], dp[r - 1][c]) + map[r][c];
			
			for (int c = 0; c < M; c++)
				dp[r][c] = Math.max(left[c], right[c]);
		}
		
		sb.append(dp[N - 1][M - 1]);
		bw.write(sb.toString());
		bw.flush();
		bw.close();
	}

}
