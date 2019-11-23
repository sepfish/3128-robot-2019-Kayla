//AUTHORSES:
    //MASON HOLST
    //jude
    //caylin
    //daniel
    //tyler
    //teo


    //not adham :/

package org.team3128.button.main;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;;

import org.team3128.athos.autonomous.*;
import org.team3128.athos.util.PrebotDeepSpaceConstants;

import org.team3128.common.NarwhalRobot;
import org.team3128.common.drive.DriveCommandRunning;
import org.team3128.common.drive.SRXTankDrive;
import org.team3128.common.drive.SRXTankDrive.Wheelbase;
import org.team3128.common.drive.calibrationutility.DriveCalibrationUtility;
import org.team3128.common.hardware.limelight.Limelight;
import org.team3128.common.hardware.navigation.Gyro;
import org.team3128.common.hardware.navigation.NavX;
import org.team3128.common.util.Constants;
import org.team3128.common.util.units.Angle;
import org.team3128.common.util.units.Length;
import org.team3128.common.vision.CmdHorizontalOffsetFeedbackDrive;
import org.team3128.gromit.util.DeepSpaceConstants;
import org.team3128.common.util.Log;
import org.team3128.common.util.RobotMath;
import org.team3128.common.util.datatypes.PIDConstants;
import org.team3128.common.narwhaldashboard.NarwhalDashboard;
import org.team3128.common.listener.ListenerManager;
import org.team3128.common.listener.controllers.ControllerExtreme3D;
import org.team3128.common.listener.controltypes.Button;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//start typing the stuff to make this a robot that isn't non-functional and bad and blank and boring and stuff thanks lol
    // - Mason Holst, "Helpful Reminders", published November 2019

public class MainButton extends NarwhalRobot {
	
	public TalonSRX leftMotor; //motors (leaders)
	public TalonSRX rightMotor;
	public VictorSPX leftFollower; //followers
	public VictorSPX rightFollower;
	public SRXTankDrive drive; 
	public Joystick joystick;
	public ListenerManager lm; //listener thing
	private DriveCommandRunning driveCmdRunning; //its only purpose is to say if something is happening
	
	@Override
	protected void constructHardware() {
		leftMotor = new TalonSRX(13);
		rightMotor = new TalonSRX(15);
		leftFollower = new VictorSPX(5);
		rightFollower = new VictorSPX(6);
		joystick = new Joystick(1);
		lm = new ListenerManager (joystick);
		addListenerManager(lm);
		
		leftFollower.set(ControlMode.Follower, leftMotor.getDeviceID());
		rightFollower.set(ControlMode.Follower, rightMotor.getDeviceID());
		
		leftMotor.setInverted(false);
		leftFollower.setInverted(false);
		rightMotor.setInverted(true);
		rightFollower.setInverted(true);
		
		driveCmdRunning = new DriveCommandRunning();
		
		double wheelCirc = 13.21 * Length.in;
		double wheelBase = 32.3 * Length.in;
		int driveMaxSpeed = 3700;
		
		SRXTankDrive.initialize(leftMotor, rightMotor, wheelCirc, wheelBase, driveMaxSpeed); //you should probably look at this method in the editor
		drive = SRXTankDrive.getInstance();

    }
    
    @Override
    protected void constructAutoPrograms() {
	    //NarwhalDashboard.addAuto("Auto Test", new CMDAutoTest()); //this doesn't actually exist yet whoops
    }

	@Override
	protected void setupListeners() { //joystick does stuff
		lm.nameControl(ControllerExtreme3D.TWIST, "MoveTurn");
		lm.nameControl(ControllerExtreme3D.JOYY, "MoveForwards");
		lm.nameControl(ControllerExtreme3D.THROTTLE, "Throttle");

		lm.addMultiListener(() -> {
				    if (!driveCmdRunning.isRunning) { //??
					    drive.arcadeDrive( //update motor values
						    -0.7 * RobotMath.thresh(lm.getAxis("MoveTurn"), 0.1), //prevents teeny movements from doing stuff
						    -1.0 * RobotMath.thresh(lm.getAxis("MoveForwards"), 0.1),
						    -1.0 * lm.getAxis("Throttle"),
						    true);		
				    }
		}, "MoveTurn", "MoveForwards", "Throttle");
		
		/*left forward button example?
		lm.nameControl(new Button(#), "leftForward");
		lm.addButtonDownListener("leftForward", () -> {
			drive.tankDrive(1, 0);
		});
		lm.addButtonUpListener("leftForward", () -> {
			drive.tankDrive(0, 0);
		});
		*/
    }

    @Override
    protected void teleopPeriodic() {
	    //nothing
    }

    @Override
    protected void updateDashboard() {
	    //nothing

    }


    public static void main(String... args) {
        RobotBase.startRobot(MainButton::new);
    }
}
