import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static final int[] dr = {-1,1,0,0};
	static final int[] dc = {0,0,-1,1};
	
	static final int[][] twoDirs = {{0,1},{2,3}};
	static final int[][] threeDirs = {{0,2},{0,3},{1,2},{1,3}};
	
	static int N, M, numCCTVs, numBlind, minBlind;
	static int[][] map;
	static CCTV[] cctvs;
	
	static class CCTV implements Comparable<CCTV> {
		int r, c, type;

		public CCTV(int r, int c, int type) {
			this.r = r;
			this.c = c;
			this.type = type;
		}

		@Override
		public int compareTo(CCTV o) {
			return Integer.compare(o.type, this.type);
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		cctvs = new CCTV[9];
		numBlind = 0;
		minBlind = Integer.MAX_VALUE;
		numCCTVs = 0;
		
		for (int r = 0; r < N; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c = 0; c < M; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
				if (map[r][c] == 0)
					numBlind++;
				else if (map[r][c] != 6)
					cctvs[numCCTVs++] = new CCTV(r, c, map[r][c]);
			}
		}
		
		Arrays.sort(cctvs, 0, numCCTVs);
		
		int curIndex = 0;
		// 5번 CCTV는 경우가 한 개만 존재하므로, 그냥 실행하기
		while (numCCTVs > curIndex && cctvs[curIndex].type == 5) {
			typeFive(cctvs[curIndex]);
			curIndex++;
		}
		
		solve(numBlind, curIndex);
		
		sb.append(minBlind);
		bw.write(sb.toString());
		bw.flush();
		bw.close();
	}
	
	static void solve(int curBlind, int curIndex) {
		if (curIndex == numCCTVs) {
			if (minBlind > curBlind)
				minBlind = curBlind;
			
			return;
		}
		
		CCTV curr = cctvs[curIndex];
		
		switch (curr.type) {
		case 1:
			for (int d = 0; d < 4; d++)
				typeOne(curr, d, curBlind, curIndex);
			
			break;
		case 2:
			for (int d = 0; d < 2; d++)
				typeTwo(curr, d, curBlind, curIndex);
			
			break;
		case 3:
			for (int d = 0; d < 4; d++)
				typeThree(curr, d, curBlind, curIndex);
			
			break;
		case 4:
			for (int d = 0; d < 4; d++)
				typeFour(curr, d, curBlind, curIndex);
			
			break;
		}
	}
	
	static void typeOne(CCTV curr, int dir, int curBlind, int curIndex) {
		int nr = curr.r;
		int nc = curr.c;
		
		while (isIn(nr += dr[dir], nc += dc[dir])) {
			if (map[nr][nc] == 6)
				break;
			
			if (map[nr][nc] <= 0) {
				if (map[nr][nc] == 0)
					curBlind--;
				
				map[nr][nc]--;
			}
		}
		
		solve(curBlind, curIndex + 1);
		
		nr = curr.r;
		nc = curr.c;
		
		while (isIn(nr += dr[dir], nc += dc[dir])) {
			if (map[nr][nc] == 6)
				break;
			
			if (map[nr][nc] < 0)
				map[nr][nc]++;
		}
	}
	
	static void typeTwo(CCTV curr, int dir, int curBlind, int curIndex) {
		int[] dirs = twoDirs[dir];
		
		for (int i = 0; i <= 1; i++) {
			int nr = curr.r;
			int nc = curr.c;
			
			while (isIn(nr += dr[dirs[i]], nc += dc[dirs[i]])) {
				if (map[nr][nc] == 6)
					break;
				
				if (map[nr][nc] <= 0) {
					if (map[nr][nc] == 0)
						curBlind--;
					
					map[nr][nc]--;
				}
			}
		}
		
		solve(curBlind, curIndex + 1);
		
		for (int i = 0; i <= 1; i++) {
			int nr = curr.r;
			int nc = curr.c;
			
			while (isIn(nr += dr[dirs[i]], nc += dc[dirs[i]])) {
				if (map[nr][nc] == 6)
					break;
				
				if (map[nr][nc] <= 0)
					map[nr][nc]++;
			}
		}
	}
	
	static void typeThree(CCTV curr, int dir, int curBlind, int curIndex) {
		for (int i = 0; i <= 1; i++) {
			int nr = curr.r;
			int nc = curr.c;
			
			while (isIn(nr += dr[threeDirs[dir][i]], nc += dc[threeDirs[dir][i]])) {
				if (map[nr][nc] == 6)
					break;
				
				if (map[nr][nc] <= 0) {
					if (map[nr][nc] == 0)
						curBlind--;
					
					map[nr][nc]--;
				}
			}
		}
		
		solve(curBlind, curIndex + 1);
		
		for (int i = 0; i <= 1; i++) {
			int nr = curr.r;
			int nc = curr.c;
			
			while (isIn(nr += dr[threeDirs[dir][i]], nc += dc[threeDirs[dir][i]])) {
				if (map[nr][nc] == 6)
					break;
				
				if (map[nr][nc] < 0)
					map[nr][nc]++;
			}
		}
	}
	
	static void typeFour(CCTV curr, int dir, int curBlind, int curIndex) {
		for (int d = 0; d < 4; d++) {
			if (d == dir)
				continue;
			
			int nr = curr.r;
			int nc = curr.c;
			
			while (isIn(nr += dr[d], nc += dc[d])) {
				if (map[nr][nc] == 6)
					break;
				
				if (map[nr][nc] <= 0) {
					if (map[nr][nc] == 0)
						curBlind--;
					
					map[nr][nc]--;
				}
			}
		}
		
		solve(curBlind, curIndex + 1);
		
		for (int d = 0; d < 4; d++) {
			if (d == dir)
				continue;
			
			int nr = curr.r;
			int nc = curr.c;
			
			while (isIn(nr += dr[d], nc += dc[d])) {
				if (map[nr][nc] == 6)
					break;
				
				if (map[nr][nc] < 0)
					map[nr][nc]++;
			}
		}
	}
	
	static void typeFive(CCTV curr) {
		
		for (int d = 0; d < 4; d++) {
			int nr = curr.r;
			int nc = curr.c;
			while (isIn(nr += dr[d], nc += dc[d])) {
				if (map[nr][nc] == 6)
					break;
				
				if (map[nr][nc] <= 0) {
					if (map[nr][nc] == 0)
						numBlind--;
					
					map[nr][nc]--;
				}
			}
		}
	}
	
	static boolean isIn(int r, int c) {
		return 0 <= r && r < N && 0 <= c && c < M;
	}
}
