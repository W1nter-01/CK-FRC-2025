package frc.robot;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase{
    private static Drivetrain instance = null;

    private final SparkMax leftMotor1;
    private final SparkMax leftMotor2;
    private final SparkMax rightMotor3;
    private final SparkMax rightMotor4;
    public double speed = 1;

    // DRIVE MODE (Tank or Evan)
    private String driveMode = "Evan";

    private Drivetrain(){
        leftMotor1 = new SparkMax(1, MotorType.kBrushed);
        leftMotor2 = new SparkMax(2, MotorType.kBrushed);
        rightMotor3 = new SparkMax(3, MotorType.kBrushed);
        rightMotor4 = new SparkMax(4, MotorType.kBrushed);
    }




    public void setMotor(double leftJoyY, double rightJoyY, double rightJoyX, boolean BTN_joyL, boolean BTN_joyR){

        if(driveMode == "Evan"){
            if (BTN_joyL){
                speed = 1;
            }else{
                speed = 0.5;
            }
            leftMotor1.set((leftJoyY - rightJoyX)*speed);
            leftMotor2.set((leftJoyY - rightJoyX)*speed);
            rightMotor3.set((-leftJoyY - rightJoyX)*speed);
            rightMotor4.set((-leftJoyY - rightJoyX)*speed);

        }else{
            if (BTN_joyL || BTN_joyR){
                speed = 1;
            }else{
                speed = 0.5;
            }
            leftMotor1.set((leftJoyY)*speed);
            leftMotor2.set((leftJoyY)*speed);
            rightMotor3.set((-rightJoyY)*speed);
            rightMotor4.set((-rightJoyY)*speed);
            //*Math.abs(leftJoyY)
            //*Math.abs(right)
        }

    }

    public static Drivetrain getInstance() {
        if(instance == null) 
            instance = new Drivetrain();
        return instance;
    }
}
