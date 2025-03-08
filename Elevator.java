package frc.robot;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase{
    private static Elevator instance = null;
    private final SparkMax driveMotorL;
    private final SparkMax driveMotorR;
    private final SparkMax wristMotor;
    private final SparkMax intakeMotor;
    
    
    private Elevator() {
        driveMotorL = new SparkMax(5, MotorType.kBrushless);
        driveMotorR = new SparkMax(6, MotorType.kBrushless);
        wristMotor = new SparkMax(7, MotorType.kBrushless);
        intakeMotor = new SparkMax(8, MotorType.kBrushless);
    }    

    private void periodic() {
    }

    public void simulationPeriodic() {
    }

    private void moveUp() {
    }

    private void moveDown () {
    }

    @Override
    private void stop() {
    }

    private double getHeight() {
    }
    
    public static Elevator getInstance() {
        if(instance == null) 
            instance = new Elevator();
        return instance;
    }
}

public class ElevatorTarget () extends Command{
    public elevatorTarget {
    }

    @Override
    public boolean checkHeight (int Target) {
        switch {
            case(0):
                
            case(1):
            
            case(2):
                    
            case(3):
                    
            case(4):        
        }
    }

    @Override
    public void end(boolean interrupted) {
    }
}
