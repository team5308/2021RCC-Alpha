// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utils;

/** Add your docs here. */
public class Acc {
    
    private float kp, ki, kd;
    private int i_max = 200;
    private double last_output = 0;
    private float[] izone = new float[i_max];
    private int ii = 0;
    
    public Acc() {
        this(0, 0, 0);
    }

    public Acc(float kp, float ki, float kd) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
    }

    public void setP(float newp) {
        kp = newp;
    }

    public void setI(float newi) {
        ki = newi;
    }

    public void setD(float newd) {
        kd = newd;
    }

    public double update(double output) {
        double p = kp * output;
        return p;
    }
    
}
