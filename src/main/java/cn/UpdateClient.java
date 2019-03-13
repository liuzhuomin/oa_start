package cn;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

public class UpdateClient {

	protected static Shell shell;
	private static String result;
	private static long length;
	private static Display current;
	private static int i;
	private static Label label;
	private static Composite composite;
	private static long curentByte;
	private static int len;
	private static String basePath;
	private static BufferedOutputStream outStream;
	private static BufferedInputStream bis;

	public static void main(String[] args) {
//		open();
//		String property = System.getProperty("user.dir");
//		System.out.println(property + File.separator + "oa_v1.exe");
//		System.out.println(loadFile());
	}

	public static String loadFile(String versions) {
		String home = System.getProperty("user.home") + File.separator + WindowStatics.ROOT_DIR + File.separator
				+ "oaSettings.properties";
		String version = null;
		File file = new File(home);
		if (file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
				Properties properties = new Properties();
				properties.load(new FileInputStream(file));
				properties.setProperty("version", "v1.0");
				OutputStream out = new FileOutputStream(file.getAbsolutePath());
				properties.store(out, "add version info");
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Properties properties = new Properties();
			try {
				properties.load(new FileInputStream(file));
				if (versions != null && !versions.isEmpty()) {
					properties.setProperty("version", versions);
					OutputStream out = new FileOutputStream(file.getAbsolutePath());
					properties.store(out, "add version info");
					out.flush();
					out.close();
				}
				version = URLEncoder.encode(properties.getProperty(URLEncoder.encode("version", "GBK")), "GBK");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return version;
	}

	/**
	 * Create contents of the window.
	 * 
	 * @param fileName
	 * 
	 * @param l
	 */
	public static String open(Version dataObj, String fileName) {
		if (dataObj == null) {
			run(fileName);
			return "";
		}
		basePath = fileName;
		String filePath = basePath + "oa_v2.exe";
		length = dataObj.getSize();
		current = Display.getDefault();
		shell = new Shell(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		int[] xywh = WindowUtils.getXYWH(350, 200);
		shell.setBounds(xywh[0], xywh[1], 350, 200);
		shell.setText("更新系统");
		shell.setImage(new Image(Display.getCurrent(), UpdateClient.class.getResourceAsStream("/icon/logo.png")));
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		shell.setBackgroundMode(SWT.INDETERMINATE);
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));

		new Label(composite, SWT.NONE).setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));

		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		label.setText("检测到系统存在" + dataObj.getVersion() + "版本,是否更新?");

		new Label(composite, SWT.NONE).setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));

		final ProgressBar bar = new ProgressBar(composite, SWT.SMOOTH);
		bar.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false, 2, 1));
		bar.setVisible(false);
		bar.setMaximum(100);
		new Label(composite, SWT.NONE).setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));

		Button shure = new Button(composite, SWT.NONE);
		shure.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		shure.setText("点击更新");

		shure.addListener(SWT.Selection, e -> {
			shure.setVisible(false);
			bar.setVisible(true);
			label.setText("更新中...");
			composite.layout();
			InputStream in = HttpUtils.sendPost2(URL.DOWN_LOAD);
			bis = new BufferedInputStream(in);
			new Thread(new Runnable() {
				public void run() {
					while (!bar.isDisposed()) {
						download(dataObj, filePath, bar, bis);
					}
				}
			}).start();
		});

		shell.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				File file = new File(basePath + "oa_v2.exe");
				if (file.exists()) {
					file.delete();
				}
				try {
					if (outStream != null) {
						outStream.close();
					}
					if (bis != null) {
						bis.close();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		shell.open();
		while (!shell.isDisposed()) {
			if (!current.readAndDispatch()) {
				Display.getCurrent().sleep();
			}
		}
		run(fileName);
		return result;
	}

	private static void download(Version dataObj, String fileName, final ProgressBar bar, BufferedInputStream bis) {
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			NumberFormat numberFormat = NumberFormat.getInstance();
			outStream = new BufferedOutputStream(new FileOutputStream(file));
			len = 0;
			byte[] b = new byte[1024];
			curentByte = 1024;
			while ((len = bis.read(b)) != -1) {
				outStream.write(b, 0, len);
				current.syncExec(new Runnable() {
					@Override
					public void run() {
						long baytea = curentByte += (len < 1024 ? Long.valueOf(len) : 1024);
						float value = (float) baytea / length * 100;
						if (value > 100) {
							value = (float) 100.000;
							label.setText("成功:" + value + "%。请等待系统配置；");
							bar.setSelection(100);
						} else {
							String result = numberFormat.format(value);
							label.setText("更新中..." + result + "%");
							long round = Math.round(value);
							if (round > value && round != i) {
								bar.setSelection(++i);
							}
						}
						composite.layout();
					}
				});
			}
			outStream.flush();
			outStream.close();
			bis.close();
			// TODO 下载了之后怎么做
			loadFile(dataObj.getVersion());
			File agoFile = new File(basePath + "oa_v1.exe");
			if (agoFile.exists()) {
				agoFile.delete();
			}
			current.syncExec(new Runnable() {
				@Override
				public void run() {
					shell.setEnabled(false);
					// TODO Auto-generated method stub

				}
			});
			FileUtils.copyFile(file, new File(basePath + "oa_v1.exe"));
			current.syncExec(new Runnable() {
				@Override
				public void run() {
					shell.dispose();
					// TODO Auto-generated method stub
				}
			});

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private static void run(String fileName) {
		try {
			Runtime.getRuntime().exec(fileName + "oa_v1.exe");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
