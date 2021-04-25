// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.Ports;
import frc.robot.Constants.PneumaticConstants;

public class PneumaticSubsystem extends SubsystemBase {
  private Compressor m_compressor = new Compressor(Ports.kPCMPort);
  private DoubleSolenoid m_ds_base = new DoubleSolenoid(Ports.kPCMPort, PneumaticConstants.kBaseF, PneumaticConstants.kBaseR);
  private DoubleSolenoid m_ds_climber = new DoubleSolenoid(Ports.kPCMPort, PneumaticConstants.kClimberF, PneumaticConstants.kClimberR);
  private Value m_baseValue = Value.kForward;
  private Value m_climberValue = Value.kForward;

  public PneumaticSubsystem() {
    m_ds_base.set(Value.kForward);
    m_ds_base.set(Value.kForward);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
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

  
}
