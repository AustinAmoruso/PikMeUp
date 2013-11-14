package com.pmu.android.api.transport.impl;

import java.util.concurrent.ConcurrentLinkedQueue;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;

import com.pmu.android.api.transport.IAsyncTransportCommand;
import com.pmu.android.api.transport.ITransportCallBack;

public class TransportQueue extends BroadcastReceiver implements Runnable {

	private ConcurrentLinkedQueue<IAsyncTransportCommand> commands;
	private ConnectivityManager cm;
	private Context context;
	private static final Object lock = new Object();
	private static final Object lockRunning = new Object();
	private Handler handle;
	private boolean isRunning;
	private ConcurrentLinkedQueue<ITransportCallBack> callbacks;

	public TransportQueue(Context newContext) {
		isRunning = false;
		commands = new ConcurrentLinkedQueue<IAsyncTransportCommand>();
		context = newContext;
		cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		handle = new Handler();
		callbacks = new ConcurrentLinkedQueue<ITransportCallBack>();
	}

	public synchronized void addCallback(ITransportCallBack callback) {
		if (!callbacks.contains(callback)) {
			callbacks.add(callback);
		}
	}

	public synchronized boolean removeCallback(ITransportCallBack callback) {
		return callbacks.remove(callback);
	}

	private synchronized void Notify(String status) {
		for (ITransportCallBack itcb : callbacks) {
			itcb.onCallback(new NetworkResponse(status));
		}
	}

	public TransportQueue() {
	}

	public void RunCommands() {
		handle.post(this);
	}

	public void add(IAsyncTransportCommand c) {
		commands.add(c);
		if (!isRunning()) {
			RunCommands();
		}
	}

	@Override
	public void onReceive(Context newContext, Intent intent) {
		if (intent.getAction().equals(
				android.net.ConnectivityManager.CONNECTIVITY_ACTION)) {
			if (commands != null && commands.size() > 0) {
				context = newContext;
				if (isConnected()) {
					if (!isRunning()) {
						RunCommands();
						Notify(NetworkResponse.AVAILABLE);
					}
				} else {
					Notify(NetworkResponse.UNAVAILABLE);
				}
			}
		}
	}

	private void attemptQueue() {
		setRunning(true);
		if (isConnected()) {
			while (commands.isEmpty() == false) {
				try {
					if (isConnected()) {
						IAsyncTransportCommand temp = commands.peek();
						AsyncTransportLayer atl = new AsyncTransportLayer();
						atl.execute(temp);
					} else {
						Notify(NetworkResponse.UNAVAILABLE);
					}
					commands.poll();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			Notify(NetworkResponse.UNAVAILABLE);
		}
		setRunning(false);
	}

	public boolean isRunning() {
		synchronized (lockRunning) {
			return isRunning;
		}
	}

	public void setRunning(boolean on) {
		synchronized (lockRunning) {
			isRunning = on;
		}
	}

	public boolean isConnected() {
		boolean connected = false;
		synchronized (lock) {
			try {
				connected = cm.getActiveNetworkInfo().isConnected();
			} catch (Exception e) {

			}
		}
		return true;
	}

	@Override
	public void run() {
		attemptQueue();
	}

}
