// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;

public class ShooterSetSpeed extends CommandBase {
  private Shooter m_shooter;
  private Turret m_turret;
  private double m_speed;
  public ShooterSetSpeed(Shooter p_shooter, Turret p_turret, double p_speed) {
    addRequirements(p_shooter);
    addRequirements(p_turret);
    m_shooter = p_shooter;
    m_turret = p_turret;
    m_speed = p_speed;
  }

  public ShooterSetSpeed(Shooter p_shooter, Turret p_turret) {
    this(p_shooter, p_turret, 4000);
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
    m_shooter.stopMotor();
    m_shooter.setShutMode();
    m_shooter.showShutdownMode();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
