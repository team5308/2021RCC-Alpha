// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.Ports;
import frc.robot.Constants.PneumaticConstants;

public class PneumaticSubsystem extends SubsystemBase {
  private Compressor m_compressor = new Compressor(Ports.kPCMPort);
  private DoubleSolenoid m_ds_base = new DoubleSolenoid(Ports.kPCMPort, PneumaticConstants.kBaseF, PneumaticConstants.kBaseR);
  public DoubleSolenoid m_ds_climber = new DoubleSolenoid(Ports.kPCMPort, PneumaticConstants.kClimberF, PneumaticConstants.kClimberR);
  private DoubleSolenoid m_ds_lock = new DoubleSolenoid(Ports.kPCMPort, PneumaticConstants.kLockF, PneumaticConstants.kLockR);
  private Solenoid m_ss_intake = new Solenoid(Ports.kPCMPort, PneumaticConstants.kSSIntake);
  private Value m_baseValue = Value.kForward;
  private Value m_climberValue = Value.kForward;
  private Value m_lockValue = Value.kForward;

  public PneumaticSubsystem() {
    m_ds_base.set(Value.kForward);
    m_ds_base.set(Value.kForward);
    m_ss_intake.set(false);
  }

  @Override
  public void periodic() {
    }

  public void CompressorBegin() {
    m_compressor.start();
  }

  public void CompressorEnd() {
    m_compressor.stop();
  }

  public void changeClimberOutput()
  {
    if (m_climberValue == Value.kForward)
    {
      m_ds_climber.set(m_climberValue = Value.kReverse);
    }
    else if (m_climberValue == Value.kReverse)
    {
      m_ds_climber.set(m_climberValue = Value.kForward);
    }
  }

  public void changeBaseOutput()
  {
    if (m_baseValue == Value.kForward)
    {
      m_ds_base.set(m_baseValue = Value.kReverse);
    }
    else if (m_baseValue == Value.kReverse)
    {
      m_ds_base.set(m_baseValue = Value.kForward);
    }
  }

  public void changeLockOutput() {
    if (m_lockValue == Value.kForward)
    {
      m_ds_lock.set(m_lockValue = Value.kReverse);
    }
    else if (m_lockValue == Value.kReverse)
    {
      m_ds_lock.set(m_lockValue = Value.kForward);
    }
  }

  public void changeIntakeOutput() {
    if (m_ss_intake.get()) {
      m_ss_intake.set(false);
      System.out.println("intake set false");
    } else if (!m_ss_intake.get()) {
      m_ss_intake.set(true);
      System.out.println("intake set true");
    }
  }
}
