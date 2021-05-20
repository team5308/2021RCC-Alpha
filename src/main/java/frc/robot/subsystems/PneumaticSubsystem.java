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
    logger.config("m_ds_lock : ");

    m_ds_climber.set(PneuStatus.kClimberUp);
    m_ds_base.set(PneuStatus.kBaseDrive);
    m_ss_intake.set(PneuStatus.kIntakeLock);
    m_ds_lock.set(PneuStatus.kClimberLock);
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
      setClimberOutput(PneuStatus.kClimberUp);
    } else if(m_climberValue == PneuStatus.kClimberUp) {
      setClimberOutput(PneuStatus.kClimberDown);
    }
  }


  public void setClimberOutput(final Value value)
  {
    logger.info("Climber output set to " + str(value));

    m_ds_climber.set(value);
  }

  public void changeBaseOutput()
  {
    m_baseValue = m_ds_climber.get();
    logger.info("Base change from " + str(m_baseValue));
    if (m_baseValue == PneuStatus.kBaseClimb)
    {
      setBaseOutput(PneuStatus.kBaseDrive);
    }
    else if (m_baseValue == PneuStatus.kBaseDrive)
    {
      setBaseOutput(PneuStatus.kBaseClimb);
    }
  }

  public void setBaseOutput(final Value value)
  {
    logger.info("Base output set to " + str(value));
    m_ds_base.set(value);
  }

  public void changeLockOutput() {
    m_lockValue = m_ds_lock.get();
    logger.info("Lock change from " + str(m_lockValue));
    if (m_lockValue == PneuStatus.kClimberLock)
    {
      m_ds_lock.set(PneuStatus.kClimberRelease);
    }
    else if (m_lockValue == PneuStatus.kClimberRelease)
    {
      m_ds_lock.set(PneuStatus.kClimberLock);
    }
  }

  public void setLockOutput(final Value value) {
    logger.info("Lock set to " + str(value));
    m_ds_lock.set(value);
  }

  public void changeIntakeOutput() {
    boolean curIntakePos = m_ss_intake.get();
    logger.info("Intake change from " + checkIntakeStatus(curIntakePos) + (curIntakePos ? "(true)" : "(false)"));
    setIntakeOutput(!curIntakePos);
  }

  public void setIntakeOutput(final boolean value) {
    logger.info("intake set to " + checkIntakeStatus(value));
    m_ss_intake.set(value);
  }

  public static String str(final Value v) {
    if(v == Value.kForward) 
    {
      return "kForward";
    } 
    else if (v == Value.kReverse) 
    {
      return "kReverse";
    } 
    else return "kOff";
  }

  private static String checkIntakeStatus(boolean curPos)
  {
    return curPos ^ PneuStatus.kIntakeLock ? "Release" : "Lock";
  }
}
