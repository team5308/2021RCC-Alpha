// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.PneumaticSubsystem;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Vision;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoLockAndDropIntake extends ParallelCommandGroup {
  /** Creates a new AutoLockAndDropIntake. */
  private final Turret m_turret;
  private final PneumaticSubsystem m_pneumatic;
  private final Vision m_vision;
  public AutoLockAndDropIntake(Turret p_turret, PneumaticSubsystem p_pneumatic, Vision p_vision) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    m_turret  = p_turret;
    m_pneumatic = p_pneumatic;
    m_vision = p_vision;
    addCommands(new TurretAimCommand(m_turret, m_vision),
                new ChangeIntake(m_pneumatic));
  }
}
