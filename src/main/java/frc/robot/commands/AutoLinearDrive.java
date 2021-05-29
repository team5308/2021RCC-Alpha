// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.logging.Logger;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class AutoLinearDrive extends CommandBase {
  /** Creates a new AutoLinearDrive. */
  private DriveSubsystem m_drive;
  private double m_targetDist;
  private int m_accEncoderRotation;
  private int initEncoderReadings;
  private int targetEncoder;

  private Logger logger = Logger.getLogger("frc.auto");
  // set distance in centimeter
  public AutoLinearDrive(DriveSubsystem p_drive, double p_dist) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(p_drive);
    m_drive = p_drive;
    m_targetDist = p_dist;
    targetEncoder = (int) m_drive.rawLengthToEncoder(m_targetDist);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    initEncoderReadings = m_drive.getBaseEncoderReading();
    m_accEncoderRotation = m_drive.getBaseEncoderReading() - initEncoderReadings;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_accEncoderRotation = m_drive.getBaseEncoderReading() - initEncoderReadings;
    m_drive.ArcadeDrive(Math.copySign(0.4, m_targetDist), 0);
  }

  // Called once the command ends or is interrupted.
  @Override

  public void end(boolean interrupted) {
    m_drive.ArcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    logger.info(String.format("Target: %d, curPos: %d init:", targetEncoder, m_accEncoderRotation));
    if (Math.abs(m_accEncoderRotation) - Math.abs(targetEncoder) < 10) {
      return true;
    }
    return false;
  }
}
