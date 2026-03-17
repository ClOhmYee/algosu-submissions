import java.io.*;
import java.util.*;

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
		
		// 초기값 세팅
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		numList = new ArrayList<>();
		index = 1;
		
		// input 값 저장하기
		for (int r = 0; r < N; r++) {
			String str = br.readLine();
			for (int c = 0; c < M; c++)
				map[r][c] = str.charAt(c) - '0';
		}
		
		// 모든 공간(0인 값)에 대해 라벨링 시작
		// 각기 다른 공간마다 고유한 번호로 저장하여(index 변수로 표현)
		// 이후 고유한 번호를 통한 탐색으로 공간 크기 파악
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < M; c++) {
				if (map[r][c] == 0) {
					bfs(new Coord(r, c));
				}
			}
		}
		
		// 이제, 벽에 대해 4방향으로 탐색하기
		// 각 공간마다 라벨링이 되어 있으므로
		// 공간의 고유 번호 확인 후, 해당 번호에 맞는 공간 크기를 구하면 끝
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < M; c++) {
				if (map[r][c] == 1)
					checkNeighbors(r, c);
			}
		}
		
		// 출력
		// 벽의 경우 모두 음수로 처리했으므로 조건을 통한 차별 대우(?) 필요
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < M; c++) {
				if (map[r][c] < 0)
					sb.append((map[r][c] * (-1)) % 10);
				else
					sb.append("0");
			}
			sb.append("\n");
		}
		
		bw.write(sb.toString());
		bw.flush();
		bw.close();
	}
	
	// 해당 지점 기준으로 4방향 탐색
	// 공간에 대해서는 크기를 파악하고, 모두 더함
	// 이후 기존 벽 위치에 음수로 공간 크기로 덮어쓰기
	static void checkNeighbors(int r, int c) {
		int[] visit = new int[4];
		int curCnt = 1;
		int i = 0;
		for (int p = 0; p < 4; p++) {
			int nr = r + dr[p];
			int nc = c + dc[p];
			
			if (isIn(nr, nc) && map[nr][nc] > 1) {
				boolean visitedFlag = false;
				for (int curIndex = 0; curIndex < i; curIndex++) {
					if (visit[curIndex] == map[nr][nc]) {
						visitedFlag = true;
						break;
					}
				}
				if (!visitedFlag) {
					visit[i++] = map[nr][nc];
					curCnt += numList.get(map[nr][nc] - 2);
				}
			}
		}
		
		map[r][c] = curCnt * (-1);
	}
	
	// 하나의 공간(0인 값)에 대해서, 같은 공간을 BFS로 탐색
	// count를 세야 하는 경우이므로, DFS보다 BFS가 더 적절하다고 판단
	// (DFS이면 static int dfs()로 선언해야 하고, ...(이하생략) 그냥 경험상 매우 복잡했음)
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
	
	// '그거'
	// 이정도면 템플릿으로 저장해도 될듯
	static boolean isIn(int r, int c) {
		return 0 <= r && r < N && 0 <= c && c < M;
	}
}
