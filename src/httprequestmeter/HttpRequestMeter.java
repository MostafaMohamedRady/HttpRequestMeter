/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httprequestmeter;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author mostafa.rady
 */
public class HttpRequestMeter {

    public static String USER_AGENT = "User-Agent\", \"Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)";

    public static void main(String[] args) {

        System.out.println("**********Http Request Metter**********");
        UserInput userInput = new UserInput();

        Scanner scanner = new Scanner(System.in);

        //  reading thread num
        System.out.print("#Enter Number Of Threads: ");
        int threadNum = scanner.nextInt();
        userInput.setThreadNum(threadNum);

        // reading request num for each thread
        System.out.print("#Enter Request Number For Each Thread: ");
        int requestNum = scanner.nextInt();
        userInput.setRequestNum(requestNum);

        // reading Api Url
        System.out.print("#Enter Api Url: ");
        String apiUrl = scanner.next();
        userInput.setUrlApi(apiUrl);

        // reading timeout
        System.out.print("#Enter Request Timeout: ");
        int timeout = scanner.nextInt();
        userInput.setTimeout(timeout);

        System.out.println("***************************************");

        ExecutorService executor = Executors.newFixedThreadPool(threadNum);//creating a pool of 5 threads
//        String url = "http://www.google.com/search?q=mkyong";
        
        AppOutput appOutput=new AppOutput();
        appOutput.setTimestampStart(System.currentTimeMillis());
        for (int i = 1; i <= threadNum; i++) {
            Runnable worker = new RequestThread(" " + i, userInput);
            executor.execute(worker);//calling execute method of ExecutorService
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }

        System.out.println("Finished all threads");
        appOutput.setTimestampEnd(System.currentTimeMillis());
        System.out.println("httprequestmeter");
        System.out.println(appOutput.toString());
    }

}
