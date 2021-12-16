package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "DriveTrainTestLinearOp")
public class MainTeleOp extends LinearOpMode {

    static final double COUNTS_PER_MOTOR_REV = 537.6;
    static final double DRIVE_GEAR_REDUCTION = 1.23;
    static final double WHEEL_DIAMETER_INCHES = 2.45;
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.14159265);

    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor spinner;
    private DcMotor arm;
    private Servo hand;

    private Servo horizontal;

    private DcMotor dSlideR;

    @Override
    public void runOpMode() throws InterruptedException {

        //TODO: Hardware mapping when phones are configured;
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        spinner = hardwareMap.get(DcMotor.class, "spinner");
        arm = hardwareMap.get(DcMotor.class, "arm");
        hand = hardwareMap.get(Servo.class, "hand");

        dSlideR = hardwareMap.get(DcMotor.class, "dSlideR");

        horizontal = hardwareMap.get(Servo.class, "horizontal");

        //Set brake.
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        arm.setDirection(DcMotor.Direction.REVERSE);

        // encoder use
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        dSlideR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        dSlideR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double minPower = -.7;
        double maxPower = .7;

        waitForStart();

        while (opModeIsActive()) {

            //compensate for hardware issue
            frontLeft.setPower(Range.clip(-gamepad1.right_stick_y, minPower, maxPower));
            backLeft.setPower(Range.clip(-gamepad1.right_stick_y, minPower, maxPower));

            frontRight.setPower(Range.clip(-gamepad1.left_stick_y, minPower, maxPower));
            backRight.setPower(Range.clip(-gamepad1.left_stick_y, minPower, maxPower));

            //spinner
            if (gamepad1.b) {

                spinner.setPower(1.0);

            } else if (!gamepad1.b) {

                spinner.setPower(0);

            }

            if (gamepad1.x) {

                spinner.setPower(-1.0);

            } else if (!gamepad1.x) {

                spinner.setPower(0);

            }

            //arm

            //arm up
            if (gamepad1.dpad_up) {

                arm.setPower(-0.55);

            } else if (!gamepad1.dpad_up) {

                arm.setPower(0);

            }

            if (gamepad1.dpad_down) {

                arm.setPower(0.55);

            } else if (!gamepad1.dpad_down) {

                arm.setPower(0);

            }

            //claw

            if (gamepad1.left_bumper) {

                hand.setPosition(0.25);

            } else if (gamepad1.right_bumper) {

                hand.setPosition(.45);

            }

            //flip flop

            if (gamepad1.y) {

                frontLeft.setDirection(DcMotor.Direction.FORWARD);
                backLeft.setDirection(DcMotor.Direction.FORWARD);
                backRight.setDirection(DcMotor.Direction.REVERSE);
                frontRight.setDirection(DcMotor.Direction.REVERSE);

                frontLeft.setPower(Range.clip(-gamepad1.left_stick_y, minPower, maxPower));
                backLeft.setPower(Range.clip(-gamepad1.left_stick_y, minPower, maxPower));

                frontRight.setPower(Range.clip(-gamepad1.right_stick_y, minPower, maxPower));
                backRight.setPower(Range.clip(-gamepad1.right_stick_y, minPower, maxPower));


            } else if (gamepad1.a) {

                frontLeft.setDirection(DcMotor.Direction.REVERSE);
                backLeft.setDirection(DcMotor.Direction.REVERSE);
                frontRight.setDirection(DcMotor.Direction.FORWARD);
                backRight.setDirection(DcMotor.Direction.FORWARD);

                frontLeft.setPower(Range.clip(-gamepad1.right_stick_y, minPower, maxPower));
                backLeft.setPower(Range.clip(-gamepad1.right_stick_y, minPower, maxPower));

                frontRight.setPower(Range.clip(-gamepad1.left_stick_y, minPower, maxPower));
                backRight.setPower(Range.clip(-gamepad1.left_stick_y, minPower, maxPower));


            }

            if (gamepad2.dpad_up) {

                dSlideR.setPower(0.3);

            } else if (gamepad2.dpad_down) {

                dSlideR.setPower(-0.3);

            } else {

                dSlideR.setPower(0);

            }

            //Horizontal Extension

            //expand
            if (gamepad2.b) {

                horizontal.setPosition(0.17);
                //compress
            } else if (gamepad2.x) {

                horizontal.setPosition(0.40);

            }

            //Vertical Extension

            //Up
            if (gamepad2.dpad_up) {
                dSliderREncoder(0.4, 10.0, 2);
            }

            //down
            if (gamepad1.dpad_down) {
                dSliderREncoder(0.4, -2.0, 2);
            }


        }

    }


    public void dSliderREncoder(double speed, double inches, double timeoutS) {
        int newTarget;
        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newTarget = dSlideR.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);

            dSlideR.setTargetPosition(newTarget);

            // Turn On RUN_TO_POSITION
            dSlideR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            dSlideR.setPower(Math.abs(speed));

            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (dSlideR.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1", "Running to", newTarget);
                telemetry.addData("Path2", "Running at",
                        dSlideR.getCurrentPosition());
                telemetry.update();
            }


            // Stop all motion;
            dSlideR.setPower(0);

            // Turn off RUN_TO_POSITION
            dSlideR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }


    }

    public void dSliderLEncoder(double speed, double inches, double timeoutS) {

        int newTarget;
        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            if (inches < 0) {

                dSlideR.setDirection(DcMotor.Direction.REVERSE);

            }

            // Determine new target position, and pass to motor controller
            newTarget = dSlideR.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);

            dSlideR.setTargetPosition(newTarget);

            // Turn On RUN_TO_POSITION
            dSlideR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            dSlideR.setPower(Math.abs(speed));

            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (dSlideR.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1", "Running to", newTarget);
                telemetry.addData("Path2", "Running at",
                        dSlideR.getCurrentPosition());
                telemetry.update();
            }


            // Stop all motion;
            dSlideR.setPower(0);

            // Turn off RUN_TO_POSITION
            dSlideR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }

    }

}