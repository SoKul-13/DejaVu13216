/* Copyright (c) 2017 FIRST. All rights reserved.
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

package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

public class DejaVuBot {
    /* Public OpMode members. */
    public DcMotorEx leftFrontMotor;
    public DcMotorEx rightFrontMotor;
    public DcMotorEx leftBackMotor;
    public DcMotorEx rightBackMotor;
    public DcMotorEx duckSpinner;
    public BNO055IMU imu;


    public DejaVuArm arm = null;

    private boolean isAuton;
    /* local OpMode members. */
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();
    static final double DUCK_SPIN_POWER = 0.5;
    //motor constants for auton calculations
    static final double WHEEL_CIRCUMFERENCE_MM = 96 * Math.PI;
    static final double COUNTS_PER_MOTOR_REV = 28.0;
    static final double DRIVE_GEAR_REDUCTION = 16.25;

    static final double COUNTS_PER_WHEEL_REV = COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION;
    static final double COUNT_PER_MM = COUNTS_PER_WHEEL_REV/WHEEL_CIRCUMFERENCE_MM;
    static final double COUNT_PER_FT = COUNT_PER_MM * 304.8;
    public static final double COUNT_PER_INCH = COUNT_PER_FT/12;

    //max rpm for our motors are 338, here we're using 175 rpm
    public static double TPS = (double) ((175/60) * COUNTS_PER_WHEEL_REV);

    /* Constructor */
    public DejaVuBot() {

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap, boolean isAuton) {
        // Save reference to Hardware map
        hwMap = ahwMap;
        this.isAuton = isAuton;

        // Define and Initialize Motors
        leftFrontMotor = hwMap.get(DcMotorEx.class, "leftFront");
        rightFrontMotor = hwMap.get(DcMotorEx.class, "rightFront");
        leftBackMotor = hwMap.get(DcMotorEx.class, "leftBack");
        rightBackMotor = hwMap.get(DcMotorEx.class, "rightBack");

        /*Initialize the arm and duck spinner
        duckSpinner = hwMap.get(DcMotorEx.class, "duckSpinner");
        arm = new DejaVuArm();
        arm.init(hwMap, isAuton);*/
        stopRobot();

        // Initializing base chassis direction
        leftFrontMotor.setDirection(DcMotorEx.Direction.FORWARD);
        rightFrontMotor.setDirection(DcMotorEx.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotorEx.Direction.FORWARD);
        rightBackMotor.setDirection(DcMotorEx.Direction.REVERSE);
    }
    public void gyroInit() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        //calculates velocity and position of robot
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hwMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }

    public void chassisEncoderOff() {
        leftFrontMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        leftBackMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        rightBackMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void chassisEncoderOn() {
        leftFrontMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

    //Power off the robot
    public void stopRobot() {
        // Set all chassis motors to zero power
        leftFrontMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        leftBackMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightBackMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void setModeForAllMotors(DcMotorEx.RunMode runMode){
        leftBackMotor.setMode(runMode);
        leftFrontMotor.setMode(runMode);
        rightBackMotor.setMode(runMode);
        rightFrontMotor.setMode(runMode);
    }

    //Turn the robot at the given speed
    public void addForwardPositionToAllMotors(int forwardLength) {
        leftBackMotor.setTargetPosition(leftBackMotor.getTargetPosition() + forwardLength);
        leftFrontMotor.setTargetPosition(leftFrontMotor.getTargetPosition() + forwardLength);
        rightBackMotor.setTargetPosition(rightBackMotor.getTargetPosition() + forwardLength);
        rightFrontMotor.setTargetPosition(rightFrontMotor.getTargetPosition() + forwardLength);
    }

    //Turn the robot at the given speed
    public void setPowerToAllMotors(double speed) {
        leftFrontMotor.setPower(speed);
        rightFrontMotor.setPower(speed);
        rightBackMotor.setPower(speed);
        leftBackMotor.setPower(speed);
    }

    public void setVelocityToAllMotors(double velocity) {
        leftFrontMotor.setVelocity(velocity);
        rightFrontMotor.setVelocity(velocity);
        rightBackMotor.setVelocity(velocity);
        leftBackMotor.setVelocity(velocity);
    }

    //Turn the robot at the given speed
    public void turnRobot(double turnSpeed) {
        leftFrontMotor.setPower(turnSpeed);
        rightFrontMotor.setPower(-turnSpeed);
        rightBackMotor.setPower(-turnSpeed);
        leftBackMotor.setPower(turnSpeed);
    }

    public void spinClockWise() {
        if (duckSpinner != null) {
            duckSpinner.setDirection(DcMotorEx.Direction.FORWARD);
            duckSpinner.setPower(DUCK_SPIN_POWER);
        }
    }

    public void spinAntiClockWise() {
        if (duckSpinner != null) {
            duckSpinner.setDirection(DcMotorEx.Direction.REVERSE);
            duckSpinner.setPower(DUCK_SPIN_POWER);
        }
    }

    //will be used if we have time
    public void scanLevel() {

    }

    //will be used based on hardware team strategy
    public void intake() {

    }
}