// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.Constants.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Turret extends SubsystemBase {

  public TalonSRX m_turret_motor = new TalonSRX(CanId.MOTOR_TURRET);
  public TalonSRXConfiguration configTurret = new TalonSRXConfiguration();

  private double current_angle;
  private double setpoint = 0;

  private double kP = 0.04;
  private double kI = 0.0035;
  private double kD = 0.0;
  private double kF = 0.1;
  int kI_Zone = 900;
  int kMaxIAccum = 1000000;
  int kCruiseVelocity = 14000;
  int kMotionAcceleration = kCruiseVelocity * 10;
  int kErrorBand = 74;

  private double target_height = 2;
  private double limelight_mount_height = 0.5;
  private double limelight_mount_angle = 20;
  private double gear_ratio = 1 / 13;
  private double minAngle = -90;
  private double maxAngle = 90;
  private int encoderUnitsPerRotation = 4096;

  public Turret() {
    m_turret_motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    m_turret_motor.setNeutralMode(NeutralMode.Brake);
    
    m_turret_motor.config_kP(0, kP);
    m_turret_motor.config_kI(0, kI);
    m_turret_motor.config_kD(0, kD);
    m_turret_motor.config_kF(0, kF);
    m_turret_motor.config_IntegralZone(0, kI_Zone);
    m_turret_motor.configMaxIntegralAccumulator(0, kMaxIAccum);
    m_turret_motor.configAllowableClosedloopError(0, kErrorBand);

    zeroAngle();
    zeroTurretEncoder();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void zeroAngle() {
    current_angle = 0;
  }

  public void zeroTurretEncoder() {
    m_turret_motor.setSelectedSensorPosition(0);
  }

  public void showCurrentAngle() {
    System.out.println(current_angle);
  }

  public void powerRotate(double power) {
    m_turret_motor.set(ControlMode.PercentOutput, power);
  }

  public void autoSetAngle(double targetAngle)
  {
    m_turret_motor.set(ControlMode.Position, degreesToEncoderUnits(getSetpoint(targetAngle)));
  }

  public void autoSetAngle(double targetAngle, boolean ClockwiseOrReverse) {
    // m_turret_motor.set(ControlMode.PercentOutput, -getSetpoint(targetAngle)/90);
    setpointSetAngle(getSetpoint(targetAngle));
  }

  public double getSetpoint(double targetAngle) {
    setpoint = targetAngle + getTurretAngle();
    
    return setpoint;
  }

  public void setpointSetAngle(double p_angle) {
    double currentAngle = getTurretAngle();
    double setpoint = p_angle;
    double error = setpoint - currentAngle;
    m_turret_motor.set(ControlMode.PercentOutput, 0.01 * -error);
  }

  // public double getSetpoint(double targetAngle) {
  //   setpoint = targetAngle + getTurretAngle();
  //   System.out.println(setpoint);
  //   if (setpoint < -90 || setpoint > 90)
  //   {
  //     System.out.println("Target position out of turning limit!");
  //     return setpoint < -90 ? -90 : 90;
  //   }
  //   return setpoint;
  // }

  public double getTurretAngle()
  {
    return encoderUnitsToDegrees(m_turret_motor.getSelectedSensorPosition());
  }

  public void clearIAccum() {
    m_turret_motor.setIntegralAccumulator(0);
  }

  public double encoderUnitsToDegrees(double encoderUnits)
  {
    return encoderUnits * gear_ratio * (360.0 / encoderUnitsPerRotation);
  }

  public int degreesToEncoderUnits(double degrees)
  {
    return (int) (degrees * (1.0 / gear_ratio) * (encoderUnitsPerRotation / 360.0));
  }

  public void turnLeft()
  {
    m_turret_motor.set(ControlMode.PercentOutput, 0.3);
  }

  public void turnRight()
  {
    m_turret_motor.set(ControlMode.PercentOutput, -0.3);
  }

  public void stopMotor(){
    powerRotate(0);
  }

  public void configSRX() {
    configTurret.slot2.kP = 0;
  }
}
