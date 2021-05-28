package frc.robot.utils;

public class KalmanFilter {
    private Integer predict;
    private Integer current;
    private Integer estimate;
    private double pdelt;
    private double mdelt;
    private double Gauss;
    private double kalmanGain;
    private final static double Q = 0.00001;
    private final static double R = 0.1;

    public void initial() {
        pdelt = 4;
        mdelt = 3;
    }

    public Integer KalmanFilter(Integer oldValue, Integer value) {
        predict = oldValue;
        current = value;
        Gauss = Math.sqrt(pdelt * pdelt + mdelt * mdelt) + Q;
        kalmanGain = Math.sqrt((Gauss * Gauss) / (Gauss * Gauss + pdelt * pdelt)) + R;
        estimate = (int) (kalmanGain * (current - predict) + predict);
        mdelt = Math.sqrt((1-kalmanGain) * Gauss * Gauss);
        return estimate;
    }
}
