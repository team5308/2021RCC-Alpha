// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Turret;

public class TurretTurn extends CommandBase {
  /** Creates a new Test. */
  private static Turret m_turret;
  private double targetAngle;
  public TurretTurn(Turret p_turret, double p_targetAngle) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(p_turret);
    m_turret = p_turret;
    targetAngle = p_targetAngle;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_turret.autoSetAngle(targetAngle);
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
