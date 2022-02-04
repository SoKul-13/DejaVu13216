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
    public DcMotorEx intakeMotor = null;
    static final double PULSES_PER_REVOLUTION = 751.8;

    //max rpm for our arm motor is 1,850, here we're using 1750 rpm
    public static double SLIDER_TPS = 2200.0;
    static HashMap<Integer, Integer> level_map = new HashMap<>();
    {
        level_map.put(0, 0);
        level_map.put(1, 1221);
        level_map.put(2, 1860);
        //set level 3 encoder value to 1 3/4 in
        //safety check -> servo should not be flipped if the arm wants to come down +
        //safety check -> arm should not come down if the green wheel is on
    }

    private int currentLevel = 0;
    public int armMotorBasePos;
    private boolean isAuton;
    private HardwareMap hwMap = null;

    public DejaVuArm() {    }

    //Initialize the arm
    public void init(HardwareMap hMap, boolean isAuton) {
        this.isAuton = isAuton;
        this.hwMap = hMap;
        this.armMotor = hwMap.get(DcMotorEx.class, "armMotor");
        armMotor.setDirection(DcMotorEx.Direction.FORWARD);
        this.bucketServo = hwMap.get(Servo.class, "bucketServo");
        bucketServo.setDirection(Servo.Direction.FORWARD);
        this.closeBucketPos();
        this.moveArmToLevel(0);
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
            armMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
            armMotor.setPower(0);
            if (armMotor.getCurrentPosition() != level_map.get(level)) {
                armMotor.setTargetPosition(level_map.get(level));
                armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                while (armMotor.isBusy()) {
                    armMotor.setVelocity(SLIDER_TPS/4);
                }
                armMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
                armMotor.setPower(0);
            }
            currentLevel = level;
        }
    }
    public void openBucketPos() {
        bucketServo.setPosition(0.113);
    }

    public void closeBucketPos() {
        bucketServo.setPosition(0.887);
    }
    public void resetArmMotor() { armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);}

}

