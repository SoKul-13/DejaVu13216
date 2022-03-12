package org.firstinspires.ftc.teamcode.vision;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.DejaVuArm;
import org.firstinspires.ftc.teamcode.DejaVuBot;

/**
 * This class represents the autonomous run from Red1 position
 */
@Autonomous(name="AutoRed2VisionOpMode", group="AutoOpModes")
public class AutoRed2VisionOpMode extends BaseAutoVisionOpMode {
    private String TAG = "AutoRed2VisionOpMode";
    private ElapsedTime runtime = new ElapsedTime();
    private Thread levelFinderThread;
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, true);
        robot.arm.closeBucketPos();
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
            tfod.setZoom(1.8, 16.0/9.0);
        }
        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();

        waitForStart();
        runtime.reset();

        Log.d(TAG, "starting thread 1");
        levelFinderThread = new Thread(levelFinderRunnable);
        levelFinderThread.start();

        // now wait for the threads to finish before returning from this method
        Log.d(TAG, "waiting for threads to finish...");
        levelFinderThread.join();
        Log.d(TAG, "thread joins complete");
        //46
        driveForwardByInches(46, robot, DejaVuBot.TPS);
        turnToPID(85,robot);
        telemetry.addData(TAG, "Turned to hub  ");
        telemetry.update();
        driveForwardByInches(-6, robot, DejaVuBot.TPS);

        robot.arm.moveArmToLevel(currentLevel);
        sleep(500);
        robot.arm.openBucketPos();
        sleep(1000);
        robot.arm.closeBucketPos();
        sleep(1000);
        robot.arm.moveArmToLevel(0);
        telemetry.addData(TAG, " Dropped the freight ");
        telemetry.update();

        //Move the robot to warehouse for second point
        driveForwardByInches(1, robot, DejaVuBot.TPS);
        turnToPID(8, robot);

        strafeDirection(robot, false, 1224);

        robot.arm.closeBucketPos();
        //robot.intake();
        driveForwardByInches(45, robot, DejaVuBot.TPS);
        sleep(500);
        strafeDirection(robot, true, 600);

        driveForwardByInches(-10, robot, DejaVuBot.TPS);

        telemetry.addData(TAG, "Parked in warehouse");
        telemetry.update();
    }

    private Runnable levelFinderRunnable = new Runnable() {
        @Override
        public void run() {
            //driveForwardByInches(4, robot, DejaVuBot.TPS);
            //Find the level in 10 attempts. If not detected set level to 3.
            if (opModeIsActive() && tfod != null) {
                telemetry.addData(">", "Detecting level using vision");
                telemetry.update();
                int count = 0;
                int lastResult = -1;
                while (opModeIsActive() && count < 3) {
                    lastResult = currentLevel;
                    currentLevel = findLevel();
                    telemetry.addData("Last Level", lastResult);
                    telemetry.addData("Current Level", currentLevel);
                    Log.i(TAG, "Called find level for "
                            + count+ " detected current level = "
                            + currentLevel + " (last level=" + lastResult + ")");
                    if(currentLevel != -1){
                        Log.i(TAG, " Setting Final level ="+ currentLevel);
                    }
                    // go back to last result if current result is not good
                    if(lastResult != -1){
                        Log.i(TAG, " Setting Final level ="+ lastResult);
                        currentLevel = lastResult;
                    }
                    count++;
                    Log.i(TAG, " count ="+ count);
                    telemetry.addData("Retry count:", count);
                }

                if(currentLevel == DejaVuArm.BOTTOM_LEVEL) {
                    telemetry.addLine(" Current level discovered is Bottom level");
                } else if(currentLevel == DejaVuArm.TOP_LEVEL) {
                    telemetry.addLine(" Current level discovered is Top level");
                } else if(currentLevel == DejaVuArm.MID_LEVEL) {
                    telemetry.addLine(" Current level discovered is Mid level");
                } else {
                    telemetry.addLine(" Current level discovered is UNKNOWN - defaulting to TOP");
                    currentLevel = DejaVuArm.TOP_LEVEL;
                }
                telemetry.update();
            } else{
                telemetry.addData(">", "Could not init vision code - defaulting to TOP");
                telemetry.update();
                currentLevel = DejaVuArm.TOP_LEVEL;
            }
            Log.d(TAG, "Thread 1 finishing up");
        }
    };
}