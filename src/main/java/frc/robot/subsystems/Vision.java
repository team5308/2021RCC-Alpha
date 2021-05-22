// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.logging.Logger;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {
  private static Logger logger = Logger.getLogger("frc.subsystems.Vision");
  private NetworkTableEntry tX;
  private NetworkTableEntry tY;
  private NetworkTableEntry tV;
  private NetworkTableEntry ledMode;
  private NetworkTableEntry camMode;
  private static double m_headingError;
  private static double m_VerticalError;
  private static double m_getValidTarget;
  public Vision() {
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    tX = table.getEntry("tx");
    tY = table.getEntry("tY");
    tV = table.getEntry("tv");
    ledMode = table.getEntry("ledMode");
    camMode = table.getEntry("camMode");
  }

  @Override
  public void periodic() {
    m_headingError = tX.getDouble(0.0);
    m_VerticalError = tY.getDouble(0.0);
    m_getValidTarget = tV.getDouble(0.0);
    logger.info(String.format("heading: %f\n", m_headingError));
  }

  public double getTargetAngle()
  {
    return 0.9 * m_headingError;
  }

  public static double getHeadingError()
  {
    return m_headingError;
  }

  public static double getValidTarget() {
    return m_getValidTarget;
  }

  public void toggleLight()
  {
    Number mode = ledMode.getNumber(0);
    if (mode.intValue() == 0 || mode.intValue() == 3)
    {
      ledMode.setNumber(1);
    }
    else if (mode.intValue() == 1)
    {
      ledMode.setNumber(3);
    }
  }

  public int getLightValue()
  {
    return ledMode.getNumber(0).intValue();
  }

  public void setLightOn()
  {
    ledMode.setNumber(3);
  }

  public void setLightOff()
  {
    ledMode.setNumber(1);
  }

  
}
