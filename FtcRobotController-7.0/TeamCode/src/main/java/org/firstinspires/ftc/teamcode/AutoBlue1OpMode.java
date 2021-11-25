package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
/*
    Red1 and Blue 1 strategy.--- 26 points
    Robot will deliver the freight at the top level - 6 points
    Robot will spin the duck table 1 time -10
    Park in warehouse- 10 points
 */
@Autonomous(name="AutoBlue1OpMode", group="AutoOpModes")
public class AutoBlue1OpMode extends LinearOpMode {
    DejaVuBot robot = new DejaVuBot();

    public static final double     FORWARD_SPEED = 0.6;
    public static final double     TURN_SPEED    = 0.5;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap,true);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("AutoBlue1OpMode: Status", "Ready to run from Blue 1 position ");
        telemetry.update();


        // Wait for the driver to press start
        waitForStart();

        // Drive forward with the piece
        robot.setPowerToAllMotors(FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2.0)) {
            telemetry.addData("AutoBlue1OpMode: Driving forward ", " %2.5f", runtime.seconds());
            telemetry.update();
        }

        /* Step 2:  Turn to carousal
        robot.turnRobot(TURN_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.5)) {
            telemetry.addData("Turned to carousal", " %2.5f", runtime.seconds());
            telemetry.update();
        }

        //Stop the robot so arm can deliver the freight
        robot.turnOffPowerToMotors();

        //Deliver the freight on the top level. Raise arm to level 3
        robot.arm.moveArmToLevel(3);
        //Drop the bucket
        robot.arm.releaseBucket();
        sleep(50);
        robot.arm.moveArmToLevel(1);

        // Turn in opposite direction to go to duck spinner
        robot.turnRobot(-TURN_SPEED);
        robot.driveRobot(FORWARD_SPEED, false);

        //Align the duck spinner to the turn table
        //Spin the table for 10 seconds.
        robot.spinAntiClockWise(10);
        //Turn to storage and park in storage.
        robot.turnRobot(TURN_SPEED);
        robot.driveRobot(FORWARD_SPEED, false);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2.0)) {
            telemetry.addData("Parked ", " %2.5f", runtime.seconds());
            telemetry.update();
        }

        // stop the robot
        robot.turnOffPowerToMotors();
        */

        telemetry.addData("AutoBlue1OpMode", "Complete");
        telemetry.update();
    }
}
