/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httprequestmeter;

import java.sql.Timestamp;

/**
 *
 * @author mostafa.rady
 */
public class AppOutput {

    private static int noOfThreads;
    private Timestamp timestampStart;
    private Timestamp timestampEnd;
    private static double totalTimeConsumed;
    private static long maxResponceTime;
    private static long minResponseTime;
    private int averageResponseTime;
    private static int noOfSuccessfulReq;
    private static int noOfFailedReq;

    public int getNoOfThreads() {
        return noOfThreads;
    }

    public void setNoOfThreads(int noOfThreads) {
        this.noOfThreads = noOfThreads;
    }

    public Timestamp getTimestampStart() {
        return timestampStart;
    }

    public void setTimestampStart(long timestampStartL) {
        this.timestampStart = new Timestamp(timestampStartL);
    }

    public Timestamp getTimestampEnd() {
        return timestampEnd;
    }

    public void setTimestampEnd(long timestampEnd) {
        this.timestampEnd = new Timestamp(timestampEnd);
    }

    public double getTotalTimeConsumed() {
        return totalTimeConsumed;
    }

    public void setTotalTimeConsumed(double totalTimeConsumed) {
        this.totalTimeConsumed = totalTimeConsumed;
    }

    public long getMaxResponceTime() {
        return maxResponceTime;
    }

    public void setMaxResponceTime(long maxResponceTime) {
        this.maxResponceTime = maxResponceTime;
    }

    public long getMinResponseTime() {
        return minResponseTime;
    }

    public void setMinResponseTime(long minResponseTime) {
        this.minResponseTime = minResponseTime;
    }

    public int getAverageResponseTime() {
        return averageResponseTime;
    }

    public void setAverageResponseTime(int averageResponseTime) {
        this.averageResponseTime = averageResponseTime;
    }

    public int getNoOfSuccessfulReq() {
        return noOfSuccessfulReq;
    }

    public void setNoOfSuccessfulReq(int noOfSuccessfulReq) {
        this.noOfSuccessfulReq = noOfSuccessfulReq;
    }

    public int getNoOfFailedReq() {
        return noOfFailedReq;
    }

    public void setNoOfFailedReq(int noOfFailedReq) {
        this.noOfFailedReq = noOfFailedReq;
    }

    public static synchronized void threadCount() {
        noOfThreads++;
    }

    public static synchronized void requestMeasurement(long responseTime, int resCode) {
        if (resCode == 200) {
            noOfSuccessfulReq++;
            if (responseTime > maxResponceTime) {
                maxResponceTime = responseTime;
            }
            if (responseTime < minResponseTime) {
                minResponseTime = responseTime;
            }
        } else {
            noOfFailedReq++;
        }
        
        totalTimeConsumed += (responseTime / 1000.0);
    }

    @Override
    public String toString() {
        return "AppOutput{" +
                "\ntimestampStart=" + timestampStart + 
                ",\n timestampEnd=" + timestampEnd +
                ",\n totalTimeConsumed=" + totalTimeConsumed + " secound "+
                ",\n maxResponceTime=" + maxResponceTime + " millisecond "+
                ",\n minResponseTime=" + minResponseTime + " millisecond "+
                ",\n averageResponseTime=" + averageResponseTime + " millisecond "+
                ",\n noOfSuccessfulReq=" + noOfSuccessfulReq + 
                ",\n noOfFailedReq=" + noOfFailedReq + "\n}";
    }

}
