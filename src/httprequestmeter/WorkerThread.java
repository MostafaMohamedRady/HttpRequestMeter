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
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author mostafa.rady
 */
public class WorkerThread implements Runnable {

    private String message;
    private String url;
    int i=1;
    public WorkerThread(String s, String url) {
        this.message = s;
        this.url = url;
    }

    @Override
    public void run() {
//        System.out.println(Thread.currentThread().getName() + " (Start) message = " + message);

        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + " (Start) Request="+"----" + ++i);

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
        try {
            obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            HttpURLConnection.setFollowRedirects(true);
            con.setConnectTimeout(100);//request time out
//            con.setReadTimeout(1000);
            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", TestThreadPool.USER_AGENT);

            int responseCode = con.getResponseCode();
            String responseMsg = con.getResponseMessage();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
//            System.out.println("Response Message : " + responseMsg);

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
//            System.out.println("done");

        }catch (java.net.SocketTimeoutException e) {
            System.out.println("socket exception");
            Logger.getLogger(TestThreadPool.class.getName()).log(Level.SEVERE, null, e);
        }
        //catch (TimeoutException  ex) {
//            System.out.println("socket exception");
////            Logger.getLogger(RequestMeter.class.getName()).log(Level.SEVERE, null, e);
//        } 
        catch (MalformedURLException ex) {
            System.out.println("MalformedURLException");
            Logger.getLogger(TestThreadPool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestThreadPool.class.getName()).log(Level.SEVERE, null, ex);
        }catch(Exception e){
            System.out.println("exception");
        }

    }

}
