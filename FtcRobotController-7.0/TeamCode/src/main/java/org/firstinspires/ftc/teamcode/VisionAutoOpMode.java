package org.firstinspires.ftc.teamcode;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.BaseAutoOpMode;
import org.firstinspires.ftc.teamcode.DejaVuArm;

import java.util.List;

@Autonomous(name="VisionAutonomousCode", group="Auto")

/** This class represents the autonomous run from Red 2  position with webcam integration
 */
public class VisionAutoOpMode extends BaseAutoOpMode {
    protected static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";
    protected static final String VUFORIA_KEY =
            "AW9JKyj/////AAABmX2UV/5fn04JpsRM9uLXuEYQW29RXmviJEnGvXKmVlhEC3qszm0BbEJjR7kjfCbN3tHX37Pyei+8GICDehSPByjRlHFSf0Vz1NFx3go62FfegYiyB3/vT+7OnT8y2hCNHOlj7RypmGPS10rPpvqJxHJzs1Mz2Tt/HARIeeSiM9eO+nHisES89lFGaiyR1dpjcKLoXteIm6U8vzL/res0hm5tKwuJnWb0Ch8H5u0Vb2k1DnAMAnQwGiPyBn1gSwnQ8yH7Ro9ocO0Z3PCNBTvhh8X7QqICk9Bdg4lHQHxQ0WYTjbIlKUDTHvsQ+6QX7Mn8TzZVOQBo0DsFrrVzQIkrw8TFGRC1qp1RB8JjvgmHmif0";

    protected static final String[] LABELS = {
            "Ball",
            "Cube",
            "Duck",
            "Marker"
    };
    private String TAG = "BaseAutoVisionOpMode";
    //Default level of the top shelf is 3 and current is -1.
    // If vision thread does not setup the level it is set to toplevel
    protected int currentLevel = 0;
    protected boolean redFlag = false;

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    protected VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    protected TFObjectDetector tfod;

    @Override
    public void runOpMode() {
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    protected void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    protected void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 320;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }

    protected Runnable bucketRunnable = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, "Bucket thread starting");

            telemetry.update();

            //end of while loop
            Log.i(TAG, "Vision thread finishing up");
        }
    };

    protected int findLevel(boolean IsRed, boolean IsOp1){
        int level = -1;
        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
        if (updatedRecognitions != null && updatedRecognitions.size() == 2 )  {
            telemetry.addData("# Object Detected", updatedRecognitions.size());
            Log.i(TAG, "DJ# Object Detected" + updatedRecognitions.size());
            Log.i(TAG, "DJ# # Label 1=" + updatedRecognitions.get(0).getLabel());
            Log.i(TAG, "DJ# # Label 2=" + updatedRecognitions.get(1).getLabel());
            telemetry.addData("# Label 1 =", updatedRecognitions.get(0).getLabel());
            telemetry.addData("# Label 2 =", updatedRecognitions.get(1).getLabel());
            /*
            if(updatedRecognitions.get(0).getLabel().equals(LABELS[1]) && updatedRecognitions.get(1).getLabel().equals(LABELS[3])){
                level = DejaVuArm.BOTTOM_LEVEL;
            }else if(updatedRecognitions.get(0).getLabel().equals(LABELS[3]) && updatedRecognitions.get(1).getLabel().equals(LABELS[1])){
                level = DejaVuArm.MID_LEVEL;
            }else if(updatedRecognitions.get(0).getLabel().equals(LABELS[3]) && updatedRecognitions.get(1).getLabel().equals(LABELS[3])){
                level = DejaVuArm.TOP_LEVEL;
            }
             */
            telemetry.addData("Setting currentLevel =", level);
        }else if (updatedRecognitions != null && updatedRecognitions.size() ==1 )  {
            telemetry.addData("# Object Detected", updatedRecognitions.size());
            Log.i(TAG, "DJ# Object Detected" + updatedRecognitions.size());
            Log.i(TAG, "DJ# # Label =" + updatedRecognitions.get(0).getLabel());
            Log.i(TAG, "DJ# # left =" + updatedRecognitions.get(0).getLeft());

            telemetry.addData("# Label  =", updatedRecognitions.get(0).getLabel());
            double left = updatedRecognitions.get(0).getLeft();
            //Check if cube found in either location.
            /*
            if(updatedRecognitions.get(0).getLabel().equals(LABELS[1])){ //Cube found and it is in the right side of picture it is on mid level
                if(redFlag) {
                    if (left > 300)
                        level = DejaVuArm.MID_LEVEL;
                    else
                        level = DejaVuArm.TOP_LEVEL;
                }else{
                    if (left < 200)
                        level = DejaVuArm.MID_LEVEL;
                    else
                        level = DejaVuArm.TOP_LEVEL;
                }
                telemetry.addData("Setting currentLevel =", level);
            }

             */
        }else{
            //Print what we see if size is not 2 and something is detected
            if (updatedRecognitions != null) {
                telemetry.addData("# Object Detected", updatedRecognitions.size());
                Log.i(TAG, "DV# Object Detected" + updatedRecognitions.size());
                // step through the list of recognitions and display boundary info.
                int i = 0;
                for (Recognition recognition : updatedRecognitions) {
                    Log.i(TAG, " Object label " + recognition.getLabel());
                    Log.i(TAG, " left,top " + recognition.getLeft() + "," + recognition.getTop());
                    Log.i(TAG, " right,bottom " + recognition.getRight() + "," + recognition.getBottom());

                    telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                    telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                            recognition.getLeft(), recognition.getTop());
                    telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                            recognition.getRight(), recognition.getBottom());
                    i++;
                }
                telemetry.update();
            }
            telemetry.addData(TAG, "no objects detected ");
        }
        telemetry.addData(" # level =", level);
        telemetry.update();
        return level;
    }
}
