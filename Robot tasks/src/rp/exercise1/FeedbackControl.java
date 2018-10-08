package rp.exercise1;

import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import rp.config.RobotConfigs;
import rp.config.WheeledRobotConfiguration;
import rp.systems.RobotProgrammingDemo;
import rp.systems.WheeledRobotSystem;
import rp.util.Rate;

public class FeedbackControl extends RobotProgrammingDemo {
	private DifferentialPilot m_pilot;
	private final UltrasonicSensor port;
	
	
	public FeedbackControl(WheeledRobotConfiguration _config, UltrasonicSensor port ){
		this.port = port;
		m_pilot = new WheeledRobotSystem(_config).getPilot();
		
	}
	
	@Override
	public void run() {
		
		this.port.continuous();
		Float maxDistance = 255f;
		Float setPoint = 20f;
		
		Rate r = new Rate(20);
		while(m_run){
			m_pilot.forward();
			r.sleep();
			int distance = port.getDistance();
			Float distanceRatio = (0.3f / maxDistance) * distance;
			
			
			if(distance > setPoint && m_pilot.getTravelSpeed() < 0.3){
				m_pilot.setTravelSpeed(m_pilot.getTravelSpeed() + distanceRatio);
				
			}
			
			if(distance < setPoint && m_pilot.getTravelSpeed() > 0){
				m_pilot.setTravelSpeed(m_pilot.getTravelSpeed() - distanceRatio);
			}
		}
		
		
	}
	
	public static void main(String[] args) {
		Button.waitForAnyPress();
		UltrasonicSensor sensor = new UltrasonicSensor(SensorPort.S4);
		RobotProgrammingDemo demo = new  FeedbackControl(RobotConfigs.EXPRESS_BOT,sensor );
		demo.run();

	}

	

}
