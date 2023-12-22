package example.controller;

import java.util.Scanner;

public abstract class Controller {
	
	public Scanner sc;

	public String cmd;
	
	public abstract void doAction(String cmd, String methodName);
}
