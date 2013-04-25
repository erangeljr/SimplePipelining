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
			System.out.printf("Usage: java TestPipelining inputFile outputFile\n");
			return;
		}
		Thread t = new Thread(new GetDataFromFile(args[0], PLSharedData.pOut12));
		t.start();
		
//		t = new Thread(new LowerToUpper(PLSharedData.pIn12));
//		t.start();
	}
}

class GetDataFromFile implements Runnable{
	
	FileReader fileIn = null;
	DataOutputStream out = null;
	
	public GetDataFromFile(String fileName, PipedOutputStream pOut){
		try{ 
			fileIn = new FileReader(fileName);
			out = new DataOutputStream(pOut);
		}
		catch(FileNotFoundException ex){
			
		}
	}
	
	public void run(){
		int data;
		try {
			while(true){
				data = fileIn.read();
				out.writeInt(data);
				out.flush();
				if(data == -1){
					out.close();
					fileIn.close();
					return;
				}
				System.out.printf("%c", (char) data);
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return;
		
	}
	
	
}
