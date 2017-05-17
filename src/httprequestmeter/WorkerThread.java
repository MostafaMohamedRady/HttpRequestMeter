/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httprequestmeter;

import static com.sun.xml.internal.ws.api.message.Packet.State.ClientResponse;
import java.awt.PageAttributes.MediaType;
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
public class WorkerThread implements Runnable {

    private String message;
    private String url;

    public WorkerThread(String s, String url) {
        this.message = s;
        this.url = url;
    }

    @Override
    public void run() {
//        System.out.println(Thread.currentThread().getName() + " (Start) message = " + message);
//        processmessage();//call processmessage method that sleeps the thread for 2 seconds  
        for (int i = 0; i < 3; i++) {
            System.out.println(Thread.currentThread().getName() + " (Start) message = " + message + "  " + i);
            sendGet();
        }
        System.out.println(Thread.currentThread().getName() + " (End)");//prints thread name  rationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void processmessage() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    

    private void sendGet() {
        URL obj;
        try {
            obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            /*HttpURLConnection.setFollowRedirects(false);
            con.setConnectTimeout(1000000);//request time out
            con.setReadTimeout(100000);*/
            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", TestThreadPool.USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
//            System.out.println(response.toString());
            System.out.println("done");

        } catch (java.net.SocketTimeoutException e) {
            System.out.println("socket exception");
//            Logger.getLogger(RequestMeter.class.getName()).log(Level.SEVERE, null, e);
        } catch (MalformedURLException ex) {
            Logger.getLogger(TestThreadPool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestThreadPool.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
