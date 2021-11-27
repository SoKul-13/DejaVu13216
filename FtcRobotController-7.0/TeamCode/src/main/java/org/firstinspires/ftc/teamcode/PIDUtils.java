package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

// Single use per object
public class PIDUtils {
    private double P, I, D;
    private ElapsedTime timer = new ElapsedTime();
    private double targetAngle;
    private double lastError = 0;
    private double accumulatedError = 0;
    private double lastTime = -1;
    private double lastSlope = 0;

    public PIDUtils(double target, double p, double i, double d) {
        this.P = p;
        this.I = i;
        this.D = d;
        targetAngle = target;
    }

    public double update(double currentAngle) {
        // P
        double error = targetAngle - currentAngle;
        error %= 360;
        error += 360;
        error %= 360;
        if (error > 180) {
            error -= 360;
        }

        // I
        accumulatedError *= Math.signum(error);
        accumulatedError += error;
        if (Math.abs(error) < 2) {
            accumulatedError = 0;
        }

        // D
        double slope = 0;
        if (lastTime > 0) {
            slope = (error - lastError) / (timer.milliseconds() - lastTime);
        }
        lastSlope = slope;
        lastError = error;
        lastTime = timer.milliseconds();

        double motorPower = 0.1 * Math.signum(error)
                + 0.9 * Math.tanh(P * error + I * accumulatedError - D * slope);
        return motorPower;
    }

    public double getLastSlope() {
        return lastSlope;
    }
}