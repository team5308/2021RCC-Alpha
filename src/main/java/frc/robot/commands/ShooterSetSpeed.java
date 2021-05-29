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
  public ShooterSetSpeed(Shooter p_shooter, Turret p_turret) {
    addRequirements(p_shooter);
    // addRequirements(p_turret);
    m_shooter = p_shooter;
    // m_turret = p_turret;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_shooter.setVelocity(m_shooter.getTargetSpeed());
    // m_shooter.getCurrent();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.stopMotor();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
