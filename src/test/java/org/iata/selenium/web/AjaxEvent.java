package org.iata.selenium.web;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import org.iata.selenium.BrowserHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AjaxEvent {

	private BrowserHandler browserHandler;
	
	private By wait;
	private AjaxEventAction action;
	
	public AjaxEvent(BrowserHandler browserHandler) {
		this.browserHandler = browserHandler;
	}
	
	public AjaxEvent render(By wait) {
		this.wait = wait;
		return this;
	}
	
	public AjaxEvent addAction(AjaxEventAction action) {
		this.action = action;
		return this;
	}
	
	public WebElement perform() {

		if (wait == null || action == null) {
			return null;
		}

		CyclicBarrier barrier = new CyclicBarrier(2);
		CountDownLatch latch = new CountDownLatch(2);
		FutureTask<WebElement> monitorTask = new FutureTask<WebElement>(new AjaxMonitorTask(barrier,latch,wait));
		AjaxWorkerTask workerTask = new AjaxWorkerTask(barrier,latch,action);
		
		new Thread(monitorTask, "selenium-ajax-monitor").start();
		new Thread(workerTask, "selenium-ajax-worker").start();
		
		try {
			System.err.println(">>>>>>>waiting for all tasks completed");
			
			latch.await();
			
			System.err.println(">>>>>>all tasks have been completed");
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			return monitorTask.get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
		 
	}
	
	
	private class AjaxMonitorTask implements Callable<WebElement> {
		private CyclicBarrier barrier;
		private By wait;
		private CountDownLatch latch;
		
		private AjaxMonitorTask(CyclicBarrier barrier, CountDownLatch latch, By wait) {
			this.barrier = barrier;
			this.latch = latch;
			this.wait = wait;
		}
		
		@Override
		public WebElement call()  {
			WebElement waitElement = browserHandler.findElement(wait);
			
			try {
				barrier.await();
			} catch (Exception e) {
				e.printStackTrace();
			}		
			System.err.println(">>>>>>waiting for unattached from dom:" + wait);
			try {
				browserHandler.until(ExpectedConditions.stalenessOf(waitElement));
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.err.println(">>>>>>waiting for attached to dom:" + wait);
			try {
				waitElement =browserHandler.until(ExpectedConditions.presenceOfElementLocated(wait));
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.err.println(">>>>>>have been rendered:" + wait);
			latch.countDown();
			return waitElement;
		}

		 
		
	}
	
	private class AjaxWorkerTask implements Runnable {
		private CyclicBarrier barrier;
		private CountDownLatch latch;
		private AjaxEventAction action;
		private AjaxWorkerTask(CyclicBarrier barrier,CountDownLatch latch,AjaxEventAction action) {
			this.barrier = barrier;
			this.latch = latch;
			this.action = action;
		}
		
		@Override
		public void run() {
			try {
				barrier.await();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.err.println(">>>>>>work task started");
			try {
				action.execute(browserHandler);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.err.println(">>>>>>work task completed");
			latch.countDown();
		}		
		
	}
	
}
