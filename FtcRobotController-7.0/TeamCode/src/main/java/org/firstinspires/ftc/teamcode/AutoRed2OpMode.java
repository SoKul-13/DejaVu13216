package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.*;

/**
 * This class represents the autonomous run from Red1 position
 */
@Autonomous(name="AutoRed2OpMode", group="AutoOpModes")
public class AutoRed2OpMode extends BaseAutoOpMode {
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

        driveForwardByInches(46, robot, DejaVuBot.TPS);
        turnToPID(90,robot);
        telemetry.addData(name, "Turned to hub  ");
        telemetry.update();
        driveForwardByInches(-6, robot, DejaVuBot.TPS);

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
        driveForwardByInches(6, robot, DejaVuBot.TPS);
        strafeDirection(robot, false, 920);
        /*
        turnToPID(-90,robot);
        driveForwardByInches(-42, robot, DejaVuBot.TPS);
        telemetry.addData(name, " Driving to wall ");
        telemetry.update();

        turnToPID(90, robot);
*/
        robot.arm.closeBucketPos();
        robot.intake();
        driveForwardByInches(45, robot, DejaVuBot.TPS);
        driveForwardByInches(4, robot, DejaVuBot.TPS);
        sleep(1500);
        driveForwardByInches(-49, robot, DejaVuBot.TPS);
        strafeDirection(robot, true, 850);

        //driveForwardByInches(44, robot, DejaVuBot.TPS);
        //turnToPID(90,robot);
        telemetry.addData(name, "Turned to hub  ");
        telemetry.update();
        driveForwardByInches(-6, robot, DejaVuBot.TPS);
        robot.stopIntake();
        robot.arm.moveArmToLevel(2);
        sleep(500);
        robot.arm.openBucketPos();
        sleep(1000);
        robot.arm.closeBucketPos();
        sleep(500);
        robot.arm.moveArmToLevel(0);

        telemetry.addData(name, " Dropped the freight ");
        telemetry.update();

        driveForwardByInches(6, robot, DejaVuBot.TPS);
        strafeDirection(robot, false, 900);
        //turnToPID(-90,robot);
        //driveForwardByInches(-42, robot, DejaVuBot.TPS);
        telemetry.addData(name, " Driving to wall ");
        telemetry.update();

        //turnToPID(90, robot);
        robot.arm.closeBucketPos();
        driveForwardByInches(52, robot, DejaVuBot.TPS);

        // Send telemetry message to signify robot waiting;
        telemetry.addData(name, "Parked in warehouse");
        telemetry.update();


    }

}