/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CanId;
import frc.robot.Constants.Deadband;

public class DriveSubsystem extends SubsystemBase {
  private WPI_TalonFX m_leftMotorFront = new WPI_TalonFX(CanId.DRIVE_LEFT_FRONT);
  private WPI_TalonFX m_leftMotorBack = new WPI_TalonFX(CanId.DRIVE_LEFT_BACK);
  private WPI_TalonFX m_rightMotorFront = new WPI_TalonFX(CanId.DRIVE_RIGHT_FRONT);
  private WPI_TalonFX m_rightMotorBack = new WPI_TalonFX(CanId.DRIVE_RIGHT_BACK);
  private SpeedControllerGroup m_leftMotors = new SpeedControllerGroup(m_leftMotorFront, m_leftMotorBack);
  private SpeedControllerGroup m_rightMotors = new SpeedControllerGroup(m_rightMotorFront, m_rightMotorBack);
  
  private DifferentialDrive m_diff = new DifferentialDrive(m_leftMotors, m_rightMotors);
  private TalonFXConfiguration configDrive = new TalonFXConfiguration();

  private AHRS m_navX = new AHRS(SPI.Port.kMXP);
  private final PowerDistributionPanel PDP = new PowerDistributionPanel(CanId.kPDP);

  private double kP;
  private double kI;
  private double kD;
  private double kF;

  private final Logger logger = Logger.getLogger("frc.subsystems.drive");

  public DriveSubsystem() {
    logger.setLevel(Level.OFF);
    configBaseFX();
    // m_leftMotorFront.setNeutralMode(NeutralMode.Brake);
    // m_leftMotorBack.setNeutralMode(NeutralMode.Brake);
    // m_rightMotorFront.setNeutralMode(NeutralMode.Brake);
    // m_rightMotorBack.setNeutralMode(NeutralMode.Brake);

    m_leftMotorFront.setNeutralMode(NeutralMode.Coast);
    m_leftMotorBack.setNeutralMode(NeutralMode.Coast);
    m_rightMotorFront.setNeutralMode(NeutralMode.Coast);
    m_rightMotorBack.setNeutralMode(NeutralMode.Coast);

    m_leftMotorFront.configAllSettings(configDrive);
    m_leftMotorBack.configAllSettings(configDrive);
    m_rightMotorFront.configAllSettings(configDrive);
    m_rightMotorBack.configAllSettings(configDrive);
  
    SmartDashboard.putNumber("Base P Gain", kP);
    SmartDashboard.putNumber("Base I Gain", kI);
    SmartDashboard.putNumber("Base D Gain", kD);
    SmartDashboard.putNumber("Base Feed Forward", kF);
  }

  @Override
  public void periodic() {
    tunePIDControl();
    configBaseFX();
    // sensorUpdate();
  }

  public void TankDrive(double leftPower, double rightPower) {
    m_diff.tankDrive(deadband(leftPower), deadband(rightPower));
  }

  public void ArcadeDrive(double forward, double rotation) {
    m_diff.arcadeDrive(forward, rotation);
  }

  public double deadband(double input) {
    return Math.abs(input) > Deadband.JOYSTICK_LIMIT ? input : 0;
  }

  // TODO: tune the PID controller here
  private void configBaseFX() {
    configDrive.slot1.kP = kP;
    configDrive.slot1.kI = kI;
    configDrive.slot1.kD = kD;
    configDrive.slot1.kF = kF;
    configDrive.openloopRamp = 1;
  }

  public void tunePIDControl() {
    double p = SmartDashboard.getNumber("Base P Gain", 0);
    double i = SmartDashboard.getNumber("Base I Gain", 0);
    double d = SmartDashboard.getNumber("Base D Gain", 0);
    double ff = SmartDashboard.getNumber("Base Feed Forward", 0);
    if (p != kP) {
      kP = p;
    }
    if (i != kI) {
      kI = i;
    }
    if (d != kD) {
      kD = d;
    }
    if (ff != kF) {
      kF = ff;
    }
    logger.fine("Base P: " + kP + "; Base I: " + kI + "; Base D: " + kD + "; Base F: " + kF);
  }

  public void resetGyro() {
    m_navX.reset();
  }

  public double getGyro() {
    return Math.IEEEremainder(Math.round(m_navX.getAngle() * 100) / 100, 360);
  }

  public double getRawGyro() {
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

  public double getLeftCurrent() {
    return m_leftMotorFront.getSupplyCurrent();
  }

  public double getRightCurrent() {
    return m_rightMotorFront.getSupplyCurrent();
  }

  public double getBusVoltage() {
    return 0.5 * (m_leftMotorFront.getBusVoltage() + m_rightMotorFront.getBusVoltage());
  }

  public double getPDPVoltage(){
    return PDP.getVoltage();
  }

  public double getPDPTotalCurrent(){
    return PDP.getTotalCurrent();
  }

  public double getPDPTotalPower(){
    return PDP.getTotalPower();
  }

  public void sensorUpdate() {
    // SmartDashboard.putNumber("Bus Voltage", getBusVoltage());
    // SmartDashboard.putNumber("PDP Voltage", getPDPVoltage());
    // SmartDashboard.putNumber("PDP Current", getPDPTotalCurrent());
    // SmartDashboard.putNumber("PDP Power", getPDPTotalPower());

    // SmartDashboard.putNumber("Left Position", getLeftPosition());
    // SmartDashboard.putNumber("Right Position", getRightPosition());

    // SmartDashboard.putNumber("Left Velocity", getLeftVelocity());
    // SmartDashboard.putNumber("Right Velocity", getRightVelocity());

    // SmartDashboard.putNumber("Left Current", getLeftCurrent());
    // SmartDashboard.putNumber("Right Current", getRightCurrent());

    // SmartDashboard.putNumber("Gyro Value", getGyro());
    // SmartDashboard.putNumber("Gyro Raw", getRawGyro());
    // SmartDashboard.putNumber("Turn Rate", getTurnRate());
    // SmartDashboard.putNumber("Gyro Graph", getGyro());
    // SmartDashboard.putNumber("Gyro Raw Graph", getRawGyro());
  }
}
