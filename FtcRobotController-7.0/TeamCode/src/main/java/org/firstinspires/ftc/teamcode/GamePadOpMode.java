package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="GamePadOpMode", group="Linear OpMode")
public class GamePadOpMode extends LinearOpMode {
    DejaVuBot robot = new DejaVuBot();
    private ElapsedTime runtime = new ElapsedTime();
    //look below at isBlue variable every gamepad run
    private boolean isBlue = true;
    static final double     FORWARD_SPEED = 0.6;
    static final double     TURN_SPEED    = 0.5;
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap,false);
        robot.chassisEncoderOff();
        robot.arm.closeBucketPos();
        robot.arm.moveArmToLevel(0);
        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready for gamepad run");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        runtime.reset();
        double leftPower, rightPower;
        double drive = -gamepad1.left_stick_y;
        double turn  =  gamepad1.right_stick_x;

        while(opModeIsActive()){
            telemetry.addData("armMotor encoder value", robot.arm.armMotor.getCurrentPosition());
            telemetry.addData("servo Position", robot.arm.bucketServo.getPosition());
            telemetry.addData("drive set to:", ""+drive);
            telemetry.addData("turn set to:", ""+turn);
            telemetry.update();
            drive = -gamepad1.left_stick_y;
            turn  =  gamepad1.right_stick_x;
            leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
            rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;
            if (gamepad1.left_trigger > 0.5) {
                leftPower = leftPower/4;
                rightPower = rightPower/4;
            }
            if(gamepad1.left_bumper) {
                robot.turnRobot(0.5);
            } else if(gamepad1.right_bumper){
                robot.turnRobot(-0.5);
            } else {
                robot.leftFrontMotor.setPower(leftPower);
                robot.rightFrontMotor.setPower(rightPower);
                robot.rightBackMotor.setPower(leftPower);
                robot.leftBackMotor.setPower(rightPower);
            }
            if (gamepad2.left_bumper) {
                if (gamepad2.b) {
                    robot.duckSpinner.setDirection(DcMotorEx.Direction.REVERSE);
                } else {
                    robot.duckSpinner.setDirection(DcMotorEx.Direction.FORWARD);
                }
                robot.spinDuck();

            }
            if (gamepad2.left_trigger > 0.5) {
                robot.stopSpinner();
            }

            if (gamepad2.right_bumper) {
                if (gamepad2.b) {
                    robot.intakeMotor.setDirection(DcMotorEx.Direction.REVERSE);
                } else {
                    robot.intakeMotor.setDirection(DcMotorEx.Direction.FORWARD);
                }
                robot.intake();
            }
            if (gamepad2.right_trigger > 0.5) {
                robot.stopIntake();
            }

            if(gamepad2.y) {
                robot.arm.moveArmToLevel(2);
            }
            if (gamepad2.x) {
                robot.arm.moveArmToLevel(1);
            }
            if (gamepad2.a) {
                robot.arm.moveArmToLevel(0);
            }
            if (gamepad2.dpad_down) {
                robot.arm.bucketServo.setPosition(0.113);
            }
            if (gamepad2.dpad_up) {
                robot.arm.bucketServo.setPosition(0.887);
            }

            //need to figure out buttons for bucketServo (3), and initial position of arm
            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Completed");
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.addData("GamePadOpMode", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 4:  Stop and close the claw.
        robot.stopRobot();

        telemetry.addData("GamePadOpMode", "Complete");
        telemetry.update();
    }
}
