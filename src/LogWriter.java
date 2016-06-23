/**
 * Keep the records of the output generated from the run time.
 *
 */
public class LogWriter {
	public static String dataFileName;
	public static int cacheHits = 0;
	public static int cacheMisses = 0;
	public static int diskReads = 0;
	public static int diskWrites = 0;
	public static long elapsedTime = 0;
	 
	/*
	 * a) The name of the data file being sorted.
	 * b) Times your program found the data it needed in a buffer and
	 *    did not have to go to the disk.
	 * c) The number of disk reads, or times your program had to read a block of
	 *	  data from disk into a bufer.
	 * d) The number of disk writes, or times your program had to write a block
	 * 	  of data to disk from a buffer.
	 * e) The time that your program took to execute the sort.
	 */
	public static String formattedOutput() {
		String s = "Heap sort statistics";
		s += "\nData file name: " + dataFileName;
		s += "\nCache hits: " + cacheHits;
		s += "\nCache misses: " + cacheMisses;
		s += "\nDisk reads: " + diskReads;
		s += "\nDisk writes: " + diskWrites;
		s += "\nSort elapsed time: " + elapsedTime + "ms";
		s += "\n\n";
		
		return s;
	} 

}