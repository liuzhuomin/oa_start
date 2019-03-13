package cn;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;


/**
 * 操作properties
 * 
 * @author liuzhuomin
 *
 */
public class PropertiesUtils {
	private static InputStream s = null;

	public static void close() {
		if (s != null) {
			try {
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据路径或者文件名称创建一个properties对象,或者查找此对象
	 * 
	 * @param path         文件路径或者名称
	 * @param containsRoot 是否使用系统根路径
	 * @return properties对象
	 */
	public static Properties findProperties(String path, boolean containsRoot) {
		if (path.startsWith(File.separator) || path.startsWith(File.pathSeparator))
			throw new RuntimeException("非法格式");
		File file = null;
		/**
		 * 代表包含根路径
		 */
		String fileName = null;
		if (containsRoot) {
			if (path.indexOf(File.separator) != -1) {
				int index = path.lastIndexOf(File.separator) + 1;
				fileName = path.substring(index).replace(File.separator, "");
				path = path.substring(0, index);
			} else {
				fileName = path;
			}
		} else {
			fileName = path;
		}
		if (containsRoot) {
			file = new File(System.getProperty("user.home") + File.separatorChar + WindowStatics.ROOT_DIR);
		} else {
			file = new File(path);
		}
		if (!file.exists()) {
			file.mkdirs();
		}
		File propertiesFile = new File(file.getAbsolutePath() + File.separator + fileName);
		if (!propertiesFile.exists()) {
			try {
				propertiesFile.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException("crate properties faild");
			}
		}
		Properties properties = new Properties();
		try {
			s = new BufferedInputStream(new FileInputStream(propertiesFile.getAbsolutePath()));
			properties.load(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	/**
	 * 根据路径和用户路径寻找完整路径，并且创建不存在的文件夹
	 * 
	 * @param path 完整路径
	 * @return {@link String}完整路径
	 */
	public static String getFallPath(String path) {
		String rootPath = System.getProperty("user.home") + File.separatorChar + WindowStatics.ROOT_DIR;
		if (!path.startsWith(File.separator)) {
			rootPath += File.separator + path;
		}
		File propertiesFile = new File(rootPath);
		if (!propertiesFile.getParentFile().exists()) {
			propertiesFile.getParentFile().mkdirs();
		}
		if (!propertiesFile.exists()) {
			try {
				propertiesFile.createNewFile();
			} catch (IOException e) {
				MessageUtils.messgeByException(new SWTException(SWTEnum.APPLET_ERROR));
			}
		}
		return propertiesFile.getAbsolutePath();
	}

	/**
	 * 找寻propertoes文件，先调用{@link #getFallPath(String)}函数，然后传过来
	 * 
	 * @param path 完整路径
	 * @return {@link Properties}对象
	 */
	public static Properties findProperties2(String path) {
		Properties properties = new Properties();
		try {
			File file = new File(path);
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					MessageUtils.messgeByException(new SWTException(SWTEnum.APPLET_ERROR));
				}
			}
			s = new BufferedInputStream(new FileInputStream(file));
			properties.load(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	public static boolean saveProperties(Properties pro, String path, boolean containsRoot) {
		if (containsRoot) {
			path = System.getProperty("user.home") + File.separatorChar + WindowStatics.ROOT_DIR + File.separatorChar
					+ path;
		}
		OutputStream out = null;
		try {
			out = new FileOutputStream(path);
			pro.store(out, "add success");
			out.flush();
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				out.close();
			} catch (IOException e) {
			}
		}

		return false;
	}

	public static boolean saveProperties2(Properties pro, String path) {
		OutputStream out = null;
		try {
			out = new FileOutputStream(path);
			pro.store(out, "add success");
			out.flush();
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				out.close();
			} catch (IOException e) {
			}
		}

		return false;
	}
}
