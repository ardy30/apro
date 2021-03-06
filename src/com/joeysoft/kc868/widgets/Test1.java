package com.joeysoft.kc868.widgets;

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Test1 {

	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display, SWT.SHELL_TRIM
				| SWT.NO_BACKGROUND | SWT.NO_REDRAW_RESIZE | SWT.V_SCROLL
				| SWT.H_SCROLL);
		Image originalImage = null;
		FileDialog dialog = new FileDialog(shell, SWT.OPEN);
		dialog.setText("选择一个图片文件或者取消");
		String string = dialog.open();
		if (string != null) {
			originalImage = new Image(display, string);
		}
		if (originalImage == null) {
			int width = 150, height = 200;
			originalImage = new Image(display, width, height);
			GC gc = new GC(originalImage);
			gc.fillRectangle(0, 0, width, height);
			gc.drawLine(0, 0, width, height);
			gc.drawLine(0, height, width, 0);
			gc.drawText("默认图片", 10, 10);
			gc.dispose();
		}
		final Image image = originalImage;
		final Point origin = new Point(0, 0);
		final ScrollBar hBar = shell.getHorizontalBar();
		hBar.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				int hSelection = hBar.getSelection();
				int destX = -hSelection - origin.x;
				origin.x = -hSelection;
				Rectangle rect = image.getBounds();
				shell.scroll(destX, 0, 0, 0, rect.width, rect.height, false);
			}
		});
		final ScrollBar vBar = shell.getVerticalBar();
		vBar.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				int vSelection = vBar.getSelection();
				int destY = -vSelection - origin.y;
				origin.y = -vSelection;
				Rectangle rect = image.getBounds();
				shell.scroll(0, destY, 0, 0, rect.width, rect.height, false);
			}
		});
		shell.addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event e) {
				Rectangle rect = image.getBounds();
				Rectangle client = shell.getClientArea();
				hBar.setMaximum(rect.width);
				vBar.setMaximum(rect.height);
				hBar.setThumb(Math.min(rect.width, client.width));
				vBar.setThumb(Math.min(rect.height, client.height));
				int hPage = rect.width - client.width;
				int vPage = rect.height - client.height;
				int hSelection = hBar.getSelection();
				int vSelection = vBar.getSelection();
				if (hSelection >= hPage) {
					if (hPage <= 0)
						hSelection = 0;
					origin.x = -hSelection;
				}
				if (vSelection >= vPage) {
					if (vPage <= 0)
						vSelection = 0;
					origin.y = -vSelection;
				}
				shell.redraw();
			}
		});
		shell.addListener(SWT.Paint, new Listener() {
			public void handleEvent(Event e) {
				GC gc = e.gc;
				gc.drawImage(image, origin.x, origin.y);
				Rectangle rect = image.getBounds();
				Rectangle client = shell.getClientArea();
				int marginWidth = client.width - rect.width;
				if (marginWidth > 0) {
					gc.fillRectangle(rect.width, 0, marginWidth, client.height);
				}
				int marginHeight = client.height - rect.height;
				if (marginHeight > 0) {
					gc.fillRectangle(0, rect.height, client.width, marginHeight);
				}
			}
		});
		shell.setSize(200, 150);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

}
