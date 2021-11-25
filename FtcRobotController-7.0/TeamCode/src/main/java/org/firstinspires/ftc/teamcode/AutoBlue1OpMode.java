package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
/*
    Red1 and Blue 1 strategy.--- 26 points
    Robot will deliver the freight at the top level - 6 points
    Robot will spin the duck table 1 time -10
    Park in warehouse- 10 points
 */
@Autonomous(name="AutoBlue1OpMode", group="AutoOpModes")
public class AutoBlue1OpMode extends LinearOpMode {
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
        telemetry.addData("AutoBlue1OpMode", "Ready for auto blue 1 run");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        //reset elapsed time for autonomous code
        runtime.reset();

        //encoder 'resetting'
        robot.setModeForAllMotors(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //moving forward desired length , setting the forward length for 1/4 rotations
        int forwardLength = 1 ;//(int)MOTOR_TICK_COUNT/100;
        robot.addForwardPositionToAllMotors(forwardLength);

        //setting speed
        robot.setPowerToAllMotors(0.25);

        //making it run the go forward
        robot.setModeForAllMotors(DcMotor.RunMode.RUN_TO_POSITION);

        while(robot.leftFrontMotor.isBusy()){}
        while(robot.leftBackMotor.isBusy()){}
        while(robot.rightFrontMotor.isBusy()){}
        while(robot.rightBackMotor.isBusy()){}

        telemetry.addData("AutoBlue1OpMode", " Reached first destination ");
        telemetry.update();

         /*------------------- Turn the robot to the hub---------*/

        int turnLength = 2; //(int)MOTOR_TICK_COUNT/100;
        //moving forward desired length
        robot.setModeForAllMotors(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //encoder 'resetting'
        //setting target location
        robot.leftBackMotor.setTargetPosition(robot.leftBackMotor.getTargetPosition() - turnLength);
        robot.leftFrontMotor.setTargetPosition(robot.leftFrontMotor.getTargetPosition() - turnLength);
        robot.rightBackMotor.setTargetPosition(robot.rightBackMotor.getTargetPosition() + turnLength);
        robot.rightFrontMotor.setTargetPosition(robot.rightFrontMotor.getTargetPosition() + turnLength);

        //setting speed
        robot.setPowerToAllMotors(0.25);

        //making it run the go forward
        robot.setModeForAllMotors(DcMotor.RunMode.RUN_TO_POSITION);
        while(robot.leftFrontMotor.isBusy()){}
        while(robot.leftBackMotor.isBusy()){}
        while(robot.rightFrontMotor.isBusy()){}
        while(robot.rightBackMotor.isBusy()){}

        telemetry.addData("AutoBlue2OpMode", " Turned to deliver the freight ");
        telemetry.update();

        /*----------------Raise the arm and drop freight ----------------------------*/

        /*----------------STRAFE to duck spinner and align the wheel ----------------*/

        /*----------------Spin the duck spinner -------------------------------------*/
        if(robot.duckSpinner != null){
            //Spin the duck spinner anti clockwise to drop one duck
            robot.spinAntiClockWise();
        }
        telemetry.addData("AutoBlue2OpMode", " Reached warehouse");
        telemetry.update();
    }
}