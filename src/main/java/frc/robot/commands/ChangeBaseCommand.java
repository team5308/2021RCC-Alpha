// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.PneumaticSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ChangeBaseCommand extends InstantCommand {
  private PneumaticSubsystem m_pneu;
  private Value target;

  public ChangeBaseCommand(PneumaticSubsystem pneu) {
    this(pneu, Value.kOff);
  }

  public ChangeBaseCommand(PneumaticSubsystem pneu, Value target) {
    addRequirements(pneu);
    this.m_pneu = pneu;
    this.target = target;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(target == Value.kOff) {
      m_pneu.changeBaseOutput();
    } else {
      m_pneu.setBaseOutput(target);
    }
  }
}