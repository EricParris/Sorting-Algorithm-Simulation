Java Source Files:

CS350SemesterProject.java - This is the main class. It creates an array of 20 jobs, outputs them to a .txt file and calls the other methods.
FirstComeFirstServeBestFit.java - This uses the .txt file created by the main and simulates a FCFS best fit scheduling system
FirstComeFirstServeFirstFit.java -  This uses the .txt file created by the main and simulates a FCFS first fit scheduling system
ShortestJobFirstBestFit.java - This uses the .txt file created by the main and simulates a SJF best fit scheduling system
Job.java - Used by all other classes to create job objects used within the simulations
Memory.java - Used by all other classes to create memory objects used within the simulations

How to Compile and Run:

1. Open CS350SemesterProject.java using notepad++ or similar method of getting to the command prompt
2. Type javac CS350SemesterProject.java and hit the enter key
3. Type java CS350SemesterProject and hit the enter key
4. Navigate to source code folder and look at the results of the simulations

Result Files:

FCFSwithBestFit.txt - results from first come first serve using best fit simulation
FCFSwithFirstFit.txt - results from first come first serve using first fit simulation
SJFwithBestFit.txt - results from shortest job first using best fit simulation
jobs.txt - contains information pertaining to the twenty jobs (id, memory needed, cpu time needed)