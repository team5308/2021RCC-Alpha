// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShooterSetSpeed extends CommandBase {
  private Shooter m_shooter;
  private double m_speed;
  public ShooterSetSpeed(Shooter p_shooter, double p_speed) {
    addRequirements(p_shooter);
    m_shooter = p_shooter;
    m_speed = p_speed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_shooter.setWorkMode();
    m_shooter.showShutdownMode();
    m_shooter.setVelocity(m_speed);
    // m_shooter.getCurrent();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.setShutMode();
    m_shooter.showShutdownMode();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
