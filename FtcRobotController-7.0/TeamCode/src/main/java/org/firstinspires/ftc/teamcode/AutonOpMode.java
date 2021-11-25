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
    static final double WHEEL_CIRCUMFERENCE_MM = 96 * Math.PI;
    static final double COUNTS_PER_MOTOR_REV = 28.0;
    static final double DRIVE_GEAR_REDUCTION = 16.25;

    static final double COUNTS_PER_WHEEL_REV = COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION;
    static final double COUNT_PER_MM = COUNTS_PER_WHEEL_REV/WHEEL_CIRCUMFERENCE_MM;
    static final double COUNT_PER_FT = COUNT_PER_MM * 304.8;

    //max rpm for our motors are 338, here we're using 175 rpm
    double TPS = (double) ((175/60) * COUNTS_PER_WHEEL_REV);

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, true);
        robot.chassisEncoderOn();
        int targetInput = (int) (2 * (304.8 * COUNT_PER_MM));
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

        while(opModeIsActive()){
            telemetry.addData("drive set to:", "");
            telemetry.addData("turn set to:", "");
            robot.chassisEncoderOn();

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Completed");
            telemetry.addData("Motors", "left (%.2f), right (%.2f)");
            telemetry.update();
        }

        telemetry.addData("AutonOpMode", "Complete");
        telemetry.update();
    }
}
