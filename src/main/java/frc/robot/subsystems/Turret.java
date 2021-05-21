// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;


import frc.robot.Constants.*;
import frc.robot.Interfaces.TurretInterface;
import frc.robot.subsystems.Vision.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Turret extends SubsystemBase implements TurretInterface{

  private TalonFX m_turret_motor = new TalonFX(CanId.MOTOR_TURRET);

  private VictorSPX m_feeder_tvictor_left = new VictorSPX(CanId.MOTOR_FEEDER_LEFT);
  private VictorSPX m_feeder_tvictor_right = new VictorSPX(CanId.MOTOR_FEEDER_RIGHT);

  private double current_angle;
  private double setpoint = 0;

  double kP = 0.4;
  double kI = 0.0035;
  double kD = 0.0;
  double kF = 0;
  int kI_Zone = 900;
  int kMaxIAccum = 1000000;
  int kCruiseVelocity = 14000;
  int kMotionAcceleration = kCruiseVelocity * 10;
  int kErrorBand = 74;

  double kfeederSpeed = 1;

  double target_height = 0;
  double limelight_mount_height = 0;
  double limelight_mount_angle = 0;
  double gear_ratio = 1 / 13;
  double minAngle = -90;
  double maxAngle = 90;
  private int encoderUnitsPerRotation = 4096;

  public Turret() {
    m_turret_motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    m_turret_motor.setNeutralMode(NeutralMode.Brake);
    m_feeder_tvictor_left.setNeutralMode(NeutralMode.Coast);
    m_feeder_tvictor_right.setNeutralMode(NeutralMode.Coast);
    m_feeder_tvictor_left.setInverted(true);
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
    double feederSpeed = SmartDashboard.getNumber("Hopper Speed", 0);
    if (feederSpeed != kfeederSpeed) {
      feederSetSpeed(feederSpeed);
    }
  }

  public void feederWork() {
    m_feeder_tvictor_left.set(ControlMode.PercentOutput, kfeederSpeed);
    m_feeder_tvictor_right.set(ControlMode.PercentOutput, kfeederSpeed);
  }

  public void feederStop(){
    m_feeder_tvictor_left.set(ControlMode.PercentOutput, 0);
    m_feeder_tvictor_right.set(ControlMode.PercentOutput, 0);
  }

  public void feederSetSpeed(double feederSpeed){
    kfeederSpeed = feederSpeed;
  }

  public void zeroAngle() {
    double limitCurrent = 0; //TODO: test the limit current
    while (m_turret_motor.getStatorCurrent() < limitCurrent) {
      m_turret_motor.set(ControlMode.PercentOutput, 0.3);
    }
    m_turret_motor.set(ControlMode.PercentOutput, 0);
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

  public void autoSetAngle()
  {
    m_turret_motor.set(ControlMode.MotionMagic, degreesToEncoderUnits(getSetpoint()));
  }

  public double getSetpoint() 
  {
    setpoint = getTargetAngle() + getTurretAngle();
    System.out.println(setpoint);
    if (setpoint < 0 && setpoint > 180)
    {
      System.out.println("Target position out of turning limit! Turn! Turn!");
      return setpoint < 0 ? 0 : 180;
    }
    return setpoint;
  }

  public double getTargetAngle()
  {
    return 0.9 * Vision.getHeadingError();
  }

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
}
