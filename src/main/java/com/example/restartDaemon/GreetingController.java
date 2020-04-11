package com.example.restartDaemon;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	//private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/")
	public ServiceStatus serviceTest(@RequestParam(value = "name", defaultValue = "0") String name,
			@RequestParam(value = "action", defaultValue = "0") String action) {
		String result = "name: " + name + " action: " + action;
		return new ServiceStatus(counter.incrementAndGet(), name, action, result);
	}

	@GetMapping("/killProcessMS")
	public ServiceStatus killProcessByIdMS(@RequestParam(value = "pid", defaultValue = "0") String pid) {

		String result = "";
		String command = "TASKKILL /PID " + pid;

		try {
			Process process = Runtime.getRuntime().exec(command);
			InputStream inputstream = process.getInputStream();
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inputstream);

			byte[] contents = new byte[1024];
			int bytesRead = 0;

			while ((bytesRead = bufferedInputStream.read(contents)) != -1) {
				result += new String(contents, 0, bytesRead);
			}

			return new ServiceStatus(counter.incrementAndGet(), "WindowsSO", command, result);
		} catch (Exception e) {
			// TODO: handle exception
			return new ServiceStatus(counter.incrementAndGet(), "error", "error", "error");
		}

	}

	@GetMapping("/killProcessUnix")
	public ServiceStatus killProcessByIdUnix(@RequestParam(value = "pid", defaultValue = "0") String pid) {

		String result = "";
		String command = "kill -9 " + pid;

		try {
			Process process = Runtime.getRuntime().exec(command);
			InputStream inputstream = process.getInputStream();
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inputstream);

			byte[] contents = new byte[1024];
			int bytesRead = 0;

			while ((bytesRead = bufferedInputStream.read(contents)) != -1) {
				result += new String(contents, 0, bytesRead);
			}

			return new ServiceStatus(counter.incrementAndGet(), "UnixSO", command, result);
		} catch (Exception e) {
			// TODO: handle exception
			return new ServiceStatus(counter.incrementAndGet(), "error", "error", "error");
		}

	}

	@GetMapping("/restartDaemon")
	public ServiceStatus restartDaemon(@RequestParam(value = "name", defaultValue = "0") String name,
			@RequestParam(value = "action", defaultValue = "0") String action) {

		String result = "";
		String command = "sudo initctl " + action + " " + name;

		try {
			Process process = Runtime.getRuntime().exec(command);
			InputStream inputstream = process.getInputStream();
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inputstream);

			byte[] contents = new byte[1024];
			int bytesRead = 0;

			while ((bytesRead = bufferedInputStream.read(contents)) != -1) {
				result += new String(contents, 0, bytesRead);
			}

			return new ServiceStatus(counter.incrementAndGet(), name, action, result);
		} catch (Exception e) {
			// TODO: handle exception
			return new ServiceStatus(counter.incrementAndGet(), name, action, "error");
		}

	}

}