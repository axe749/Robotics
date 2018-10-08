package rp.exercise2;

import java.util.Random;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import rp.config.RobotConfigs;
import rp.config.WheeledRobotConfiguration;
import rp.systems.RobotProgrammingDemo;
import rp.systems.WheeledRobotSystem;
import rp.util.Rate;

public class gridLineFollow extends RobotProgrammingDemo {

	// Black = l28 r32 c28
	private DifferentialPilot m_pilot;
	private final LightSensor leftSensor;
	private final LightSensor rightSensor;
	private final LightSensor centreSensor;
	private int lSensor;
	private int rSensor;
	private int cSensor;
	private boolean escape = true;

	public gridLineFollow(WheeledRobotConfiguration _config, LightSensor port1, LightSensor port2, LightSensor port3) {
		this.leftSensor = port1;
		this.rightSensor = port2;
		this.centreSensor = port3;
		m_pilot = new WheeledRobotSystem(_config).getPilot();

	}

	@Override
	public void run() {
		
		while(escape) {
			System.out.println("Calibrate sensors");
			lSensor = leftSensor.getLightValue() + 2;
			System.out.println("Left light value: " + lSensor);
			rSensor = rightSensor.getLightValue() + 2;
			System.out.println("Right light value: " + rSensor);
			cSensor = centreSensor.getLightValue() + 2;
			System.out.println("Centre light value: " + rSensor);
			System.out.println("Press any button to start program");
			Button.waitForAnyPress();
			escape = false;
		}
		

		Rate r = new Rate(20);
		while (m_run) {
			int leftSensLightVal = leftSensor.getLightValue();
			int rightSensLightVal = rightSensor.getLightValue();
			int centreSensLightVal = centreSensor.getLightValue();
			Random random = new Random();
			int[] direction = { 93, -93};

			m_pilot.forward();

			r.sleep();
			m_pilot.setTravelSpeed(0.05);

			if (((leftSensLightVal <= lSensor) && (rightSensLightVal <= rSensor)) || ((leftSensLightVal <= lSensor) && (centreSensLightVal <= cSensor)) || ((centreSensLightVal <= cSensor) && (rightSensLightVal <= rSensor))) {
				int directionChosen = (random.nextInt(direction.length));
				m_pilot.stop();
				m_pilot.travel(0.05);
				m_pilot.rotate(direction[directionChosen]);
			}
			else if (leftSensLightVal <= lSensor) {
				m_pilot.steer(200, 10);
				rightSensLightVal = rightSensor.getLightValue();
				if (rightSensLightVal <= rSensor) {
					m_pilot.travel(0.05);
					m_pilot.steer(200, 70);
				}
			}
			else if (rightSensLightVal <= rSensor) {
				m_pilot.steer(-200, -10);
				leftSensLightVal = leftSensor.getLightValue();
				if (leftSensLightVal <= lSensor) {
					m_pilot.travel(0.05);
					m_pilot.steer(-200, -70);
				}
			}

		}

	}

	public static void main(String[] args) {
		Button.waitForAnyPress();
		LightSensor leftLightSensor = new LightSensor(SensorPort.S1);
		LightSensor rightLightSensor = new LightSensor(SensorPort.S2);
		LightSensor centreLightSensor = new LightSensor(SensorPort.S3);
		RobotProgrammingDemo demo = new gridLineFollow(RobotConfigs.EXPRESS_BOT, leftLightSensor, rightLightSensor, centreLightSensor);
		demo.run();
	}

}
