/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.vision;

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

/**
 * This class represents the autonomous run from Red 2  position with webcam integration
 */
@Autonomous(name="BaseAutoVisionOpMode", group="AutoOpModes")
//@Disabled
public class BaseAutoVisionOpMode extends BaseAutoOpMode {
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
    protected int currentLevel =0;
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

    protected Thread bucketThread;
    protected Thread armThread;

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

    protected int findLevel(){
        int level = -1;
        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
        if (updatedRecognitions != null && updatedRecognitions.size() ==2 )  {
            telemetry.addData("# Object Detected", updatedRecognitions.size());
            Log.i(TAG, "DJ# Object Detected" + updatedRecognitions.size());
            Log.i(TAG, "DJ# # Label 1=" + updatedRecognitions.get(0).getLabel());
            Log.i(TAG, "DJ# # Label 2=" + updatedRecognitions.get(1).getLabel());
            telemetry.addData("# Label 1 =", updatedRecognitions.get(0).getLabel());
            telemetry.addData("# Label 2 =", updatedRecognitions.get(1).getLabel());

            if(updatedRecognitions.get(0).getLabel().equals(LABELS[1]) && updatedRecognitions.get(1).getLabel().equals(LABELS[3])){
                level = DejaVuArm.BOTTOM_LEVEL;
            }else if(updatedRecognitions.get(0).getLabel().equals(LABELS[3]) && updatedRecognitions.get(1).getLabel().equals(LABELS[1])){
                level = DejaVuArm.MID_LEVEL;
            }else if(updatedRecognitions.get(0).getLabel().equals(LABELS[3]) && updatedRecognitions.get(1).getLabel().equals(LABELS[3])){
                level = DejaVuArm.TOP_LEVEL;
            }
            telemetry.addData("Setting currentLevel =", level);
        }else if (updatedRecognitions != null && updatedRecognitions.size() ==1 )  {
            telemetry.addData("# Object Detected", updatedRecognitions.size());
            Log.i(TAG, "DJ# Object Detected" + updatedRecognitions.size());
            Log.i(TAG, "DJ# # Label =" + updatedRecognitions.get(0).getLabel());
            Log.i(TAG, "DJ# # left =" + updatedRecognitions.get(0).getLeft());

            telemetry.addData("# Label  =", updatedRecognitions.get(0).getLabel());
            double left = updatedRecognitions.get(0).getLeft();
            //Check if cube found in either location.
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
