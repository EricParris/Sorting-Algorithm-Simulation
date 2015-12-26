//**************************************************************************************
//* Eric N. Parris                                                                     *
//* Noel Overton                                                                       * 
//* 4-15-2014                                                                          *
//* CS 350                                                                             *
//* Term Project                                                                       *
//* FirstComeFirstServeFirstFit.java description:                                      *
//* This file provides the FirstComeFirstServeFirstFit class. This class is used       *
//* within the main to perform a simulation and output to a file.                      *
//**************************************************************************************


// import things needed
import java.io.*;
import java.util.*;

// This class is used to simulate the first come first serve sorting algorithm using
// first fit.
 public class FirstComeFirstServeFirstFit{
	// This method takes an integer representing the number of time quantum and loops through the program until 
	// a number reaches the time quantum, simulating a first come first serve first fit job sorting algorithm
	// and printing the results to a file.
	public int FCFSFF(int time) throws IOException {
		// Create the memory
		Memory[] memory = new Memory[7];
		memory[0] = new Memory(0, 32);
		memory[1] = new Memory(1, 48);
		memory[2] = new Memory(2, 24);
		memory[3] = new Memory(3, 16);
		memory[4] = new Memory(4, 64);
		memory[5] = new Memory(5, 40);
		memory[6] = new Memory(6, 32);
		// Create a job array
		Job[] jobs = new Job[20];
		int r = 0;
		String line;
		//Begin reading from file
		BufferedReader inputFile = new BufferedReader(new FileReader("jobs.txt"));
		// loop through file and create objects
		while ((line = inputFile.readLine()) != null) {
			// pull content from each line and parse into needed data
			String[] contents = line.split(" ");
			int jobID = Integer.parseInt(contents[0]);
			int jobMemSize = Integer.parseInt(contents[1]);
			int jobCpuTime = Integer.parseInt(contents[2]);
			// generate job object using data obtained from file
			Job newJob = new Job(jobID, jobMemSize, jobCpuTime);
			jobs[r] = newJob;
			r++;
		}
		// Create variables needed for simulation
		int usedProcessors = 0;
		int pree = 1;
		// Open output file
		PrintWriter outputFile = new PrintWriter("FCFSwithFirstFit.txt");
		outputFile.println("TIME	ID	SEGMENT      MEM REQUEST	TIME REMAIN	MESSAGES");
		for(int timer = 1; timer <= time; timer++){
			// check all 20 jobs and place in memory if possible
			for(int i = 0; i < 20; i++){
				if (jobs[i].jobStatus == "Waiting"){
					for(int z=0; z < 7; z++){
						if (jobs[i].memorySize <= memory[z].memoryCapacity){
							if(memory[z].occupied == false){
								jobs[i].jobStatus = "Ready";
								memory[z].occupied = true;
								memory[z].memoryWasted = memory[z].memoryCapacity - jobs[i].memorySize;
								jobs[i].queueNumber = pree;
								jobs[i].memoryAssigned = z;
								pree ++;
							}
						}
					}
				}
			}
			// switch up to four processes to "running" as long as there are four or less used processors
			for(int q = 0; q < 20; q++){
				if(usedProcessors <= 4){
					int first = 19;
					//Find out which job is the first within the queue
					for(int i = 0; i < 20; i++){
						if(jobs[i].jobStatus == "Ready" && jobs[i].queueNumber <= first){
							first = jobs[i].queueNumber;
						}
					}
					//Run Through and set the found job to ready
					for(int i = 0; i < 20; i++){
						if(jobs[i].queueNumber == first){
							jobs[i].jobStatus = "Running";
							usedProcessors ++;						
						}
					}
				}
			}
			// subtract from processes time left and change to finished if needed
			for(int i = 0; i <20; i++){
				if(jobs[i].jobStatus == "Running"){
					jobs[i].cpuTimeLeft -= 1;
					if(jobs[i].cpuTimeLeft == 0){
						jobs[i].jobStatus = "Finished";
						int placeholder = jobs[i].memoryAssigned;
						memory[placeholder].usedThisQuantum = true;
						jobs[i].memoryAssigned = -1;
						usedProcessors -= 1;
						System.out.println("	Job " + i + " Finished.");
					}
				}
			}
			// Print things to the output file
			for(int i = 0; i < 20; i++){
				outputFile.println("" + timer + "      " + i + "         " + jobs[i].memoryAssigned + "              " + jobs[i].memorySize +
					"                " + jobs[i].cpuTimeLeft + "           " + jobs[i].jobStatus);
			}
			// Set all jobs that were running back to ready for the next time quantum
			for(int i = 0; i < 20; i++){
				if(jobs[i].jobStatus == "Running"){
					jobs[i].jobStatus = "Ready";
					usedProcessors -= 1;
				}
			}
			// Calculate how many processes are "Waiting"
			int wasted = 0;
			int waiting = 0;
			for(int i = 0; i < 20; i++){
				if(jobs[i].jobStatus == "Waiting"){
					waiting ++;
				}
			}
			// Calculate how much memory was wasted 
			for(int i = 0; i < 7; i++){
				if(memory[i].occupied == false){
					wasted += memory[i].memoryCapacity;
				}
				else{
					wasted += memory[i].memoryWasted;
				}
			}
			// change memory used for finished jobs back to free and full wasted value
			for (int i = 0; i < 7; i++){
				if (memory[i].usedThisQuantum == true){
					memory[i].occupied = false;
					memory[i].memoryWasted = memory[i].memoryCapacity;
					memory[i].usedThisQuantum = false;
				}
			}
			// print the wasted memory and number of jobs left waiting to the file
			outputFile.println("" + wasted + " kilobytes of memory were wasted.");
			outputFile.println("" + waiting + " processes are waiting to be executed.");
			outputFile.println("");
		}
		// close the output file
		outputFile.close();
		System.out.println("First come first serve first fit simulation complete.");
		System.out.println("");
		return 1;
	}
}