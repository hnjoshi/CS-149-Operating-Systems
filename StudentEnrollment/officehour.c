#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <semaphore.h>
#include <signal.h>
#include <sys/time.h>

#define ID_BASE 101

#define CHAIR_COUNT 3
#define STUDENT_COUNT 25

#define MAX_MEETING_DURATION 5
#define OFFICE_HOUR_DURATION 60

// An example multithreaded program
// by Ron Mak
// Department of Computer Science
// San Jose State University
//
// WARNING: Contains a subtle threading error which can cause a deadlock!
//          Can you find and fix it?

int chairs[CHAIR_COUNT];     // circular buffer of chairs
pthread_mutex_t chairMutex;  // mutex protects chairs and wait count
pthread_mutex_t printMutex;  // mutex protects printing
sem_t filledChairs;          // professor waits on this semaphore

struct itimerval profTimer;  // professor's office hour timer
time_t startTime;

int in = 0, out = 0;
int meetingId = 0;

int arrivalsCount = 0;
int waitCount = 0;
int leavesCount = 0;
int meetingsCount = 0;
int parforeCount = 0;

int firstPrint = 1;

// Print a line for each event:
//   elapsed time
//   who is meeting with the professor
//   who is waiting in the chairs
//   what event occurred
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

    // Acquire the mutex lock to protect the printing.
    pthread_mutex_lock(&printMutex);

    if (firstPrint) {
        printf("TIME | MEETING | WAITING     | EVENT\n");
        firstPrint = 0;
    }

    // Elapsed time.
    printf("%1d:%02d | ", min, sec);

    // Who's meeting with the professor.
    if (meetingId > 0) {
        printf("%5d   |", meetingId);
    }
    else {
        printf("        |");
    }

    // Acquire the mutex lock to protect the chairs and the wait count.
    pthread_mutex_lock(&chairMutex);

    int i = out;
    int j = waitCount;
    int k = 0;

    // Who's waiting in the chairs.
    while (j-- > 0) {
        printf("%4d", chairs[i]);
        i = (i+1)%CHAIR_COUNT;
        k++;
    }

    // Release the mutex lock.
    pthread_mutex_unlock(&chairMutex);

    // What event occurred.
    while (k++ < CHAIR_COUNT) printf("    ");
    printf(" | %s\n", event);

    // Release the mutex lock.
    pthread_mutex_unlock(&printMutex);
}

// A student arrives.
void studentArrives(int id)
{
    char event[80];
    arrivalsCount++;

    if (waitCount < CHAIR_COUNT) {

        // Acquire the mutex lock to protect the chairs and the wait count.
        pthread_mutex_lock(&chairMutex);

        // Seat a student into a chair.
        chairs[in] = id;
        in = (in+1)%CHAIR_COUNT;
        waitCount++;

        // Release the mutex lock.
        pthread_mutex_unlock(&chairMutex);

        sprintf(event, "Student %d arrives and waits", id);
        print(event);

        // Signal the "filledSlots" semaphore.
        sem_post(&filledChairs);  // signal
    }
    else {
        leavesCount++;
        sprintf(event, "Student %d arrives and leaves", id);
        print(event);
    }
}

// The student thread.
void *student(void *param)
{
    int id = *((int *) param);

    // Students will arrive at random times during the office hour.
    sleep(rand()%OFFICE_HOUR_DURATION);
    studentArrives(id);

    return NULL;
}

int timesUp = 0;  // 1 = office hour is over

// The professor meets a student (or works on ParFore).
void professorMeetsStudent()
{
    // No student waiting, so work on ParFore language.
    if (waitCount == 0) {
        print("Professor works on ParFore");
        parforeCount++;
    }

    if (!timesUp) {

        // Wait on the "filledChairs" semaphore for a student.
        sem_wait(&filledChairs);

        // Acquire the mutex lock to protect the chairs and the wait count.
        pthread_mutex_lock(&chairMutex);

        // Critical region: Remove a student from a chair.
        meetingId = chairs[out];
        out = (out+1)%CHAIR_COUNT;
        waitCount--;

        // Release the mutex lock.
        pthread_mutex_unlock(&chairMutex);

        char event[80];
        sprintf(event, "Professor meets with student %d",  meetingId);
        print(event);

        // Meet with the student.
        sleep(rand()%MAX_MEETING_DURATION + 1);
        meetingsCount++;

        sprintf(event, "Professor finishes with student %d",  meetingId);
        meetingId = 0;
        print(event);
    }
}

// The professor thread.
void *professor(void *param)
{
    print("Professor opens her door");

    // Set the timer for for office hour duration.
    profTimer.it_value.tv_sec = OFFICE_HOUR_DURATION;
    setitimer(ITIMER_REAL, &profTimer, NULL);

    // Meet students until the office hour is over.
    do {
        professorMeetsStudent();
    } while (!timesUp);

    print("Professor closes her door");
    return NULL;
}

// Timer signal handler.
void timerHandler(int signal)
{
    timesUp = 1;  // office hour is over
}

// Main.
int main(int argc, char *argv[])
{
    int studentIds[STUDENT_COUNT];
    int professorId = 0;
    
    // Initialize the mutexes and the semaphore.
    pthread_mutex_init(&chairMutex, NULL);
    pthread_mutex_init(&printMutex, NULL);
    sem_init(&filledChairs, 0, 0);

    srand(time(0));
    time(&startTime);

    // Create the professor thread.
    pthread_t professorThreadId;
    pthread_attr_t profAttr;
    pthread_attr_init(&profAttr);
    pthread_create(&professorThreadId, &profAttr, professor, &professorId);

    // Create the student threads.
    int i;
    for (i = 0; i < STUDENT_COUNT; i++) {
        studentIds[i] = ID_BASE + i;
        pthread_t studentThreadId;
        pthread_attr_t studentAttr;
        pthread_attr_init(&studentAttr);
        pthread_create(&studentThreadId, &studentAttr, student, &studentIds[i]);
    }

    // Set the timer signal handler.
    signal(SIGALRM, timerHandler);

    // Wait for the professor to complete the office hour.
    pthread_join(professorThreadId, NULL);
    
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
    printf("%5d times Prof. Fore worked on ParFore\n", parforeCount);

    return 0;
}
