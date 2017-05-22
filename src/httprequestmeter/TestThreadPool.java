/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httprequestmeter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author mostafa.rady
 */
public class TestThreadPool {
    
     public static String USER_AGENT="User-Agent\", \"Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)";

    
    public static void main(String[] args) {
         ExecutorService executor = Executors.newFixedThreadPool(5);//creating a pool of 5 threads
         String url = "http://www.google.com/search?q=mkyong";

        for(int i=0 ;i<5;i++){
            Runnable worker = new WorkerThread(" "+i , url);  
//            executor.execute(worker);//calling execute method of ExecutorService
             executor.submit(worker);
        }
        executor.shutdown();  
        while (!executor.isTerminated()) {  
//            System.out.println("teminated");
        }  
  
        System.out.println("Finished all threads");  
         
    }
    
}
