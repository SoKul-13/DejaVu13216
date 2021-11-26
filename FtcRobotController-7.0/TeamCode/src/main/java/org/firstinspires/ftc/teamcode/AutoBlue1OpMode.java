package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
/*
    Red1 and Blue 1 strategy.--- 26 points
    Robot will spin the duck table 1 time -10
    Robot will deliver the freight at the top level - 6 points
    Park in warehouse- 10 points
 */
/*  Problems: Back Right Motor tight, not spaced enough/allowing robot to move straight or turn or strafe
    Action Items In Order Of Priority
    Strafe -> Gyro sensor
    Vision -> OpenCV*/
@Autonomous(name="AutoBlue1OpMode", group="AutoOpModes")
public class AutoBlue1OpMode extends LinearOpMode {
    DejaVuBot robot = new DejaVuBot();
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, true);
        robot.stopRobot();
        int targetInput = (int) (37 * DejaVuBot.COUNT_PER_INCH);
        telemetry.addData("Target Position Set to:", targetInput);
        telemetry.update();
        telemetry.addData("BeforeMotorMode:", robot.leftBackMotor.getMode());
        telemetry.update();
        robot.leftFrontMotor.setTargetPosition(targetInput);
        robot.rightFrontMotor.setTargetPosition(targetInput);
        robot.leftBackMotor.setTargetPosition(targetInput);
        robot.rightBackMotor.setTargetPosition(targetInput);
        robot.chassisEncoderOn();
        telemetry.addData("AfterMotorMode:", robot.leftBackMotor.getMode());
        telemetry.update();
        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready for run");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
        robot.setVelocityToAllMotors(DejaVuBot.TPS);
        while (opModeIsActive() && robot.leftFrontMotor.isBusy()) {
            telemetry.addData("left", robot.leftFrontMotor.getCurrentPosition());
            telemetry.addData("right", robot.rightFrontMotor.getCurrentPosition());
            telemetry.update();
        }
        telemetry.addData("AutonOpMode", "Complete");
        telemetry.update();
    }
}