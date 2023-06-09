import java.util.Arrays;

//1. first fit : assign memory to the process to the first hole 
//               that is big enough to accomodate the memory size of the process 

//2. next fit :  start searching for the first hole 
//               in which the memory needed by a process need to be allocated 
//               from where previous memory allocation end

//3. best fit : search for the hole 
//              that  will left least memory left in hole 
//              after allocation of memory for that process in that hole 
//              (minimum internal fragmentation )

//4. worst fit : opposite of best fit
//               search for the  hole that will left max memory space 
//               after memory allocation for that process 



public class memory_management {

    static void first_fit(int[] arr, int[] process_sizes){
        System.out.println("first fit");
        int [] c_arr=Arrays.copyOf(arr, arr.length);

    for (int j = 0; j < process_sizes.length; j++) {
        
    
        for (int i = 0; i < c_arr.length; i++)
         {
            if(c_arr[i]>=process_sizes[j])
            {
                c_arr[i]=c_arr[i]-process_sizes[j];
                System.out.println("memory state after allocating "+ j + "th process : " + Arrays.toString(c_arr));
                break;
            }
        }

    }
 }
 static void next_fit(int[] arr, int[] process_sizes){
    System.out.println("next fit");
    int [] c_arr=Arrays.copyOf(arr, arr.length);
    

    for (int j = 0; j < process_sizes.length; j++) {
        int hole_idx_prev_process=0;
    
        for (int i = (hole_idx_prev_process%c_arr.length); i < c_arr.length; i++)
         {
            if(c_arr[i]>=process_sizes[j])
            {
                c_arr[i]=c_arr[i]-process_sizes[j];
                System.out.println("memory state after allocating "+ j + "th process : " + Arrays.toString(c_arr));
                hole_idx_prev_process=i;
                break;
            }
        }

    }
 }

 static void best_fit(int[] arr, int[] process_sizes){
    System.out.println("best fit");
    int [] c_arr=Arrays.copyOf(arr, arr.length);

    for (int j = 0; j < process_sizes.length; j++) {
        int least_left_space=Integer.MAX_VALUE;
        int best_hole_idx=-1;  
    
        for (int i = 0; i < c_arr.length; i++)
         {
            if(c_arr[i]-process_sizes[j]< least_left_space && c_arr[i]-process_sizes[j]>=0)
            {
                least_left_space=c_arr[i]-process_sizes[j];
                best_hole_idx=i;
            }
        }
        c_arr[best_hole_idx]-=process_sizes[j];
        System.out.println("memory state after allocating "+ j + "th process : " + Arrays.toString(c_arr));
    }
 }

 static void worst_fit(int[] arr, int[] process_sizes){

    System.out.println("worst fit");
    int [] c_arr=Arrays.copyOf(arr, arr.length);

    for (int j = 0; j < process_sizes.length; j++) {
        int least_left_space=Integer.MIN_VALUE;
        int best_hole_idx=-1;  
    
        for (int i = 0; i < c_arr.length; i++)
         {
            if(c_arr[i]-process_sizes[j]> least_left_space)
            {
                least_left_space=c_arr[i]-process_sizes[j];
                best_hole_idx=i;
            }
        }
        c_arr[best_hole_idx]-=process_sizes[j];
        System.out.println("memory state after allocating "+ j + "th process : " + Arrays.toString(c_arr));
    }
 }

    public static void main(String[] args) {
    int [ ] ini_mem_state={0,1,0,0,0,2,3,4,5};
    int [ ] process_sizes={3,2,1};

    first_fit(ini_mem_state, process_sizes);
    next_fit(ini_mem_state, process_sizes);
    best_fit(ini_mem_state, process_sizes);
    worst_fit(ini_mem_state, process_sizes);

    
    }
}
