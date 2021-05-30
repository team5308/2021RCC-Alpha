// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoShooterFire extends ParallelCommandGroup {
  private final Shooter m_shooter;
  private final Hopper m_hopper;
  private final Feeder m_feeder;
  private final Turret m_turret;
  private final Vision m_vision;
  private final DriveSubsystem m_drive;

  public AutoShooterFire(DriveSubsystem p_drive, Turret p_turret, Vision p_vision, Shooter p_shooter, Hopper p_hopper, Feeder p_feeder) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_turret = p_turret;
    m_vision = p_vision;
    m_shooter = p_shooter;
    m_hopper = p_hopper;
    m_feeder = p_feeder;
    m_drive = p_drive;
  
    addCommands(new ShooterSetSpeed(m_shooter, 6), new HopperWorkCommand(m_hopper), new FeederWorkCommand(m_feeder));
  }
}
