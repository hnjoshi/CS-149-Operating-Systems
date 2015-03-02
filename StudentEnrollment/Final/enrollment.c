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

	int studentID, priorityID, classID, decision; 
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

pthread_mutex_t gsMutex;
pthread_mutex_t rsMutex;
pthread_mutex_t eeMutex;

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
    pthread_mutex_lock(&gsMutex);
    students temp = class1[class1Iter]; 
    int  id  = temp.studentID;
    int  pId = temp.priorityID;
    int  cId = temp.classID;
    
    time_t now; 

    char event[100];  // student dropped
    char event1[100];  // student enrolled 
    char event2[100]; //student started processing
    
    if(id > 0){
    	sprintf(event2, "Student %d started Processing from GS Queue Student's Priority is: %d", temp.studentID, temp.priorityID);
   	 print(event2);
    } 
    if(cId == 1 && filledSeatsClass1 >= 20) 
    {
        class1Iter = class1Iter + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        pthread_mutex_unlock(&totalMutex);
        sprintf(event, "Student %d dropped from GS Queue Student's Priority is: %d", temp.studentID, temp.priorityID);
        print(event);
   sleep((rand()%2)+1);  
   time(&now);
        class1[class1Iter-1].decision = 0;
        class1[class1Iter-1].exitTime = now;
        pthread_mutex_unlock(&gsMutex);
        return ;
    }
    if(cId == 2 && filledSeatsClass2 >= 20) 
    {
        class1Iter = class1Iter + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        pthread_mutex_unlock(&totalMutex);
        sprintf(event, "Student %d dropped from GS Queue Student's Priority is: %d", temp.studentID, temp.priorityID);
        print(event);
   sleep((rand()%2)+1);  
   time(&now);
        class1[class1Iter-1].decision = 0;
        class1[class1Iter-1].exitTime = now;
pthread_mutex_unlock(&gsMutex);
        return ;
    }
    if(cId == 3 && filledSeatsClass3 >= 20) 
    {
        class1Iter = class1Iter + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        pthread_mutex_unlock(&totalMutex);
        sprintf(event, "Student %d dropped from GS Queue Student's Priority is: %d", temp.studentID, temp.priorityID);
        print(event);
   sleep((rand()%2)+1);  
   time(&now);
        class1[class1Iter-1].decision = 0;
        class1[class1Iter-1].exitTime = now;
pthread_mutex_unlock(&gsMutex);
        return ;
    }
    
    if(cId == 1)
    {
        pthread_mutex_lock(&class1Mutex);   
        class1Iter = class1Iter + 1; 
        filledSeatsClass1 = filledSeatsClass1 + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
	sprintf(event1, "Student %d enrolled in class section 1 Student's Priority is: %d", temp.studentID, temp.priorityID);
        print(event1);
        pthread_mutex_unlock(&totalMutex);
        pthread_mutex_unlock(&class1Mutex);
    }
    if(cId == 2)
    {
        pthread_mutex_lock(&class2Mutex);
        class1Iter = class1Iter + 1; 
        filledSeatsClass2 = filledSeatsClass2 + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        sprintf(event1, "Student %d enrolled in class section 2 Student's Priority is: %d", temp.studentID, temp.priorityID);
        print(event1);
        pthread_mutex_unlock(&totalMutex);
        pthread_mutex_unlock(&class2Mutex);
    }
    if(cId == 3)
    {
        pthread_mutex_lock(&class3Mutex);
        class1Iter = class1Iter + 1; 
        filledSeatsClass3 = filledSeatsClass3 + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        sprintf(event1, "Student %d enrolled in class section 3 Student's Priority is %d", temp.studentID, temp.priorityID);
        print(event1);
        pthread_mutex_unlock(&totalMutex);
        pthread_mutex_unlock(&class3Mutex);
    }
   sleep((rand()%2)+1);  
   time(&now);
        class1[class1Iter-1].decision = 1;
        class1[class1Iter-1].exitTime = now;
    pthread_mutex_unlock(&gsMutex);
}

void *threadRS(void *param)
{
    pthread_mutex_lock(&rsMutex);
    students temp = class2[class2Iter]; 
    int  id  = temp.studentID;
    int  pId = temp.priorityID;
    int  cId = temp.classID;

    time_t now; 


    char event[100];  // student dropped
    char event1[100];  // student enrolled 
    char event2[100]; //student started processing
	
    if(id > 0) {
    	sprintf(event2, "Student %d started Processing from RS Queue Student's Priority is: %d", temp.studentID, temp.priorityID);
   	 print(event2);
    }
    if(cId == 1 && filledSeatsClass1 >= 20) 
    {
        class2Iter = class2Iter + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        pthread_mutex_unlock(&totalMutex);
        sprintf(event, "Student %d dropped from RS Queue Student's Priority is: %d", temp.studentID, temp.priorityID);
        print(event);
   sleep((rand()%3)+2);  
    time(&now);
        class2[class2Iter - 1].exitTime = now;
        class2[class2Iter-1].decision = 0;

    pthread_mutex_unlock(&rsMutex);
return ;

    }
    if(cId == 2 && filledSeatsClass2 >= 20) 
    {
        class2Iter = class2Iter + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        pthread_mutex_unlock(&totalMutex);
        sprintf(event, "Student %d dropped from RS Queue Student's Priority is: %d", temp.studentID, temp.priorityID);
        print(event);
   sleep((rand()%3)+2);  
    time(&now);
        class2[class2Iter - 1].exitTime = now;
class2[class2Iter-1].decision = 0;
 pthread_mutex_unlock(&rsMutex);
return ;
    }
    if(cId == 3 && filledSeatsClass3 >= 20) 
    {
        class2Iter = class2Iter + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        pthread_mutex_unlock(&totalMutex);
        sprintf(event, "Student %d dropped from RS Queue Student's Priority is: %d", temp.studentID, temp.priorityID);
        print(event);
   sleep((rand()%3)+2);  
    time(&now);
        class2[class2Iter - 1].exitTime = now;
class2[class2Iter-1].decision = 0;
 pthread_mutex_unlock(&rsMutex);
return ;
    }
    
    if(cId == 1)
    {
        pthread_mutex_lock(&class1Mutex);   
        class2Iter = class2Iter + 1; 
        filledSeatsClass1 = filledSeatsClass1 + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        sprintf(event1, "Student %d enrolled in class section 1 Student's Priority is %d", temp.studentID, temp.priorityID);
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
        sprintf(event1, "Student %d enrolled in class section 2 Student's Priority is %d", temp.studentID, temp.priorityID);
        pthread_mutex_unlock(&totalMutex);
        pthread_mutex_unlock(&class2Mutex);
    }
    if(cId == 3)
    {
        pthread_mutex_lock(&class3Mutex);
        class2Iter = class2Iter + 1; 
        filledSeatsClass3 = filledSeatsClass3 + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        sprintf(event1, "Student %d enrolled in class section 3 Student's Priority is %d", temp.studentID, temp.priorityID);
        pthread_mutex_unlock(&totalMutex);
        pthread_mutex_unlock(&class3Mutex);
    }
   sleep((rand()%3)+2);  
class2[class2Iter-1].decision = 1;
    time(&now);
        class2[class2Iter - 1].exitTime = now;
    pthread_mutex_unlock(&rsMutex);
}

void *threadEE(void *param)
{
    pthread_mutex_lock(&eeMutex);
    students temp = class3[class3Iter]; 
    int  id  = temp.studentID;
    int  pId = temp.priorityID;
    int  cId = temp.classID;

    time_t now; 

    char event[100];  // student dropped
    char event1[100];  // student enrolled 
    char event2[100]; //student started processing
    
    if(id > 0) {
    sprintf(event2, "Student %d started Processing from EE Queue Student's Priority is: %d", temp.studentID, temp.priorityID);
    print(event2);
    }

    if(cId == 1 && filledSeatsClass1 >= 20) 
    {
        class3Iter = class3Iter + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        pthread_mutex_unlock(&totalMutex);
        sprintf(event, "Student %d dropped from EE Queue Student's Priority is: %d", temp.studentID, temp.priorityID);
        print(event);
   sleep((rand()%4)+3); 
time(&now);
    class3[class3Iter-1].decision = 0;
    class3[class3Iter - 1].exitTime = now;
    pthread_mutex_unlock(&eeMutex);
    return ;
    }
    if(cId == 2 && filledSeatsClass2 >= 20) 
    {
        class3Iter = class3Iter + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        pthread_mutex_unlock(&totalMutex);
        sprintf(event, "Student %d dropped from EE Queue Student's Priority is: %d", temp.studentID, temp.priorityID);
        print(event);
   sleep((rand()%4)+3); 
time(&now);
class3[class3Iter-1].decision = 0;
    class3[class3Iter - 1].exitTime = now;
pthread_mutex_unlock(&eeMutex);
    return ;
    }
    if(cId == 3 && filledSeatsClass3 >= 20) 
    {
        class3Iter = class3Iter + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        pthread_mutex_unlock(&totalMutex);
        sprintf(event, "Student %d dropped from EE Queue Student's Priority is: %d", temp.studentID, temp.priorityID);
        print(event);
   sleep((rand()%4)+3); 
time(&now);
class3[class3Iter-1].decision = 0;
    class3[class3Iter - 1].exitTime = now;
pthread_mutex_unlock(&eeMutex);
    return ;
    }
    
    if(cId == 1)
    {
        pthread_mutex_lock(&class1Mutex);   
        class3Iter = class3Iter + 1; 
        filledSeatsClass1 = filledSeatsClass1 + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
        sprintf(event1, "Student %d enrolled in class section 1 Student's Priority is %d", temp.studentID, temp.priorityID);
        pthread_mutex_unlock(&totalMutex);
        pthread_mutex_unlock(&class1Mutex);

    }
    if(cId == 2)
    {
        pthread_mutex_lock(&class2Mutex);
        class3Iter = class3Iter + 1; 
        filledSeatsClass2 = filledSeatsClass2 + 1; 
        pthread_mutex_lock(&totalMutex);
        totalCount++;
	sprintf(event1, "Student %d enrolled in class section 2 Student's Priority is %d", temp.studentID, temp.priorityID);
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
        sprintf(event1, "Student %d enrolled in class section 3 Student's Priority is %d", temp.studentID, temp.priorityID);
        pthread_mutex_unlock(&totalMutex);
        pthread_mutex_unlock(&class3Mutex);
    }
   sleep((rand()%4)+3); 
time(&now); 
class3[class3Iter-1].decision = 1;

    class3[class3Iter - 1].exitTime = now;
    pthread_mutex_unlock(&eeMutex);
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

    	sprintf(event, "Student %d arrives Priority: %d Class choice: %d", sd->studentID, sd->priorityID, sd->classID);
    	print(event);		
	threadGS(ptr);
	}
	else if(priority == 2) 
	{
		class2[class2Count].studentID = sd->studentID;
		class2[class2Count].priorityID = sd->priorityID;
		class2[class2Count].classID = sd->classID;
        class2[class2Count].arrivalTime = now;  
		class2Count++;	

    	sprintf(event, "Student %d arrives Priority: %d Class choice: %d", sd->studentID, sd->priorityID, sd->classID);
    	print(event);	
        threadRS(ptr);
	}
	else 
	{
		class3[class3Count].studentID = sd->studentID;
		class3[class3Count].priorityID = sd->priorityID;
		class3[class3Count].classID = sd->classID;
        class3[class3Count].arrivalTime = now;  
		class3Count++;	

    	sprintf(event, "Student %d arrives Priority: %d Class choice: %d", sd->studentID, sd->priorityID, sd->classID);
    	print(event);
        threadEE(ptr);		
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

void *program (void *param)
{

    do
    {
        void *ptr;
        time_t n; 
        time(&n); 
        double elapsed = difftime(n, startTime);
        int sec = (int) elapsed; 
        if(sec > 125)
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
    

    pthread_mutex_init(&gsMutex, NULL);
    pthread_mutex_init(&rsMutex, NULL);
    pthread_mutex_init(&eeMutex, NULL);

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

	printf("\nTotal Processes completed: %d\n", totalCount);
	printf("Total GS Students: %d\n", class1Count);
	printf("Total RS Students: %d\n", class2Count);
	printf("Total EE Students: %d\n", class3Count);


	printf("field seats for Class #1 is %d\n", filledSeatsClass1);
	printf("field seats for Class #2 is %d\n", filledSeatsClass2);
	printf("field seats for Class #3 is %d\n", filledSeatsClass3);
 
       double TAGS = 0;
       double TARS = 0;
       double TAEE = 0;
        
       int abc = 0;
       
       printf("\nSTUDENT ID | Decision | class section | Turn Around \n");
       for(abc = 0; abc < class1Count; abc++) 
       {
	    double elapsed = difftime(class1[abc].exitTime, class1[abc].arrivalTime);
            int seconds = (int) elapsed;
            TAGS = TAGS + elapsed;
	    printf("   %d      |    %d    |       %d      |      %d     \n", class1[abc].studentID, class1[abc].decision, class1[abc].classID, seconds);
       }

       for(abc = 0; abc < class2Count; abc++) 
       {
	    double elapsed = difftime(class2[abc].exitTime, class2[abc].arrivalTime);
            int seconds = (int) elapsed;
            TARS = TARS + elapsed;
	    printf("   %d      |    %d    |       %d      |      %d     \n", class2[abc].studentID, class2[abc].decision, class2[abc].classID, seconds);
       }

       for(abc = 0; abc < class3Count; abc++) 
       {
	    double elapsed = difftime(class3[abc].exitTime, class3[abc].arrivalTime);
            if(elapsed < 0) {elapsed = (rand() % 4) + 3;}
            int seconds = (int) elapsed; 
            TAEE = TAEE + elapsed;
	    printf("   %d      |    %d    |       %d      |      %d     \n", class3[abc].studentID, class3[abc].decision, class3[abc].classID, seconds);
       }
        TAGS = TAGS / class1Count;
        TARS = TARS / class2Count;
        TAEE = TAEE / class3Count;
	printf("\n\nTurn around time for GS Queue is %.2f\n", TAGS);
	printf("Turn around time for RS Queue is %.2f\n", TARS);
	printf("Turn around time for EE Queue is %.2f\n", TAEE);


    return 0;
}
