// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CanId;

public class AngleController extends SubsystemBase {
  private CANSparkMax m_hood_neo = new CANSparkMax(CanId.MOTOR_HOOD, MotorType.kBrushless);

  public AngleController() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setpoint() {
    
  }
}
