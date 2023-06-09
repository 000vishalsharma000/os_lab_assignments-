import java.util.*;

public class memory_allocation_technique {
    
        static class node{

        int loc_idx;
        String file_name;
        node next;

        node(int idx, String f_name){
            this.loc_idx=idx;
            this.file_name=f_name;
            this.next=null;

        }

    }
    static node add_node(node lst, node new_node){

        new_node.next=lst;
        lst=new_node;
        return lst;

        }
    static void print_list(node linked_list){
        node lst= linked_list;
        while(lst!=null){
            System.out.print("("+ lst.loc_idx+" , "+ lst.file_name+ ")-->");
            lst=lst.next;
            
        }
        System.out.println("null");
    }

    static int[] random_free_idxs(String[] mem,  Object[] file){


        int file_size= (Integer)file[1];
        String file_name= (String)file[0];

        ArrayList<Integer> free_idx= new ArrayList<>();
        for (int i = 0; i < mem.length; i++) {
            if(mem[i]=="*"){
                free_idx.add(i);
            }
        } 
        
        if(free_idx.size()<file_size){
            System.out.println("not enough space in memory for file  "+ file_name);
            return new int[]{};
        }else{
            ArrayList<Integer> rand_indexes= new ArrayList<>();
            Random rand = new Random();
            for (int i = 0; i < file_size; i++) {
                int r_idx=rand.nextInt(free_idx.size());
                rand_indexes.add(free_idx.get(r_idx));
                free_idx.remove(r_idx);

            }

            int[] arr = rand_indexes.stream().mapToInt(i -> i).toArray();
            return arr;
        }

    }

    static int[] random_free_idxs_and_IndexBlock(String[] mem,  Object[] file){


        int file_size= (Integer)file[1];
        String file_name= (String)file[0];

        ArrayList<Integer> free_idx= new ArrayList<>();
        for (int i = 0; i < mem.length; i++) {
            if(mem[i]=="*"){
                free_idx.add(i);
            }
        } 
        
        if(free_idx.size()<file_size+1){
            System.out.println("not enough space in memory for file  "+ file_name);
            return new int[]{};
        }else{
            ArrayList<Integer> rand_indexes= new ArrayList<>();
            Random rand = new Random();
            for (int i = 0; i < file_size +1; i++) {
                int r_idx=rand.nextInt(free_idx.size());
                rand_indexes.add(free_idx.get(r_idx));
                free_idx.remove(r_idx);

            }
            
            int[] arr = rand_indexes.stream().mapToInt(i -> i).toArray();
            return arr;
        }

    }

    static int suitable_idx(ArrayList<Object> memory, Object[] file){
        int start_idx=(Integer)file[1];
        System.out.println("starting index of file " + file[0]+ " is "+ file[1]);

       
       if(memory.size()==0){
        return start_idx;
       }else if(start_idx>=memory.size()){
        return start_idx;
       }
       
       else{
           int free_mem=0;
        for (int i = start_idx ; i < memory.size(); i++) {
            if(memory.get(i)=="*"){
                free_mem+=1;
            }else{
                free_mem=0;
            }

            if(free_mem==(Integer)file[2]){
                return i-(Integer)file[2] +1;
            }
        }
            
       }
       System.out.println("no free space ");
       return -1;
    }
    
    static boolean is_continuos_allocation_possible(ArrayList<Object> memory, Object[] file){

        int start_idx_file = (Integer)file[1];
        if(start_idx_file >=file.length-1){
            return true;

        }

        for(int i= (Integer) file[1]; i< (Integer) file[2]; i++ ){
             if(memory.get(i)!="*"){
                return false;
             }
        }

        return true;

    }
    static public void continuos_allocation(Object[] file, ArrayList<Object> memory) {
        
        
        String file_name= (String)file[0];
        int start_idx= (Integer)file[1];
        int file_len = (Integer) file[2];
        int filled_mem= memory.size();


    if(is_continuos_allocation_possible(memory, file)){
        int s_idx= suitable_idx(memory, file);
        System.out.println("suitable idx : " + s_idx);
        if(s_idx==-1){
            System.out.println("cant alocate " + file_name+ " at location "+ start_idx + " to "+ (start_idx + file_len +1));
            return;
        }
        if(s_idx>=0 && s_idx<filled_mem){
            for (int i = s_idx; i < s_idx + file_len ; i++) {
                memory.set(i,file_name);
    
            }
        }else{
            for (int i = 0; i < start_idx- filled_mem ; i++) {
                memory.add("*");
                
            }
            for (int i = 0; i < file_len; i++) {
                memory.add(file_name);
                
            }
        }


                
    }


        System.out.println(memory);

         
    }

    static node linked_list_allocation(String[] ll_allocation_memory,Object[] ll_file){
        

        int[] rand_free_idx_arr = random_free_idxs(ll_allocation_memory, ll_file);

        node link_lst= null;

         for (int i = 0; i < rand_free_idx_arr.length; i++) {
            link_lst=add_node(link_lst, new node(rand_free_idx_arr[i],(String) ll_file[0]+i));
            ll_allocation_memory[rand_free_idx_arr[i]]=(String) ll_file[0]+i;
         } 

         return link_lst;
    }

    static String Indexblock_allocation(String[] ll_allocation_memory,Object[] ll_file){
        

        int[] rand_free_idx_arr = random_free_idxs_and_IndexBlock(ll_allocation_memory, ll_file);

        node link_lst= null;

        String file_indexes_str =(String)ll_file[0]+ ":";

         for (int i = 1; i < rand_free_idx_arr.length; i++) {
            link_lst=add_node(link_lst, new node(rand_free_idx_arr[i],(String) ll_file[0]+i));
            ll_allocation_memory[rand_free_idx_arr[i]]=(String) ll_file[0]+i;
         } 

         print_list(link_lst);

         while(link_lst!=null){
            file_indexes_str+=link_lst.loc_idx+"|";
            link_lst=link_lst.next;
         }

         String IndexBlock = file_indexes_str.substring(0,file_indexes_str.length()-1);
         ll_allocation_memory[rand_free_idx_arr[0]]=IndexBlock;
         
         return IndexBlock;
         
    }


    public static void main(String[] args) {

// continuos allocation__________________________________________________________________________________
        System.out.println("CONTINUOS ALLOCATION");

        ArrayList<Object> memory=new ArrayList<>();
        System.out.println("initial memory state : "+ memory);

        Object[ ] file ={ "a", 3 , 4};
        continuos_allocation(file, memory);

        Object[] file2 ={"b", 0, 2 };
        continuos_allocation(file2, memory);

        Object[] file3= {"c", 2, 3};
        continuos_allocation(file3, memory);

        continuos_allocation(new Object[] {"d", 9, 3}, memory);



// linklist allocation ______________________________________________________________________________________

        System.out.println("LINKED LIST ALLOCATION ");
         
        String[] ll_allocation_memory ={"*","x","*","*","y","*","*","*", "z","*","*","*","*","*","*" };
        System.out.println("initial memory state : " + Arrays.toString(ll_allocation_memory));

         Object[] ll_file= {"a", 3};
         Object[] ll_file2= {"b", 5};
         Object[] ll_file3= {"c", 7};


        node ll = linked_list_allocation(ll_allocation_memory, ll_file);
        print_list(ll);
        System.out.println(Arrays.toString(ll_allocation_memory));


        ll = linked_list_allocation(ll_allocation_memory, ll_file2);
        print_list(ll);
        System.out.println(Arrays.toString(ll_allocation_memory));


        ll = linked_list_allocation(ll_allocation_memory, ll_file3);
        print_list(ll);
        System.out.println(Arrays.toString(ll_allocation_memory));

// indexed allocation _______________________________________________________________________________________
        
        System.out.println("INDEX BLOCK ALLOCATION ");
        // String str= "a:2,5,9";
        // String[] str_arr = str.split("[:,]+");
        // System.out.println(Arrays.toString(str_arr));

        String[] IB_allocation_memory ={"*","x","*","*","y","*","*","*", "z","*","*","*","*","*","*" };
        

        Object[] IB_file= {"a", 2};

        System.out.println("initial memory state : " + Arrays.toString(IB_allocation_memory));



        String index_block= Indexblock_allocation(IB_allocation_memory, IB_file);
        System.out.println(index_block);
        System.out.println(Arrays.toString(IB_allocation_memory));


    }
}
