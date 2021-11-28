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

        turnPID(90);
        //arm code here
        sleep(5000);

    }
}