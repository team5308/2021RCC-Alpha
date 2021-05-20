/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

import java.util.logging.Logger;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.*;
import frc.robot.subsystems.*;


/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private static Logger logger = Logger.getLogger("frc.RobotContainer");

  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  private final DriveSubsystem m_drive = new DriveSubsystem();
  private final PneumaticSubsystem m_pneumatic = new PneumaticSubsystem();
  private final Shooter m_shooter = new Shooter();
  private final Turret m_turret = new Turret();
  private final Hood m_hood = new Hood();

  private Joystick m_leftJoy = new Joystick(0);
  private Joystick m_rightJoy = new Joystick(1);

  private JoystickButton m_leftButton1 = new JoystickButton(m_leftJoy, 1);
  private JoystickButton m_leftButton2 = new JoystickButton(m_leftJoy, 2);
  private JoystickButton m_leftButton3 = new JoystickButton(m_leftJoy, 3);
  private JoystickButton m_leftButton4 = new JoystickButton(m_leftJoy, 4);
  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    SmartDashboard.putData("Shooter", m_shooter);
    SmartDashboard.putData("Pneumatic", m_pneumatic);
    SmartDashboard.putData("Turret", m_turret);
    SmartDashboard.putData("Hood", m_hood);
    SmartDashboard.putData("Drive", m_drive);

  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    logger.info("configureButtonBindings");
    // m_leftButton1.whenHeld(new InstantCommand(m_pneumatic::changeBaseOutput, m_pneumatic));
    // m_leftButton2.whenHeld(new InstantCommand(m_pneumatic::changeClimberOutput, m_pneumatic));
    // m_leftButton1.whenHeld(new ShooterSetSpeed(m_shooter, m_turret, -2000));// minus sign?
    // m_leftButton2.whenHeld(new InstantCommand(m_turret::feederWork, m_turret));
    // m_leftButton1.whenHeld(new ArmUpCommand(m_pneumatic));
    // TODO: why cannot the shooter stop when I release the joystick

    m_leftButton1.whenPressed(new ChangeClimberCommand(m_pneumatic));
  }
  
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }

  public void teleopInit() {
    configureButtonBindings();
    logger.info("teleopInit - start compressor");
    m_pneumatic.CompressorBegin();
    // final Command tankDriveCommand = new RunCommand(() -> m_drive.TankDrive(m_leftJoy.getY(), m_rightJoy.getY()), m_drive);
    final Command arcadeDriveCommand = new RunCommand(() -> m_drive.ArcadeDrive(m_leftJoy.getY(), m_leftJoy.getX()), m_drive);
    m_drive.setDefaultCommand(arcadeDriveCommand);
  }

  public void disableInit() {
    logger.info("disable init - ending compressor");
    m_pneumatic.CompressorEnd();
  }
}
