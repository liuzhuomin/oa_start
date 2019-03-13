package cn;

public enum SWTEnum {

	/** 程序错误 */
	APPLET_ERROR(404, "程序错误!"),
	/** 程序错误 */
	APPLET_EXSIT(404, "另一个程序正在运行中,无法运行!"),
	/** 链接服务器出错 */
	CONECT_SERVER_ERROR(104, "链接服务器出错");
	private int code;
	private String message;

	private SWTEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public int getCode() {
		return code;
	}

}
