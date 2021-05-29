// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class AutoLinearDrive extends CommandBase {
  /** Creates a new AutoLinearDrive. */
  private DriveSubsystem m_drive;
  private double m_targetDist;
  private double m_accEncoderRotation;
  private int initEncoderReadings;
  // set distance in centimeter
  public AutoLinearDrive(DriveSubsystem p_drive, double p_dist) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(p_drive);
    m_drive = p_drive;
    m_targetDist = p_dist;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    initEncoderReadings = m_drive.getBaseEncoderReading();
    m_accEncoderRotation = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_accEncoderRotation = Math.abs(m_drive.getBaseEncoderReading() - initEncoderReadings);
    while (m_drive.encoderToRawLength(m_accEncoderRotation) - Math.abs(m_targetDist) > 0.01) {
      m_drive.ArcadeDrive(Math.copySign(0.3, m_targetDist), 0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override

  public void end(boolean interrupted) {
    m_drive.stopMotor();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
