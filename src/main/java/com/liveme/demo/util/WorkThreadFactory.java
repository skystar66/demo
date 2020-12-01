package com.liveme.demo.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工厂
 * @author wgx
 * @version 2014年12月22日
 */
public class WorkThreadFactory implements ThreadFactory{

	static final AtomicInteger poolNumber = new AtomicInteger(1);
	final AtomicInteger threadNumber = new AtomicInteger(1);
	final ThreadGroup group;
	final String namePrefix;
	final boolean isDaemon;

	public WorkThreadFactory() {
		this("pool");
	}

	public WorkThreadFactory(String name) {
		this(name, false);
	}

	public WorkThreadFactory(String preffix, boolean daemon) {
		SecurityManager s = System.getSecurityManager();
		group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
		namePrefix = preffix + "-" + poolNumber.getAndIncrement() + "-thread-";
		isDaemon = daemon;
	}

	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
		t.setDaemon(isDaemon);
		if (t.getPriority() != Thread.NORM_PRIORITY) {
			t.setPriority(Thread.NORM_PRIORITY);
		}
		return t;
	}

}