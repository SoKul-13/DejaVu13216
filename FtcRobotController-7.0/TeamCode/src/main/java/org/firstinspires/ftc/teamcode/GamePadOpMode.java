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

    static final double     FORWARD_SPEED = 0.6;
    static final double     TURN_SPEED    = 0.5;
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready for gamepad run");
        telemetry.update();

        robot.leftFront.setDirection(DcMotor.Direction.FORWARD);
        robot.rightFront.setDirection(DcMotor.Direction.REVERSE);

        robot.leftBack.setDirection(DcMotor.Direction.FORWARD);
        robot.rightBack.setDirection(DcMotor.Direction.REVERSE);

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

            if(gamepad1.left_bumper == true){
                robot.leftFront.setPower(-0.5);
                robot.rightFront.setPower(0.5);
                robot.rightBack.setPower(0.5);
                robot.leftBack.setPower(-0.5);
            } else if(gamepad1.right_bumper == true){
                robot.leftFront.setPower(0.5);
                robot.rightFront.setPower(-0.5);
                robot.rightBack.setPower(-0.5);
                robot.leftBack.setPower(0.5);
            } else {
                robot.leftFront.setPower(leftPower);
                robot.rightFront.setPower(rightPower);
                robot.rightBack.setPower(leftPower);
                robot.leftBack.setPower(rightPower);
            };

            double DuckPower = gamepad2.right_stick_x;
            DuckPower = Range.clip(DuckPower, -1, 1);
            robot.Duck.setPower(DuckPower);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Completed");
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.addData("GamePadOpMode", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 4:  Stop and close the claw.
        robot.leftFront.setPower(0);
        robot.rightFront.setPower(0);
        robot.rightBack.setPower(0);
        robot.leftBack.setPower(0);

        telemetry.addData("GamePadOpMode", "Complete");
        telemetry.update();
    }
}
