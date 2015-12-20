/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UsfTools;

//import MBTAArc.ArcListByRoute;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import yanchen.asg3.Stop;

/**
 *
 * @author troy
 */
public class FileIO {

    public static String getAbsFilePath() {
        return absFilePath;
    }

    public static String getComFilePath() {
        return comFilePath;
    }

    public FileIO() {
    }
    
    private String stopsByLocationRes;
    private boolean exists;
    private static String absFilePath = "/WEB-INF/haha.txt";
    private static String comFilePath = "haha.txt";
    private BufferedWriter bufferWriter;
    
    public void WriteToFile(String res, String path) throws IOException{
        File varTmpDir = new File(path);
            exists = varTmpDir.exists();
        if(exists != true){
//            FileResource fileResource = new FileResource(new File(basepath + absFilePath));
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, 
                    "UTF-8");
            bufferWriter = new BufferedWriter(outputStreamWriter); 
            bufferWriter.write(res);
            bufferWriter.flush();
            bufferWriter.close();            
        } else{
            System.out.println("The file already exists!");
        }
    }
    
    public void MBTAConnect1() throws IOException{
        String mbtaBaseURL = "http://realtime.mbta.com/developer/api/v2/";
        String apiKey = "api_key=wX9NwuHnZU2ToO7GmGR9uw";
        String format = "json";
//        String requestURL = mbtaBaseURL + "stopsbylocation" + apiKey + "&lat=42.347158&lon=-71.075727&format=" + format;               
        String requestURL = "http://realtime.mbta.com/developer/api/v2/stopsbylocation?api_key=wX9NwuHnZU2ToO7GmGR9uw&lat=42.346961&lon=-71.076640&format=json";
        CloseableHttpClient myClient = HttpClients.createDefault();             //CloseableHttpClient
        HttpGet myGetRequest = new HttpGet(requestURL);
        HttpEntity myEntity = null;
        CloseableHttpResponse myResponse = null; 
        String res = null;
        try{
            myResponse = myClient.execute(myGetRequest);  //cannot be placed outside of try
            myEntity = myResponse.getEntity();
            res = EntityUtils.toString(myEntity);
        }catch(IOException ex){
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);  //.class
        }finally{
            myResponse.close();
        }
        System.out.println(res);
        stopsByLocationRes = res;
    }
    
    public static void main(String[] args) throws IOException
    {
        
        FileIO fileIO = new FileIO();
        fileIO.MBTAConnect1();
        fileIO.WriteToFile("success", null);
    }

    public String getStopsByLocationRes() {
        return stopsByLocationRes;
    }

    public boolean isExists() {
        return exists;
    }

    public BufferedWriter getBufferWriter() {
        return bufferWriter;
    }
}
