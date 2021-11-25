package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashMap;

public class DejaVuArm {
    /* Public OpMode members. */
    public DcMotorEx armMotor = null;
    public Servo bucketServo = null;
    //values of this dictionary are subject to change after encoders
    static HashMap<Integer, Integer> level_map = new HashMap<>();
    {
        level_map.put(1, 200);
        level_map.put(2, 400);
        level_map.put(3, 700);
    }
    public static final double MID_SERVO = 0.5;
    private int currentLevel = 1;
    private boolean isAuton;
    private HardwareMap hwMap = null;

    public DejaVuArm() {

    }

    //Initialize the arm
    public void init(HardwareMap hMap, boolean isAuton) {
        this.isAuton = isAuton;
        this.hwMap = hMap;
        this.armMotor = hwMap.get(DcMotorEx.class, "arm_motor");
        this.bucketServo = hwMap.get(Servo.class, "bucket_servo");
        this.currentLevel = 1;
        this.armMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

    public void moveArmToLevel(int level) {
        int l = level_map.get(level);
        armMotor.setTargetPosition(l);
        while(armMotor.isBusy()) {
            armMotor.getVelocity();
        }
        armMotor.setVelocity(0);
    }

    public void openBucketPos() {
        bucketServo.setPosition(1);
    }

    public void loadBucketPos() {
        bucketServo.setPosition(0);
    }

    public void closeBucketPos() {
        bucketServo.setPosition(-0.8);
    }
}