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
    private final double MOTOR_TICK_COUNT = 384.5;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, true);
        robot.chassisEncoderOn();
        int targetInput = (int) (37 * DejaVuBot.COUNT_PER_INCH);
        robot.leftFrontMotor.setTargetPosition(targetInput);
        robot.rightFrontMotor.setTargetPosition(targetInput);
        robot.leftBackMotor.setTargetPosition(targetInput);
        robot.rightBackMotor.setTargetPosition(targetInput);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready for gamepad run");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();


        telemetry.addData("AutonOpMode", "Complete");
        telemetry.update();
    }
}