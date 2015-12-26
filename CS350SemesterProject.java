//**************************************************************************************
//* Eric N. Parris                                                                     *
//* Noel Overton                                                                       * 
//* 4-15-2014                                                                          *
//* CS 350                                                                             *
//* Term Project                                                                       *
//* CS350SemesterProject.java description:                                             *
//* This file serves as the program's main. First, twenty job objects are created      *
//* after which, the twenty jobs are written to an output file to be used in the       *
//* simulator classes. After doing so, The three simulations are ran.                  *
//**************************************************************************************


// import things needed
import java.io.*;
import java.util.*;

// This class is used to create the twenty randomized jobs and write them to a file
// After doing this, three simulations are ran using First come first serve first fit
// sorting algorithm, first come first serve best fit sorting algorithm, and shortest
// job first best fit sorting algorithm.
public class CS350SemesterProject{
    public static void main(String args[]) throws IOException{
		// Create array of 20 jobs
        Job[] jobs1 = new Job[20];
		// Create jobs and add to array
        for(int i=0; i<20; i++){
			jobs1[i] = new Job(i);
        }
		// Print created jobs to output file
		PrintWriter outputFile = new PrintWriter("jobs.txt");
		for(int i = 0; i < 20; i++){
				outputFile.println("" + jobs1[i].iD + " " + jobs1[i].memorySize + " " + jobs1[i].cpuTime);
		}
		// close the output file
		outputFile.close();
		// create a simulation for first come first serve first fit
		FirstComeFirstServeFirstFit simulation1 = new FirstComeFirstServeFirstFit();
		System.out.println("First come first serve first fit simulation beginning... ");
		int sim1 = simulation1.FCFSFF(30);
		System.out.println("Shortest job first best fit simulation beginning... ");
		ShortestJobFirstBestFit simulation3 = new ShortestJobFirstBestFit();
		int sim3 = simulation3.SJFBF(30);
		// create a simulation for the first come first serve best fit
		FirstComeFirstServeBestFit simulation2 = new FirstComeFirstServeBestFit();
		System.out.println("First come first serve best fit simulation beginning... ");
		int sim2 = simulation2.FCFSBF(30);

    }
} 