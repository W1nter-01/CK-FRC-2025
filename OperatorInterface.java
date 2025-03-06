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
        double rightE = driverCTRL.getRawAxis(4);
        boolean pressJoyL = driverCTRL.getRawButton(8);
        boolean pressJoyR = driverCTRL.getRawButton(9);
        Drive.setMotor(left, right, rightE, pressJoyL, pressJoyR);
        boolean xButton = driverCTRL.getRawButton(0);
        
    }

    public static OperatorInterface getInstance(){
        if(instance == null) instance = new OperatorInterface();
        return instance;
    }
}
