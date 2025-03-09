package frc.robot;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase{
    private static Elevator instance = null;
    private final SparkFlex driveMotorL;
    private final SparkFlex driveMotorR;
    private final SparkFlex wristMotor;
    private final SparkFlex intakeMotor;

    private RelativeEncoder ElevatorDriveEncoder;
    private SparkLimitSwitch bottomElevator;
    
    private PIDController elevatorPID;
    private ElevatorFeedforward elevatorFeedforward;
    
    private boolean controllingElevator = false;
    private double elevatorTargetPosition;

    private Elevator() {
        driveMotorL = new SparkFlex(5, MotorType.kBrushless);
        driveMotorR = new SparkFlex(6, MotorType.kBrushless);
        wristMotor = new SparkFlex(7, MotorType.kBrushless);
        intakeMotor = new SparkFlex(8, MotorType.kBrushless);

        ElevatorDriveEncoder = driveMotorL.getEncoder();
        bottomElevator = driveMotorL.getReverseLimitSwitch();

        elevatorPID = new PIDController(0, 0, 0);
        elevatorFeedforward = new ElevatorFeedforward(0.22643, 4.4162, 0.32248);
    }    

    private void setElevatorPos(double targetPosition){
        double currentPosition = ElevatorDriveEncoder.getPosition();
        double maxPosRate = 1.0;
        double posError = targetPosition - currentPosition;

        double targetSpeed = maxPosRate * (posError > 0 ? 1 : -1);
        double rampDownSpeed = posError * maxPosRate; //MAY NEED TO ADD RAMP DOWN DISTANCE BY DIVIDING POS ERROR BY A CONSTANT
        
        if (Math.abs(rampDownSpeed) < Math.abs(targetSpeed)){
            targetSpeed = rampDownSpeed;
        }
        setElevatorRate(targetSpeed);
    }

    private void setElevatorRate(double targetSpeed){
        double rate = ElevatorDriveEncoder.getVelocity();
        double calcPID = elevatorPID.calculate(rate, targetSpeed);
        double calcFeedForward = elevatorFeedforward.calculate(targetSpeed);

        double result = calcPID + calcFeedForward;
        setElevatorMotor(result);
    }

    
    public void setElevatorMotor(double result){
        //if (bottomElevator.isPressed() && result < 0) {
        //    result = 0;
        //}
        driveMotorL.setVoltage(result);
        driveMotorR.setVoltage(result);
    }

    @Override
    public void periodic() {
        if (controllingElevator){
            setElevatorPos(elevatorTargetPosition);
        }
    }

    public void setEnabledElevator(boolean enabled){
        controllingElevator = enabled;
    }
    
    public boolean getEnabledElevator(){
        return controllingElevator;
    }

    public void setElevatorTargetFromInput(double target){
        elevatorTargetPosition = target;

    }

    @Override
    public void simulationPeriodic() {
    }

    
    public void moveUp() {
    }

    
    public void moveDown () {
    }

    
    public void stop() {
    }

    
    // public double getHeight() {
    // }
    
    public static Elevator getInstance() {
        if(instance == null) 
            instance = new Elevator();
        return instance;
    }
}
