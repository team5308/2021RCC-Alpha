// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;

public class ShootCommand extends CommandBase {
  /** Creates a new Shoot. */
  private final Shooter m_shooter;
  private final Feeder m_feeder;

  private final JoystickButton bindBtn;

  private double m_speed;

  public ShootCommand(Shooter m_shooter, Feeder m_feeder, JoystickButton bindBtn, double speed) {
    addRequirements(m_shooter);
    addRequirements(m_feeder);

    this.m_shooter = m_shooter;
    this.m_feeder = m_feeder;
    this.bindBtn = bindBtn;

    this.m_speed = speed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.setWorkMode();
    m_shooter.showShutdownMode();
    m_shooter.setVelocity(m_speed);
    m_feeder.feederWork();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_feeder.feederWork();
    m_shooter.setVelocity(m_speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.stopMotor();
    m_shooter.setShutMode();
    m_shooter.showShutdownMode();
    m_feeder.feederStop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return bindBtn.get();
  }
}
