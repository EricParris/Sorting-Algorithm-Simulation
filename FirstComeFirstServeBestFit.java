//**************************************************************************************
//* Eric N. Parris                                                                     *
//* Noel Overton                                                                       * 
//* 4-15-2014                                                                          *
//* CS 350                                                                             *
//* Term Project                                                                       *
//* FirstComeFirstServeBestFit.java description:                                       *
//* This file provides the FirstComeFirstServeBestFit class. This class is used        *
//* within the main to perform a simulation and output to a file.                      *
//**************************************************************************************

// import things needed
import java.io.*;
import java.util.*;

// This class is used to simulate the first come first serve sorting algorithm using
// best fit.
class FirstComeFirstServeBestFit{
	// This method takes an integer representing the number of time quantum and loops through the program until 
	// a number reaches the time quantum, simulating a first come first serve best fit job sorting algorithm
	// and printing the results to a file.
	public int FCFSBF(int time)throws IOException, ArrayIndexOutOfBoundsException {
		// Create the memory
		Memory[] memory = new Memory[7];
		memory[0] = new Memory(0, 32);
		memory[1] = new Memory(1, 48);
		memory[2] = new Memory(2, 24);
		memory[3] = new Memory(3, 16);
		memory[4] = new Memory(4, 64);
		memory[5] = new Memory(5, 40);
		memory[6] = new Memory(6, 32);
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
			// create job object using data obtained from file
			Job newJob = new Job(jobID, jobMemSize, jobCpuTime);
			jobs[r] = newJob;
			r++;
		}
		// Declare variables needed by the simulation
		int usedProcessors = 0;
		int pree = 1;
		// open the output file
		PrintWriter outputFile = new PrintWriter("FCFSwithBestFit.txt");
		outputFile.println("TIME	ID	SEGMENT      MEM REQUEST	TIME REMAIN	MESSAGES");
		// Loop through until the time quantum limit is reached
		for(int timer = 1; timer <= time; timer++){
			// check all 20 jobs and place in memory if possible
			for(int i = 0; i < 20; i++){
				if (jobs[i].jobStatus == "Waiting"){
					int bestFit = 64;
					int block = 10;
					int fit ;
					boolean found = false;
					for(int z=0; z < 7; z++){
						//Find the best fit if applicable
						if(memory[z].occupied == false){
							fit = memory[z].memoryCapacity - jobs[i].memorySize;
							if(fit >= 0 && fit <= bestFit){
								bestFit = fit;
								block = z;
								found = true;
							}
						}
					}
					// Set job to "Ready" if a fit is found and perform needed operations
					if(found == true){
						jobs[i].jobStatus = "Ready";
						memory[block].occupied = true;
						memory[block].memoryWasted = memory[block].memoryCapacity - jobs[i].memorySize;
						jobs[i].queueNumber = pree;
						if(block != -1){
							jobs[i].memoryAssigned = block;
						}
						pree++;
					}
				}
			}
			// switch up to four processes to "running" as long as there are four or less used processors
			for(int q = 0; q < 20; q++){
				if(usedProcessors < 4){
					int first = 19;
					//Find out which ready job was added to the queue first
					for(int i = 0; i < 20; i++){
						if(jobs[i].jobStatus == "Ready" && jobs[i].queueNumber <= first){
							first = jobs[i].queueNumber;
						}
					}
					//Set found ready job to "Running"
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
					if(jobs[i].cpuTimeLeft <= 0){
						jobs[i].jobStatus = "Finished";
						int placeholder = jobs[i].memoryAssigned;
						memory[placeholder].usedThisQuantum = true;
						jobs[i].memoryAssigned = -1;
						usedProcessors -= 1;
						System.out.println("	Job " + i + " Finished.");
					}
				}
			}
			// print really cool things to file
			for(int i = 0; i < 20; i++){
				outputFile.println("" + timer + "      " + i + "         " + jobs[i].memoryAssigned + "              " + jobs[i].memorySize +
					"                " + jobs[i].cpuTimeLeft + "           " + jobs[i].jobStatus);
			}
			// Set jobs back to ready
			for(int i = 0; i < 20; i++){
				if(jobs[i].jobStatus == "Running"){
					jobs[i].jobStatus = "Ready";
					usedProcessors -= 1;
				}
			}
			int wasted = 0;
			int waiting = 0;
			// calculate how many jobs are waiting
			for(int i = 0; i < 20; i++){
				if(jobs[i].jobStatus == "Waiting"){
					waiting ++;
				}
			}
			// calculate how much memory was wasted
			for(int i = 0; i < 7; i++){
				if(memory[i].occupied == false){
					wasted += memory[i].memoryCapacity;
				}
				else{
					wasted += memory[i].memoryWasted;
				}
			}
			// change used memory for finished jobs back to free and full wasted value
			for (int i = 0; i < 7; i++){
				if(memory[i].usedThisQuantum == true){
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
		// Close the output file
		outputFile.close();
		System.out.println("First come first serve best fit simulation complete.");
		System.out.println("");
		return 2;
	}
}