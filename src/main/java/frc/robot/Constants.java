/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

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
        public static final int MOTOR_HOOD = 0;
    }

    public final class Deadband {
        public static final double JOYSTICK_LIMIT = 0.3;
    }

    public final class Ports {
        public static final int kPCMPort = 0;
    }

    public final class PneumaticConstants {
        public static final int kBaseF = 1;
        public static final int kBaseR = 2;
        public static final int kClimberF = 3;
        public static final int kClimberR = 4;
    }

    public final class ShooterConstants {
        
    }

    public final class Converters {
        
    }
}
