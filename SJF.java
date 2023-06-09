// non preemtive SJF
import java.util.*;

public class SJF {
    static int process_count = 5;
    static int[] arr_time =   { 2, 5, 1, 0, 4 };
    static int[] burst_time = { 6, 2, 8, 3, 4 };
    static int curr_time = 0;
    // total turn around time=completion time - arrival time
    // waiting time=total turn around time - burst time
    // Response Time (Ri) = Sum of completion times of all previous processes - Arrival Time (Ai)

    static int findIndex(int[] arr, int key) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == key) {
                return i;
            }
        }
        return -1;
    }
    static int min(int[] arr){
        Arrays.sort(arr);
        return arr[0];
    }

public static void sjf_algo(int process_count, int[] arr_time, int[] burst_time, int curr_time ){
    int[] c_arr_time= Arrays.copyOf(arr_time, process_count);
        
    var comp_time = new ArrayList<p_t_pair>();
    var ready_q = new TreeSet<Integer>();
    for (int i = 0; i < burst_time.length; i++) {
        for (int p = 0; p < arr_time.length; p++) {
            if (arr_time[p] <= curr_time) {
                ready_q.add(burst_time[p]);

            }
        }
        //System.out.println(ready_q);
        int nxt_shortest_job_time = ready_q.pollFirst();
        // System.out.println(nxt_shortest_job_time);
        var pid_completed = findIndex(burst_time, nxt_shortest_job_time);
        // System.out.println(pid_completed);
        comp_time.add(new p_t_pair(pid_completed , nxt_shortest_job_time));
        curr_time += nxt_shortest_job_time;
        arr_time[pid_completed] = Integer.MAX_VALUE;

    }
    for (int i = 0; i < c_arr_time.length; i++) {
        arr_time[i]=c_arr_time[i];
        
    }
    for (int i = 1; i < comp_time.size(); i++) {
        comp_time.get(i).completionTime+=comp_time.get(i-1).completionTime;
    }
    System.out.println("gantt chart: "+comp_time);
    var c_comp_time= new ArrayList<p_t_pair>(comp_time);
    
    Collections.sort(c_comp_time,new Comparator<p_t_pair>() {
        public int compare(p_t_pair o1, p_t_pair o2){
           return  o1.p_no- o2.p_no;
        }
        
    });

    var ptm = new ArrayList<ArrayList<Integer>>();
    // ptm --> process time matrix

    for (int i = 0; i < process_count; i++) {  

                                                                       // index
        ptm.add(new ArrayList<Integer>());
        ptm.get(i).add(i);                                            // 0-->process id
        ptm.get(i).add(arr_time[i]);                                  // 1--> arrival time
        ptm.get(i).add(burst_time[i]);                                // 2--> burst time(total execution time )
        ptm.get(i).add(c_comp_time.get(i).completionTime-arr_time[i]);// 3-->total turn around time
        ptm.get(i).add(ptm.get(i).get(3)-burst_time[i]);       // 4--> wait time
        
        
    }
    
    ptm.get(comp_time.get(0).p_no).add(0);
    for (int i = 1; i < process_count; i++) {
        int pid=comp_time.get(i).p_no;
        ptm.get(pid).add(comp_time.get(i-1).completionTime-arr_time[pid]) ; //5--> response time 
    }

    System.out.println(ptm);
}
    public static void main(String[] args) {

        sjf_algo(process_count, arr_time, burst_time, curr_time);

    }
}

class p_t_pair {
    // p_t_pair == process, completion time pair 
    //ArrayList of p_t_pair will be  utilised to print gantt chart
    int p_no;
    int completionTime;

    p_t_pair(int pi, int t) {
        this.p_no = pi;
        this.completionTime = t;
    }

    public String toString() {
        return "(p" + p_no + "," + completionTime + ")";
    }

}
