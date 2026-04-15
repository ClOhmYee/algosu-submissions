import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    static int[] parent;
    static int[] size;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        
        int T = Integer.parseInt(br.readLine());
        
        for (int t = 0; t < T; t++) {
            int F = Integer.parseInt(br.readLine());
            
            parent = new int[F * 2];
            size = new int[F * 2];
            
            for (int i = 0; i < F * 2; i++) {
                parent[i] = i;
                size[i] = 1;
            }
            
            HashMap<String, Integer> friends = new HashMap<>();
            int id = 0;
            
            for (int f = 0; f < F; f++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                String p1 = st.nextToken();
                String p2 = st.nextToken();
                
                if (!friends.containsKey(p1)) friends.put(p1, id++);
                if (!friends.containsKey(p2)) friends.put(p2, id++);
                
                int root = union(friends.get(p1), friends.get(p2));
                
                sb.append(size[root]).append("\n");
            }
        }
        
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    static int find(int x) {
        if (parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }
    
    static int union(int x, int y) {
        x = find(x);
        y = find(y);
        
        if (x != y) {
            parent[y] = x;
            size[x] += size[y];
        }
        
        return x;
    }
}