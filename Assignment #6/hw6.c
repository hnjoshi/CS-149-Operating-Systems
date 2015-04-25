#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#include <sys/wait.h>
#include <unistd.h>
#include <fcntl.h>
#include <errno.h>

#define TOTAL_PIPES 5
#define TOTAL_DESC 2

int parent(int p[TOTAL_PIPES][TOTAL_DESC]) 
{
	   char buf[250], std[250];
       //Close the Write End of the Pipe.
       int i;
       for(i = 0; i < TOTAL_PIPES; i++) {       
	   	close(p[i][1]);
       }	

       fd_set master, tset;
       int nfd;

       FD_ZERO(&master);
       FD_SET(STDIN_FILENO, &master);

       for (i = 0; i < TOTAL_PIPES; i++) 
       {
       	FD_SET(p[i][0], &master);
       }

       tset = master;
       nfd = p[TOTAL_PIPES-1][0]+1;

       struct timeval start, end;
       gettimeofday(&start, NULL);

       int diff = 0;
       //While loop for 30 seconds. 
       while(diff < 30.) 
       {
       		gettimeofday(&end, NULL);
       		diff = end.tv_sec - start.tv_sec;

       		if(select(nfd, &tset, NULL, NULL, NULL) > 0) {
	       		if(FD_ISSET(STDIN_FILENO, &tset)) {
	       			printf("From stdin: ");
	       			read(STDIN_FILENO, std, 250);
	       			printf("%s\n", std);
	       		}


	       		for(i = 0; i < TOTAL_PIPES; i++) 
	       		{
	       			if(FD_ISSET(p[i][0], &tset)) {
	       				if(read(p[i][0], buf, 250) > 0) {
	       					int minDiff = (end.tv_sec - start.tv_sec) / 60;
	       					printf("%d:%s\n" , minDiff, buf);
	       				}
	       			}
	       		}

	       		tset = master;
       		}
       }
       return 0;
}

int children(int p[], int id) 
{
       //Close the Read End of the Pipe.
       close(p[0]);

       struct timeval start, end;
	   gettimeofday(&start, NULL);
	   int message = 1;
	   int diff = 0;
	   int microDiff; 
	   int randomTime;
	   char buffer[250];

	   while(diff < 30.) 
	   {
	   		gettimeofday(&end, NULL);
       		diff = end.tv_sec - start.tv_sec;
       		microDiff = (end.tv_usec - start.tv_usec) % 1000;
       		sprintf(buffer, "%02d.%03d Child %d message %d", diff, microDiff, id, message++);
       		write(p[1], buffer, 250);

       		randomTime = rand() % 3; 
       		sleep(randomTime);
	   }
       return 0;	
}

int main() 
{
	int pipes[TOTAL_PIPES][TOTAL_DESC];
	
	int i;
	for(i = 0; i < TOTAL_PIPES; i++) {
		if(pipe(pipes[i]) == -1) {
			printf("Calling to PIPE caused an Error.");
			exit(1);
		}
		
		int pid_t = fork();
		
		if(pid_t == 0) {
			/*CHILD PROCESS*/
			children(pipes[i], i);
		} else if (pid_t == -1) {
			printf("Calling to FORK caused an Error.");
			exit(1);
		}
	}
	parent(pipes);
	return 0;
}


/**
Notes:
1). Need to seperate stdIO in seperate function. 
2). Seperate out Child 5, and assign it to do stdIO.
**/