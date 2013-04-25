/* Eddie Rangel
 * Simple Pipelining
 */

import java.io.*;

public class TestPipelining {

	public static void main(String [] args){
		
		try{
			PLSharedData.pOut12 = new PipedOutputStream();
			PLSharedData.pIn12 = new PipedInputStream(PLSharedData.pOut12);
		}
		catch(IOException e){
			System.out.printf("Exception occurred in Piped I/O initializtion.\n");
			e.printStackTrace();
			return;
		}
		
		if(args.length < 2){
			System.out.printf("Usage: java PipeLining inputFile outputFile\n");
			return;
		}
//		Thread t = new Thread(new GetDataFromFile(args[0], PLSharedData.pOut12));
//		t.start();
//		
//		t = new Thread(new LowerToUpper(PLSharedData.pIn12));
//		t.start();
	}
}
