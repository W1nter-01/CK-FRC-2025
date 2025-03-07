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




    public void setMotor(double left, double right, double rightE, boolean pressJoyL, boolean pressJoyR){

        if(driveMode == "Evan"){
            if (pressJoyL){
                speed = 1;
            }else{
                speed = 0.5;
            }
            leftMotor1.set((left - rightE)*speed);
            leftMotor2.set((left - rightE)*speed);
            rightMotor3.set((-left - rightE)*speed);
            rightMotor4.set((-left - rightE)*speed);

        }else{
            if (pressJoyL || pressJoyR){
                speed = 1;
            }else{
                speed = 0.5;
            }
            leftMotor1.set((left)*speed);
            leftMotor2.set((left)*speed);
            rightMotor3.set((-right)*speed);
            rightMotor4.set((-right)*speed);
            //*Math.abs(left)
            //*Math.abs(right)
        }

    }

    public static Drivetrain getInstance() {
        if(instance == null) 
            instance = new Drivetrain();
        return instance;
    }
}
