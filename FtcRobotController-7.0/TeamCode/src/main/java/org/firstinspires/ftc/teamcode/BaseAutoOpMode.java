package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
// 2.4 rotations for top, 1/2 rotation for safe zone
@Autonomous( name="BaseAutoOpMode", group="AutoOpModes")
public class BaseAutoOpMode extends LinearOpMode {
    private Orientation lastAngles = new Orientation();
    private double currAngle = 0.0;

    DejaVuBot robot = new DejaVuBot();

    public double getAbsoluteAngle() {
        return robot.imu.getAngularOrientation(
                AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES
        ).firstAngle;
    }

    public void turnPID(double degrees) {
        turnToPID(degrees + getAbsoluteAngle());
    }

    void turnToPID(double targetAngle) {
        if(targetAngle < 0){
            turnToPIDForNegativeAngle(targetAngle);
        }else {
            PIDUtils pid = new PIDUtils(targetAngle, 0.01, 0, 0.003);
            telemetry.setMsTransmissionInterval(50);
            // Checking lastSlope to make sure that it's not "oscillating" when it quits
            telemetry.addData(" turn to pid sTART abs angle = ", getAbsoluteAngle());
            telemetry.addData(" turn to pid start diff = ", Math.abs(targetAngle - getAbsoluteAngle()));
            telemetry.addData(" turn to pid start slope = ", pid.getLastSlope());
            telemetry.update();

            while (opModeIsActive() && (Math.abs(targetAngle - getAbsoluteAngle()) > 0.5 || pid.getLastSlope() > 0.75)) {
                double motorPower = pid.update(getAbsoluteAngle());

                robot.leftFrontMotor.setPower(-motorPower);
                robot.leftBackMotor.setPower(-motorPower);
                robot.rightFrontMotor.setPower(motorPower);
                robot.rightBackMotor.setPower(motorPower);

                telemetry.addData(" turn to pid angle difference = ", Math.abs(targetAngle - getAbsoluteAngle()));
                telemetry.addData(" turn to pid slope = ", pid.getLastSlope());
                telemetry.update();
            }

            robot.setPowerToAllMotors(0);
        }

    }
    public void driveForwardByInches(int distance, DejaVuBot bot) {
        bot.stopRobot();
        int targetInput = (int) ((48/41)*(distance * DejaVuBot.COUNT_PER_INCH));
        telemetry.addData("Target Position Set to:", targetInput);
        telemetry.update();
        bot.leftFrontMotor.setTargetPosition(targetInput);
        bot.rightFrontMotor.setTargetPosition(targetInput);
        bot.leftBackMotor.setTargetPosition(targetInput);
        bot.rightBackMotor.setTargetPosition(targetInput);
        bot.chassisEncoderOn();
        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready for run");
        telemetry.update();

        bot.setVelocityToAllMotors(DejaVuBot.TPS);
        while (opModeIsActive() && bot.leftFrontMotor.isBusy()) {
            telemetry.addData("left", bot.leftFrontMotor.getCurrentPosition());
            telemetry.addData("right", bot.rightFrontMotor.getCurrentPosition());
            telemetry.update();
        }
    }

    @Override
        public void runOpMode() throws InterruptedException {
            telemetry.addData("Status", "Initialized");
            telemetry.update();
            // Wait for the game to start (driver presses PLAY)
            waitForStart();
            // run until the end of the match (driver presses STOP)
            while (opModeIsActive()) {

                telemetry.addData("Status", "Running");
                telemetry.update();
            }
        }

        /* Code below turns the robot based on non PID code */
    static final double     FORWARD_SPEED = 0.6;
    static final double     TURN_SPEED    = 0.5;// resets currAngle Value
    public void resetAngle() {
        lastAngles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        currAngle = 0;
    }

    public double getAngle() {

        // Get current orientation
        Orientation orientation = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        // Change in angle = current angle - previous angle
        double deltaAngle = orientation.firstAngle - lastAngles.firstAngle;

        // Gyro only ranges from -179 to 180
        // If it turns -1 degree over from -179 to 180, subtract 360 from the 359 to get -1
        if (deltaAngle < -180) {
            deltaAngle += 360;
        } else if (deltaAngle > 180) {
            deltaAngle -= 360;
        }

        // Add change in angle to current angle to get current angle
        currAngle += deltaAngle;
        lastAngles = orientation;
        telemetry.addData("gyro", orientation.firstAngle);
        return currAngle;
    }

    public void turn(double degrees){
        resetAngle();

        double error = degrees;

        while (opModeIsActive() && Math.abs(error) > 2) {
            double motorPower = (error < 0 ? -0.3 : 0.3);
            robot.setMotorPower(-motorPower, motorPower, -motorPower, motorPower);
            error = degrees - getAngle();
            telemetry.addData("error", error);
            telemetry.update();
        }

        robot.setPowerToAllMotors(0);
    }

    public void turnTo(double degrees){
        if(degrees < 0){
            //Use the PID function for negative turns
            turnToPIDForNegativeAngle(degrees);
        }else {

            Orientation orientation = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

            System.out.println(orientation.firstAngle);
            double error = degrees - orientation.firstAngle;

            if (error > 180) {
                error -= 360;
            } else if (error < -180) {
                error += 360;
            }

            turn(error);
        }
    }

    void turnToPIDForNegativeAngle(double targetAngle) {
        PIDUtils pid = new PIDUtils(targetAngle, 0.0001, 0, 0.003);
        telemetry.setMsTransmissionInterval(50);
        // Checking lastSlope to make sure that it's not "oscillating" when it quits
        telemetry.addData(" turnToPIDForNegativeAngle s abs angle = ", getAbsoluteAngle());
        telemetry.addData("  turnToPIDForNegativeAngle s  diff = ", Math.abs(Math.abs(targetAngle) - getAbsoluteAngle()) );
        telemetry.addData("  turnToPIDForNegativeAngle  start slope = ", pid.getLastSlope());
        telemetry.update();

        while (opModeIsActive()
                && (Math.abs(Math.abs(targetAngle) - getAbsoluteAngle()) > 0.5
                || pid.getLastSlope() > 0.75)) {
            double motorPower = pid.update(getAbsoluteAngle());

            robot.leftFrontMotor.setPower(-motorPower);
            robot.leftBackMotor.setPower(-motorPower);
            robot.rightFrontMotor.setPower(motorPower);
            robot.rightBackMotor.setPower(motorPower);

            telemetry.addData(" turnToPIDForNegativeAngle loop abs angle = ", getAbsoluteAngle());
            telemetry.addData(" turnToPIDForNegativeAngle angle difference = ", Math.abs(Math.abs(targetAngle) - getAbsoluteAngle()) );
            telemetry.addData(" turnToPIDForNegativeAngle slope = ", pid.getLastSlope());
            telemetry.update();
        }

        robot.setPowerToAllMotors(0);
    }

    public void spinForOneDuck(DejaVuBot bot) {
        bot.duckSpinner.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bot.duckSpinner.setDirection(DcMotorEx.Direction.FORWARD);
        bot.duckSpinner.setTargetPosition(DejaVuBot.ONE_DUCK_SPIN_TARGET_LENGTH);
        bot.duckSpinner.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        bot.duckSpinner.setVelocity(DejaVuBot.TPS);

        telemetry.addData(" target :",bot.duckSpinner.getTargetPosition());
        telemetry.update();

        while (opModeIsActive() && bot.duckSpinner.isBusy()) {
            telemetry.addData(" bot needs to move inches  :",DejaVuBot.ONE_DUCK_SPIN_TARGET_LENGTH);
            telemetry.addData(" spinner position =", bot.duckSpinner.getCurrentPosition());
            telemetry.update();
        }
        //Stop the motor
        bot.duckSpinner.setPower(0);
    }
}

