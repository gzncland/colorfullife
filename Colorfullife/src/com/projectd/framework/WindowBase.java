package com.projectd.framework;

public abstract class WindowBase {
	public boolean isOpening;
	public boolean isCloseing;
	public abstract void dispose();
	public abstract void resume();
}
