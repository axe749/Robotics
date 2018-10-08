package rp.exercise3;

import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.NXTCam;
import lejos.robotics.navigation.DifferentialPilot;
import rp.config.RobotConfigs;
import rp.config.WheeledRobotConfiguration;
import rp.systems.RobotProgrammingDemo;
import rp.systems.WheeledRobotSystem;
import rp.util.Rate;

public class LightFollow extends RobotProgrammingDemo {
	
	// middle x = 105
	private DifferentialPilot m_pilot;
	private final NXTCam camera;
	

	public LightFollow(WheeledRobotConfiguration _config, NXTCam port4) {
		this.camera = port4;
		m_pilot = new WheeledRobotSystem(_config).getPilot();

	}

	@Override
	public void run() {
		
		this.camera.sortBy(NXTCam.SIZE);
		this.camera.enableTracking(true);
		
		double centre = 105;
		double xPosition;
		
		m_pilot.forward();
		m_pilot.setTravelSpeed(0.2);

		Rate r = new Rate(20);
		while (m_run) {
			
			r.sleep();
			
			
			
			if (this.camera.getNumberOfObjects() > 0) {
				m_pilot.setTravelSpeed(0.2);
				xPosition = this.camera.getRectangle(0).getX();
			}
			else {
				m_pilot.setTravelSpeed(0);
				xPosition = centre;
			}
			
			
			
			if (xPosition < (centre - 10)) {
				m_pilot.steer(50);
			}
			else if (xPosition > (centre + 10)) {
				m_pilot.steer(-50);
			}
			else {
				m_pilot.forward();
			}

		}

	}
	

	public static void main(String[] args) {
		Button.waitForAnyPress();
		NXTCam camera = new NXTCam(SensorPort.S1);
		RobotProgrammingDemo demo = new LightFollow(RobotConfigs.EXPRESS_BOT, camera);
		demo.run();
	}

}