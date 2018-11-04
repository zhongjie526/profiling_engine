package com.singtel;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.ExecStartResultCallback;

public class ExecuteShellCommand {

	public static void main(String[] args) throws InterruptedException {


		//cmd("/Users/frankzhang/dev/test.py");
		//cmd("docker exec hadoopContainer bash myscript.sh ");
		
		DockerClient dockerClient = DockerClientBuilder.getInstance().build();
		List<Container> containers = dockerClient.listContainersCmd().exec();
		
		containers.forEach(x->System.out.println(x.getNames()[0]+":"+x.getId()));
		
		ExecCreateCmdResponse execCreateCmdResponse = dockerClient.execCreateCmd("505ed208a9c1")
					.withAttachStdout(true)
					.withAttachStderr(true)
					.withCmd("pwd")
	        		.exec();

	     dockerClient.execStartCmd(execCreateCmdResponse.getId()).exec(
	                new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
		
	}

	private static void cmd(String command) {
		
		try {
			System.out.println("============================");
			
			ProcessBuilder proc = new ProcessBuilder(command.split(" "));
			System.out.println("Executing command: " + command);
			Process p = proc.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			
		} catch (Exception e) {
			System.out.println("Error executing "+command);
			e.printStackTrace();
		}
		
	}
	
	

}
