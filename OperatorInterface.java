package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class OperatorInterface extends SubsystemBase {
    private static OperatorInterface instance = null;
    private final Joystick driverCTRL;
    private final Joystick operatorCTRL;
    //gives controllers names

    public OperatorInterface(){
        driverCTRL = new Joystick(0);
        operatorCTRL = new Joystick(1);
    }
    //driver

    @Override
    public void periodic(){
        Drivetrain Drive = Drivetrain.getInstance();
        double left = driverCTRL.getRawAxis(1);
        double right = driverCTRL.getRawAxis(5);
        double rightE = driverCTRL.getRawAxis(2);
        boolean pressJoyL = driverCTRL.getRawButton(10);
        boolean pressJoyR = driverCTRL.getRawButton(11);

        boolean pressSquare = driverCTRL.getRawButton(0);
        boolean pressX = driverCTRL.getRawButton(1);
        boolean pressCircle = driverCTRL.getRawButton(2);
        boolean pressTriangle = driverCTRL.getRawButton(3);
        boolean pressR1 = driverCTRL.getRawButton(5);
        boolean pressR2 = driverCTRL.getRawButton(7);
        boolean pressOptions = driverCTRL.getRawButton(8);
        boolean pressShare = driverCTRL.getRawButton(9);
        boolean pressL1 = driverCTRL.getRawButton(4);

        Drive.setMotor(left, right, rightE, pressJoyL, pressJoyR);
        (pressSquare, pressX, pressCircle, pressTriangle, pressR1, pressR2, pressOptions, pressShare, pressL1);
    }

    public static OperatorInterface getInstance(){
        if(instance == null) instance = new OperatorInterface();
        return instance;
    }
}
