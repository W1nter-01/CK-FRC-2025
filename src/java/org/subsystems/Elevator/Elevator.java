// package frc.robot;

// import com.revrobotics.RelativeEncoder;
// import com.revrobotics.spark.SparkFlex;
// import com.revrobotics.spark.SparkLimitSwitch;
// import com.revrobotics.spark.SparkMax;
// import com.revrobotics.spark.SparkLowLevel.MotorType;

// import edu.wpi.first.math.controller.ElevatorFeedforward;
// import edu.wpi.first.math.controller.PIDController;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;

// public class Elevator extends SubsystemBase{
//     private static Elevator instance = null;
//     private final SparkFlex driveMotorL;
//     private final SparkFlex driveMotorR;
//     private final SparkFlex wristMotor;
//     private final SparkFlex intakeMotor;

    
//     private RelativeEncoder ElevatorDriveEncoder;
//     private SparkLimitSwitch bottomElevator;
    
//     private PIDController elevatorPID;
//     private ElevatorFeedforward elevatorFeedforward;
    
//     private boolean controllingElevator = false;
//     private double elevatorTargetPosition;

//     private Elevator() {
//         driveMotorL = new SparkFlex(5, MotorType.kBrushless);
//         driveMotorR = new SparkFlex(6, MotorType.kBrushless);
//         wristMotor = new SparkFlex(7, MotorType.kBrushless);
//         intakeMotor = new SparkFlex(8, MotorType.kBrushless);

//         ElevatorDriveEncoder = driveMotorL.getEncoder();
//         bottomElevator = driveMotorL.getReverseLimitSwitch();

//         elevatorPID = new PIDController(0, 0, 0);
//         elevatorFeedforward = new ElevatorFeedforward(0.22643, 4.4162, 0.32248);
//     }    

//     private void setElevatorPos(double targetPosition){
//         double currentPosition = ElevatorDriveEncoder.getPosition();
//         double maxPosRate = 1.0;
//         double posError = targetPosition - currentPosition;

//         double targetSpeed = maxPosRate * (posError > 0 ? 1 : -1);
//         double rampDownSpeed = posError * maxPosRate; //MAY NEED TO ADD RAMP DOWN DISTANCE BY DIVIDING POS ERROR BY A CONSTANT
        
//         if (Math.abs(rampDownSpeed) < Math.abs(targetSpeed)){
//             targetSpeed = rampDownSpeed;
//         }
//         setElevatorRate(targetSpeed);
//     }

//     private void setElevatorRate(double targetSpeed){
//         double rate = ElevatorDriveEncoder.getVelocity();
//         double calcPID = elevatorPID.calculate(rate, targetSpeed);
//         double calcFeedForward = elevatorFeedforward.calculate(targetSpeed);

//         double result = calcPID + calcFeedForward;
//         setElevatorMotor(result);
//     }

    
//     public void setElevatorMotor(double result){
//         //if (bottomElevator.isPressed() && result < 0) {
//         //    result = 0;
//         //}
//         driveMotorL.setVoltage(result);
//         driveMotorR.setVoltage(result);
//     }

//     @Override
//     public void periodic() {
//         if (controllingElevator){
//             setElevatorPos(elevatorTargetPosition);
//         }
//     }

//     public void setEnabledElevator(boolean enabled){
//         controllingElevator = enabled;
//     }
    
//     public boolean getEnabledElevator(){
//         return controllingElevator;
//     }

//     public void setElevatorTargetFromInput(double target){
//         elevatorTargetPosition = target;

//     }

//     @Override
//     public void simulationPeriodic() {
//     }

    
//     public void moveUp() {
//     }

    
//     public void moveDown () {
//     }

    
//     public void stop() {
//     }

    
//     // public double getHeight() {
//     // }
    
//     public static Elevator getInstance() {
//         if(instance == null) 
//             instance = new Elevator();
//         return instance;
//     }
// }


package frc.robot;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
    
    // Singleton instance
    private static Elevator instance = null;

    // Motor and sensor declarations
    private final SparkFlex driveMotorL;
    private final SparkFlex driveMotorR;
    private final SparkFlex wristMotor;
    private final SparkFlex intakeMotor;

    private final RelativeEncoder elevatorEncoder;
    private final SparkLimitSwitch bottomElevator;

    // PID and Feedforward control
    private final PIDController elevatorPID;
    private final ElevatorFeedforward elevatorFeedforward;

    private boolean controllingElevator = false;
    private double elevatorTargetPosition;

    // =========================
    // Constructor and Initialization
    // =========================
    private Elevator() {
        // Initialize motors
        driveMotorL = new SparkFlex(5, MotorType.kBrushless);
        driveMotorR = new SparkFlex(6, MotorType.kBrushless);
        wristMotor = new SparkFlex(7, MotorType.kBrushless);
        intakeMotor = new SparkFlex(8, MotorType.kBrushless);

        // Encoder and limit switch
        elevatorEncoder = driveMotorL.getEncoder();
        bottomElevator = driveMotorL.getReverseLimitSwitch();

        // PID and Feedforward configuration
        elevatorPID = new PIDController(0.1, 0, 0);  // Use non-zero P-value
        elevatorFeedforward = new ElevatorFeedforward(0.22643, 4.4162, 0.32248);
    }

    // =========================
    // Singleton Instance
    // =========================
    public static Elevator getInstance() {
        if (instance == null) {
            instance = new Elevator();
        }
        return instance;
    }

    // =========================
    // Motor Control Methods
    // =========================

    /**
     * Sets the elevator motors to a target speed with limit switch protection.
     * @param speed Voltage to apply to the motors.
     */
    public void setElevatorMotor(double speed) {
        // Prevent moving downward if bottom limit switch is pressed
        if (bottomElevator.isPressed() && speed < 0) {
            speed = 0;
        }
        driveMotorL.setVoltage(speed);
        driveMotorR.setVoltage(speed);
    }

    /**
     * Sets the elevator rate with PID and Feedforward control.
     * @param targetSpeed Desired speed (voltage).
     */
    private void setElevatorRate(double targetSpeed) {
        double currentRate = elevatorEncoder.getVelocity();
        double pidOutput = elevatorPID.calculate(currentRate, targetSpeed);
        double feedForward = elevatorFeedforward.calculate(targetSpeed);

        double motorOutput = pidOutput + feedForward;
        setElevatorMotor(motorOutput);
    }

    /**
     * Moves the elevator to the target position.
     * @param targetPosition Target position in encoder units.
     */
    private void setElevatorPos(double targetPosition) {
        double currentPosition = elevatorEncoder.getPosition();
        double maxRate = 1.0;

        // Calculate position error and target speed
        double posError = targetPosition - currentPosition;
        double targetSpeed = maxRate * Math.signum(posError);
        double rampDownSpeed = posError * maxRate;  // Slow down near target

        // Gradually slow down as it approaches the target
        if (Math.abs(rampDownSpeed) < Math.abs(targetSpeed)) {
            targetSpeed = rampDownSpeed;
        }

        setElevatorRate(targetSpeed);
    }

    // =========================
    // Public Control Methods
    // =========================

    /**
     * Enables or disables elevator control.
     */
    public void setEnabledElevator(boolean enabled) {
        controllingElevator = enabled;
    }

    /**
     * Checks if the elevator is currently being controlled.
     */
    public boolean getEnabledElevator() {
        return controllingElevator;
    }

    /**
     * Sets the elevator target position from external input.
     */
    public void setElevatorTargetFromInput(double target) {
        elevatorTargetPosition = target;
    }

    // =========================
    // Movement Control Methods
    // =========================

    /**
     * Moves the elevator up.
     */
    public void moveUp() {
        setElevatorTargetFromInput(elevatorEncoder.getPosition() + 5);
        setEnabledElevator(true);
    }

    /**
     * Moves the elevator down.
     */
    public void moveDown() {
        setElevatorTargetFromInput(elevatorEncoder.getPosition() - 5);
        setEnabledElevator(true);
    }

    /**
     * Stops the elevator by disabling control.
     */
    public void stop() {
        setEnabledElevator(false);
        setElevatorMotor(0);
    }

    /**
     * Returns the current elevator height.
     * @return Current height in encoder units.
     */
    public double getHeight() {
        return elevatorEncoder.getPosition();
    }

    // =========================
    // Periodic Methods
    // =========================

    @Override
    public void periodic() {
        if (controllingElevator) {
            setElevatorPos(elevatorTargetPosition);
        }
    }

    @Override
    public void simulationPeriodic() {
        // Simulation-specific periodic code
    }
}
