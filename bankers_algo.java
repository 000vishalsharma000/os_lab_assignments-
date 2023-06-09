import java.util.Arrays;

public class bankers_algo {

    static void bankers_algorithm(int[] instanceCount_of_resources, String[] resources,int[][] resource_allocated,int[][] resource_request, String[] processes ){
        // NECESSARY CONDITION OF DEADLOCK:
        // 1. Mutual Exclusion: Two or more resources are non-shareable (Only one process can use at a time) 
        // 2. Hold and Wait: A process is holding at least one resource and waiting for resources. 
        // 3. No Preemption: A resource cannot be taken from a process unless the process releases the resource. 
        // 4. Circular Wait: A set of processes are waiting for each other in circular form. 
    
        // using bankers algorithm to detect deadlock
        //link to video : https://youtu.be/7gMLNiEz3nw  
    
    
            System.out.println("\ninitial syestem resources are : ");
            for (int i = 0; i < resources.length; i++) {
                System.out.print(resources[i] +"\t");
            }
            System.out.println();
            for (int i = 0; i < instanceCount_of_resources.length; i++) {
                System.out.print(instanceCount_of_resources[i] +"\t");
            }
    
    
    
            while(! is_all_req_processed(resource_request)){
                int count=0;
            for (int i = 0; i < resource_request.length; i++) {
                if(resource_request[i]!=null && is_current_resource_vector_greater(instanceCount_of_resources,resource_request[i])){
                    resource_vector_addition(instanceCount_of_resources, resource_allocated[i]);
                    count+=1;
                    resource_request[i]=null;
                    System.out.println(processes[i] + "'s all resource request is processed ");
                    System.out.println("current available resources: "+Arrays.toString(instanceCount_of_resources));

                }
                
                
    
            }
            if(count==0){
                System.out.println("\nthere is a deadlock");
                return;
            }
        }
            
            
            System.out.println("\nall request processed");
         }
    
         static boolean is_all_req_processed(int[][] resource_request){
            for (int i = 0; i < resource_request.length; i++) {
                if(resource_request[i]!=null){
                    return false;
                }
            }
            return true ;
         }
    
         static void resource_vector_addition(int[] current_resource_vector , int[] resource_request_vector ){
            for (int i = 0; i < resource_request_vector.length; i++) {
                
                    current_resource_vector[i]+=resource_request_vector[i];
                
                
            }
        }
    
        
        static boolean is_current_resource_vector_greater(int[] current_resource_vector,int[] resource_request_vector ){
            for (int i = 0; i < current_resource_vector.length; i++) {
                if(current_resource_vector[i]-resource_request_vector[i]<0){
                    return false;
                }
            }
            
            return true;
         }


    public static void main(String[] args) {
    // 1. list of resources 
    String[] resources= {"r1", "r2", "r3", "r4"};
    // 2.instance of each resource 
    int[] instanceCount_of_resources= {1,2,3,4};
    // 3.list of processes 
    String[] processes= {"p1", "p2", "p3", "p4"};
    // 4. resource allocated by each process 
    int[][] resource_allocated = {{1,0,0,0},{0,1,0,0},{0,0,1,0},{0,0,0,1}};
    // 5. resource request by each process 
    int[][] resource_request = {{0,0,1,0},{0,0,0,2},{0,0,0,0},{0,0,0,5}};

    bankers_algorithm(instanceCount_of_resources, resources, resource_allocated,resource_request,processes);
    }
}
