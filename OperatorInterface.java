// package frc.robot;

// import edu.wpi.first.wpilibj.Joystick;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;

// public class OperatorInterface extends SubsystemBase {
//     private static OperatorInterface instance = null;
//     private final Joystick driverCTRL;
//     private final Joystick operatorCTRL;
//     //gives controllers names

//     public OperatorInterface(){
//         driverCTRL = new Joystick(0);
//         operatorCTRL = new Joystick(1);
//     }
//     //driver

//     @Override
//     public void periodic(){
//         Drivetrain Drive = Drivetrain.getInstance();
//         Elevator elevator = Elevator.getInstance();
//         double left = driverCTRL.getRawAxis(1);
//         double right = driverCTRL.getRawAxis(5);
//         double rightE = driverCTRL.getRawAxis(2);
//         boolean pressJoyL = driverCTRL.getRawButton(10);
//         boolean pressJoyR = driverCTRL.getRawButton(11);

//         boolean pressSquare = driverCTRL.getRawButton(0);
//         boolean pressX = driverCTRL.getRawButton(1);
//         boolean pressCircle = driverCTRL.getRawButton(2);
//         boolean pressTriangle = driverCTRL.getRawButton(3);
//         boolean pressR1 = driverCTRL.getRawButton(5);
//         boolean pressR2 = driverCTRL.getRawButton(7);
//         boolean pressOptions = driverCTRL.getRawButton(8);
//         boolean pressShare = driverCTRL.getRawButton(9);
//         boolean pressL1 = driverCTRL.getRawButton(4);
        
//         //inputs for elevator heights
//         if(pressSquare){
//             elevator.setElevatorTargetFromInput(2);
//             elevator.setEnabledElevator(true);
//         }else if(pressX){
//             elevator.setElevatorTargetFromInput(5);
//             elevator.setEnabledElevator(true);
//         }

//         //inputs for manual moving elevator
//         if(pressOptions){
//             elevator.setEnabledElevator(false);
//             elevator.setElevatorMotor(2);
//         } else if(pressShare) {
//             elevator.setEnabledElevator(false);
//             elevator.setElevatorMotor(-2);
//         }else if(!elevator.getEnabledElevator()){
//             elevator.setElevatorMotor(0);
//         }

//         Drive.setMotor(left, right, rightE, pressJoyL, pressJoyR);
//         //(pressSquare, pressX, pressCircle, pressTriangle, pressR1, pressR2, pressOptions, pressShare, pressL1);
//     }

//     public static OperatorInterface getInstance(){
//         if(instance == null) instance = new OperatorInterface();
//         return instance;
//     }
// }

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class OperatorInterface extends SubsystemBase {

    // Singleton instance
    private static OperatorInterface instance = null;

    // Joystick declarations
    private final Joystick driverCTRL;
    private final Joystick operatorCTRL;

    // =========================
    // Constructor
    // =========================
    public OperatorInterface() {
        driverCTRL = new Joystick(0);
        operatorCTRL = new Joystick(1);
    }

    // =========================
    // Periodic Method
    // =========================
    @Override
    public void periodic() {
        Drivetrain drive = Drivetrain.getInstance();
        Elevator elevator = Elevator.getInstance();

        //double leftJoyX = driverCTRL.getRawAxis(0);
        //double leftJoyX = driverCTRL.getRawAxis(0);
        double leftJoyY = driverCTRL.getRawAxis(1);
        double rightJoyX = driverCTRL.getRawAxis(2);
        //double L2 = driverCTRL.getRawAxis(3);
        //double R2 = driverCTRL.getRawAxis(4);
        double rightJoyY = driverCTRL.getRawAxis(5);
        
        int pov = driverCTRL.getPOV();

        boolean BTN_square = driverCTRL.getRawButton(0);
        boolean BTN_x = driverCTRL.getRawButton(1);
        boolean BTN_circle = driverCTRL.getRawButton(2);
        boolean BTN_triangle = driverCTRL.getRawButton(3);
        boolean BTN_L1 = driverCTRL.getRawButton(4);
        boolean BTN_R1 = driverCTRL.getRawButton(5);
        boolean BTN_L2 = driverCTRL.getRawButton(6);
        boolean BTN_R2 = driverCTRL.getRawButton(7);
        boolean BTN_share = driverCTRL.getRawButton(8);
        boolean BTN_options = driverCTRL.getRawButton(9);
        boolean BTN_joyL = driverCTRL.getRawButton(10);
        boolean BTN_joyR = driverCTRL.getRawButton(11);
        boolean BTN_PS4 = driverCTRL.getRawButton(12);
        boolean BTN_dPad = driverCTRL.getRawButton(13);

        // =========================
        // Elevator Height Control
        // =========================
        if (BTN_square) {
            elevator.setElevatorTargetFromInput(2);
            elevator.setEnabledElevator(true);
        } else if (BTN_x) {
            elevator.setElevatorTargetFromInput(5);
            elevator.setEnabledElevator(true);
        }

        // =========================
        // Manual Elevator Control
        // =========================
        if (BTN_options) {
            elevator.setEnabledElevator(false);
            elevator.setElevatorMotor(2);
        } else if (BTN_share) {
            elevator.setEnabledElevator(false);
            elevator.setElevatorMotor(-2);
        } else if (!elevator.getEnabledElevator()) {
            elevator.setElevatorMotor(0);
        }

        // =========================
        // Drivetrain Control
        // =========================
        drive.setMotor(leftJoyY, rightJoyY, rightJoyX, BTN_joyL, BTN_joyR);
    }

    // =========================
    // Singleton Instance Method
    // =========================
    public static OperatorInterface getInstance() {
        if (instance == null) {
            instance = new OperatorInterface();
        }
        return instance;
    }
}
