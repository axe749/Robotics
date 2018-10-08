package rp.exercise1;

import java.util.ArrayList;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import rp.config.RobotConfigs;
import rp.config.WheeledRobotConfiguration;
import rp.systems.RobotProgrammingDemo;
import rp.systems.WheeledRobotSystem;
import rp.util.Rate;

public class LineFollow extends RobotProgrammingDemo {
	
	// Black = l28 r32
	private DifferentialPilot m_pilot;
	private final LightSensor leftSensor;
	private final LightSensor rightSensor;
	private int lSensor;
	private int rSensor;
	private boolean escape = true;
	
	
	public LineFollow(WheeledRobotConfiguration _config, LightSensor port1, LightSensor port2) {
		this.leftSensor = port1;
		this.rightSensor = port2;
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
			System.out.println("Press any button to start program");
			Button.waitForAnyPress();
			escape = false;
		}
		
		
		
		
		m_pilot.forward();
		m_pilot.setTravelSpeed(0.2);
		Rate r = new Rate(20);
		
		int leftSensLightVal;
		int rightSensLightVal;
		
		while(m_run) {
			r.sleep();
			leftSensLightVal = leftSensor.getLightValue();
			rightSensLightVal = rightSensor.getLightValue();		
			
			
			if(leftSensLightVal <= lSensor) {
					m_pilot.steer(200, 10);
					rightSensLightVal = rightSensor.getLightValue();
					if(rightSensLightVal <= rSensor) {
						m_pilot.travel(0.05);
						m_pilot.steer(200, 70);
					}
			}
			if(rightSensLightVal <= rSensor) {
					m_pilot.steer(-200, -10);
					leftSensLightVal = leftSensor.getLightValue();
					if(leftSensLightVal <= lSensor) {
						m_pilot.travel(0.05);
						m_pilot.steer(-200, -70);
					}
			}
			
			m_pilot.forward();
			
		}
		
	}
	
	public static void main(String[] args) {
		Button.waitForAnyPress();
		LightSensor leftLightSensor = new LightSensor(SensorPort.S1);
		LightSensor rightLightSensor = new LightSensor(SensorPort.S2);
		RobotProgrammingDemo demo = new  LineFollow(RobotConfigs.EXPRESS_BOT, leftLightSensor, rightLightSensor);
		demo.run();
	}

}
