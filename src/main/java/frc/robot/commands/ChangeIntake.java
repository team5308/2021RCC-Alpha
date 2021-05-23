// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.PneumaticSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ChangeIntake extends InstantCommand {
  private boolean isChangeMode;
  private boolean target;
  private PneumaticSubsystem m_pneuSub;

  public ChangeIntake(PneumaticSubsystem pneuSub) {
    this(pneuSub, true, true);
  }

  public ChangeIntake(PneumaticSubsystem pneuSub, boolean target) {
    this(pneuSub, false, target);
  }

  private ChangeIntake(PneumaticSubsystem pneuSub, boolean isChangeMode, boolean target) {
    addRequirements(pneuSub);
    this.target = target;
    this.isChangeMode = isChangeMode;
    this.m_pneuSub = pneuSub;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(isChangeMode) {
      m_pneuSub.changeIntakeOutput();
    } else {
      m_pneuSub.setIntakeOutput(target);
    }
  }
}
