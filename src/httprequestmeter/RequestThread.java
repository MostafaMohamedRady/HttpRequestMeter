/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httprequestmeter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mostafa.rady
 */
public class RequestThread implements Runnable {

    private String message;
    private UserInput userInput;

    public RequestThread(String s, UserInput userInput) {
        this.message = s;
        this.userInput = userInput;
        AppOutput.threadCount();
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " (Start) message = " + message);
        for (int i = 1; i <= userInput.getRequestNum(); i++) {
            System.out.println(Thread.currentThread().getName() + " (Request) =" + "----" + i);
            sendGet();
//            processmessage();//call processmessage method that sleeps the thread for 2 seconds  
        }
        System.out.println(Thread.currentThread().getName() + " (End)");//prints thread name  rationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void processmessage() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendGet() {
        URL obj;
        long startTimeMillis=0;
        int responseCode=0;
        HttpURLConnection con=null;
        try {
            obj = new URL(userInput.getUrlApi());
            con = (HttpURLConnection) obj.openConnection();

            boolean redirect = true;

            // normally, 3xx is redirect
//            int status = con.getResponseCode();
//            if (status != HttpURLConnection.HTTP_OK) {
//                if (status == HttpURLConnection.HTTP_MOVED_TEMP
//                        || status == HttpURLConnection.HTTP_MOVED_PERM
//                        || status == HttpURLConnection.HTTP_SEE_OTHER) {
//                    redirect = true;
//                }
//            }
            HttpURLConnection.setFollowRedirects(redirect);

            con.setConnectTimeout(userInput.getTimeout());//request time out

//            con.setReadTimeout(userInput.getTimeout());
            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", HttpRequestMeter.USER_AGENT);

            responseCode = con.getResponseCode();

            startTimeMillis = System.currentTimeMillis();

            String responseMsg = con.getResponseMessage();
//            System.out.println("\nSending 'GET' request to URL : " + userInput.getUrlApi());
//            System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));

//            InputStream inputStream = con.getInputStream();

            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
//            System.out.println("done");
        } catch (java.net.SocketTimeoutException e) {
//            e.printStackTrace();
//            Logger.getLogger(HttpRequestMeter.class.getName()).log(Level.SEVERE, null, e);
        } catch (MalformedURLException ex) {
//            ex.printStackTrace();
//            Logger.getLogger(HttpRequestMeter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
//            Logger.getLogger(HttpRequestMeter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            con.disconnect();
        }
        long endTimeMillis = System.currentTimeMillis();

        AppOutput.requestMeasurement(endTimeMillis - startTimeMillis, responseCode);
    }

}
