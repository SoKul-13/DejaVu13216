package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.*;

/**
 * This class represents the autonomous run from Red1 position
 */
@Autonomous(name="AutoRed2OpMode", group="AutoOpModes")
public class AutoRed1OpMode extends BaseAutoOpMode {
    private String name = "AutoRed2OpMode";
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, true);

        // Send telemetry message to signify robot waiting;
        telemetry.addData(name, " Robot ready for run");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Send telemetry message to signify robot waiting;
        telemetry.addData(name, " Running the opmode ");
        telemetry.update();
        /*
        driveForwardByInches(44, robot);
        robot.gyroInit();
        robot.chassisEncoderOff();
        turnTo(-90);
        turnTo(-90);
        turnTo(-90);
        telemetry.addData(name, "Turned to hub  ");
        telemetry.update();
        sleep(2000);
        //Drop the piece here and reset the arm to initial position
        //robot.arm.openBucketPos();
        //robot.arm.moveArmToLevel(DejaVuArm.level_map.get(0));
        telemetry.addData(name, " Dropped the freight ");
        telemetry.update();

        /*Move the robot to spin the duck
        driveForwardByInches(18, robot);
        robot.chassisEncoderOff();
        turnTo(-90);
        turnTo(-90);
        turnTo(-90);
        driveForwardByInches(-18, robot);*/
        spinForOneDuck(robot);
        telemetry.addData(name, " Duck spinned ");
        telemetry.update();
        /*Turn and park in warehouse
        robot.chassisEncoderOff();
        turnTo(-90);

        driveForwardByInches(-44, robot);*/

        // Send telemetry message to signify robot waiting;
        telemetry.addData(name, "Parked in warehouse");
        telemetry.update();

    }
}