// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Vision;

public class HoodSetPositionCommand extends CommandBase {

  private static Hood m_hood;
  private static Vision m_vision;
  
  public HoodSetPositionCommand(Hood p_hood, Vision p_vision) {
    addRequirements(p_hood);
    addRequirements(p_hood);
    m_hood = p_hood;
    m_vision = p_vision;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_hood.setPosition(m_vision.getVerticalAngle());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
