// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.*;

public class DriveForwardCommand extends CommandBase {
  /** Creates a new DriveForwardCommand. */
  private static DriveSubsystem m_drive;
  private double length;
  private double changePos;
  private double initialLeftPos;
  private double initialRightPos;

  //encoder unit per 2048
  double allowedErrorRange = 10;

  public DriveForwardCommand(DriveSubsystem p_drive, double lengthCm) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(p_drive);
    m_drive = p_drive;
    length = lengthCm;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    changePos = m_drive.rawLengthToEncoder(length);
    initialLeftPos = m_drive.getLeftPosition();
    initialRightPos = m_drive.getRightPosition();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drive.setLeftPosition(changePos + initialLeftPos);
    m_drive.setRightPosition(changePos + initialRightPos);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return ((Math.abs(m_drive.getLeftPosition())-changePos) < allowedErrorRange);
  }
}
