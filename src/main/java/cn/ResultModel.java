package cn;

import com.alibaba.fastjson.JSON;

/**
 * 	自定义结果集合
 * @author liuzhuomin
 *
 */
public class ResultModel {
	private boolean hasError;
	private String msg;
	private String data;
	private int code;
	private Integer count;// 总数量
	private Class<?> clazz;

	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getData() {
		return data;
	}

	public <T> T getDataObj(Class<T> clazz) {
		try {
			return JSON.parseObject(this.data, clazz);
		} catch (Exception e) {
			return null;
		}
	}

//	
//	static class Obj{
//		private String a;
//		private String b;
//		public String getA() {
//			return a;
//		}
//		public void setA(String a) {
//			this.a = a;
//		}
//		public String getB() {
//			return b;
//		}
//		public void setB(String b) {
//			this.b = b;
//		}
//		@Override
//		public String toString() {
//			return "Obj [a=" + a + ", b=" + b + "]";
//		}
//		
//	}
//	
//	public static void main(String[] args) {
//		String json="[{'a':'a','b':'b'},{'a':123,'b':'321'}]";
//		List parseObject = JSON.parseObject(json, java.util.List.class);
//		for (Object object : parseObject) {
//			Logger.getLogger(this.getClass().getName()).info(JSON.parseObject(object.toString(),Obj.class));
//		}
//	}
	public void setData(String data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public ResultModel(boolean hasError, String msg, String data, int code) {
		super();
		this.hasError = hasError;
		this.msg = msg;
		this.data = data;
		this.code = code;
	}

	public ResultModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public ResultModel(boolean hasError, String msg, String data, int code, Integer count) {
		super();
		this.hasError = hasError;
		this.msg = msg;
		this.data = data;
		this.code = code;
		this.count = count;
	}

	@Override
	public String toString() {
		return "ResultModel [hasError=" + hasError + ", msg=" + msg + ", data=" + data + ", code=" + code + ", count="
				+ count + "]";
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	
	

}
