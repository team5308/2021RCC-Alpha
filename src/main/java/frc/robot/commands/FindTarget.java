// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Vision;

public class FindTarget extends CommandBase {

  private static Turret m_turret;
  private static Vision m_vision;
  private boolean firstReached;
  private boolean secondReached;
  private double currentTurretPosition;
  private double firstTargetPosition;
  private double secondTargetPosition;

  public FindTarget(Turret p_turret, Vision p_vision) {
    addRequirements(p_turret);
    addRequirements(p_vision);
    m_turret = p_turret;
    m_vision = p_vision;
    firstReached = false;
    secondReached = false;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    currentTurretPosition = m_turret.getTurretAngle();
    firstTargetPosition = currentTurretPosition > 0 ? 90 : -90;
    secondTargetPosition = -firstTargetPosition;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (firstReached) {
      if (secondReached) {
        m_turret.setpointSetAngle(currentTurretPosition);
      } else {
        m_turret.setpointSetAngle(secondTargetPosition);
        if (m_turret.getTurretAngle() - secondTargetPosition < 1) {
          secondReached = true;
        }
      }
    } else {
      m_turret.setpointSetAngle(firstTargetPosition);
      if (m_turret.getTurretAngle() - firstTargetPosition < 1) {
        firstReached = true;
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Vision.getValidTarget();
  }
}
