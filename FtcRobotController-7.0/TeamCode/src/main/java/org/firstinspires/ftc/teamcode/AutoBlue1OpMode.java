package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/*
    Red1 and Blue 1 strategy.--- 26 points
    Robot will spin the duck table 1 time -10
    Robot will deliver the freight at the top level - 6 points
    Park in warehouse- 10 points
*/
/*  Action Items In Order Of Priority

    Advanced Paths -> RoadRunner
    Strafe -> Gyro sensor
    Vision -> OpenCV
*/
@Autonomous(name="AutoBlue1OpMode", group="AutoOpModes")
public class AutoBlue1OpMode extends BaseAutoOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, true);
        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready for run");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        driveForwardByInches(44, robot);
        telemetry.addData("AutoBlue1OpMode", "Ran forward ");
        telemetry.update();

        robot.gyroInit();
        robot.chassisEncoderOff();
        // Send telemetry message to signify robot waiting;
        telemetry.addData("AutoBlue1OpMode", "Ready to turn ");
        telemetry.update();

        turnPID(-90);
        robot.chassisEncoderOn();
        //arm code here
        sleep(5000);
        driveForwardByInches(34, robot);
        robot.chassisEncoderOff();
        turnPID(90);
        robot.chassisEncoderOn();
        driveForwardByInches(-18, robot);
        robot.spinClockWise();
        sleep(3000);
        robot.duckSpinner.setPower(0);
        //Turn and park in warehouse
        robot.chassisEncoderOff();
        turnPID(-90);
        robot.chassisEncoderOn();
        driveForwardByInches(-144, robot);
    }
}