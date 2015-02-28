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
    time_t arrivalTime, exitTime; 

}students; 


time_t startTime;
students class1[STUDENT_COUNT];
students class2[STUDENT_COUNT];
students class3[STUDENT_COUNT];

int totalCount  = 0; 
int class1Count = 0;
int class2Count = 0;
int class3Count = 0;
 
int filledSeatsClass1;
int filledSeatsClass2;
int filledSeatsClass3;

int class1Iter = 0;
int class2Iter = 0; 
int class3Iter = 0;  

int firstPrint = 1; 

pthread_mutex_t class1Mutex;
pthread_mutex_t class2Mutex;
pthread_mutex_t class3Mutex;
pthread_mutex_t totalMutex; 
pthread_mutex_t printMutex; 

void print(char *event)
{
    time_t now;
    time(&now);
    double elapsed = difftime(now, startTime);
    int min = 0;
    int sec = (int) elapsed;

    if (sec >= 60) {
        min++;
        sec -= 60;
    }

    pthread_mutex_lock(&printMutex);

    if (firstPrint) {
        printf("TIME | EVENT\n");
        firstPrint = 0;
    }

    printf("%1d:%02d | ", min, sec);
    printf("%s\n", event);


	pthread_mutex_unlock(&printMutex);
    
}


void *threadGS(void *param)
{
	students temp = class1[class1Iter]; 
    int  id  = temp.studentID;
    int  pId = temp.priorityID;
    int  cId = temp.classID;

    char event[100];  // student dropped
    char event1[100];  // student enrolled 
    char event2[100]; //student started processing

    sprintf(event2, "Student %d started Processing from GS Queue", temp.studentID);
    print(event2);
 
    if(cId == 1 && filledSeatsClass1 > 20) 
    {
        class1Iter = class1Iter + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        pthread_mutex_unlock(&totalMutex);
        sprintf(event, "Student %d dropped from GS Queue", temp.studentID);
        print(event);
        return NULL; 
    }
    if(cId == 2 && filledSeatsClass2 > 20) 
    {
        class2Iter = class2Iter + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        pthread_mutex_unlock(&totalMutex);
        sprintf(event, "Student %d dropped from GS Queue", temp.studentID);
        print(event);
        return NULL; 
    }
    if(cId == 3 && filledSeatsClass3 > 20) 
    {
        class3Iter = class3Iter + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        pthread_mutex_unlock(&totalMutex);
        sprintf(event, "Student %d dropped from GS Queue", temp.studentID);
        print(event);
        return NULL; 
    }
    
    if(cId == 1)
    {
        pthread_mutex_lock(&class1Mutex);   
        class1Iter = class1Iter + 1; 
        filledSeatsClass1 = filledSeatsClass1 + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        pthread_mutex_unlock(&totalMutex);
        pthread_mutex_unlock(&class1Mutex);
        sprintf(event1, "Student %d enrolled in class section 1", temp.studentID);
        print(event1);

    }
    if(cId == 2)
    {
        pthread_mutex_lock(&class2Mutex);
        class2Iter = class2Iter + 1; 
        filledSeatsClass2 = filledSeatsClass2 + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        pthread_mutex_unlock(&totalMutex);
        pthread_mutex_unlock(&class2Mutex);
        sprintf(event1, "Student %d enrolled in class section 2", temp.studentID);
        print(event1);
    }
    if(cId == 3)
    {
        pthread_mutex_lock(&class3Mutex);
        class3Iter = class3Iter + 1; 
        filledSeatsClass3 = filledSeatsClass3 + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        pthread_mutex_unlock(&totalMutex);
        pthread_mutex_lock(&class3Mutex);
        sprintf(event1, "Student %d enrolled in class section 3", temp.studentID);
        print(event1);
    }
}

void *threadRS(void *param)
{
	students temp = class2[class1Iter]; 
    int  id  = temp.studentID;
    int  pId = temp.priorityID;
    int  cId = temp.classID;

    if(cId == 1 && filledSeatsClass1 > 20) 
    {
        class1Iter = class1Iter + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        pthread_mutex_unlock(&totalMutex);
        return NULL; 
    }
    if(cId == 2 && filledSeatsClass2 > 20) 
    {
        class2Iter = class2Iter + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        pthread_mutex_unlock(&totalMutex);
        return NULL; 
    }
    if(cId == 3 && filledSeatsClass3 > 20) 
    {
        class3Iter = class3Iter + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        pthread_mutex_unlock(&totalMutex);
        return NULL; 
    }
    
    if(cId == 1)
    {
        pthread_mutex_lock(&class1Mutex);   
        class1Iter = class1Iter + 1; 
        filledSeatsClass1 = filledSeatsClass1 + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        pthread_mutex_unlock(&totalMutex);
        pthread_mutex_unlock(&class1Mutex);

    }
    if(cId == 2)
    {
        pthread_mutex_lock(&class2Mutex);
        class2Iter = class2Iter + 1; 
        filledSeatsClass2 = filledSeatsClass2 + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        pthread_mutex_unlock(&totalMutex);
        pthread_mutex_unlock(&class2Mutex);
    }
    if(cId == 3)
    {
        pthread_mutex_lock(&class3Mutex);
        class3Iter = class3Iter + 1; 
        filledSeatsClass3 = filledSeatsClass3 + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        pthread_mutex_unlock(&totalMutex);
        pthread_mutex_lock(&class3Mutex);
    }
}

void *threadEE(void *param)
{
	students temp = class3[class1Iter]; 
    int  id  = temp.studentID;
    int  pId = temp.priorityID;
    int  cId = temp.classID;

    if(cId == 1 && filledSeatsClass1 > 20) 
    {
        class1Iter = class1Iter + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        pthread_mutex_unlock(&totalMutex);
        return NULL; 
    }
    if(cId == 2 && filledSeatsClass2 > 20) 
    {
        class2Iter = class2Iter + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        pthread_mutex_unlock(&totalMutex);
        return NULL; 
    }
    if(cId == 3 && filledSeatsClass3 > 20) 
    {
        class3Iter = class3Iter + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        pthread_mutex_unlock(&totalMutex);
        return NULL; 
    }
    
    if(cId == 1)
    {
        pthread_mutex_lock(&class1Mutex);   
        class1Iter = class1Iter + 1; 
        filledSeatsClass1 = filledSeatsClass1 + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        pthread_mutex_unlock(&totalMutex);
        pthread_mutex_unlock(&class1Mutex);

    }
    if(cId == 2)
    {
        pthread_mutex_lock(&class2Mutex);
        class2Iter = class2Iter + 1; 
        filledSeatsClass2 = filledSeatsClass2 + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        pthread_mutex_unlock(&totalMutex);
        pthread_mutex_unlock(&class2Mutex);
    }
    if(cId == 3)
    {
        pthread_mutex_lock(&class3Mutex);
        class3Iter = class3Iter + 1; 
        filledSeatsClass3 = filledSeatsClass3 + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        pthread_mutex_unlock(&totalMutex);
        pthread_mutex_lock(&class3Mutex);
    }
}


// A student arrives.
void studentArrives(students *sd)
{

	int priority = sd->priorityID; 
    time_t now; 
    time(&now);
    char event[100];
    void *ptr;

	if(priority == 1) 
	{
		class1[class1Count].studentID = sd->studentID;
		class1[class1Count].priorityID = sd->priorityID;
		class1[class1Count].classID = sd->classID;
        class1[class1Count].arrivalTime = now;        
		class1Count++;			
        if(class1Count && class1Count > class1Iter)
        {
            threadGS(ptr);
        }  
	}
	else if(priority == 2) 
	{
		class2[class2Count].studentID = sd->studentID;
		class2[class2Count].priorityID = sd->priorityID;
		class2[class2Count].classID = sd->classID;
        class2[class2Count].arrivalTime = now;  
		class2Count++;	
        if(class2Count && class2Count > class2Iter)
        {
            threadEE(ptr);
        }		
	}
	else 
	{
		class3[class3Count].studentID = sd->studentID;
		class3[class3Count].priorityID = sd->priorityID;
		class3[class3Count].classID = sd->classID;
        class3[class3Count].arrivalTime = now;  
		class3Count++;	
        if(class3Count && class3Count > class3Iter)
        {
            threadRS(ptr);
        }		
	}
    sprintf(event, "Student %d arrives", sd->studentID);
    print(event);

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

void *program (void *param)
{

    do
    {
        void *ptr;
        time_t n; 
        time(&n); 
        double elapsed = difftime(n, startTime);
        int sec = (int) elapsed; 
        if(sec > 120)
            {timesUp = 1;}
 
    } while (!timesUp);
}
// Main.
int main(int argc, char *argv[])
{
    students studentIds[STUDENT_COUNT];
    int professorId = 0;

    pthread_mutex_init(&class1Mutex, NULL);
    pthread_mutex_init(&class2Mutex, NULL);
    pthread_mutex_init(&class3Mutex, NULL);
    pthread_mutex_init(&totalMutex, NULL); 
    pthread_mutex_init(&printMutex, NULL);
    
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
    pthread_t programThread; 
    pthread_create(&programThread, &classAttr, program, ptr);



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


    // Set the timer signal handler.
    signal(SIGALRM, timerHandler);

    pthread_join(programThread, NULL);


  /*  int x;
    for(x = 0; x < class1Count; x++)
    {
	printf("id is = %d priority is = %d \n", class1[x].studentID,class1[x].priorityID); 
    }
    */
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
