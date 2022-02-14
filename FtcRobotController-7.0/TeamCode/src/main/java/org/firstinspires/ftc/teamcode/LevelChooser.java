package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

public class LevelChooser extends OpenCvPipeline {
    private OpenCvCamera cam;
    private Mat mat = new Mat();
    private Rect middleROI = new Rect(new Point(264,12), new Point(392,229));
    private Rect leftROI = new Rect(new Point(2, 32), new Point(95,248));
    private Rect rightROI = new Rect(new Point(516,15), new Point(607,211));
    private Mat rightMat;
    private Mat leftMat;
    private Mat middleMat;
    private AnsTarget target;
    public LevelChooser(HardwareMap hwMap, Telemetry t) {
        int camMonViewId = hwMap.appContext.getResources().getIdentifier(
            "cameraMonitorViewId",
                "id",
                hwMap.appContext.getPackageName()
        );
        t.addData("1st", "success");
        t.update();
        cam = OpenCvCameraFactory.getInstance().createWebcam(
                hwMap.get(WebcamName.class, "Webcam 1"),
                camMonViewId
        );
        t.addData("1st", "success");
        t.update();
        cam.setPipeline(this);
        t.addData("1st", "success");
        t.update();
        cam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                cam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {

            }
        });
        t.addData("1st", "success");
        t.update();
    }
    @Override
    public Mat processFrame(Mat input) {
        //--thresholding
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGBA2RGB);
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2HSV);
        /*--next step, take photo, use color picker, convert to hsv, in android studio,
        have to divide value as normal is 360 and this one is to 179 degrees*/
        Scalar lowerBound = new Scalar(15.0/2, 100, 100);
        Scalar upperBound = new Scalar(45.0, 255, 255);
        Core.inRange(mat, lowerBound, upperBound, mat);
        //to find where and how large rectangle areas of interest are based on viewpoint, imgproc.Rect, then return it, to see it, try ot move it around
        //--divide--might be optional
        rightMat = mat.submat(rightROI);
        middleMat = mat.submat(middleROI);
        leftMat = mat.submat(leftROI);

        //--average
        double leftValue = Math.round(Core.mean(leftMat).val[2] / 255);
        double middleValue = Math.round(Core.mean(middleMat).val[2] / 255);
        double rightValue = Math.round(Core.mean(rightMat).val[2] / 255);
        //--compare
        final double THRESHOLD = 10; // can adjust in future, very low for well lit and low lit env
        if (leftValue > THRESHOLD) {
            target = AnsTarget.D;
        } else if (middleValue > THRESHOLD) {
            target = AnsTarget.C;
        } else if (rightValue > THRESHOLD) {
            target = AnsTarget.B;
        } else {
            target = AnsTarget.A;
        }
        Scalar colorOrangeSensed = new Scalar(0, 255, 0);
        Scalar colorReg = new Scalar(255, 0, 0);
        Imgproc.rectangle(mat, leftROI, target == AnsTarget.D? colorOrangeSensed:colorReg);
        Imgproc.rectangle(mat, leftROI, target == AnsTarget.D? colorOrangeSensed:colorReg);
        Imgproc.rectangle(mat, leftROI, target == AnsTarget.D? colorOrangeSensed:colorReg);
        leftMat.release();
        middleMat.release();
        rightMat.release();
        //mat.release();
        //return null;
        return mat;
    }
    public AnsTarget getTarget() {
        return target;
    }
    public void stop() {
        //add something to empty lambda as could cause null pointer exception for feeding nothing to listener function
        cam.closeCameraDeviceAsync(() -> {});
    }

}
