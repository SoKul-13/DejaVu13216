package org.firstinspires.ftc.teamcode.vision;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.BaseAutoOpMode;
import org.firstinspires.ftc.teamcode.DejaVuArm;
import org.firstinspires.ftc.teamcode.DejaVuBot;

/**
 * This class represents the autonomous run from Red1 position
 */
@Autonomous(name="AutoRed2VisionOpMode", group="AutoOpModes")
public class AutoRed2VisionOpMode extends BaseAutoVisionOpMode {
    private String TAG = "AutoRed2VisionOpMode";
    private ElapsedTime runtime = new ElapsedTime();
    DejaVuBot robot = new DejaVuBot();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap, true);
        currentLevel = -1;
        redFlag = true;
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();
        initTfod();

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();
            tfod.setZoom(1.0, 16.0/9.0);
        }
        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();

        waitForStart();
        driveForwardByInches(4, robot, DejaVuBot.TPS);
        //Find the level in 10 attempts. If not detected set level to 3.
        if (opModeIsActive() && tfod != null) {
            telemetry.addData(">", "Detecting level using vision");
            telemetry.update();
            int count = 0;
            while (opModeIsActive() && count < 10) {
                currentLevel = findLevel();
                Log.i(TAG, "Called find level for "+ count+ " detected current level ="+ currentLevel);
                if(currentLevel != -1){
                    Log.i(TAG, " Setting Final level ="+ currentLevel);
                    break;
                }
                count++;
                Log.i(TAG, " count ="+ count);
                sleep(300);
            }

            telemetry.addData(" Current level discovered is =", currentLevel);
            telemetry.update();
        }else{
            telemetry.addData(">", "Could not init vision code.");
            telemetry.update();
        }
        //46
        driveForwardByInches(38, robot, DejaVuBot.TPS);
        turnToPID(90,robot);
        telemetry.addData("name", "Turned to hub  ");
        telemetry.update();
        driveForwardByInches(-2, robot, DejaVuBot.TPS);

        robot.arm.moveArmToLevel(currentLevel);
        sleep(500);
        //robot.arm.openBucketPos();
        sleep(1000);
        //robot.arm.closeBucketPos();
        sleep(500);
        robot.arm.moveArmToLevel(0);
        telemetry.addData("name", " Dropped the freight ");
        telemetry.update();

        //Move the robot to warehouse for second point
        driveForwardByInches(2, robot, DejaVuBot.TPS);
        strafeDirection(robot, false, 920);

        robot.arm.closeBucketPos();
        //robot.intake();
        driveForwardByInches(45, robot, DejaVuBot.TPS);
        strafeDirection(robot, true, 500);

        telemetry.addData("name", "Parked in warehouse");
        telemetry.update();
        /*
        //Move to 135 degree and drive forward.
        turnToPID(135,robot);
        Log.i(TAG, " Turned to hub ");
        driveForwardByInches(-24, robot, DejaVuBot.TPS);
        Log.i(TAG, " drove forward =");
        //Set to top level if vision did not detect the team element
        telemetry.addData(TAG, " Vision detected current level = "+ currentLevel);
        //TODO : WE dont have level 0 bucket drop so set level to top
        if(currentLevel < DejaVuArm.MID_LEVEL ) {
            currentLevel = DejaVuArm.TOP_LEVEL;
            Log.i(TAG, " adjusted the level to top level "+ currentLevel);
        }
        telemetry.addData(TAG, " Updated current level set to  "+ currentLevel);
        robot.arm.moveArmToLevel(currentLevel);
        Log.i(TAG, " Moved the arm to level  "+ currentLevel);
        robot.arm.openBucketPos();
        Log.i(TAG, " Bucket open called ");
        robot.arm.closeBucketPos();
        Log.i(TAG, " Bucket closed ");
        robot.arm.moveArmToLevel(0);
        Log.i(TAG, " arm moved down ");
        telemetry.addData(TAG, " Dropped the freight ");
        telemetry.update();

        //Go to wall
        turnToPID(-45,robot);
        Log.i(TAG, " aligning with wall ");
        strafeDirection(robot, false, 200);
        Log.i(TAG, " strafing to wall complete ");
        driveForwardByInches(50, robot, DejaVuBot.TPS);
        Log.i(TAG, " In warehouse to pickup second block ");
        robot.intake();
        sleep(500);
        robot.stopIntake();

        //TODO - add strafe back to drop the second freight
        //strafeDirection(robot, false, 200);
        //strafe45Direction(robot,true,false,50);

        // Step 4:  Stop and close the claw.
        robot.stopRobot();

        telemetry.addData(TAG, "Parked in warehouse");
        telemetry.update();
        */
    }
}