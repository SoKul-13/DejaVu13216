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

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

public class DejaVuBot {
    /* Public OpMode members. */
    public DcMotor leftFrontMotor = null;
    public DcMotor rightFrontMotor = null;
    public DcMotor leftBackMotor = null;
    public DcMotor rightBackMotor = null;
    public DcMotor duckSpinner = null;

    public DejaVuArm arm = null;

    private boolean isAuton;
    /* local OpMode members. */
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();
    static final double DUCK_SPIN_POWER = 0.5;

    /* Constructor */
    public DejaVuBot() {

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap, boolean isAuton) {
        // Save reference to Hardware map
        hwMap = ahwMap;
        this.isAuton = isAuton;
        //Initialize the arm
        arm = new DejaVuArm();
        arm.init(hwMap, isAuton);
        // Define and Initialize Motors
        leftFrontMotor = hwMap.get(DcMotor.class, "leftFront");
        rightFrontMotor = hwMap.get(DcMotor.class, "rightFront");
        leftBackMotor = hwMap.get(DcMotor.class, "leftBack");
        rightBackMotor = hwMap.get(DcMotor.class, "rightBack");
        duckSpinner = hwMap.get(DcMotor.class, "duck_spinner");
        stopRobot();

        // Initializing base chassis direction
        leftFrontMotor.setDirection(DcMotor.Direction.FORWARD);
        rightFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotor.Direction.FORWARD);
        rightBackMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void chassisEncoderOff() {
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void chassisEncoderOn() {
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    //Power off the robot
    public void stopRobot() {
        // Set all chassis motors to zero power
        leftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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
            duckSpinner.setDirection(DcMotor.Direction.FORWARD);
            duckSpinner.setPower(DUCK_SPIN_POWER);
        }
    }

    public void spinAntiClockWise() {
        if (duckSpinner != null) {
            duckSpinner.setDirection(DcMotor.Direction.REVERSE);
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