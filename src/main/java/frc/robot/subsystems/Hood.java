// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.*;

public class Hood extends SubsystemBase {
  private CANSparkMax m_hood_motor = new CANSparkMax(CanId.MOTOR_HOOD, MotorType.kBrushless);
  private CANPIDController m_pidController;
  private CANEncoder m_encoder; // 0 ~ -9.5 (raw)
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

  NetworkTableEntry hoodPositionEntry;
  NetworkTableEntry hoodTimestampEntry;

  public Hood() {
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("hood");
    hoodPositionEntry = table.getEntry("hood");
    hoodTimestampEntry = table.getEntry("timestamp");

    m_hood_motor.restoreFactoryDefaults();
    m_hood_motor.setIdleMode(IdleMode.kBrake);

    m_pidController = m_hood_motor.getPIDController();
    m_encoder = m_hood_motor.getEncoder();
    m_encoder.setPosition(0);
    // m_encoder.setPositionConversionFactor(-1.0);

    kP = 0;
    kI = 0;
    kD = 0;
    kIz = 0;
    kFF = 0;
    kMaxOutput = 1000; // TODO: the upper range is to be tested
    kMinOutput = 0;

    m_pidController.setP(kP);
    m_pidController.setI(kI);
    m_pidController.setD(kD);
    m_pidController.setIZone(kIz);
    m_pidController.setFF(kFF);
    m_pidController.setOutputRange(kMinOutput, kMaxOutput);
  
    SmartDashboard.putNumber("P Gain", kP);
    SmartDashboard.putNumber("I Gain", kI);
    SmartDashboard.putNumber("D Gain", kD);
    SmartDashboard.putNumber("I Zone", kIz);
    SmartDashboard.putNumber("Feed Forward", kFF);
    SmartDashboard.putNumber("Max Output", kMaxOutput);
    SmartDashboard.putNumber("Min Output", kMinOutput);
    SmartDashboard.putNumber("Set Rotations", 0);


  }

  @Override
  public void periodic() {
    hoodPositionEntry.setDouble(m_encoder.getPosition());
    hoodTimestampEntry.setDouble(Timer.getFPGATimestamp());
    // read PID coefficients from SmartDashboard
    double p = SmartDashboard.getNumber("P Gain", 0);
    double i = SmartDashboard.getNumber("I Gain", 0);
    double d = SmartDashboard.getNumber("D Gain", 0);
    double iz = SmartDashboard.getNumber("I Zone", 0);
    double ff = SmartDashboard.getNumber("Feed Forward", 0);
    double max = SmartDashboard.getNumber("Max Output", 0);
    double min = SmartDashboard.getNumber("Min Output", 0);
    double rotations = SmartDashboard.getNumber("Set Rotations", 0);

    // if PID coefficients on SmartDashboard have changed, write new values to controller
    if((p != kP)) { m_pidController.setP(p); kP = p; }
    if((i != kI)) { m_pidController.setI(i); kI = i; }
    if((d != kD)) { m_pidController.setD(d); kD = d; }
    if((iz != kIz)) { m_pidController.setIZone(iz); kIz = iz; }
    if((ff != kFF)) { m_pidController.setFF(ff); kFF = ff; }
    if((max != kMaxOutput) || (min != kMinOutput)) { 
      m_pidController.setOutputRange(min, max); 
      kMinOutput = min;
      kMaxOutput = max;
    }
    m_pidController.setReference(rotations, ControlType.kPosition);
    SmartDashboard.putNumber("SetPoint", rotations);
    SmartDashboard.putNumber("ProcessVariable", m_encoder.getPosition());
  }

  public void setPosition() {
    
  }
}
