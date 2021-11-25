package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="AutonOpMode", group="Linear OpMode")
public class AutonOpMode extends LinearOpMode {
    DejaVuBot robot = new DejaVuBot();
    private ElapsedTime runtime = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, false);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready for gamepad run");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        while(opModeIsActive()){
            double leftPower, rightPower;
            double drive = -gamepad1.left_stick_y;
            double turn  =  gamepad1.right_stick_x;

            telemetry.addData("drive set to:", ""+drive);
            telemetry.addData("turn set to:", ""+turn);

            leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
            rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;

            if(gamepad1.left_bumper){
                robot.turnRobot(-0.5);
            } else if(gamepad1.right_bumper){
                robot.turnRobot(0.5);
            } else {
                robot.leftFrontMotor.setPower(leftPower);
                robot.rightFrontMotor.setPower(rightPower);
                robot.rightBackMotor.setPower(leftPower);
                robot.leftBackMotor.setPower(rightPower);
            }
            if (robot.duckSpinner != null) {
                //Need to add if statement for changing directions
                robot.spinClockWise();
                double duckPower = gamepad2.right_stick_x;
                robot.duckSpinner.setPower(duckPower);
            }

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Completed");
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }

        telemetry.addData("GamePadOpMode", "Complete");
        telemetry.update();
    }
}
