package com.fanwe.library.utils;

import java.util.List;

import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.view.MotionEvent;

public class SDCameraUtil
{

	private Camera camera;
	private float focusRectWidth;
	private float focusRectHeight;

	private float previewWidth;
	private float previewHeight;
	private float previewX;
	private float previewY;
	private Rect previewRect;

	public SDCameraUtil()
	{

	}

	public void focus(MotionEvent event, AutoFocusCallback callback)
	{
		if (event != null)
		{
			focus(event.getRawX(), event.getRawY(), callback);
		}
	}

	public void focus(float x, float y, AutoFocusCallback callback)
	{
		if (!isTouchPreview(x, y))
		{
			return;
		}

		// Rect focusRect = calculateClickArea(x, y);
		// Rect meteringRect = calculateClickArea(x, y);
		// Camera.Parameters parameters = camera.getParameters();
		// if (parameters.getMaxNumFocusAreas() > 0)
		// {
		// List<Camera.Area> focusAreas = new ArrayList<Camera.Area>();
		// focusAreas.add(new Camera.Area(focusRect, 1000));
		// parameters.setFocusAreas(focusAreas);
		// }
		// if (parameters.getMaxNumMeteringAreas() > 0)
		// {
		// List<Camera.Area> meteringAreas = new ArrayList<Camera.Area>();
		// meteringAreas.add(new Camera.Area(meteringRect, 1000));
		// parameters.setMeteringAreas(meteringAreas);
		// }
		// camera.setParameters(parameters);

		if (callback == null)
		{
			callback = new AutoFocusCallback()
			{

				@Override
				public void onAutoFocus(boolean success, Camera camera)
				{

				}
			};
		}
		camera.cancelAutoFocus();
		setFocusMode(Parameters.FOCUS_MODE_AUTO);
		camera.autoFocus(callback);
	}

	private Rect calculateClickArea(float x, float y)
	{
		Rect rect = new Rect();
		float[] location = parseToCameraLocation(x, y);

		int left = (int) (location[0] - focusRectWidth / 2);
		int top = (int) (location[1] - focusRectHeight / 2);
		int right = (int) (left + focusRectWidth);
		int bottom = (int) (top + focusRectHeight);

		rect.left = clamp(left, -1000, 1000);
		rect.top = clamp(top, -1000, 1000);
		rect.right = clamp(right, -1000, 1000);
		rect.bottom = clamp(bottom, -1000, 1000);

		return rect;
	}

	private float[] parseToCameraLocation(float x, float y)
	{
		float[] location = new float[2];

		if (x < previewX)
		{
			x = previewX;
		}
		if (y < previewY)
		{
			y = previewY;
		}

		float px = ((float) (x - previewX) / previewWidth);
		float py = ((float) (y - previewY) / previewHeight);

		location[0] = -1000 + 2000 * px;
		location[1] = -1000 + 2000 * py;

		return location;
	}

	private int clamp(int x, int min, int max)
	{
		if (x > max)
		{
			return max;
		}
		if (x < min)
		{
			return min;
		}
		return x;
	}

	private boolean isTouchPreview(float x, float y)
	{
		if (previewRect == null)
		{
			previewRect = new Rect();
			previewRect.left = (int) previewX;
			previewRect.top = (int) previewY;
			previewRect.right = (int) (previewX + previewWidth);
			previewRect.bottom = (int) (previewY + previewHeight);
		}
		return previewRect.contains((int) x, (int) y);
	}

	public void setFocusMode(String mode)
	{
		Parameters params = camera.getParameters();
		List<String> listMode = params.getSupportedFocusModes();
		if (listMode.contains(mode))
		{
			params.setFocusMode(mode);
			camera.setParameters(params);
		}
	}

	public void setFlashMode(String mode)
	{
		Parameters params = camera.getParameters();
		List<String> listMode = params.getSupportedFlashModes();
		if (listMode.contains(mode))
		{
			params.setFlashMode(mode);
			camera.setParameters(params);
		}
	}

	public void enableFlash(boolean enable)
	{
		if (enable)
		{
			setFlashMode(Parameters.FLASH_MODE_TORCH);
		} else
		{
			setFlashMode(Parameters.FLASH_MODE_OFF);
		}
	}

	public void setCamera(Camera camera)
	{
		this.camera = camera;
	}

	public Camera getCamera()
	{
		return camera;
	}

	public void setPreviewHeight(int previewHeight)
	{
		this.previewHeight = previewHeight;
	}

	public void setPreviewWidth(int previewWidth)
	{
		this.previewWidth = previewWidth;
	}

	public void setFocusRectHeight(int focusRectHeight)
	{
		this.focusRectHeight = focusRectHeight;
	}

	public void setFocusRectWidth(int focusRectWidth)
	{
		this.focusRectWidth = focusRectWidth;
	}

	public void setPreviewX(int previewX)
	{
		this.previewX = previewX;
	}

	public void setPreviewY(int previewY)
	{
		this.previewY = previewY;
	}

	public boolean enable()
	{
		return camera != null;
	}

}
