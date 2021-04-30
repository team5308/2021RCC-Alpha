// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CanId;

public class Hopper extends SubsystemBase {

  private double khopperRPM;

  private TalonSRX m_hopper_motor = new TalonSRX(CanId.MOTOR_HOPPER);

  public Hopper() {
    khopperRPM = 0;

    m_hopper_motor.setNeutralMode(NeutralMode.Coast);

    SmartDashboard.putNumber("Hopper Speed", khopperRPM);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    double hopperRPM = SmartDashboard.getNumber("Hopper Speed", 0);
    if (hopperRPM != khopperRPM) {
      setSpeed(hopperRPM);
    }
  }

  public void setSpeed(double newRPM) {
    // In RPM
    khopperRPM = newRPM;
  }

  public void getCurrent() {
    m_hopper_motor.getStatorCurrent();
  }

  public double RPMtoRawSensorUnit(double velocity) {
    return velocity * 2048 / 600;
  }

  public double RawSensorUnittoRPM(double velocity) {
    return velocity / 2048 * 600;
  }
}
