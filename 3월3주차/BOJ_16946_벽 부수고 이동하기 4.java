import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	static final int[] dr = {-1,1,0,0};
	static final int[] dc = {0,0,-1,1};
	static int N, M;
	static int[][] map;
	static List<Integer> numList;
	static int index;
	
	static class Coord {
		int r, c;

		public Coord(int r, int c) {
			this.r = r;
			this.c = c;
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
		numList = new ArrayList<>();
		index = 1;
		
		for (int r = 0; r < N; r++) {
			String str = br.readLine();
			for (int c = 0; c < M; c++)
				map[r][c] = str.charAt(c) - '0';
		}
		
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < M; c++) {
				if (map[r][c] == 0) {
					bfs(new Coord(r, c));
				}
			}
		}
		
		int[][] result = new int[N][M];
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < M; c++) {
				if (map[r][c] == 1) {
					Set<Integer> set = new HashSet<>();
					int curCnt = 1;
					int i = 0;
					for (int p = 0; p < 4; p++) {
						int nr = r + dr[p];
						int nc = c + dc[p];
						
						if (isIn(nr, nc) && map[nr][nc] > 1 && !set.contains(map[nr][nc])) {
							set.add(map[nr][nc]);
							curCnt += numList.get(map[nr][nc] - 2);
						}
					}
					
					result[r][c] = curCnt;
				}
			}
		}
		
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < M; c++) {
				sb.append(result[r][c] % 10);
			}
			sb.append("\n");
		}
		
		bw.write(sb.toString());
		bw.flush();
		bw.close();
	}
	
	static void bfs(Coord coord) {
		Queue<Coord> queue = new LinkedList<>();
		queue.add(coord);
		int count = 1;
		map[coord.r][coord.c] = ++index;
		
		while (true) {
			Coord cur = queue.poll();
			
			for (int p = 0; p < 4; p++) {
				int nr = cur.r + dr[p];
				int nc = cur.c + dc[p];
				
				if (isIn(nr, nc) && map[nr][nc] == 0) {
					map[nr][nc] = index;
					count++;
					queue.add(new Coord(nr, nc));
				}
			}
			
			if (queue.isEmpty())
				break;
		}
		
		numList.add(count);
	}
	
	static boolean isIn(int r, int c) {
		return 0 <= r && r < N && 0 <= c && c < M;
	}
}