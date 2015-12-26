//**************************************************************************************
//* Eric N. Parris                                                                     *
//* Noel Overton                                                                       * 
//* 4-15-2014                                                                          *
//* CS 350                                                                             *
//* Term Project                                                                       *
//* Memory.java description:                                                           *
//* This file provides the Memory Class utilized by the main and simulation methods    *
//**************************************************************************************

// import things needed
import java.io.*;
import java.util.*;

// This class provides a means for the simulation to simulate
// blocks of memory in order to compare job sorting algorithms
public class Memory{
	//declare variables utilized by class
	public int segmentNumber;
	public int memoryCapacity;
	public int memoryWasted;
	public Boolean occupied = false;
	public Boolean usedThisQuantum = false;
	// Constructor for memory class
	public Memory(int segment,int size){
		this.segmentNumber = segment;
		this.memoryCapacity = size;
	}
}