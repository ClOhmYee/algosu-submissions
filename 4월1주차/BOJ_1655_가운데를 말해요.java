import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.PriorityQueue;

public class Main {
	static PriorityQueue<Integer> leftQueue, rightQueue;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int N = Integer.parseInt(br.readLine());
		
		leftQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2, o1));
		rightQueue = new PriorityQueue<>();
		
		// 우선 첫 번째 값에 대해서 처리 및 초기화
		int centerNum = Integer.parseInt(br.readLine());
		sb.append(centerNum + "\n");
		
		for (int n = 2; n <= N; n++) {
			int curNum = Integer.parseInt(br.readLine());
			
			// 1. leftQueue와 rightQueue의 크기가 같을 때
			// 무조건 rightQueue에 값을 넣어야 함
			if (isSameSize()) {
				// 1-1. 중간값보다 현재값이 클 때
				// 바로 넣기
				if (curNum >= centerNum)
					rightQueue.add(curNum);
				// 1-2. 현재값이 leftQueue의 최대값보다 더 낮을 때
				// leftQueue의 최대값이 중간값으로 정의됨
				else if (!leftQueue.isEmpty() && leftQueue.peek() > curNum) {
					rightQueue.add(centerNum);
					centerNum = leftQueue.poll();
					leftQueue.add(curNum);
				}
				// 1-3. 현재값이 중간값인 경우
				else {
					rightQueue.add(centerNum);
					centerNum = curNum;
				}
			}
			// 2. leftQueue의 크기가 rightQueue의 크기보다 1 작을 때
			// 무조건 leftQueue에 값을 넣어야 함
			else {
				// 2-1. 중간값보다 현재값이 작을 때
				// 바로 넣기
				if (curNum <= centerNum)
					leftQueue.add(curNum);
				// 2-2. 현재값이 rightQueue의 최소값보다 더 클 때
				// rightQueue의 최소값이 중간값으로 정의됨
				else if (!rightQueue.isEmpty() && rightQueue.peek() < curNum) {
					leftQueue.add(centerNum);
					centerNum = rightQueue.poll();
					rightQueue.add(curNum);
				}
				// 2-3. 현재값이 중간값인 경우
				else {
					leftQueue.add(centerNum);
					centerNum = curNum;
				}
			}
			
			sb.append(centerNum + "\n");
		}
		
		bw.write(sb.toString());
		bw.flush();
		bw.close();
	}
	
	static boolean isSameSize() {
		if (leftQueue.size() == rightQueue.size())
			return true;
		else
			return false;
	}
}
