// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc6527.ShortSirKitTest;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc6527.ShortSirKitTest.commands.*;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.PIDController;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    Command autonomousCommand;

    public static OI oi;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    
    private XboxController xboxController;
    private Encoder encL;
    private Encoder encR;
    private VictorSP victorSPL;
    private VictorSP victorSPR;
    private PIDController pidL;
    private PIDController pidR;
    private boolean toggle;
    private boolean reverse;
    
    
    public void robotInit() {
    RobotMap.init();
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        /* OI must be constructed after subsystems. If the OI creates Commands
        * (which it very likely will), subsystems are not guaranteed to be
        * constructed yet. Thus, their requires() statements may grab null
        * pointers. Bad news. Don't move it.
        */
        oi = new OI();

        // instantiate the command used for the autonomous period
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

        autonomousCommand = new AutonomousCommand();

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
        this.xboxController = new XboxController(0);
        this.victorSPL = new VictorSP(1);
        this.victorSPR = new VictorSP(0);
        this.encL = new Encoder(0,1);
        this.encR = new Encoder(2,3);
        this.pidL = new PIDController(0.001, 0.1, 0, encL, victorSPL);
        this.pidR = new PIDController(0.001, 0.1, 0, encR, victorSPR);
        CameraServer.getInstance().startAutomaticCapture(0);
        CameraServer.getInstance().startAutomaticCapture(1);
        
        
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){

    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
        
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        reverse = false;
        toggle = true;
        
        
    }

    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        double yAxis = xboxController.getRawAxis(1);
        double xAxis = xboxController.getRawAxis(0);
        if (yAxis < 0.15 && yAxis > -0.15) yAxis = 0;
        if (xAxis < 0.15 && xAxis > -0.15) xAxis = 0;
        
        
        if (toggle && xboxController.getRawButton(8)) {
        	toggle = false;
        	if (reverse) reverse = true;
        	else reverse = false;
        } else if(xboxController.getRawButton(8) == false) toggle = true;
        if (reverse) yAxis *= -1;
        //if (xboxController.getRawButton(8)) yAxis *= -1;
        
        
        int pov = xboxController.getPOV();
        if (-1 != pov) pov /= 45;
        if (!(xboxController.getRawButton(5) || xboxController.getRawButton(6) || -1 != pov)) {
        	pidL.setSetpoint((yAxis - xAxis) / 2 / 3);
        	pidR.setSetpoint((yAxis + xAxis) / 2 / 3);
        } else if (xboxController.getRawButton(6)) {
        	pidL.setSetpoint(-0.5);
        	pidR.setSetpoint(-0.5);
        }else if (xboxController.getRawButton(5)) {
        	pidL.setSetpoint((yAxis - xAxis) / 2);
        	pidR.setSetpoint((yAxis + xAxis) / 2);
        }else if (-1 != pov){
        	switch (pov) {
	        	case 0:	pidL.setSetpoint(-((double)1/(double)6));
	        			pidR.setSetpoint(-((double)1/(double)6));
	        	break;
	        	case 1:	pidL.setSetpoint(-((double)1/(double)6));
	        			pidR.setSetpoint(0);
    			break;
	        	case 2:	pidL.setSetpoint(-((double)1/(double)6));
	        			pidR.setSetpoint(((double)1/(double)6));
    			break;
	        	case 3:	pidL.setSetpoint(((double)1/(double)6));
	        			pidR.setSetpoint(0);
    			break;
	        	case 4:	pidL.setSetpoint(((double)1/(double)6));
	        			pidR.setSetpoint(((double)1/(double)6));
		    	break;
		    	case 5:	pidL.setSetpoint(0);
		    			pidR.setSetpoint(((double)1/(double)6));
				break;
		    	case 6:	pidL.setSetpoint(((double)1/(double)6));
						victorSPR.set(-((double)1/(double)6));
				break;
		    	case 7:	pidL.setSetpoint(0);
						victorSPR.set(-((double)1/(double)6));
				break;
        }
        }
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
