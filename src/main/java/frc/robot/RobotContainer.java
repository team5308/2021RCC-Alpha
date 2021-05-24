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
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.PneuStatus;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.utils.TriggerToBoolean;


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
  private final Intake m_intake = new Intake();
  private final Hopper m_hopper = new Hopper();
  private final Vision m_vision = new Vision();
  private final Feeder m_feeder = new Feeder();

  private Joystick m_leftJoy = new Joystick(0);
  private Joystick m_rightJoy = new Joystick(1);
  private XboxController m_xbox = new XboxController(2);

    private JoystickButton m_back = new JoystickButton(m_xbox, Button.kBack.value);
  private JoystickButton m_start = new JoystickButton(m_xbox, Button.kStart.value);
  private JoystickButton m_A = new JoystickButton(m_xbox, Button.kA.value);
  private JoystickButton m_B = new JoystickButton(m_xbox, Button.kB.value);
  private JoystickButton m_X = new JoystickButton(m_xbox, Button.kX.value);
  private JoystickButton m_Y = new JoystickButton(m_xbox, Button.kY.value);
  private JoystickButton m_leftXboxStick = new JoystickButton(m_xbox, Button.kStickLeft.value);
  private JoystickButton m_rightXboxStick = new JoystickButton(m_xbox,Button.kStickRight.value);
  private JoystickButton m_BumperLeft = new JoystickButton(m_xbox, Button.kBumperLeft.value);
  private JoystickButton m_BumperRight = new JoystickButton(m_xbox, Button.kBumperRight.value);
  private TriggerToBoolean m_leftXboxTrigger = new TriggerToBoolean(m_xbox, Axis.kLeftTrigger.value, 0.7);
  private TriggerToBoolean m_rightXboxTrigger = new TriggerToBoolean(m_xbox, Axis.kRightTrigger.value, 0.7);

  private JoystickButton m_leftButton1 = new JoystickButton(m_leftJoy, 1);
  private JoystickButton m_leftButton2 = new JoystickButton(m_leftJoy, 2);
  private JoystickButton m_leftButton3 = new JoystickButton(m_leftJoy, 3);
  private JoystickButton m_leftButton4 = new JoystickButton(m_leftJoy, 4);
  private JoystickButton m_leftButton5 = new JoystickButton(m_leftJoy, 5);
  private JoystickButton m_leftButton6 = new JoystickButton(m_leftJoy, 6);
  private JoystickButton m_leftButton7 = new JoystickButton(m_leftJoy, 7);
  private JoystickButton m_leftButton8 = new JoystickButton(m_leftJoy, 8);
  private JoystickButton m_leftButton9 = new JoystickButton(m_leftJoy, 9);
  private JoystickButton m_leftButton10 = new JoystickButton(m_leftJoy, 10);
  private JoystickButton m_leftButton11 = new JoystickButton(m_leftJoy, 11);
  private JoystickButton m_leftButton12 = new JoystickButton(m_leftJoy, 12);

  private JoystickButton m_rightButton1 = new JoystickButton(m_rightJoy,1);
  private JoystickButton m_rightButton2 = new JoystickButton(m_rightJoy,2);
  private JoystickButton m_rightButton3 = new JoystickButton(m_rightJoy,3);
  private JoystickButton m_rightButton4 = new JoystickButton(m_rightJoy,4);

  private final ChangeBase m_changeBaseCommand = new ChangeBase(m_pneumatic);
  private final ChangeIntake m_ChangeIntakeCommand = new ChangeIntake(m_pneumatic);
  private final ChangeClimber m_ChangeClimberCommand = new ChangeClimber(m_pneumatic);
  private final ChangeLock m_ChangeLockCommand = new ChangeLock(m_pneumatic);
  
  private final FeederWorkCommand m_feederWork = new FeederWorkCommand(m_feeder, m_shooter);
  private final TurretAimCommand autoAimdCommand = new TurretAimCommand(m_turret, m_vision);

  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    SmartDashboard.putData("Shooter", m_shooter);
    SmartDashboard.putData("Pneumatic", m_pneumatic);
    SmartDashboard.putData("Turret", m_turret);
    SmartDashboard.putData("Hood", m_hood);
    SmartDashboard.putData("Drive", m_drive);
    SmartDashboard.putData("Intake", m_intake);
    SmartDashboard.putData("Hopper", m_hopper);
 
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    logger.info("configureButtonBindings");

    // m_leftButton1.whenHeld(new TurretTurn(m_turret, 30));

    // m_leftButton2.whenPressed(new InstantCommand(m_hopper::hopperStart, m_hopper)).whenReleased(new InstantCommand(m_hopper::hopperStop,m_hopper));

    // m_leftButton3.whenHeld(m_ChangeIntakeCommand);
    // m_leftButton4.whenHeld(new InstantCommand(m_intake::intakeStart, m_intake)).whenReleased(new InstantCommand(m_intake::intakeStop, m_intake));

    // m_leftButton1.whenHeld(autoAimdCommand);
    // // m_leftButton1.whenHeld(m_feederWork);
    
    // m_leftButton5.whenPressed(m_changeBaseCommand);
    // m_leftButton6.whenPressed(m_ChangeClimberCommand);
    // m_leftButton7.whenPressed(m_ChangeLockCommand);
    // m_leftButton2.whenPressed(new InstantCommand(m_pneumatic::changeClimberOutput, m_pneumatic));
    // m_leftButton3.whenPressed(new InstantCommand(m_pneumatic::changeLockOutput,m_pneumatic));
    
    // m_leftButton7.whenPressed(m_changeBaseCommand);

    // m_leftButton6.whenPressed(new InstantCommand(m_pneumatic::changeClimberOutput, m_pneumatic));
    
    // m_leftButton3.whenPressed(new InstantCommand(m_pneumatic::changeIntakeOutput,m_pneumatic));    
    //TODO Minus sign delete or not?
    // m_leftButton1.whenHeld(new ShooterSetSpeed(m_shooter, m_turret, -3500));// minus sign?
    // m_leftButton2.whenHeld(new InstantCommand(m_turret::turnLeft, m_turret)).whenReleased(new InstantCommand(m_turret::stopMotor, m_turret));
    // m_leftButton3.whenHeld(new InstantCommand(m_turret::turnRight, m_turret)).whenReleased(new InstantCommand(m_turret::stopMotor, m_turret));
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
    // configureButtonBindings();
    logger.info("teleopInit - start compressor");
    m_vision.setLightOff();
    m_pneumatic.CompressorBegin();
    //m_pneumatic.CompressorEnd();
    m_pneumatic.setBaseOutput(PneuStatus.kBaseDrive);
    // final Command tankDriveCommand = new RunCommand(() -> m_drive.TankDrive(m_leftJoy.getY(), m_rightJoy.getY()), m_drive);
    final Command arcadeDriveCommand = new RunCommand(() -> m_drive.ArcadeDrive(m_leftJoy.getY(), -m_leftJoy.getX()), m_drive);
    m_drive.setDefaultCommand(arcadeDriveCommand);
  }

  public void disableInit() {
    logger.info("disable init - ending compressor");
    m_pneumatic.CompressorEnd();
  }
}
