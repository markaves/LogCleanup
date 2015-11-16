package LogCleanup;
import java.io.*;

public class SourceReader {
int cnt = 0;
    
    public String[] readFile(String filename) {
            String[] out = new String[2000];
            
            try {
                   
                FileReader file = new FileReader(filename);
                BufferedReader buff = new BufferedReader(file) ;
                
                boolean eof = false;
                while (!eof) {
                    String line = buff.readLine();
                    if (line == null)   {
                        eof = true;
                    } else {
                        
                        out[cnt] = line;
                        //System.out.println(url[cnt]);
                        cnt++;
                        
                    }
                    }
                    buff.close();
                } catch (IOException e) {
                    System.out.println("Error -- " + e.toString());
                }
            return out;
        }
}