package cn;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

public class WindowUtils {
	/**
	 * 获取屏幕中甲的xy
	 * 
	 * @return
	 */
	public static int[] getXYWH(int width, int height) {
		Rectangle bounds = Display.getDefault().getPrimaryMonitor().getBounds();
		int x = bounds.x + (bounds.width - width) / 2;
		int y = bounds.y + (bounds.height - height) / 2;
		return new int[] { x, y, bounds.width, bounds.height };
	}

}
