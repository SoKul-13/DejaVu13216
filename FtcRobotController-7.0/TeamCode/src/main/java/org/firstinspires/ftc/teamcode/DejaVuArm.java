package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashMap;
import java.util.Hashtable;

public class DejaVuArm {
    /* Public OpMode members. */
    public DcMotorEx armMotor = null;
    public Servo bucketServo = null;
    static final double ARM_WHEEL_CIRCUMFERENCE_MM = 2 * Math.PI;

    static final double ARM_COUNTS_PER_MOTOR_REV = 28.0;
    static final double ARM_DRIVE_GEAR_REDUCTION = 0.6030;

    static final double ARM_COUNTS_PER_WHEEL_REV = ARM_COUNTS_PER_MOTOR_REV * ARM_DRIVE_GEAR_REDUCTION;
    static final double ARM_COUNT_PER_MM = ARM_COUNTS_PER_WHEEL_REV/ARM_WHEEL_CIRCUMFERENCE_MM;
    static final double ARM_COUNT_PER_FT = ARM_COUNT_PER_MM * 304.8;
    public static final double ARM_COUNT_PER_INCH = ARM_COUNT_PER_FT/12;

    //max rpm for our arm motor is 1,850, here we're using 1750 rpm
    public static double SLIDER_TPS = 1750.0;
    static HashMap<Integer, Integer> level_map = new HashMap<>();
    {
        level_map.put(0, 0);
        level_map.put(1, (int) (145/3 * ARM_COUNT_PER_INCH));
        level_map.put(2, (int) (260/3 * ARM_COUNT_PER_INCH));
    }

    private int currentLevel = 0;
    private boolean isAuton;
    private HardwareMap hwMap = null;

    public DejaVuArm() {    }

    //Initialize the arm
    public void init(HardwareMap hMap, boolean isAuton) {
        this.isAuton = isAuton;
        this.hwMap = hMap;
        this.armMotor = hwMap.get(DcMotorEx.class, "armMotor");
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setDirection(DcMotorEx.Direction.FORWARD);
        this.bucketServo = hwMap.get(Servo.class, "bucketServo");
        this.currentLevel = 0;
    }


    public void moveArmToLevel(int level) {
        if(level != currentLevel) {
            int l = level_map.get(level);
            armMotor.setTargetPosition(l);
            this.armMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            while (armMotor.isBusy()) {
                armMotor.setVelocity(SLIDER_TPS);
            }
            armMotor.setPower(0);
            currentLevel = level;
        }
    }
    public void openBucketPos() {
        bucketServo.setPosition(0.75);
    }

    public void closeBucketPos() {
        bucketServo.setPosition(0.0);
    }
}

