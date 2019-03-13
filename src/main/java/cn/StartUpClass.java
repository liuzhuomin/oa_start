package cn;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

/**
 * <li>主要入口类
 * 
 * @author liuliuliu
 *
 */
/**
 * @author liuzhuomin
 *
 */
public class StartUpClass {

	/**
	 * Launch the application.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String filePath = System.getProperty("user.dir") + File.separator;
		String keyWord = "oa_v1.exe";
		if (checkRuntime(keyWord)) {
			return;
		}
		if (checkRuntime("oa_st.exe")) {
			return;
		}
		try {
			ResultModel checkNewVersion = UpdateInterFaceImpl.checkNewVersion(UpdateClient.loadFile(""));
			if (checkNewVersion == null) {
				MessageUtils.messgeByException(new SWTException(SWTEnum.CONECT_SERVER_ERROR));
				return;
			}
			System.out.println(checkNewVersion.getMsg());
			if (!"success".equals(checkNewVersion.getMsg())) {
				Version dataObj = checkNewVersion.getDataObj(Version.class);
				UpdateClient.open(dataObj, filePath);
			} else {
				Logger.getLogger(StartUpClass.class.getName()).info("路径为:" + filePath);
				Runtime.getRuntime().exec(filePath + "oa_v1.exe");
			}
		} catch (Exception e) {
			MessageUtils.messgeByException(new SWTException(SWTEnum.APPLET_ERROR));
		}
	}

	private static boolean checkRuntime(String keyWord) {
		boolean result = false;
		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec("cmd /c Tasklist");
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String s;
			int i = 0;
			while ((s = in.readLine()) != null) {
				s = s.toLowerCase();
				if (s.startsWith(keyWord)) {
					i++;
				}
				if (i > 1) {
					result = true;
					break;
				}
			}
			if (in != null)
				in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
