// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import frc.robot.Constants.CanId;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Intake extends SubsystemBase {
  /** Creates a new Intake. */
  private double kintakeRPM;
  private VictorSPX m_intake_motor = new VictorSPX(CanId.MOTOR_INTAKE);

  public Intake() {
    kintakeRPM = 0;

    m_intake_motor.setNeutralMode(NeutralMode.Coast);
  
    SmartDashboard.putNumber("Intake Speed", kintakeRPM);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    double intakeRPM = SmartDashboard.getNumber("Hopper Speed", 0);
    if (intakeRPM != kintakeRPM) {
      setSpeed(intakeRPM);
    }
  }

  public void setSpeed(double newRPM){
    kintakeRPM = newRPM;
  }

  public double RPMtoRawSensorUnit(double velocity) {
    return velocity * 2048 / 600;
  }

  public double RawSensorUnittoRPM(double velocity) {
    return velocity / 2048 * 600;
  }

}
