import java.util.*;

public class round_robin {

    static int p_count = 5;
    //process id       0, 1, 2, 3, 4
    static int[] arv_tm =   { 3, 2, 4, 5, 8 };
    static int[] burst_tm = { 3, 2, 1, 6, 8 };
    static int curr_tm = 0 ;
    static int time_quantum=2;

    // total turn around time=completion time - arrival time
    // waiting time=total turn around time - burst time
    // Response Time (Ri) = Sum of completion times of all previous processes - Arrival Time (Ai)

    static int fst_arvd_pid(int[] at){ 
        int pid=0;
        
        for (int i = 0; i < at.length; i++) {
            if(at[i]<at[pid]){
                pid=i;
            }
            


            else if(at[i]==at[pid] ){
                if(i<pid){
                    pid=i;
                }
            }

        }
        return pid;
    }

    static int pid_in_rq_in_seq(LinkedHashSet<Integer> r_q){
        if(r_q.size()==0){
            return -1;
        }
        ArrayList<Integer> ready_q= new ArrayList<Integer>(r_q);

             
             
            int nxt_pid= ready_q.get(0);

            return nxt_pid;

    }
    public static void rr_algo(int p_count, int[] arv_tm, int[] burst_tm, int curr_tm, int time_quantum  ){
        LinkedHashSet<Integer> r_q=new LinkedHashSet<>();
        ArrayList<Integer> pro_comp_tm= new ArrayList<>();
        ArrayList<Integer> gchart= new ArrayList<>();
        ArrayList<Integer> pro_arr_tm=new ArrayList<>();
        LinkedHashSet<pid_rspTm_pair> fst_tm_cpu_accessTime_seq =new LinkedHashSet<>();
    
        int fid=fst_arvd_pid(arv_tm);
        int arv_tm_fst_pro=arv_tm[fid]; 
        curr_tm+=arv_tm_fst_pro;
        
        String g_chart_str= "";
        int prev_curr_time=-1;
    
        while (gchart.size()<p_count) {
    
            for (int j = 0; j < arv_tm.length; j++) {
                if(arv_tm[j]<=curr_tm){
                    r_q.add(Integer.valueOf(j));
                    arv_tm[j]=Integer.MAX_VALUE;
                }   
            }
            
            System.out.println("processes in ready queue : "+ r_q);
        
            int nxt_pid= pid_in_rq_in_seq(r_q);
    
            if(nxt_pid==-1){
                System.out.println("ready queue is empty");
    
                nxt_pid=fst_arvd_pid(arv_tm);
                System.out.println("next process with min arrival time is process : " + nxt_pid);
                System.out.println("process " + nxt_pid + " is transfered to ready queue ");
    
                r_q.add(nxt_pid);
                System.out.println("processes in ready queue : "+ r_q);
                int nxt_tm=arv_tm[nxt_pid];
                while(curr_tm<nxt_tm){
                    g_chart_str+= "idle|";
                    curr_tm++;
                }
                System.out.println("current time ="+ curr_tm);
                pro_arr_tm.remove(Integer.valueOf(prev_curr_time));
                pro_arr_tm.add(curr_tm);
                arv_tm[nxt_pid]=Integer.MAX_VALUE;
            }
    
            g_chart_str += nxt_pid + "|";
            fst_tm_cpu_accessTime_seq.add(new pid_rspTm_pair( nxt_pid , curr_tm));
    
            for(int i=0; i<time_quantum;i++){
    
                burst_tm[nxt_pid]-=1;
                System.out.println("running p" + nxt_pid + " for unit time ");
                curr_tm+=1;
                if(burst_tm[nxt_pid]==0){
                    break;
                }
            }
    
            System.out.println("remaining burst time of p" + nxt_pid + " is " + burst_tm[nxt_pid]);
            System.out.println("current time = "+ curr_tm);
            System.out.println();
    
           
    
    
    
            if(burst_tm[nxt_pid]==0 ){
                gchart.add(nxt_pid);
                r_q.remove(nxt_pid);
                System.out.println("P" + nxt_pid + " IS TERMINATED");
                System.out.println();
                pro_comp_tm.add(curr_tm);
                pro_arr_tm.add(curr_tm);
                prev_curr_time=curr_tm;
            }else{
                int first = r_q.stream().findFirst().get();
                r_q.remove(Integer.valueOf(first));
                r_q.add(Integer.valueOf(first));
            }
    
        }
    
        System.out.println("gantt chart: " + g_chart_str);
    }
   public static void main(String[] args) {

    rr_algo(p_count, arv_tm, burst_tm, curr_tm, time_quantum);
   

   } 
}
class pid_rspTm_pair implements Comparable<pid_rspTm_pair> {
    int id;
    int time;

    pid_rspTm_pair(int pid, int curr_time) {
        this.id = pid;
        this.time = curr_time;

    }

    @Override
    public int compareTo(pid_rspTm_pair o) {
        return this.id - o.id;
    }

    @Override
    public String toString() {
        return "(" + id + " , " + time + ")";
    }


    @Override 
    public boolean equals (Object o){
        if(this==o){
            return true;

        }
        if(o==null || getClass() != o.getClass()){
            return false;
        }
        pid_rspTm_pair prtm= (pid_rspTm_pair) o;
        return this.id== prtm.id;
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(id).hashCode();
    }

}
