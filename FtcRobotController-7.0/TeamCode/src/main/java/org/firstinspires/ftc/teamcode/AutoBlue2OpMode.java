package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.*;

/**
 * This class represents the autonomous run from Red1 position
 */
@Autonomous(name="AutoBlue2OpMode", group="AutoOpModes")
public class AutoBlue2OpMode extends BaseAutoOpMode {
    private String name = "AutoBlue2OpMode";
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

        driveForwardByInches(44, robot, DejaVuBot.TPS);
        turnToPID(-90,robot);
        telemetry.addData(name, "Turned to hub  ");
        telemetry.update();
        driveForwardByInches(-2, robot, DejaVuBot.TPS);
        //originally 6 inches

        robot.arm.moveArmToLevel(2);
        sleep(500);
        robot.arm.openBucketPos();
        sleep(1000);
        robot.arm.closeBucketPos();
        sleep(500);
        robot.arm.moveArmToLevel(0);
        telemetry.addData(name, " Dropped the freight ");
        telemetry.update();

        //Move the robot to warehouse for second point
        driveForwardByInches(2, robot, DejaVuBot.TPS);
        strafeDirection(robot, true, 900);

        driveForwardByInches(36, robot, DejaVuBot.TPS);
        strafeDirection(robot, false, 500);

        telemetry.addData(name, "Parked in warehouse");
        telemetry.update();

    }

}