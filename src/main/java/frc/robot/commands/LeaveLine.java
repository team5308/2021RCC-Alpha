// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class LeaveLine extends CommandBase {
  /** Creates a new LeaveLine. */
  private final DriveSubsystem m_drive;
  private final Timer m_timer = new Timer();
  public LeaveLine(DriveSubsystem p_drive) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drive = p_drive;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_timer.get() > 1) {
      m_drive.ArcadeDrive(0.6, 0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_timer.get() > 2;
  }
}
