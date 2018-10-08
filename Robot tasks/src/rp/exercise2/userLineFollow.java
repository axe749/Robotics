package rp.exercise2;

import java.util.ArrayList;
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

public class userLineFollow extends RobotProgrammingDemo {

	private DifferentialPilot m_pilot;

	private final LightSensor leftSensor;
	private final LightSensor rightSensor;
	private final LightSensor middleSensor;
	private ArrayList<Integer> store = new ArrayList<Integer>();
	private boolean escape = true;
	private int leftRight = 0;
	private int counter = 0;
	private int i;
	private int lSensor;
	private int rSensor;
	private int mSensor;
	private boolean escapeConfig = true;

	public userLineFollow(WheeledRobotConfiguration _config, LightSensor port1, LightSensor port2, LightSensor port3) {
		this.leftSensor = port1;
		this.rightSensor = port2;
		this.middleSensor = port3;
		m_pilot = new WheeledRobotSystem(_config).getPilot();
	}

	@Override
	public void run() {
		
		
		while(escapeConfig) {
			System.out.println("Calibrate sensors");
			lSensor = leftSensor.getLightValue() + 2;
			System.out.println("Left light value: " + lSensor);
			rSensor = rightSensor.getLightValue() + 2;
			System.out.println("Right light value: " + rSensor);
			mSensor = middleSensor.getLightValue() + 2;
			System.out.println("Centre light value: " + rSensor);
			System.out.println("Press any button to start program");
			Button.waitForAnyPress();
			escapeConfig = false;
		}
		
		
		while (escape) {
			
			leftRight = Button.waitForAnyPress();
			
			if (leftRight == 8 || leftRight == 1) {
				escape = false;
			}
			
			store.add(leftRight);
			System.out.println(store.get(counter));
			counter++;
		}
		
		Rate r = new Rate(20);
		while (m_run) {
			
			int leftSensLightVal = leftSensor.getLightValue();
			int middleSensLightVal = middleSensor.getLightValue();
			int rightSensLightVal = rightSensor.getLightValue();
		
			m_pilot.forward();
			r.sleep();
			m_pilot.setTravelSpeed(0.09);
			m_pilot.setRotateSpeed(0.09);
			if (((leftSensLightVal <= lSensor) && (rightSensLightVal <= rSensor))
					|| ((leftSensLightVal <= lSensor) && (middleSensLightVal <= mSensor))
					|| ((middleSensLightVal) <= mSensor && (rightSensLightVal <= rSensor))) {
				if (store.get(i) == 2) {
					m_pilot.stop();
					m_pilot.travel(0.05);
					m_pilot.steer(200, 90);
					Delay.msDelay(10);
						i++;
					
					System.out.println("big turn left");
				} else if (store.get(i) == 4) {
					m_pilot.stop();
					m_pilot.travel(0.05);
					m_pilot.steer(200, -90);
					Delay.msDelay(10);
						i++;
					
					System.out.println("big turn right");
				}
			} else if (leftSensLightVal <= lSensor) {
				if (!(middleSensLightVal <= mSensor)) {
					m_pilot.steer(200, 5);
				}
				rightSensLightVal = rightSensor.getLightValue();
				if (rightSensLightVal <= rSensor) {
					m_pilot.travel(0.05);
					if (store.get(i) == 2) {
						m_pilot.steer(200, 70);
						System.out.println("small turn left");
							i++;
						
					}
				}
			} else if (rightSensLightVal <= rSensor) {
				if (!(middleSensLightVal <= mSensor)) {
					m_pilot.steer(-200, -5);
				}
				leftSensLightVal = leftSensor.getLightValue();
				if (leftSensLightVal <= lSensor) {
					m_pilot.travel(0.05);
					if (store.get(i) == 4)
					m_pilot.steer(-200, -70);
					System.out.println("small turn right");
						i++;
					

				}

			}

		if(i >= counter -1)
			break;
		}

	}
	
	public static void main(String[] args) {
		Button.waitForAnyPress();
		LightSensor leftLightSensor = new LightSensor(SensorPort.S1);
		LightSensor rightLightSensor = new LightSensor(SensorPort.S2);
		LightSensor middleLightSensor = new LightSensor(SensorPort.S3);
		RobotProgrammingDemo demo = new userLineFollow(RobotConfigs.EXPRESS_BOT, leftLightSensor, rightLightSensor, middleLightSensor);
		demo.run();

	}

}
