/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public final class CanId {
        public static final int DRIVE_LEFT_FRONT = 11;
        public static final int DRIVE_LEFT_BACK = 14;
        public static final int DRIVE_RIGHT_FRONT = 13;
        public static final int DRIVE_RIGHT_BACK = 12;

        public static final int MOTOR_SHOOTER_LEFT = 18;
        public static final int MOTOR_SHOOTER_RIGHT = 19;
        public static final int MOTOR_HOOD = 26;

        public static final int MOTOR_HOPPER = 36;

        public static final int MOTOR_INTAKE = 30;

        public static final int MOTOR_TURRET = 31;
        public static final int MOTOR_FEEDER_LEFT = 24;
        public static final int MOTOR_FEEDER_RIGHT = 22;

        public static final int kPDP = 2;
    }

    public final class Deadband {
        public static final double JOYSTICK_LIMIT = 0.3;
    }

    public final class Ports {
        public static final int kPCMPort = 3;
    }

    public final class PneumaticConstants {
        public static final int kBaseF = 0;
        public static final int kBaseR = 1;
        public static final int kClimberF = 2;
        public static final int kClimberR = 3;
        public static final int kLockF = 4;
        public static final int kLockR = 5;
        public static final int kSSIntake = 6;
    }

    public static final class PneuStatus {
        public static final DoubleSolenoid.Value kClimberUp = DoubleSolenoid.Value.kForward;
        public static final DoubleSolenoid.Value kClimberDown = DoubleSolenoid.Value.kReverse;
        
        public static final DoubleSolenoid.Value kBaseClimb = DoubleSolenoid.Value.kReverse;
        public static final DoubleSolenoid.Value kBaseDrive = DoubleSolenoid.Value.kForward;

        public static final DoubleSolenoid.Value kClimberLock = DoubleSolenoid.Value.kForward;
        public static final DoubleSolenoid.Value kClimberRelease = DoubleSolenoid.Value.kReverse;

        public static final Boolean kIntakeLock = false;
        public static final Boolean kIntakeRelease = !kIntakeLock;
    }

    public final class ShooterConstants {
        
    }

    public final class BaseConstants {

    }

    public final class Converters {
        
    }
    
}
