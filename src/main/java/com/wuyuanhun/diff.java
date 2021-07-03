package com.wuyuanhun;

import com.wuyuanhun.utils.math.Num;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Transform2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;

public class diff {

    private final double m_base;
    private Pose2d m_pos;
    private Transform2d m_last_trans;
    private double m_last_dt;
    
    public diff(double base_length) {
        this.m_base = base_length;
        this.m_pos = new Pose2d();
    }

    public Transform2d update(double distL, double distR, double dt) {
        m_last_dt = dt;
        if (Num.signOne(distL) * Num.signOne(distR) >= 0) {
            boolean inverse = Num.signOne(distL) >= 0;
            
            distL = Math.abs(distL);
            distR = Math.abs(distR);
            double distC = (distL + distR) / 2.0;
            double dtheta = Math.abs(distL - distR) / m_base; 
            double rk = distC / dtheta;
            double xk = rk * Math.sin(dtheta);
            double yk = rk - rk * Math.cos(dtheta);
            
            if (distR > distL) {
                yk *= -1;
            } else {
                dtheta = Math.PI - dtheta;
            }
            m_last_trans = new Transform2d(new Translation2d(xk, yk), new Rotation2d(dtheta));
            
            if (inverse)
                m_last_trans = m_last_trans.inverse();
            m_pos.plus(m_last_trans);
            return m_last_trans;
        } else {
            /**
             * if two side move with opposite velocity, it should move as a pure rotation, thus |distR| = |distL|\
             * in this case, we add two side together to reduce uncertainties
             */
            boolean rotateLeft = distR > 0;
            double distA = (Math.abs(distL) + Math.abs(distR)) / 2.0;
            double rtheta = distA / (m_base / 2.0);
            
            if (!rotateLeft)
                rtheta *= -1;
            
            m_last_trans = new Transform2d(new Translation2d(), new Rotation2d(rtheta));
            m_pos.plus(m_last_trans);
            return m_last_trans;
        }
    }
}
