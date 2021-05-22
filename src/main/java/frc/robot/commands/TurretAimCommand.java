// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Vision;

public class TurretAimCommand extends CommandBase {
  
  private Turret m_turret;
  private Vision m_vision;

  public TurretAimCommand(Turret p_turret, Vision p_vision) {
    addRequirements(p_turret);
    addRequirements(p_vision);
    m_turret = p_turret;
    m_vision = p_vision;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_vision.setLightOn();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_turret.autoSetAngle(m_vision.getTargetAngle());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_turret.powerRotate(0);
    m_vision.setLightOff();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
