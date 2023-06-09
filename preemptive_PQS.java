// preemptive priority queue scheduling 

import java.util.*;

public class preemptive_PQS {
    // to find first arrived process with max priority and least pid
    static int fst_arvd_pid_wt_mx_pri(int[] at, int[] pri){ 
        int pid=0;
        
        for (int i = 0; i < at.length; i++) {
            if(at[i]<at[pid]){
                pid=i;
            }
            
            else if(at[i]==at[pid]){
                if(pri[i]> pri[pid]){
                    pid=i;
                }
            }

            else if(at[i]==at[pid] && pri[i]== pri[pid]){
                if(i<pid){
                    pid=i;
                }
            }

        }
        return pid;
    }

    // to find process in ready queue with max priority, 
    //if two or more process have max priority then selection of process is based on (min pid)
    static int pid_in_rq_wt_mx_pri(TreeSet<Integer> r_q, int[] pri, int[] at){
        if(r_q.size()==0){
            return -1;
        }
        ArrayList<Integer> ready_q= new ArrayList<Integer>(r_q);

             int pid=ready_q.get(0);  //assuming pid with max priority in ready queue 
             int priority=pri[pid];
             int arv_tm= at[pid];
             
             for (int i = 0; i < ready_q.size(); i++)
              {
                int pid_in_rq=ready_q.get(i);
                if(pri[pid_in_rq]> priority){
                    pid=pid_in_rq;
                    priority=pri[pid_in_rq];
                    arv_tm=at[pid];
                }
                
                else if(pri[pid_in_rq]== priority ){
                    if(at[pid_in_rq]<arv_tm ){
                        pid=pid_in_rq;
                        priority=pri[pid_in_rq];
                        arv_tm=at[pid];
                    }

                }

                else if(pri[pid_in_rq]== priority && at[i]==arv_tm){
                    if(pid_in_rq<pid){
                        pid=pid_in_rq;
                        priority=pri[pid_in_rq];
                        arv_tm=at[pid];
                    }
                }

             }


             return pid;

    }

    static void print_gchart(ArrayList<Integer> ith_pro_in_gchrt, ArrayList<Integer> comp_tm_ith_pro_in_gchrt, int[] at, int[] pri, ArrayList<Integer> arr_tm_ith_pro_in_gchrt, ArrayList<pid_rspTm_pair> lst_fstTimeCpuAccessTime)
    {
        int fid =fst_arvd_pid_wt_mx_pri(at, pri);
        int arv_tm_fst_pro=at[fid]; 
        
        System.out.println("GANTT CHART");
        System.out.println("(0-"+ arv_tm_fst_pro+ ") --> cpu idle");
        

        for (int i = 0; i < at.length; i++) {
         System.out.println(" ("+  resp_tm_ith_pro_in_gcht(i, ith_pro_in_gchrt, lst_fstTimeCpuAccessTime)+" , "  + comp_tm_ith_pro_in_gchrt.get(i) +")--> " +ith_pro_in_gchrt.get(i)  );
         if(i!=at.length-1){
            if(comp_tm_ith_pro_in_gchrt.get(i)!=arr_tm_ith_pro_in_gchrt.get(i+1)){
                System.out.println("("+ comp_tm_ith_pro_in_gchrt.get(i) + " , "+ arr_tm_ith_pro_in_gchrt.get(i+1)+ ")" + "--> cpu idle ");   
            }
         }
        }

        System.out.println(" all process terminated");

    }

    static void print_pro_tm_table(ArrayList<ArrayList<Integer>> ptm){

        System.out.println();
        System.out.println("pid"+"\t"+"at"+"\t"+ "bt" + "\t" + "prt" + "\t" + "tat"+ "\t" +"wt"+ "\t" +"rt");
        for (int i = 0; i < ptm.size(); i++) {
        System.out.println(ptm.get(i).get(0)+"\t"+ptm.get(i).get(1)+"\t"+ ptm.get(i).get(2) + "\t" + ptm.get(i).get(3) + "\t" + ptm.get(i).get(4)+ "\t" +ptm.get(i).get(5)+ "\t" +ptm.get(i).get(6));   
        }
    }
    static int comp_tm_of_all_prev_pro(int i, ArrayList<Integer> pid_comp_tm_lst, ArrayList<Integer> g_cht, int[] bst_tm){
        
     int idx_of_ith_pro_in_gchart=-1;
     for (int j = 0; j < g_cht.size(); j++) {
        if(i==g_cht.get(j)){
            idx_of_ith_pro_in_gchart=j;
        }
     }
     return (pid_comp_tm_lst.get(idx_of_ith_pro_in_gchart)-bst_tm[i]) ;
        
    }

    static int idx_of_ith_pro_in_gchart(int i, ArrayList<Integer> g_cht){
        int idx=-1;
        for (int j = 0; j < g_cht.size(); j++) {
           if(g_cht.get(j)==i){
               idx=j;
           }
        }
        return idx;   
    }

    static int nxt_pro(int cr_tm, int[] pri, int[] at){
        TreeSet<Integer> r_q =new TreeSet<>();
        for (int i = 0; i < at.length; i++) {
            if(at[i]==cr_tm){
                r_q.add(i);
            }
        }
        if(r_q.isEmpty()){
            return -1;
        }

        int id = pid_in_rq_wt_mx_pri(r_q, pri, at);

        return id;
    }

    static int resp_tm_ith_pro_in_gcht(int i, ArrayList<Integer> g_cht, ArrayList<pid_rspTm_pair> lst_fstTime_cpu_access_time){
        int pid_at_ith_idx_in_gchart=g_cht.get(i);
         int resp_time=-1;
        for (int j = 0; j < lst_fstTime_cpu_access_time.size(); j++) {
            if(pid_at_ith_idx_in_gchart==lst_fstTime_cpu_access_time.get(j).id){
                 resp_time=lst_fstTime_cpu_access_time.get(j).time;
            }
        }

        return resp_time;
    }

    // static void print_gchart2(String g_cht_str){
    //     String [] arr_of_pid= g_cht_str.split("\\|");
    //     System.out.println(Arrays.toString(arr_of_pid));
    //        String pid=arr_of_pid[0];
           
    //        int count=1; 
    //        System.out.println(count + " --> p"+arr_of_pid[0]);
    //     for (int i = 0; i < arr_of_pid.length; i++) {
    //         if(pid.equals(arr_of_pid[i])){
    //             count+=1;
    //             System.out.println(count);
    //         }else{
    //             System.out.println( count+1 +" --> "+"p"+ arr_of_pid[i]  );
    //             pid=arr_of_pid[i];
    //             count+=1;
                
    //         }


    //     }
    // }
    public static void main(String[] args) {
        int p_count = 5;
        //process id       0, 1, 2, 3, 4
        int[] arv_tm =   { 7, 1, 9, 23, 9 };
        int[] burst_tm = { 5, 5, 2, 8, 2 };
        int[] priority=  { 1, 4, 4, 3, 5 };
        int curr_tm = 0 ;
        // total turn around time=completion time - arrival time
        // waiting time=total turn around time - burst time
        // Response Time (Ri) = Sum of completion times of all previous processes - Arrival Time (Ai)  
        
        System.out.println("assumed initial time  = " + curr_tm);

        int[] c_arv_tm = Arrays.copyOf(arv_tm, p_count); 
        int[] c_burst_tm =Arrays.copyOf(burst_tm, p_count);
        int fid =fst_arvd_pid_wt_mx_pri(arv_tm, priority);
        int arv_tm_fst_pro=arv_tm[fid]; 
        curr_tm+=arv_tm_fst_pro;
        
        // Integer[ ] at= Arrays.stream(arv_tm).boxed().toArray(Integer[]:: new);// way to convert int[] to Integer[]




        var gchart= new ArrayList<Integer>();
        var r_pq=new TreeSet<Integer>();
        ArrayList<Integer> pro_comp_tm = new ArrayList<>(); //list of completion time of process in order in which they are executed
        ArrayList<Integer> pro_arr_tm=new ArrayList<>();
        LinkedHashSet<pid_rspTm_pair> fst_tm_cpu_accessTime_seq =new LinkedHashSet<>();

        pro_arr_tm.add(arv_tm_fst_pro);

        System.out.println("first process arrived at time = "+ curr_tm);
        int prev_curr_time=-1;
        
        String g_chart_str= "";

        while (gchart.size()<p_count) {

            for (int j = 0; j < arv_tm.length; j++) {
                if(arv_tm[j]<=curr_tm){
                    r_pq.add(Integer.valueOf(j));
                    arv_tm[j]=Integer.MAX_VALUE;
                }   
            }

            System.out.println("processes in ready queue : "+ r_pq);

            
            int nxt_pid= pid_in_rq_wt_mx_pri(r_pq, priority, arv_tm);
            if(nxt_pid==-1){
                System.out.println("ready queue is empty");

                nxt_pid=fst_arvd_pid_wt_mx_pri(arv_tm, priority);
                System.out.println("next process with min arrival time is process : " + nxt_pid);
                System.out.println("process " + nxt_pid + " is transfered to ready queue ");

                r_pq.add(nxt_pid);
                System.out.println("processes in ready queue : "+ r_pq);
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

            burst_tm[nxt_pid]-=1;
            System.out.println("running p" + nxt_pid + " for unit time ");
            curr_tm+=1;
            System.out.println("remaining burst time of p" + nxt_pid + " is " + burst_tm[nxt_pid]);
            System.out.println("current time = "+ curr_tm);
            System.out.println();

           



            if(burst_tm[nxt_pid]==0 ){
                gchart.add(nxt_pid);
                r_pq.remove(nxt_pid);
                System.out.println("P" + nxt_pid + " IS TERMINATED");
                System.out.println();
                pro_comp_tm.add(curr_tm);
                pro_arr_tm.add(curr_tm);
                prev_curr_time=curr_tm;
            }

            
            

            

            
        }
        ArrayList<pid_rspTm_pair> lst_of_pid_fstTimeCpuAccessTime = new ArrayList<>(fst_tm_cpu_accessTime_seq);
        Collections.sort(lst_of_pid_fstTimeCpuAccessTime);

        System.out.println();


        print_gchart(gchart, pro_comp_tm, c_arv_tm, priority,pro_arr_tm, lst_of_pid_fstTimeCpuAccessTime);
        
    ArrayList<ArrayList<Integer>> ptm= new ArrayList<ArrayList<Integer>>();


    for (int i = 0; i < p_count; i++) {
        ptm.add(new ArrayList<>());
        ptm.get(i).add(i);
        ptm.get(i).add(c_arv_tm[i]);
        ptm.get(i).add(c_burst_tm[i]);
        ptm.get(i).add(priority[i]);
        ptm.get(i).add(pro_comp_tm.get(idx_of_ith_pro_in_gchart(i, gchart))-c_arv_tm[i]);
        ptm.get(i).add(ptm.get(i).get(4)- c_burst_tm[i]);
        ptm.get(i).add(lst_of_pid_fstTimeCpuAccessTime.get(i).time - c_arv_tm[i]);


    }

    print_pro_tm_table( ptm);
    System.out.println(lst_of_pid_fstTimeCpuAccessTime);
    System.out.println("gantt chart : "+ g_chart_str);
    
    
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