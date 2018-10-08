package rp.assignments.individual.ex2;

import lejos.robotics.RangeFinder;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import rp.config.RangeFinderDescription;
import rp.robotics.DifferentialDriveRobot;
import rp.systems.RobotProgrammingDemo;
import rp.systems.StoppableRunnable;
import rp.util.Rate;


public class Controller implements StoppableRunnable {

	private final DifferentialPilot pilot;
	private RangeFinderDescription desc;
	private RangeFinder ranger;
	private Float maxDistance;
	private boolean m_run = true;
	
	public Controller (DifferentialDriveRobot robot, RangeFinderDescription desc, RangeFinder ranger, Float maxDistance) {
		pilot = robot.getDifferentialPilot();
		this.desc = desc;
		this.ranger = ranger;
		this.maxDistance = maxDistance;
	}

	@Override
	public void run() {

		double minRange = desc.getMinRange();
		double maxRange = desc.getMaxRange();
		double maxSpeed = pilot.getMaxTravelSpeed();

		pilot.setTravelSpeed(maxSpeed);
		pilot.forward();
		while(m_run) {
			double distance = ranger.getRange();			
			
			if(distance >= maxDistance/2.0 + minRange)
				pilot.setTravelSpeed(maxSpeed);
			if(distance <= maxDistance/2.0 )
				pilot.setTravelSpeed(0.01);
			if(distance == minRange || distance == maxRange)
				pilot.stop();
			
		}
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

}
