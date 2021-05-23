// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.logging.Logger;

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

  private final Logger logger = Logger.getLogger("frc.subsystems.turret");

  public TalonSRX m_turret_motor = new TalonSRX(CanId.MOTOR_TURRET);
  public TalonSRXConfiguration configTurret = new TalonSRXConfiguration();

  private double current_angle;
  private double setpoint = 0;

  private double kP = 0.002;
  private double kI = 0;
  private double kD = 0.0;
  int kCruiseVelocity = 14000;
  int kMotionAcceleration = kCruiseVelocity * 10;

  private double target_height = 2;
  private double limelight_mount_height = 0.5;
  private double limelight_mount_angle = 20;
  private double gear_ratio = 1.0 / 13.0;
  private double minAngle = -90;
  private double maxAngle = 90;
  private int encoderUnitsPerRotation = 4096;

  public Turret() {
    m_turret_motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    m_turret_motor.setNeutralMode(NeutralMode.Brake);
    m_turret_motor.setSelectedSensorPosition(0);
    
    m_turret_motor.config_kP(0, kP);
    m_turret_motor.config_kI(0, kI);
    m_turret_motor.config_kD(0, kD);

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
    int pos = degreesToEncoderUnits(targetAngle);
    int targetPosition = ((int) m_turret_motor.getSelectedSensorPosition()) - pos;
    m_turret_motor.set(ControlMode.MotionMagic, targetPosition);
    // m_turret_motor.set(ControlMode.MotionMagic, degreesToEncoderUnits(getSetpoint(targetAngle)));
    logger.info(String.format("targetAngle: %.2f TARGETPOSITION: %d curV: %.2f",targetAngle, targetPosition, m_turret_motor.getMotorOutputPercent()));
  }

  public double getSetpoint(double targetAngle) {
    setpoint = getTurretAngle() - targetAngle;
    if (Math.abs(setpoint) > 90)
    {
      System.out.println("Target position out of turning limit!");
      return Math.signum(setpoint) * 90;
    }
    return setpoint;
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
    m_turret_motor.set(ControlMode.Position, m_turret_motor.getSelectedSensorPosition());
  }

  public void configSRX() {
    configTurret.slot2.kP = 0;
  }
}
