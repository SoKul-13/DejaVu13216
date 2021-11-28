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
        PIDUtils pid = new PIDUtils(targetAngle, 0.01, 0, 0.003);
        telemetry.setMsTransmissionInterval(50);
        // Checking lastSlope to make sure that it's not "oscillating" when it quits
        while (opModeIsActive() && (Math.abs(targetAngle - getAbsoluteAngle()) > 0.5 || pid.getLastSlope() > 0.75)) {
            double motorPower = pid.update(getAbsoluteAngle());
            robot.leftFrontMotor.setPower(-motorPower);
            robot.leftBackMotor.setPower(-motorPower);
            robot.rightFrontMotor.setPower(motorPower);
            robot.rightBackMotor.setPower(motorPower);
        }
        robot.setPowerToAllMotors(0);
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
            //bucketServo = hardwareMap.get(Servo.class, "bucketServo");
            //bucketServo.setPosition(0);
            telemetry.addData("Status", "Initialized");
            telemetry.update();
            // Wait for the game to start (driver presses PLAY)
            waitForStart();
            // run until the end of the match (driver presses STOP)
            while (opModeIsActive()) {
                //bucketServo.setPosition(1);
                telemetry.addData("Status", "Running");
                telemetry.update();
            }
        }
    }

