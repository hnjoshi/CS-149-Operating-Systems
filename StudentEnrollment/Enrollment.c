#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <semaphore.h>
#include <signal.h>
#include <sys/time.h>

#define ID_BASE 101
#define STUDENT_COUNT 75
#define ENROLLMENT_DURATION 120

//studentId: are distinct values for each student thread
//priorityId: are 1,2,3 --> GS,RS,EE
//classId:    are 1,2,3 corresponding to class sections  
typedef struct {

	int studentID, priorityID, classID; 

}students; 


time_t startTime;
students class1[STUDENT_COUNT];
students class2[STUDENT_COUNT];
students class3[STUDENT_COUNT];

int totalCount  = 0; 
int class1Count = 0;
int class2Count = 0;
int class3Count = 0;
 




void print(char *event)
{
	
}


void *threadGS(void *param)
{
	return NULL;
}

void *threadRS(void *param)
{
	return NULL;
}

void *threadEE(void *param)
{
	return NULL;
}


// A student arrives.
void studentArrives(students *sd)
{

	int priority = sd->priorityID; 
	if(priority == 1) 
	{
		class1[class1Count].studentID = sd->studentID;
		class1[class1Count].priorityID = sd->priorityID;
		class1[class1Count].classID = sd->classID;
		class1Count++;			
	}
	else if(priority == 2) 
	{
		class2[class2Count].studentID = sd->studentID;
		class2[class2Count].priorityID = sd->priorityID;
		class2[class2Count].classID = sd->classID;
		class2Count++;			
	}
	else 
	{
		class3[class3Count].studentID = sd->studentID;
		class3[class3Count].priorityID = sd->priorityID;
		class3[class3Count].classID = sd->classID;
		class3Count++;			
	}
	

}

// The student thread.
void *student(void *param)
{
    students *temp = (students *) param ; 
    int  id  = temp->studentID;
    int  pId = temp->priorityID;
    int  cId = temp->classID;

	

   sleep(rand()%ENROLLMENT_DURATION);
   //printf ("id is = %d, pID is = %d, cID is = %d \n", id, pId, cId);
   //Students will arrive at random times during the office hour.
   studentArrives(temp);

    return NULL;
}

int timesUp = 0;  // 1 = office hour is over



// Timer signal handler.
void timerHandler(int signal)
{
    timesUp = 1;  // office hour is over
}

// Main.
int main(int argc, char *argv[])
{
    students studentIds[STUDENT_COUNT];
    int professorId = 0;
    
    srand(time(0));
    time(&startTime);
	
    //class threads starts here
	void *ptr;
    	pthread_t classThread1;
	pthread_t classThread2;
	pthread_t classThread3;
        pthread_attr_t classAttr;
        pthread_attr_init(&classAttr);
        pthread_create(&classThread1, &classAttr, threadGS, ptr);
	pthread_create(&classThread2, &classAttr, threadRS, ptr);
	pthread_create(&classThread3, &classAttr, threadEE, ptr);




    // Create the student threads.
    int i;
    pthread_t studentThreadId;
    for (i = 0; i < STUDENT_COUNT; i++) {
 
	studentIds[i].studentID = ID_BASE + i; 
	studentIds[i].priorityID = rand()%3 + 1;
	studentIds[i].classID = rand()%3 + 1;
        //pthread_t studentThreadId;
        pthread_attr_t studentAttr;
        pthread_attr_init(&studentAttr);
        pthread_create(&studentThreadId, &studentAttr, student, &studentIds[i]);
    }
 
	//printf("\n");h
    // Set the timer signal handler.
    signal(SIGALRM, timerHandler);
    pthread_join(studentThreadId, NULL);
 
    int x;
    for(x = 0; x < class1Count; x++)
    {
	printf("id is = %d priority is = %d \n", class1[x].studentID,class1[x].priorityID); 
    }
    /*
    // Remaining waiting students leave.
    meetingId = 0;
    while (waitCount-- > 0) {
        int studentId = chairs[out];
        out = (out+1)%CHAIR_COUNT;
        leavesCount++;
        
        char event[80];
        sprintf(event, "Student %d leaves",  studentId);
        print(event);
    }
    // Final statistics.
    printf("\n");
    printf("%5d students arrived\n", arrivalsCount);
    printf("%5d students met with Prof. Fore\n", meetingsCount);
    printf("%5d students left without meeting\n", leavesCount);
    printf("%5d times Prof. Fore worked on ParFore\n", parforeCount); */

    return 0;
}
