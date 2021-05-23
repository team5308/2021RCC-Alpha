// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.Constants.PneuStatus;
import frc.robot.subsystems.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoCommand extends SequentialCommandGroup {
  /** Creates a new AutoCommand. */
  private double line1 = 100;

  private static Intake m_intake;
  private static PneumaticSubsystem m_pneumatic;
  private static DriveSubsystem m_drive;
  private static Turret m_turret;
  private static Vision m_vision;
  private static Shooter m_shooter;



  public AutoCommand(Intake p_intake, PneumaticSubsystem p_pneumatic, DriveSubsystem p_drive, Vision p_vision, Shooter p_shooter) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addRequirements(p_intake);
    addRequirements(p_pneumatic);
    m_intake = p_intake;
    m_pneumatic = p_pneumatic;
    m_drive = p_drive;
    m_vision = p_vision;
    m_shooter = p_shooter;

    addCommands(
      new TurretAimCommand(m_turret, m_vision),
      new ShooterSetSpeed(m_shooter, m_turret),

      new ChangeIntakeCommand(m_pneumatic, PneuStatus.kIntakeRelease),
      new DriveForwardCommand(m_drive, line1)
    );
  }
}
