
import java.util.*;

public class resource_allocation_graph {

    static void print_resource_allocation_graph(String[] resources, String[] processes, int[][] resource_allocated,
            int[] instanceCount_of_resources) {

        System.out.println(" allocation matrix : \n");
        int no_of_resources = resources.length;
        int no_of_processes = processes.length;

        String[][] res_allocation_matrix = new String[no_of_processes + 1][no_of_resources + 1];

        res_allocation_matrix[0][0] = "";
        for (int i = 1; i < res_allocation_matrix[0].length; i++) {
            res_allocation_matrix[0][i] = resources[i - 1];

        }
        for (int i = 1; i < res_allocation_matrix.length; i++) {
            res_allocation_matrix[i][0] = processes[i - 1];
        }
        for (int i = 1; i < res_allocation_matrix.length; i++) {
            for (int j = 1; j < res_allocation_matrix[i].length; j++) {
                res_allocation_matrix[i][j] = Integer.toString(resource_allocated[i - 1][j - 1]);
            }
        }

        printMatrix(res_allocation_matrix);
    }

    static void printMatrix(String[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

    static void request_adjacency_list(int[][] resource_request, String[] processes, String[] resources) {
        System.out.println("request adjacency list: \n");
        int no_of_processes = processes.length;
        ArrayList<String>[] req_list = new ArrayList[no_of_processes];// way to create array of ArrayList
        for (int i = 0; i < req_list.length; i++) {
            req_list[i] = new ArrayList<String>();
        }
        for (int i = 0; i < processes.length; i++) {
            for (int j = 0; j < resources.length; j++) {
                req_list[i].add("(" + resources[j] + " " + resource_request[i][j] + ")");
            }
        }

        print_adjacency_list(processes, req_list);

    }

    static void print_adjacency_list(String[] processes, ArrayList<String>[] req_list) {
        for (int i = 0; i < req_list.length; i++) {
            System.out.println(processes[i] + " : " + req_list[i]);
        }
    }

    static void is_deadlock(int[] instanceCount_of_resources, String[] resources, int[][] resource_allocated,
            int[][] resource_request) {
        // NECESSARY CONDITION OF DEADLOCK:
        // 1. Mutual Exclusion: Two or more resources are non-shareable (Only one
        // process can use at a time)
        // 2. Hold and Wait: A process is holding at least one resource and waiting for
        // resources.
        // 3. No Preemption: A resource cannot be taken from a process unless the
        // process releases the resource.
        // 4. Circular Wait: A set of processes are waiting for each other in circular
        // form.

        // using bankers algorithm to detect deadlock
        // link to video : https://youtu.be/7gMLNiEz3nw

        System.out.println("\ninitial syestem resources are : ");
        for (int i = 0; i < resources.length; i++) {
            System.out.print(resources[i] + "\t");
        }
        System.out.println();
        for (int i = 0; i < instanceCount_of_resources.length; i++) {
            System.out.print(instanceCount_of_resources[i] + "\t");
        }

        while (!is_all_req_processed(resource_request)) {
            int count = 0;
            for (int i = 0; i < resource_request.length; i++) {
                if (resource_request[i] != null
                        && is_current_resource_vector_greater(instanceCount_of_resources, resource_request[i])) {
                    resource_vector_addition(instanceCount_of_resources, resource_allocated[i]);
                    count += 1;
                    resource_request[i] = null;
                }

            }
            if (count == 0) {
                System.out.println("\nthere is a deadlock");
                return;
            }
        }

        System.out.println("\nall request processed");
    }

    static boolean is_all_req_processed(int[][] resource_request) {
        for (int i = 0; i < resource_request.length; i++) {
            if (resource_request[i] != null) {
                return false;
            }
        }
        return true;
    }

    static void resource_vector_addition(int[] current_resource_vector, int[] resource_request_vector) {
        for (int i = 0; i < resource_request_vector.length; i++) {

            current_resource_vector[i] += resource_request_vector[i];

        }
    }

    static boolean is_current_resource_vector_greater(int[] current_resource_vector, int[] resource_request_vector) {
        for (int i = 0; i < current_resource_vector.length; i++) {
            if (current_resource_vector[i] - resource_request_vector[i] < 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // 1. list of resources
        String[] resources = { "r1", "r2" };
        // 2.instance of each resource
        int[] instanceCount_of_resources = { 0, 0 };
        // 3.list of processes
        String[] processes = { "p1", "p2", "p3" };
        // 4. resource allocated by each process
        int[][] resource_allocated = { { 1, 0 }, { 0, 1 }, { 0, 0 } };
        // 5. resource request by each process
        int[][] resource_request = { { 0, 1, 0 }, { 1, 0, 0 }, { 0, 1, 0 } };

        print_resource_allocation_graph(resources, processes, resource_allocated, instanceCount_of_resources);
        // Arrays.deepToString(int[][]) converts 2D array to string in a single step.//
        // but it will show that in single line rather like a matrix
        request_adjacency_list(resource_request, processes, resources);

        is_deadlock(instanceCount_of_resources, resources, resource_allocated, resource_request);
    }
}
