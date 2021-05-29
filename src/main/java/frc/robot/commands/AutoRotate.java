// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class AutoRotate extends CommandBase {
  /** Creates a new RotationCommand. */
  private static DriveSubsystem m_drive;
  private double m_setAngle;
  private double m_targetAngle;
  private double m_currentAngle;
  private double m_rotateDirection;
  
  public AutoRotate(DriveSubsystem p_drive, double p_setAngle) {
    addRequirements(p_drive);
    m_drive = p_drive;
    m_setAngle = p_setAngle;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_currentAngle = m_drive.getRawGyroAngle();
    m_targetAngle = m_currentAngle + m_setAngle;
    m_rotateDirection = Math.copySign(1, m_setAngle);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_currentAngle = m_drive.getRawGyroAngle();
    while (Math.abs(m_currentAngle - m_targetAngle) > 0.001) {
      m_drive.ArcadeDrive(0, 0.3 * m_rotateDirection);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
