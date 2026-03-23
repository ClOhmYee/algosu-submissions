import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int[] dr = {-1,1,0,0};
	static final int[] dc = {0,0,-1,1};
	static int R, C;
	static boolean[][] map;
	static Queue<Coord> fireQ, jiQ;
	static StringBuilder sb;
	static boolean[][] visitedJi, visitedFire;
	
	static class Coord {
		int r, c;

		public Coord(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
        // 전체 지도를 boolean array로 표현
        // true : '사람'이 이동 가능, 빈 공간에 해당
        // false : '사람'이 이동 불가, 벽 및 불에 해당
		map = new boolean[R][C];
		visitedJi = new boolean[R][C];
		visitedFire = new boolean[R][C];
		fireQ = new LinkedList<>();
		jiQ = new LinkedList<>();
		
		for (int r = 0; r < R; r++) {
			String str = br.readLine();
			for (int c = 0; c < C; c++) {
				char cur = str.charAt(c);
				
				if (cur == 'J') {
					jiQ.add(new Coord(r, c));
					cur = '.';
					visitedJi[r][c] = true;
				}
				else if (cur == 'F') {
					fireQ.add(new Coord(r, c));
					visitedFire[r][c] = true;
				}
				
				if (cur == '.')
					map[r][c] = true;
 			}
		}
		
		bfs();
		
		bw.write(sb.toString());
		bw.flush();
		bw.close();
	}
	
	static void bfs() {
		int count = 1;
		
		while (true) {
			int size;
			// 불 전파 먼저
            // 아무 것도 없는 경우, size = 0으로 두기
			if (fireQ.peek() == null)
				size = 0;
			else
				size = fireQ.size();
			
            // 이전 상황의 불들을 모두 전파하기 (size만큼)
			for (int i = 0; i < size; i++) {
				Coord cur = fireQ.poll();
				for (int p = 0; p < 4; p++) {
					int nr = cur.r + dr[p];
					int nc = cur.c + dc[p];
					
					if (isIn(nr, nc) && !visitedFire[nr][nc] && map[nr][nc]) {
						visitedFire[nr][nc] = true;
						map[nr][nc] = false;
						fireQ.add(new Coord(nr, nc));
					}
				}
			}
			
			// 이후 지훈 전파
			size = jiQ.size();
			for (int j = 0; j < size; j++) {
				Coord cur = jiQ.poll();
				for (int p = 0; p < 4; p++) {
					int nr = cur.r + dr[p];
					int nc = cur.c + dc[p];
					
					if (!isIn(nr, nc)) {
						sb.append(count);
						return;
					}
					
					if (!visitedJi[nr][nc] && map[nr][nc]) {
						visitedJi[nr][nc] = true;
						jiQ.add(new Coord(nr, nc));
					}
				}
			}
			
            // 다음에 이동할 수 있는 지훈이의 경로가 없다?
            // 그럼 gg
			if (jiQ.isEmpty()) {
				sb.append("IMPOSSIBLE");
				return;
			}
			
            // 문제 없이 반복을 진행하기 전에
            // 현재 count 증가
			count++;
		}
	}
	
	static boolean isIn(int r, int c) {
		return 0 <= r && r < R && 0 <= c && c < C;
	}
}
