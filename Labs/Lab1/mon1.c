/*--------------------------------------------------------------------------
File: mon1.c

Description:  This program will create a process with the program name
               found on the order. Then he will go procmon
               to monitor the first process. After 20 seconds
               send signals to finish everything.
--------------------------------------------------------------------------*/
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <signal.h>



int main(int argc, char **argv)
{
    char    *program;
    char    pidStr[32];
    int     prpid, monpid;

    if (argc != 2) {
        printf("Usage: mon fileName\n where fileName is an executable file.\n");
        exit(-1);
    } else
        program = argv[1];  

    prpid = fork();
    if (prpid == -1) 
    {
        printf("First fork failed.\n");
        exit(-1);
    } 
    else if (prpid == 0)  /* Child - we start the program */
    {
        execl(program, program, NULL);
	exit(-1); 
    }
    

    /*  parent */
    sprintf(pidStr, "%d", prpid); 
    monpid = fork();  /* Another child to go procmon */
    if (monpid == -1) 
    {
        printf("Second fork failed.\n");
        kill(prpid, SIGTERM); /* kill the other child*/
        exit(-1);
    } 
    else if (monpid == 0)  /*In the child - load procmon */
        execl("procmon", "procmon", pidStr, NULL);
    
    /*Always in the parent*/
   
    sleep(20);	
    printf("Killing %s\n", program); 
    kill(prpid, SIGTERM); /* end up with the first child */
    sleep(2);
    printf("Killing procmon.\n"); /* let's finish with procmon */
    kill(monpid, SIGTERM);

    return(0);
}
