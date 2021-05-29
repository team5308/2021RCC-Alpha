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

  public AutoShooterFire(Shooter p_shooter, Hopper p_hopper, Feeder p_feeder) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_shooter = p_shooter;
    m_hopper = p_hopper;
    m_feeder = p_feeder;
  
    addCommands(new ShooterSetSpeed(m_shooter, 5), new HopperWorkCommand(m_hopper), new DelayedFeederWork(m_feeder));
  }
}
