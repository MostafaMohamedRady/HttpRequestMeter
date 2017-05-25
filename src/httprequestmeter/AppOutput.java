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
    private static long totalTimeConsumed;
    private static long maxResponseTime;
    private static long minResponseTime;
    private long averageResponseTime;
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

    public long getTotalTimeConsumed() {
        return totalTimeConsumed;
    }

    public void setTotalTimeConsumed(long totalTimeConsumed) {
        this.totalTimeConsumed = totalTimeConsumed;
    }

    public long getMaxResponseTime() {
        return maxResponseTime;
    }

    public void setMaxResponseTime(long maxResponseTime) {
        this.maxResponseTime = maxResponseTime;
    }

    public long getMinResponseTime() {
        return minResponseTime;
    }

    public void setMinResponseTime(long minResponseTime) {
        this.minResponseTime = minResponseTime;
    }

    public long getAverageResponseTime() {
        
        return averageResponseTime;
    }

    public void setAverageResponseTime(long averageResponseTime) {
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

    /**
     * method count thread number
     */
    public static synchronized void threadCount() {
        noOfThreads++;
    }

    /**
     * process request measurement
     * count success request
     * count failed request
     * max response time
     * min response time
     * @param responseTime
     * @param resposeCode
     */
    public static synchronized void requestMeasurement(long responseTime, int resCode) {
        if (resCode == 200) {
            noOfSuccessfulReq++;
            if (responseTime > maxResponseTime) {
                maxResponseTime = responseTime;
            }
            if (responseTime < minResponseTime) {
                minResponseTime = responseTime;
            }
        } else {
            noOfFailedReq++;
        }

        totalTimeConsumed += (responseTime);
    }

    @Override
    public String toString() {
        averageResponseTime=(totalTimeConsumed/ noOfSuccessfulReq);
        return "AppOutput{"
                + " \ntimestampStart = " + timestampStart
                + ",\n timestampEnd = " + timestampEnd
                + ",\n totalTimeConsumed = " + (totalTimeConsumed  / 1000.0)+ " secound "
                + ",\n maxResponceTime = " + maxResponseTime + " millisecond "
                + ",\n minResponseTime = " + minResponseTime + " millisecond "
                + ",\n averageResponseTime = " + averageResponseTime + " millisecond "
                + ",\n noOfSuccessfulReq = " + noOfSuccessfulReq
                + ",\n noOfFailedReq = " + noOfFailedReq + "\n}";
    }

}
