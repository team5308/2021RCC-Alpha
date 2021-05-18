// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.logging.Logger;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.Ports;
import frc.robot.Constants.PneumaticConstants;
import frc.robot.Constants.PneuStatus;

public class PneumaticSubsystem extends SubsystemBase {
  private static Logger logger = Logger.getLogger("frc.subsystems.Pneumatic");

  private Compressor m_compressor = new Compressor(Ports.kPCMPort);
  private DoubleSolenoid m_ds_base = new DoubleSolenoid(Ports.kPCMPort, PneumaticConstants.kBaseF, PneumaticConstants.kBaseR);
  public DoubleSolenoid m_ds_climber = new DoubleSolenoid(Ports.kPCMPort, PneumaticConstants.kClimberF, PneumaticConstants.kClimberR);
  private DoubleSolenoid m_ds_lock = new DoubleSolenoid(Ports.kPCMPort, PneumaticConstants.kLockF, PneumaticConstants.kLockR);
  private Solenoid m_ss_intake = new Solenoid(Ports.kPCMPort, PneumaticConstants.kSSIntake);
  private Value m_baseValue = Value.kForward;
  private Value m_climberValue = Value.kForward;
  private Value m_lockValue = Value.kForward;

  public PneumaticSubsystem() {
    logger.info("Pneumatic");
    logger.config("m_ds_climber : kForward");
    logger.config("m_ds_base : kForward");
    logger.config("m_ss_intake : false");

    m_ds_climber.set(Value.kForward);
    m_ds_base.set(Value.kForward);
    m_ss_intake.set(false);
  }

  @Override
  public void periodic() {
    }

  public void CompressorBegin() {
    logger.info("Compressor start");

    m_compressor.start();
  }

  public void CompressorEnd() {
    logger.info("Compressor stop");

    m_compressor.stop();
  }

  public void changeClimberOutput()
  {
    m_climberValue = m_ds_climber.get();
    logger.info("Climber change from " + str(m_climberValue));
    if(m_climberValue == PneuStatus.kClimberDown) {
      _setClimberOutput(PneuStatus.kClimberUp);
    } else if(m_climberValue == PneuStatus.kClimberUp) {
      _setClimberOutput(PneuStatus.kClimberDown);
    }
  }

  public void changeClimberOutput(final Value value)
  {
    _setClimberOutput(value);
  }

  public void _setClimberOutput(final Value value)
  {
    logger.info("Climber output set to " + str(value));

    m_ds_climber.set(value);
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
    boolean curIntakePos = m_ss_intake.get();
    if (curIntakePos) {
      logger.info("intake set false");
      m_ss_intake.set(false);
      // System.out.println("intake set false");
    } else if (!curIntakePos) {
      logger.info("intake set true");
      m_ss_intake.set(true);
      // System.out.println("intake set true");
    }
  }

  public static String str(final Value v) {
    if(v == Value.kForward) {
      return "kForward";
    } else if (v == Value.kReverse) {
      return "kReverse";
    } else return "kOff";
  }
}
