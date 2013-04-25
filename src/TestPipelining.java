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

class GetDataFromFile implements Runnable{
	
	FileReader fIn = null;
	PrintStream out = null;
	
	public GetDataFromFile(String fileName, PipedOutputStream pOut){
		try{ 
			fIn = new FileReader(fileName);
			out = new PrintStream(pOut, true);
		}
		catch(FileNotFoundException ex){
			
		}
	}
	
	public void run(){
		int ch;
		try {
			while(true){
				ch = fIn.read();
				
				out.print(ch);
				if(ch == -1){
					out.close();
					fIn.close();
					return;
				}
				System.out.printf("%c", (char) ch);
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return;
		
	}
	
	
}
