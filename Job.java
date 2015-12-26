//**************************************************************************************
//* Eric N. Parris                                                                     *
//* Noel Overton                                                                       * 
//* 4-15-2014                                                                          *
//* CS 350                                                                             *
//* Term Project                                                                       *
//* Job.java description:                                                              *
//* This file provides the Job Class utilized by the main and simulation methods       *
//**************************************************************************************

// import things needed
import java.io.*;
import java.util.*;

// This class provides a means for the simulation to simulate
// blocks of memory in order to compare job sorting algorithms
public class Job{
	// declare variables utilized by class
	public String jobName, jobStatus;
	public int iD, memorySize, cpuTime, cpuTimeLeft, queueNumber;
	public int  memoryAssigned = -1;
	// Constructor used in main to create randomized jobs
	// The randomized jobs are later printed to a file 
	// and recreated in the simulation classes
    public Job(int ID){
		this.jobName = "Job " + ID;
        this.iD=ID;
        this.memorySize=(int) (Math.random()*38)+12;
        this.cpuTime= (int) (Math.random()*8)+2;
		this.cpuTimeLeft = this.cpuTime;
		this.jobStatus = "Waiting";
    }
	// Second constructor for Job Class
	// used in simulation methods to recreate 
	// job ohjects from the file created in the 
	// main
	public Job(int ID, int memSize, int CpuTime){
		this.jobName = "Job " + ID;
		this.iD = ID;
		this.memorySize = memSize;
		this.cpuTime = CpuTime;
		this.cpuTimeLeft = this.cpuTime;
		this.jobStatus = "Waiting";
	}
}