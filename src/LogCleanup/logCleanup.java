package LogCleanup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class logCleanup {

   public void zipFiles(String directoryName, int zipOlderThan){
   System.out.println("Zip Files older than " + zipOlderThan + " days:");
   File directory = new File(directoryName);
   
    //get all the files from a directory
    File[] fList = directory.listFiles();

    for (File file : fList){
        if (file.isFile()){
            
            String delims = "[.]+";
            String[] tokens = file.getName().split(delims);
            System.out.println(file.getName());
                        
            //Zip .log Files greater than 5 days
            if (".log".endsWith(tokens[1])){
                
                int zipOlderThanEpoc = zipOlderThan * 60 * 60 * 24;
                if (zipOlderThanEpoc < (System.currentTimeMillis()/1000)-(file.lastModified()/1000)){                
                    System.out.println("ZIP");
                byte[] buffer = new byte[1024];
                try{
 
                    FileOutputStream fos = new FileOutputStream(directoryName + tokens[0] + ".zip");
                    ZipOutputStream zos = new ZipOutputStream(fos);
                    ZipEntry ze= new ZipEntry(file.getName());
                    zos.putNextEntry(ze);
                    FileInputStream in = new FileInputStream(directoryName + file.getName());
 
                    int len;
                    while ((len = in.read(buffer)) > 0) {
                    	zos.write(buffer, 0, len);
                    }   
 
                    in.close();
                    zos.closeEntry();
 
                    //remember close it
                    zos.close();
 
                    System.out.println("Done");
 
                }catch(IOException ex){
                    ex.printStackTrace();
                }
                file.delete();
            }}
                
        }    
    }
   }    
   
public void delFiles(String directoryName, int deleteOlderThan){
    System.out.println("Deleting Files older than " + deleteOlderThan + " days:");
    File directory = new File(directoryName);
   
    //get all the files from a directory
    File[] fList = directory.listFiles();

    for (File file : fList){
        if (file.isFile()){
            
            String delims = "[.]+";
            String[] tokens = file.getName().split(delims);
            System.out.println(file.getName());
            
            //Delete zip files greater than 15 days
            //System.out.println((System.currentTimeMillis()/1000)-(file.lastModified()/1000));
            int deleteOlderThanEpoc = deleteOlderThan * 60 * 60 * 24;
            if (deleteOlderThanEpoc < (System.currentTimeMillis()/1000)-(file.lastModified()/1000)){ 
                    file.delete();
                    System.out.println("DELETE");
            }
                
        }    
    }
   }    
       
    public static void main(String[] args) {
        
      String[] path = new String[50];  
      String[] config = new String[50];
      int deleteOlderThan = 15;
      int zipOlderThan = 5;
      String logFileExtention = ".log";
          
      //Load Path File
      SourceReader file = new SourceReader();
      path = file.readFile("c://logrotation//path.txt");
      
      //Load Config File
      SourceReader configFile = new SourceReader();
      config = configFile.readFile("c://logrotation//config.cfg");
      
      for (int cnt=0; cnt < configFile.cnt; cnt++)
      {
          String delims = "[=]";
          String[] tokens = config[cnt].split(delims);
          if ( "delete_older_than".equals(tokens[0]))
          {   
              deleteOlderThan = Integer.parseInt(tokens[1]);
          }else if ( "zip_older_than".equals(tokens[0]))
          {
               zipOlderThan = Integer.parseInt(tokens[1]);
          }else if ( "logfile_extention".equals(tokens[0]))
          {
               logFileExtention = tokens[1];
          }
      }
     
      
      logCleanup files = new logCleanup();
      
      for (int cnt=0; cnt < file.cnt; cnt++)
      {
            System.out.println(path[cnt]);
            files.delFiles(path[cnt],deleteOlderThan);
            files.zipFiles(path[cnt],zipOlderThan);
            
           
                   
      }

        //final String directoryLinuxMac ="C://Temp//dir1//";
        
        //logCleanup files = new logCleanup();
        //files.zipFiles(directoryLinuxMac);
        
    }
    
}