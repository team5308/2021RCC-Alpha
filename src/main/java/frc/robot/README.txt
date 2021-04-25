================================================
+----------Base / Pneumatic Test Logs----------+

+-----DRIVE-----+

++--2021.03.06--++
Added the base PID to the Drive Subsystem;
Parameters not tested;
//TODO:
1. test the Parameters for the PID
2. get a feedforward value if possible
3. write code fot Autonomous:
    make use of WPILib trajectories;
    make use of pathweaver;
    make use of PigeonIMU; (nav-X)


++--2021.04.16--++
Added the pneumatic subsystem to test




==============================================
+----------Shooter / Hood Test Logs----------+

+-----SHOOTER-----+

++--2021.02.09--++
For the shooter pid,
where there is no I&D value 
Table of values:
+--- Shooter RPM --+-- Speed Limit ---+
|       2500       |       952        |
|       3000       |       1145       |
|       3500       |       1342       |
|       4000       |       1534       |
|       4500       |       1724       |
+------------------+------------------+
Tested Integral zone limit
IZL = 0.6134 SS + 13.7
to avoid errors, the following may be more reasonable:
(IZL - Integral Zone Limit; SS - Set Speed)
IZL = 0.62 SS + 14
*** to test whether this is correct,
*** shift between different speed quickly
*** and see if the I factor is working properly

# the P value is able to speed the wheels up to 38.67% target speed

Changes made: 
1. Integral limit function added, ILZ now changes with the SS - but not tested!
2. Added a Xbox Xontroller, speed now controlled by the Xbox
3. Two speed level in control, 100 and 3000
4. prepared for the current test, a method for setting current added



++--2021.02.10--++
Xbox's right bumper is broken, so Xbox is discarded;
instead, we will use joystick to continue the testing;

test completed, all PID values are fixed;
# P: 0.03 under 800 rpm / 0.8 above 800 rpm;
# I: 0.000125;
# D: 0;
# IntegralZone: 0 ~ (0.62 * targetSpeed + 14) * 2048 / 600
Under three-ball dartle, the trajectory varied only in the limit range;
Success!

//NEXT TODO:
1. write a logic to:
    make the wheel turn at a low speed when there are no balls in hopper;
    control the above condition with Voltage mode (possible);
    if the method above does not work, use velocity mode instead;
2. add the HOOD subsystem to this test doc
3. add this doc to the assembly and test it with the networktable parameters
4. test the auto-distance-meassuring function after completing the steps above