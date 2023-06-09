// C++ program to demonstrate calculation in parent and
// child processes using fork()

// orphan process: 
    // - process execute even after termination of its parent process 
    // - but only parent process can terminate its child process using its pid
    // - so when a parent process terminates , the child process became a child of init procress 
    // - init is kinda parent of all process 
    // - finally init terminates an former orphan process using its pid 
    // - has controlling terminal 
//zombie process 
    // - when child process terminates before parent process zombie process are created 
    // - as child finished its execution it stops utilising system resources and no longer performing any function
    // - but even after its execution is finished its pid is there in process table (process controll block still ther in memory )
    // - and only parent process can terminate the child process which is still executing
    // - so in this case child process is like a zombie as while it has already finished its execution it still exists in process table 
    // - has controlling terminal 

// deamon process 
    // - start working when system is booted 
    // - stop working when system shuts down 
    // - always run in the background 

// process table : (pid-> process contoll block)
// gfg_article_link: https://www.geeksforgeeks.org/difference-between-zombie-orphan-and-daemon-processes/

// fork() is not available on Windows operating system.

#include <bits/stdc++.h>
#include <iostream>
#include <unistd.h>

using namespace std;

// Driver code
int main()
{
	int arr[]={9,3,6,0,1};
    int arr_size=sizeof(arr)/sizeof(arr[0]);

    int n;
	n = fork();

	// Checking if n is not 0
	if (n > 0) {
        sort(arr, arr+n, greater<int>());
        
		cout << "Parent process \n";
		cout << "desc order " << arr << endl;
        wait(NULL);
	}

	// If n is 0 i.e. we are in child process
	else {
        sort(arr, arr+n);
		cout << "Child process \n";
		cout << "\n ascending order " << arr << endl;
        // wait(NULL);
	}
	return 0;
}
