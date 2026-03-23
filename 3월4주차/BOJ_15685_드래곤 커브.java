import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static boolean[][] map;
    // 근본 좌표를 지정하고, 이 좌표는 이후 List에 포함하지 않는다
    // 항상 Generation 진행에 따른 기준점(회전의 중심)은 이 근본 좌표를 기준으로 생성된다
	static Coord base;
	
	static class Coord {
		int x, y;

		public Coord(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int N = Integer.parseInt(br.readLine());
		map = new boolean[101][101];
		for (int n = 0; n < N; n++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			int g = Integer.parseInt(st.nextToken());
			base = new Coord(x, y);
			
			List<Coord> list = new ArrayList<>();
            // 현재의 기준 : standard
            // 그 다음 generation에서의 기준 : base를 standard 기준으로 90도 시계 방향으로 돌린 좌표
			Coord standard;
			map[x][y] = true;
			if (d == 0) {
				map[x + 1][y] = true;
				standard = new Coord(x + 1, y);
			}
			else if (d == 1) {
				map[x][y - 1] = true;
				standard = new Coord(x, y - 1);
			}
			else if (d == 2) {
				map[x - 1][y] = true;
				standard = new Coord(x - 1, y);
			}
			else {
				map[x][y + 1] = true;
				standard = new Coord(x, y + 1);
			}
			
            // 드래곤 커브 생성
			newGen(standard, list, 0, g);
		}
		
        // 만족하는 조건 수 계산
		sb.append(findOut());
		
		bw.write(sb.toString());
		bw.flush();
		bw.close();
	}
	
	static int findOut() {
		int count = 0;
		
		for (int y = 0; y < 100; y++) {
			for (int x = 0; x < 100; x++) {
				if (map[x + 1][y] && map[x + 1][y + 1]) {
                    // 조건 만족하는 경우
					if (map[x][y] && map[x][y + 1])
						count++;
				}
                // (x+1, y) && (x+1, y+1)가 모두 true가 아니라면
                // x + 1로 넘어가서 조건을 만족하는지 체크할 필요가 없다(이미 조건 불만족)
                // 그러므로, 그 상황에는 빠르게 x + 2로 넘어가자.
				else
					x++;
			}
		}
		
		return count;
	}
	
    // curGen : 지금 현재의 Generation
	static void newGen(Coord standard, List<Coord> list, int curGen, int goalGen) {
		if (curGen == goalGen)
			return;
		
        // 다음 Generation에서의 회전 기준점 (standard)
		Coord newStandard = new Coord(standard.x - base.y + standard.y, standard.y + base.x - standard.x);
		// 다음 standard는 우선 list에 넣지 않고, 다음 standard 마지막 부분에서 포함한다.
        map[newStandard.x][newStandard.y] = true;
		int size = list.size();
		
		for (int i = 0; i < size; i++) {
			Coord cur = list.get(i);
			int xLen = cur.x - standard.x;
			int yLen = cur.y - standard.y;
			
			map[standard.x - yLen][standard.y + xLen] = true;
			list.add(new Coord(standard.x - yLen, standard.y + xLen));
		}
        // 현재 standard는 더 이상 standard가 아닌 일반 점이므로
        // list에 포함하여 일괄 처리한다.
		list.add(standard);
		
        // 다음 Generation으로 넘어가기
		newGen(newStandard, list, curGen + 1, goalGen);
	}
}
