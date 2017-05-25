/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httprequestmeter;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mostafa.rady
 */
public class HttpRequestMeter {

    public static String USER_AGENT = "User-Agent\", \"Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)";
    public static String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%?=~_|]";

    public static void main(String[] args) {

        System.out.println("**********Http Request Metter**********");
        UserInput userInput = readInputData();
        System.out.println("***************************************");

        ExecutorService executor = Executors.newFixedThreadPool(userInput.getThreadNum());//creating a pool of 5 threads

        AppOutput appOutput = new AppOutput();
        appOutput.setTimestampStart(System.currentTimeMillis());
        for (int i = 1; i <= userInput.getThreadNum(); i++) {
            Runnable worker = new RequestThread(" " + i, userInput);
            executor.execute(worker);//calling execute method of ExecutorService
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }

        System.out.println("Finished all threads");
        appOutput.setTimestampEnd(System.currentTimeMillis());

        System.out.println("***************************************");
        System.out.println(appOutput.toString());
    }

    /**
     * read data from screen
     * @return UserInput  
     */
    private static UserInput readInputData() {

        UserInput userInput = new UserInput();

        Scanner scanner = new Scanner(System.in);
        //  reading thread num
        System.out.print("#Enter Number Of Threads: ");
        while (!scanner.hasNextInt()) {
            System.out.println("#Enter Valid Number Of Threads like 1,2.. :");
            scanner.next(); // this is important!
        }
        int threadNum = scanner.nextInt();
        userInput.setThreadNum(threadNum);

        // reading request num for each thread
        System.out.print("#Enter Request Number For Each Thread: ");
        while (!scanner.hasNextInt()) {
            System.out.println("#Enter Valid Number like 1,2.. :");
            scanner.next(); // this is important!
        }
        int requestNum = scanner.nextInt();
        userInput.setRequestNum(requestNum);

        // reading Api Url
        System.out.print("#Enter Api Url: ");
        String apiUrl = scanner.next();
        while (!validateUrl(apiUrl)) {
            System.out.print("#Enter valid Api Url like 'http://www.google.com/search?q=developer' : ");
            apiUrl = scanner.next();
        }
        userInput.setUrlApi(apiUrl);

        // reading timeout
        System.out.print("#Enter Request Timeout: ");
        int timeout = scanner.nextInt();
        userInput.setTimeout(timeout);
        return userInput;
    }

    /**
     * validate Url
     * @param Url
     * @return boolean
     */
    private static boolean validateUrl(String urlVar) {
        try {
            Pattern patt = Pattern.compile(regex);
            Matcher matcher = patt.matcher(urlVar);
            return matcher.matches();
        } catch (RuntimeException e) {
            return false;
        }
    }

}
