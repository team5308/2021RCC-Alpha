/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
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

  public DriveSubsystem() {
    configBaseFX();
    m_leftMotorFront.setNeutralMode(NeutralMode.Brake);
    m_leftMotorBack.setNeutralMode(NeutralMode.Brake);
    m_rightMotorFront.setNeutralMode(NeutralMode.Brake);
    m_rightMotorBack.setNeutralMode(NeutralMode.Brake);

    m_leftMotorFront.configAllSettings(configDrive);
    m_leftMotorBack.configAllSettings(configDrive);
    m_rightMotorFront.configAllSettings(configDrive);
    m_rightMotorBack.configAllSettings(configDrive);
  }

  @Override
  public void periodic() {
    configBaseFX();
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

  private void configBaseFX() {
    configDrive.slot1.kP = 0.01;
    configDrive.slot1.kI = 0;
    configDrive.slot1.kD = 0;
    configDrive.openloopRamp = 1;
  }
}
