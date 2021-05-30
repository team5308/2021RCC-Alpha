// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Intake;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoDriveAndIntake extends ParallelCommandGroup {
  /** Creates a new AutoDriveAndIntake. */
  private final DriveSubsystem m_drive;
  private final Intake m_intake;
  public AutoDriveAndIntake(DriveSubsystem p_drive, Intake p_intake) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    m_drive = p_drive;
    m_intake = p_intake;
    addCommands(new AutoLinearDrive(m_drive, 50),
                new IntakeWorkCommand(m_intake));
  }
}
