// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PneumaticSubsystem;

public class ArmUpCommand extends CommandBase {
  /** Creates a new ArmUpCommand. */
  private PneumaticSubsystem m_pneu;
  public ArmUpCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_pneu);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("ArmUp init");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_pneu.m_ds_climber.get() == Value.kForward)
    {
      m_pneu.changeClimberOutput();
      System.out.println("now kRevrse");
    }
    else
    {
      m_pneu.changeClimberOutput();
      System.out.println("now kForward");
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
