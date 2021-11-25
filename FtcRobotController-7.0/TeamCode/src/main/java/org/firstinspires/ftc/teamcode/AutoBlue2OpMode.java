package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="AutoBlue2OpMode", group="Linear OpMode")

public class AutoBlue2OpMode extends LinearOpMode {
    DejaVuBot robot = null;
    private final double MOTOR_TICK_COUNT = 384.5;
    private ElapsedTime runtime = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, true);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready for gamepad run");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        //reset elapsed time for autonomous code
        runtime.reset();
        //making the forward length with 4 rotations
        double forwardLength = 384.5*4;
        //moving forward desired length
        robot.leftBackMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftFrontMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightBackMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightFrontMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        //encoder 'resetting'
        int NewLeftBackMotorTarget = robot.leftBackMotor.getTargetPosition() + (int)forwardLength;
        int NewLeftFrontMotorTarget = robot.leftFrontMotor.getTargetPosition() + (int)forwardLength;
        int NewRightBackMotorTarget = robot.rightBackMotor.getTargetPosition() + (int)forwardLength;
        int NewRightFrontMotorTarget = robot.rightFrontMotor.getTargetPosition() + (int)forwardLength;
        //setting target location
        robot.leftBackMotor.setTargetPosition(NewLeftBackMotorTarget);
        robot.leftFrontMotor.setTargetPosition(NewLeftFrontMotorTarget);
        robot.rightBackMotor.setTargetPosition(NewRightBackMotorTarget);
        robot.rightFrontMotor.setTargetPosition(NewRightFrontMotorTarget);
        //setting speed
        robot.leftBackMotor.setPower(1);
        robot.leftFrontMotor.setPower(1);
        robot.rightBackMotor.setPower(1);
        robot.rightFrontMotor.setPower(1);
        //making it run the go forward
        robot.leftBackMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        robot.leftFrontMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        robot.rightBackMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        robot.rightFrontMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        /*-------------------NEW ACTION---------------------------*/

        double turnLength = 384.5/4;
        //moving forward desired length
        robot.leftBackMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftFrontMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightBackMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightFrontMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        //encoder 'resetting'
        int NewLeftBackMotorTargetTurn = robot.leftBackMotor.getTargetPosition() - (int)turnLength;
        int NewLeftFrontMotorTargetTurn = robot.leftFrontMotor.getTargetPosition() - (int)turnLength;
        int NewRightBackMotorTargetTurn = robot.rightBackMotor.getTargetPosition() + (int)turnLength;
        int NewRightFrontMotorTargetTurn = robot.rightFrontMotor.getTargetPosition() + (int)turnLength;
        //setting target location
        robot.leftBackMotor.setTargetPosition(NewLeftBackMotorTarget);
        robot.leftFrontMotor.setTargetPosition(NewLeftFrontMotorTarget);
        robot.rightBackMotor.setTargetPosition(NewRightBackMotorTarget);
        robot.rightFrontMotor.setTargetPosition(NewRightFrontMotorTarget);
        //setting speed
        robot.leftBackMotor.setPower(1);
        robot.leftFrontMotor.setPower(1);
        robot.rightBackMotor.setPower(1);
        robot.rightFrontMotor.setPower(1);
        //making it run the go forward
        robot.leftBackMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        robot.leftFrontMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        robot.rightBackMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        robot.rightFrontMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        /*----------------MOTOR FOR ARM----------------------------------------------*/
        /*----------------STRAFE----------------------------------------------*/
        double strafeLength = 384.5*4;
        //moving forward desired length
        robot.leftBackMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftFrontMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightBackMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightFrontMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        //encoder 'resetting'
        int NewLeftBackMotorTargetStrafe = robot.leftBackMotor.getTargetPosition() + (int)strafeLength;
        int NewLeftFrontMotorTargetStrafe = robot.leftFrontMotor.getTargetPosition() - (int)strafeLength;
        int NewRightBackMotorTargetStrafe = robot.rightBackMotor.getTargetPosition() + (int)strafeLength;
        int NewRightFrontMotorTargetStrafe = robot.rightFrontMotor.getTargetPosition() - (int)strafeLength;
        //setting target location
        robot.leftBackMotor.setTargetPosition(NewLeftBackMotorTarget);
        robot.leftFrontMotor.setTargetPosition(NewLeftFrontMotorTarget);
        robot.rightBackMotor.setTargetPosition(NewRightBackMotorTarget);
        robot.rightFrontMotor.setTargetPosition(NewRightFrontMotorTarget);
        //setting speed
        robot.leftBackMotor.setPower(1);
        robot.leftFrontMotor.setPower(1);
        robot.rightBackMotor.setPower(1);
        robot.rightFrontMotor.setPower(1);
        //making it run the go forward
        robot.leftBackMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        robot.leftFrontMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        robot.rightBackMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        robot.rightFrontMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }
}
