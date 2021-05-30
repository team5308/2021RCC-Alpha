// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.logging.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Vision;

public class TurretAimCommand extends CommandBase {

  private Logger logger = Logger.getLogger("frc.commands.TurretAimCommand");
  
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
    if(Vision.getValidTarget()) {
      m_turret.autoSetAngle(m_vision.getTargetAngle());
    } else {
      m_turret.stopMotor();
      logger.info("no valid target");
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_turret.stopMotor();
    m_vision.setLightOff();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
    // return  1 != SmartDashboard.getNumber("AutoAim", 1) && (Vision.getValidTarget() && Math.abs(m_vision.getTargetAngle()) <= 1.0);
  }
}
