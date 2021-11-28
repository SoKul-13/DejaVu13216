package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="GamePadOpMode", group="Linear OpMode")
public class GamePadOpMode extends LinearOpMode {
    DejaVuBot robot = new DejaVuBot();
    private ElapsedTime runtime = new ElapsedTime();
    private boolean isBlue = true;
    static final double     FORWARD_SPEED = 0.6;
    static final double     TURN_SPEED    = 0.5;
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap,false);

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
            telemetry.addData("drive set to:", ""+drive);
            telemetry.addData("turn set to:", ""+turn);
            drive = -gamepad1.left_stick_y;
            turn  =  gamepad1.right_stick_x;
            leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
            rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;

            if(gamepad1.left_bumper) {
                robot.turnRobot(-0.5);
            } else if(gamepad1.right_bumper){
                robot.turnRobot(0.5);
            } else {
                robot.leftFrontMotor.setPower(leftPower);
                robot.rightFrontMotor.setPower(rightPower);
                robot.rightBackMotor.setPower(leftPower);
                robot.leftBackMotor.setPower(rightPower);
            }
            while(gamepad2.left_bumper) {
                if (isBlue) {
                    robot.spinAntiClockWise();
                } else {
                    robot.spinClockWise();
                }
            }
            while (gamepad2.right_bumper) {
                robot.intake();
            }
            if(gamepad2.y) {
                robot.arm.moveArmToLevel(3);
            }
            if (gamepad2.x) {
                robot.arm.moveArmToLevel(2);
            }
            if (gamepad2.a) {
                robot.arm.moveArmToLevel(1);
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
