public class multi_level_queue {
    //highest priority- system process - rr
    //medium priority - interactive process - sjf
    //lowest priority -batch process - fcfs  

    public static void main(String[] args) {
     // for all system processes , which will execute first 
    round_robin.rr_algo(round_robin.p_count, round_robin.arv_tm, round_robin.burst_tm, round_robin.curr_tm, round_robin.time_quantum);
    System.out.println("all system processes are executed \n");

    // for interactive processes 
    SJF.sjf_algo(SJF.process_count, SJF.arr_time, SJF.burst_time, SJF.curr_time);
    System.out.println("all interactive  processes are executed \n");

    //batch process will be executed after system processes and interactive process completion only 
    fcfs.fcfs_algo(fcfs.p_count, fcfs.arv_tm, fcfs.burst_tm, fcfs.curr_tm);
    System.out.println("all batch processes are executed \n");


    }
}
