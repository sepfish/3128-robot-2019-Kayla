package org.team3128.button.autonomous;

import org.team3128.common.util.enums.Direction;
import org.team3128.common.util.units.Length;

import org.team3128.common.drive.SRXTankDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class CmdAutoTest extends CommandGroup {
  public SRXTankDrive drive;
  public CmdAutoTest () {
    SRXTankDrive drive = SRXTankDrive.getInstance();
    
    //adds commands in a sequence. The large number at the end is the number of milliseconds it will wait to do the next command if it can't do that one
    addSequential(drive.new CmdDriveStraight(98 * Length.in, 0.5, 10000));
    addSequential(drive.new CmdInPlaceTurn(45, Direction.RIGHT, 0.5, 10000));
    addSequential(drive.new CmdDriveStraight(38 * Length.in, 0.5, 10000));
    addSequential(drive.new CmdInPlaceTurn(105, Direction.LEFT, 0.5, 10000));
    addSequential(drive.new CmdDriveStraight(58 * Length.in, 0.5, 10000));
    for (int i = 0; i < 15; i++) {
      addSequential(drive.new CmdDriveStraight(5 * Length.in, 0.5, 10000));
      addSequential(drive.new CmdInPlaceTurn(6, Direction.RIGHT, 0.5, 10000));
    }
    addSequential(drive.new CmdDriveStraight(57 * Length.in, 0.5, 10000));
    addSequential(drive.new CmdInPlaceTurn(45, Direction.RIGHT, 0.5, 10000));
    addSequential(drive.new CmdDriveStraight(27 * Length.in, 0.5, 10000));
    addSequential(drive.new CmdInPlaceTurn(90, Direction.RIGHT, 0.5, 10000));
    addSequential(drive.new CmdDriveStraight(21 * Length.in, 0.5, 10000));
    addSequential(drive.new CmdInPlaceTurn(90, Direction.LEFT, 0.5, 10000));
    addSequential(drive.new CmdDriveStraight(116 * Length.in, 0.5, 10000));
    
  }
}
