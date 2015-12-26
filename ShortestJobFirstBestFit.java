//**************************************************************************************
//* Eric N. Parris                                                                     *
//* Noel Overton                                                                       * 
//* 4-15-2014                                                                          *
//* CS 350                                                                             *
//* Term Project                                                                       *
//* ShortestJobFirstBestFit.java description:                                          *
//* This file provides the ShortestJobFirstBestFi class. This class is used            *
//* within the main to perform a simulation and output to a file.                      *
//**************************************************************************************

// import things needed
import java.io.*;
import java.util.*;

// This class is used to simulate the shortest job first sorting algorithm using
// the best fit
public class ShortestJobFirstBestFit{
	public int SJFBF(int time)throws IOException {
			// Create the memory
		Memory[] memory = new Memory[7];
		memory[0] = new Memory(0, 32);
		memory[1] = new Memory(1, 48);
		memory[2] = new Memory(2, 24);
		memory[3] = new Memory(3, 16);
		memory[4] = new Memory(4, 64);
		memory[5] = new Memory(5, 40);
		memory[6] = new Memory(6, 32);
		Job[] job = new Job[20];
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
			job[r] = newJob;
			r++;
		}
		
		Job newJob1, newJob2, newJob3, newJob4, newJob5;
		PrintWriter outputFile = new PrintWriter("SJFwithBestFit.txt");
		outputFile.println("TIME	ID		SEGMENT      MEM REQUEST	TIME REMAIN			MESSAGES \n");
		
		for(int timer = 1; timer <= time; timer++){
			int t = 0, min = 64, usedProcessors = 0, pree = 1;
			int difference;
			int a = 0, b = 0, c = 0, e = 0, f = 0, g = 0, h = 0, j = 0, k = 0;
			
			// Find the first job with the least amount of CPU time remaining********************
			// "a" is the job id for the shortest job
			newJob1 = job[0];
			for (int i = 19; i > 0; i--){
				newJob2 = job[i];
				if (job[c].cpuTimeLeft < 1){
					c++;
					newJob1 = job[c];
				}
				else if(newJob1.cpuTimeLeft >= newJob2.cpuTimeLeft && newJob2.cpuTimeLeft >= 1){
					newJob1 = newJob2;
					a = i;
				}
			}
			// Find the second shortest job ****************************************************
			// "e" is the job id for the  second shortest job
			newJob2 = job[0];
			for (int i = 19; i > 0; i--){
				newJob3 = job[i];
				if (job[c].cpuTimeLeft < 1){
					c++;
					newJob2 = job[c];
				}
				else if (i != a){
					if(newJob2.cpuTimeLeft >= newJob3.cpuTimeLeft && newJob3.cpuTimeLeft >= 1){
						newJob2 = newJob3;
						e = i;
				
					}
				}
			}
		// Find the third shortest job *********************************************************
		// "g" is the job id for the third shortest job
			newJob3 = job[0];
			for (int i = 19; i > 0; i--){
				newJob4 = job[i];
				if (job[c].cpuTimeLeft < 1){
					c++;
					newJob3 = job[c];
				}
				else if(i != a && i != e){
					if(newJob3.cpuTimeLeft >= newJob4.cpuTimeLeft && newJob4.cpuTimeLeft >= 1){						
						newJob3 = newJob4;
						g = i;
					}
				}
			}
			//Find the fourth shortest job ******************************************************
			// "j" is the job id for the fourth shortest job
			newJob4 = job[0];
			for (int i = 19; i > 0; i--){
				newJob5 = job[i];
				if (job[c].cpuTimeLeft < 1){
					c++;
					newJob4 = job[c];
				}
				else if(i != a && i != e && i != g){
					if(newJob4.cpuTimeLeft >= newJob5.cpuTimeLeft && newJob5.cpuTimeLeft > 0){
						newJob4 = newJob5;
						j = i;
					}
				}
			}
			// Find the memory space that has the right amount of memory to fit into the space in which it will fill the memory space the most.
			for (int i = 0; i < 7; i++){
				difference = memory[i].memoryCapacity - job[a].memorySize;
				if(min > difference && difference >= 0 && memory[i].occupied == false){
					min = difference;
					b = i;
				}
			}	
			// Make the first job with the shortest cpu time ready to run 
			if(memory[b].occupied == false && job[a].cpuTimeLeft > 0 && job[a].jobStatus != "Running"){
				job[a].jobStatus = "Ready";
				memory[b].occupied = true;
				job[a].queueNumber = pree;
				job[a].memoryAssigned = b;
				pree++;	
			}
			min = 64;
			// Find the memory space that has the right amount of memory to fit into the space in which it will fill the memory space the most.
			for (int i = 0; i < 7; i++){
				difference = memory[i].memoryCapacity - job[e].memorySize;
				if(min > difference && difference >= 0 && i != b){
					min = difference;
					f = i;
				}
			}	
			// Make the second job with the shortest cpu time ready to run 
			if(memory[f].occupied == false && job[e].cpuTimeLeft > 0 && job[e].jobStatus != "Running"){
				job[e].jobStatus = "Ready";
				memory[f].occupied = true;
				job[e].queueNumber = pree;
				job[e].memoryAssigned = f;
				pree++;	
			}
			min = 64;
			// Find the memory space that has the right amount of memory
			// to fit into the space in which it will fill the memory space the most.
			for (int i = 0; i < 7; i++){
				difference = memory[i].memoryCapacity - job[g].memorySize;
				if(min > difference && difference >= 0 && i != b && i != f){
					min = difference;
					h = i;
				}
			}	
			// Make the third job with the shortest cpu time ready to run 
			if(memory[h].occupied == false && job[g].cpuTimeLeft > 0 && job[g].jobStatus != "Running"){
				job[g].jobStatus = "Ready";
				memory[h].occupied = true;
				job[g].queueNumber = pree;
				job[g].memoryAssigned = h;
				pree++;	
			}
			min = 64;
			// Find the memory space that has the right amount of memory
			// to fit into the space in which it will fill the memory space the most.
			for (int i = 0; i < 7; i++){
				difference = memory[i].memoryCapacity - job[j].memorySize;
				if(min > difference && difference >= 0 && i != b && i != f && i != g){
					min = difference;
					k = i;
				}
			}
			if(memory[k].occupied == false && job[j].cpuTimeLeft > 0 && job[j].jobStatus != "Running"){
				job[j].jobStatus = "Ready";
				memory[k].occupied = true;
				job[j].queueNumber = pree;
				job[j].memoryAssigned = k;
				pree++;
			}
			// switch up to four processes to "running"
			for(int i = 0; i < 20; i++){
				if (usedProcessors < 5){
					if (job[i].jobStatus == "Running"){
						if(job[i].cpuTimeLeft < 1){
							int placeholder = job[i].memoryAssigned;
							memory[placeholder].usedThisQuantum = true;
							job[i].jobStatus = "Finished";
							System.out.println("	Job " + i + " Finished.");
							usedProcessors--;
						}	
						else{
							job[i].cpuTimeLeft--;
							usedProcessors++;
						}
					}
					if (job[i].jobStatus == "Running"){
						job[i].cpuTimeLeft--;
						usedProcessors++;
					}	
					else if(job[i].jobStatus.equals("Ready")){
						job[i].jobStatus = "Running";
						job[i].cpuTimeLeft--;
						usedProcessors++;
					}
				}
				// print really cool things to file
				outputFile.println("" + timer + "      " + i + "         " + job[i].memoryAssigned + "              " + job[i].memorySize +
						"                " + job[i].cpuTimeLeft + "           " + job[i].jobStatus);
			}
			int wasted = 0;
			int waiting = 0;
			int spaceUsed = 0;
			int total;
			for(int i = 0; i < 20; i++){
				if(job[i].jobStatus == "Waiting"){
					waiting ++;
				}
			}
			for(int i = 0; i < 7; i++){
				if(memory[i].occupied == false){
					wasted += memory[i].memoryCapacity;	
				}
				else{
					wasted += memory[i].memoryWasted;
				}
			}
			for(int i = 0; i < 20; i++){
				if(job[i].jobStatus == "Running"){
					spaceUsed = spaceUsed + job[i].memorySize;
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
			total =  wasted - spaceUsed;
			outputFile.println("" + total + " kilobytes of memory were wasted.");
			outputFile.println("" + waiting + " processes are waiting to be executed.");
			outputFile.println("");
		}
		outputFile.close();
		System.out.println("Shortest Job First Simulation Complete...");
		return 3;
	}
}