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

  private final Intake m_intake;
  private final PneumaticSubsystem m_pneumatic;
  private final DriveSubsystem m_drive;
  private final Turret m_turret;
  private final Vision m_vision;
  private final Shooter m_shooter;
  private final Hopper m_hopper;
  private final Feeder m_feeder;


  public AutoCommand(Turret p_turret, Intake p_intake, PneumaticSubsystem p_pneumatic, DriveSubsystem p_drive, Vision p_vision, Shooter p_shooter, Hopper p_hopper, Feeder p_feeder) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    m_turret = p_turret;
    m_intake = p_intake;
    m_pneumatic = p_pneumatic;
    m_drive = p_drive;
    m_vision = p_vision;
    m_shooter = p_shooter;
    m_hopper = p_hopper;
    m_feeder = p_feeder;

    //new AutoLockAndDropIntake(m_turret, m_pneumatic, m_vision),
    addCommands(new AutoShooterFire(m_drive, m_turret, m_vision, m_shooter, m_hopper, m_feeder),   
                new LeaveLine(m_drive)
                );
                
  }
}
