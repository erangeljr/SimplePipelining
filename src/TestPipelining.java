/* Eddie Rangel
 * Simple Pipelining
 * Assignment 4
 */

import java.io.*;

public class TestPipelining {

	public static void main(String [] args){
		
		try{
			PLSharedData.pOut12 = new PipedOutputStream();
			PLSharedData.pIn12 = new PipedInputStream(PLSharedData.pOut12);
			PLSharedData.pOut23 = new PipedOutputStream();
			PLSharedData.pIn23 = new PipedInputStream(PLSharedData.pOut23 );
			PLSharedData.pOut34 = new PipedOutputStream();
			PLSharedData.pIn34 = new PipedInputStream(PLSharedData.pOut34);
			
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
		
		t = new Thread(new LowerToUpper(PLSharedData.pIn12, PLSharedData.pOut23));
		t.start();
		
		t = new Thread(new UpperToLower(PLSharedData.pIn23, PLSharedData.pOut34));
		t.start();
		
		t = new Thread(new WriteDataToFile(PLSharedData.pIn34, args[1]));
		t.start();
	}
}

class WriteDataToFile implements Runnable{
	
	FileWriter fileOut = null;
	DataInputStream in = null;
	
	public WriteDataToFile(PipedInputStream pIn, String fileName){
		try{
			fileOut = new FileWriter(fileName);
			in = new DataInputStream(pIn);
		}
		catch(IOException ex){
			
		}
	}
	
	public void run(){
		int data;
		int count = 0;
		try {
			while(true){
				data = in.read();
				fileOut.append((char) data);
				fileOut.flush();
				if(data == -1){
					in.close();
					fileOut.close();
					System.out.printf("\nCharacters read: %d\n", count);
					return;
				}
				count++;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return;
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
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return;
	}
}

class LowerToUpper implements Runnable{
	
	DataInputStream in = null;
	DataOutputStream out = null;
	
	public LowerToUpper(PipedInputStream pIn, PipedOutputStream pOut) {
		in = new DataInputStream(pIn);
		out = new DataOutputStream(pOut);
	}
	
	public void run(){
		int data = 0;
		int count = 0;
		while(true){
			try {
				data = in.readInt();
				out.writeInt(data);
				out.flush();
				if(data == -1){
					in.close();
					out.close();
					System.out.printf("\nCharacters read: %d\n", count);
					return;
				}
				count++;
		        System.out.printf("%c", Character.toUpperCase( (char) data) );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
	
				
		}
	}
}

class UpperToLower implements Runnable{
	
	DataInputStream in = null;
	DataOutputStream out = null;
	
	public UpperToLower(PipedInputStream pIn, PipedOutputStream pOut){
		in = new DataInputStream(pIn);
		out = new DataOutputStream(pOut);
	}
	
	public void run(){
		int data = 0;
		int count = 0;
		while(true){
			try {
				data = in.readInt();
				out.writeInt(data);
				out.flush();
				if(data == -1){
					in.close();
					out.close();
					System.out.printf("\nCharacters read: %d\n", count);
					return;
				}
				count++;
		        System.out.printf("%c", Character.toLowerCase( (char) data) );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
					
		}
	}
}

