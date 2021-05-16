// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/** Add your docs here. */
public class Interfaces {
    public interface ShooterInterface {
        public void StopMotor();
        public double getVelocity();
        public void setPower(double power);
        public void setVelocity(double velocity); // in RPM
        public void setCurrent(double current);
        // shooter control should make use of PID closed loop control method
    }

    public interface TurretInterface {
        public void feederWork(double power);
        public void zeroAngle();
        public void showCurrentAngle();
        public void powerRotate(double power);
        public void autoSetAngle();
        public double getSetpoint();
        public void clearIAccum();
    }

    public interface DriveTrainInterface {

    }

    public interface ClimberInterface {

    }

    public interface IntakeInterface {

    }

    public interface PneumaticsInterface {

    }

    public interface HopperInterface {

    }
}
