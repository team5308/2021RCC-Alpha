// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.PneumaticSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ChangeClimberCommand extends InstantCommand {
  private final PneumaticSubsystem pneuSub;
  private final DoubleSolenoid.Value target;

  public ChangeClimberCommand(PneumaticSubsystem pneSub)
  {
    this(pneSub, DoubleSolenoid.Value.kOff);
  }

  public ChangeClimberCommand(PneumaticSubsystem pneSub, DoubleSolenoid.Value target) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(pneSub);
    pneuSub = pneSub;
    this.target = target;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if( target == DoubleSolenoid.Value.kOff) {
      pneuSub.changeClimberOutput();
    }
    else pneuSub.setClimberOutput(target);
  }
}
