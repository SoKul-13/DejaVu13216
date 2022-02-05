package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="VisionAutonomousCode", group="Auto")
public class VisionAutonomousCode extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        LevelChooser chooser = new LevelChooser(hardwareMap, telemetry);
        waitForStart();
        AnsTarget target = chooser.getTarget();
        chooser.stop();
        switch(target) {
            case A:
                telemetry.addData("ValueofTarget", "A");
                telemetry.update();
            case B:
                telemetry.addData("ValueofTarget", "B");
                telemetry.update();
            case C:
                telemetry.addData("ValueofTarget", "C");
                telemetry.update();
            case D:
                telemetry.addData("ValueofTarget", "D");
                telemetry.update();

        }
    }
}
