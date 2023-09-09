package org.tutorial.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
public class MyTask {
	@Scheduled(cron = "* 0/20 * * * ?")
	public void print() {
		System.out.println("print task");
	}

	// 可在.yml做更多task相關配置
}
