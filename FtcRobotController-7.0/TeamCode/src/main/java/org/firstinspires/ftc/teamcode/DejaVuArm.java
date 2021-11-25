package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class DejaVuArm {
    /* Public OpMode members. */
    public DcMotor armMotor   = null;
    public Servo bucketServo  = null;

    public static final double MID_SERVO       =  0.5 ;
    public static final double ARM_UP_POWER    =  0.45 ;
    public static final double ARM_DOWN_POWER  = -0.45 ;

    private int currentLevel = 1;

    private HardwareMap hwMap           =  null;

    public DejaVuArm(){}

    //Initialize the arm
    public void init(HardwareMap hMap ){
        hwMap = hMap;
        armMotor = hwMap.get(DcMotor.class , "arm_motor");
        bucketServo = hwMap.get(Servo.class , "bucket_servo");
        currentLevel = 1;

    }

    public void moveArmToLevel(int level){
        if( currentLevel != level){
            armMotor.setPower(ARM_UP_POWER);
            //wait for the seconds and set power to 0
            armMotor.setPower(0);
        }
    }

    public void bringDownArm(int level){
        if(currentLevel!=level){
            armMotor.setPower(ARM_DOWN_POWER);
            //wait for some seconds. set the target on arm motor
            //wait for the seconds and set power to 0
            armMotor.setPower(0);
        }
    }

    //Release the bucket contents and go to original position after 0.5 seconds.
    public void releaseBucket(){
        bucketServo.setDirection(Servo.Direction.FORWARD);
        bucketServo.setPosition(3);
    }

    public void openBucket(){
        if(bucketServo != null){
            double current = bucketServo.getPosition();
            if(current != 1.0){
                bucketServo.setPosition(3.0);
            }
        }
    }

    public void closeBucket(){

    }
}
