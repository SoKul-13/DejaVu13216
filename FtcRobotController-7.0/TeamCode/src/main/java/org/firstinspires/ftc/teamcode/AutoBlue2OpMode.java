package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="AutoBlue2OpMode", group="Linear OpMode")

public class AutoBlue2OpMode extends LinearOpMode {
    DejaVuBot robot = new DejaVuBot();
    private final double MOTOR_TICK_COUNT = 384.5;
    private ElapsedTime runtime = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Initializing robot..");
        telemetry.update();

        robot.init(hardwareMap,true);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("AutoBlue2OpMode", "Ready for auto blue 2 run");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        //reset elapsed time for autonomous code
        runtime.reset();

        //encoder 'resetting'
        robot.setModeForAllMotors(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        //moving forward desired length , setting the forward length for 1/4 rotations
        int forwardLength = 1 ;//(int)MOTOR_TICK_COUNT/100;
        robot.addForwardPositionToAllMotors(forwardLength);

        //setting speed
        robot.setPowerToAllMotors(0.25);

        //making it run the go forward
        robot.setModeForAllMotors(DcMotorEx.RunMode.RUN_TO_POSITION);

        while(robot.leftFrontMotor.isBusy()){}
        while(robot.leftBackMotor.isBusy()){}
        while(robot.rightFrontMotor.isBusy()){}
        while(robot.rightBackMotor.isBusy()){}

        telemetry.addData("AutoBlue2OpMode", " Reached first destination ");
        telemetry.update();


        /*-------------------NEW ACTION---------------------------*/

        int turnLength = 2; //(int)MOTOR_TICK_COUNT/100;
        //moving forward desired length
        robot.setModeForAllMotors(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        //encoder 'resetting'
        //setting target location
        robot.leftBackMotor.setTargetPosition(robot.leftBackMotor.getTargetPosition() - turnLength);
        robot.leftFrontMotor.setTargetPosition(robot.leftFrontMotor.getTargetPosition() - turnLength);
        robot.rightBackMotor.setTargetPosition(robot.rightBackMotor.getTargetPosition() + turnLength);
        robot.rightFrontMotor.setTargetPosition(robot.rightFrontMotor.getTargetPosition() + turnLength);

        //setting speed
        robot.setPowerToAllMotors(0.25);

        //making it run the go forward
        robot.setModeForAllMotors(DcMotorEx.RunMode.RUN_TO_POSITION);
        while(robot.leftFrontMotor.isBusy()){}
        while(robot.leftBackMotor.isBusy()){}
        while(robot.rightFrontMotor.isBusy()){}
        while(robot.rightBackMotor.isBusy()){}

        telemetry.addData("AutoBlue2OpMode", " Turned to deliver the freight ");
        telemetry.update();

        /*----------------MOTOR FOR ARM----------------------------------------------*/
        /*----------------STRAFE----------------------------------------------*/

        //moving forward desired length
        robot.setModeForAllMotors(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        //setting target location
        int strafeLength = 4; //(int)MOTOR_TICK_COUNT/100;
        robot.leftBackMotor.setTargetPosition(robot.leftBackMotor.getTargetPosition() + strafeLength);
        robot.leftFrontMotor.setTargetPosition(robot.leftFrontMotor.getTargetPosition() - strafeLength);
        robot.rightBackMotor.setTargetPosition(robot.rightBackMotor.getTargetPosition() + strafeLength);
        robot.rightFrontMotor.setTargetPosition(robot.rightFrontMotor.getTargetPosition() - strafeLength);
        //setting speed
        robot.setPowerToAllMotors(0.25);

        //making it run the go forward
        robot.setModeForAllMotors(DcMotorEx.RunMode.RUN_TO_POSITION);
        while(robot.leftFrontMotor.isBusy()){}
        while(robot.leftBackMotor.isBusy()){}
        while(robot.rightFrontMotor.isBusy()){}
        while(robot.rightBackMotor.isBusy()){}
        telemetry.addData("AutoBlue2OpMode", " Reached warehouse");
        telemetry.update();
    }
}