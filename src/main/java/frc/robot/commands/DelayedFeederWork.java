// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Feeder;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DelayedFeederWork extends SequentialCommandGroup {
  /** Creates a new DelayedFeeederWork. */
  private final Feeder m_feeder;
  public DelayedFeederWork(Feeder p_feeder) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    m_feeder = p_feeder;
    addCommands(new Delay(3), new FeederWorkCommand(m_feeder));
  }
}
