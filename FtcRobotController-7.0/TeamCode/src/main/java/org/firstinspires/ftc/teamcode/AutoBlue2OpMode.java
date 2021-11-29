package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="AutoBlue2OpMode", group="Linear OpMode")

public class AutoBlue2OpMode extends BaseAutoOpMode {
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
        telemetry.addData("AutoBlue2OpMode", "Ran forward ");
        telemetry.update();

        robot.gyroInit();
        robot.chassisEncoderOff();
        // Send telemetry message to signify robot waiting;
        telemetry.addData("AutoBlue2OpMode", "Ready to turn ");
        telemetry.update();

        turnPID(-90);

        robot.chassisEncoderOn();
        driveForwardByInches(8,robot);
        //arm code here
        sleep(1000);
        robot.gyroInit();
        robot.chassisEncoderOff();
        turnPID(-90);
        robot.chassisEncoderOn();
        driveForwardByInches(40, robot);
        robot.gyroInit();
        robot.chassisEncoderOff();
        turnPID(-90);
        robot.chassisEncoderOn();
        driveForwardByInches(49, robot);
        //intake code here
        sleep(1000);
        /*----------------------------------loop start if incorporated-----------------------------------------*/
        robot.chassisEncoderOn();
        driveForwardByInches(-49, robot);
        robot.gyroInit();
        robot.chassisEncoderOff();
        turnPID(90);
        robot.chassisEncoderOn();
        driveForwardByInches(-40, robot);
        robot.gyroInit();
        robot.chassisEncoderOff();
        turnPID(90);
        //arm code here
        sleep(1000);
        robot.gyroInit();
        robot.chassisEncoderOff();
        turnPID(-90);
        robot.chassisEncoderOn();
        driveForwardByInches(40, robot);
        robot.gyroInit();
        robot.chassisEncoderOff();
        turnPID(-90);
        robot.chassisEncoderOn();
        driveForwardByInches(49, robot);
        //intake code here
        sleep(1000);
        /*----------------------------------loop end if incorporated-----------------------------------------*/
    }
}