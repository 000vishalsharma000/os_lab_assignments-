// non preemptime first come first serve
import java.util.*;
public class fcfs{
    public static int findIndex(int[] arr, int key) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == key) {
                return i;
            }
        }
        return -1;
    }
    static int p_count = 5;
    static int[] arv_tm =   { 3, 2, 4, 5, 8 };
    static int[] burst_tm = { 3, 2, 1, 6, 8 };
    static int curr_tm = 0 ;
    // total turn around time=completion time - arrival time
    // waiting time=total turn around time - burst time
    // Response Time (Ri) = Sum of completion times of all previous processes - Arrival Time (Ai)
public static void fcfs_algo(int p_count, int[] arv_tm, int[] burst_tm, int curr_tm){
    Queue<Integer> pq_arv_tm= new PriorityQueue<>();

    for (int i = 0; i < p_count; i++) {
        pq_arv_tm.offer(Integer.valueOf(arv_tm[i]));
    }
    System.out.println(pq_arv_tm);

    List<p_t_pair> g_chart= new ArrayList<>();


    for (int i = 0; i <p_count; i++) {
        
       int idx= findIndex(arv_tm, pq_arv_tm.poll()) ; // index of shortest job
       curr_tm+=burst_tm[idx];
        g_chart.add(new p_t_pair(idx, burst_tm[idx] + curr_tm ));
        
    }

    System.out.println(g_chart);

    Collections.sort(g_chart, new Comparator<p_t_pair>() {
        public int compare(p_t_pair o1, p_t_pair o2){
            return  o1.p_no- o2.p_no;
        }
        
    });

    ArrayList<ArrayList<Integer>> ptm= new ArrayList<ArrayList<Integer>>();
    for (int i = 0; i < p_count; i++) {
        ptm.add(new ArrayList<>());
        ptm.get(i).add(Integer.valueOf(i));
        ptm.get(i).add(Integer.valueOf(arv_tm[i]));
        ptm.get(i).add(Integer.valueOf(burst_tm[i]));
        ptm.get(i).add(Integer.valueOf(g_chart.get(i).completionTime- arv_tm[i]));
        ptm.get(i).add(Integer.valueOf(ptm.get(i).get(3)- burst_tm[i]));



    }

    ptm.get(g_chart.get(0).p_no).add(0);
    for (int i = 1; i < p_count; i++) {
        int pid=g_chart.get(i).p_no;
        ptm.get(pid).add(g_chart.get(i-1).completionTime-arv_tm[pid]) ; //5--> response time 
    }

    System.out.println(ptm);
}
    public static void main(String[] args) {

        fcfs_algo(p_count, arv_tm, burst_tm,curr_tm );

    }
}
public class p_t_pair  {
    // p_t_pair == process, completion time pair 
    //ArrayList of p_t_pair will be  utilised to print gantt chart
    int p_no;
    int completionTime;

    p_t_pair(int pi, int t) {
        this.p_no = pi;
        this.completionTime = t;
    }

    // @Override
    // public int compareTo(p_t_pair o) {
        
    //     return o.p_no - this.p_no;
    // }

    public String toString() {
        return "(p" + p_no + "," + completionTime + ")";
    }

}