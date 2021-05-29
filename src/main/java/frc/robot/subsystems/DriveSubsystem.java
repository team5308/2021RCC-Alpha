/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CanId;
import frc.robot.Constants.Deadband;
import frc.robot.commands.TurretAimCommand;

public class DriveSubsystem extends SubsystemBase {
  private WPI_TalonFX m_leftMotorFront = new WPI_TalonFX(CanId.DRIVE_LEFT_FRONT);
  private WPI_TalonFX m_leftMotorBack = new WPI_TalonFX(CanId.DRIVE_LEFT_BACK);
  private WPI_TalonFX m_rightMotorFront = new WPI_TalonFX(CanId.DRIVE_RIGHT_FRONT);
  private WPI_TalonFX m_rightMotorBack = new WPI_TalonFX(CanId.DRIVE_RIGHT_BACK);
  
  private DifferentialDrive m_diff = new DifferentialDrive(m_leftMotorFront, m_rightMotorFront);
  private TalonFXConfiguration configDrive = new TalonFXConfiguration();

  private AHRS m_navX = new AHRS(SPI.Port.kMXP);
  private final PowerDistributionPanel PDP = new PowerDistributionPanel(CanId.kPDP);

  private double kP = 0.5;
  private double kI = 0;
  private double kD = 0;
  private double kF = 0;

  private int reverseBase = 1;

  //wheel diameter in centimeter
  private final double WHEEL_DIAMETER = 5 * 2.54;
  private final double WHEEL_PERIMETER = Math.PI * WHEEL_DIAMETER;
  private final double ENCODER_RESOLUTION = 2048;

  private final Logger logger = Logger.getLogger("frc.subsystems.drive");

  public DriveSubsystem() {
    // logger.setLevel(Level.OFF);
    configBaseFX();
    m_leftMotorFront.configAllSettings(configDrive);
    m_leftMotorBack.configAllSettings(configDrive);
    m_rightMotorFront.configAllSettings(configDrive);
    m_rightMotorBack.configAllSettings(configDrive);

    m_leftMotorBack.follow(m_leftMotorFront);
    m_rightMotorBack.follow(m_rightMotorFront);

    m_leftMotorFront.setNeutralMode(NeutralMode.Brake);
    m_leftMotorBack.setNeutralMode(NeutralMode.Brake);
    m_rightMotorFront.setNeutralMode(NeutralMode.Brake);
    m_rightMotorBack.setNeutralMode(NeutralMode.Brake);
  
    SmartDashboard.putNumber("Base P Gain", kP);
    SmartDashboard.putNumber("Base I Gain", kI);
    SmartDashboard.putNumber("Base D Gain", kD);
    SmartDashboard.putNumber("Base Feed Forward", kF);

    m_navX.calibrate();
    resetGyro();
  }

  @Override
  public void periodic() {
    // sensorUpdate();
    double leftv = m_leftMotorFront.get();
    double rightv = m_rightMotorFront.get();
    // logger.info(String.format("l: %.2f r: %.2f\n", leftv, rightv));
  }

  public void TankDrive(double leftPower, double rightPower) {
    m_diff.tankDrive(deadband(leftPower), deadband(rightPower));
  }

  public void ArcadeDrive(double forward, double rotation) {
    m_diff.arcadeDrive(reverseBase*deadband(forward), reverseBase*rotationScale(deadband(rotation)), true);
  }

  public double deadband(double input) {
    return Math.abs(input) > Deadband.JOYSTICK_LIMIT ? input : 0;
  }

  public double rotationScale(double rotation) {
    return Math.abs(rotation) > 0.6 ? Math.copySign(0.6, rotation) : rotation;
  }

  public int getBaseEncoderReading() {
    return m_leftMotorFront.getSelectedSensorPosition();
  }

  // TODO: tune the PID openloop ramp for the base
  private void configBaseFX() {
    configDrive.slot1.kP = 0.04;
    configDrive.slot1.kI = 0;
    configDrive.slot1.kD = 0;
    configDrive.slot1.kF = 0;
    configDrive.openloopRamp = 1;
  }

  public void resetGyro() {
    m_navX.reset();
  }

  public double getGyroAngle() {
    return Math.IEEEremainder(Math.round(m_navX.getAngle() * 100) / 100, 360);
  }

  public double getRawGyroAngle() {
    return m_navX.getAngle();
  }

  public double getTurnRate() {
    return m_navX.getRate();
  }

  public double getLeftPosition() {
    return m_leftMotorFront.getSelectedSensorPosition();
  }

  public double getRightPosition() {
    return m_rightMotorFront.getSelectedSensorPosition();
  }

  public double getLeftVelocity() {
    return m_leftMotorFront.getSelectedSensorVelocity();
  }

  public double getRightVelocity() {
    return m_rightMotorFront.getSelectedSensorVelocity();
  }

  public double encoderToRawLength(double encoderValue){
    double length = encoderValue/ENCODER_RESOLUTION * WHEEL_PERIMETER;
    return length;
  }

  //length should be in units of cm!!!
  public int rawLengthToEncoder(double rawLengthChange){
    int encoderPos = (int) (rawLengthChange/WHEEL_PERIMETER * ENCODER_RESOLUTION);
    return encoderPos;
  }

  public void setLeftPosition(double changeUnit){
    m_leftMotorFront.set(ControlMode.Position, changeUnit);
  }

  public void setRightPosition(double changeUnit){
    m_rightMotorFront.set(ControlMode.Position, changeUnit);

  }

  public void reverseBase() {
    reverseBase *= -1;
  }

}
