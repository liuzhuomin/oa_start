package cn;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * 用于模拟HTTP请求中GET/POST方式
 * 
 * @author landa
 *
 */
public class HttpUtils {

	/**
	 * 发送GET请求
	 * 
	 * @param url        目的地址
	 * @param parameters 请求参数，Map类型。
	 * @return 远程响应结果
	 */
	public static String sendGet(String url, Map<String, String> parameters) {
		String result = "";
		BufferedReader in = null;// 读取响应输入流
		StringBuffer sb = new StringBuffer();// 存储参数
		String params = "";// 编码之后的参数
		try {
			// 编码请求参数
			if (parameters.size() == 1) {
				for (String name : parameters.keySet()) {
					sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"));
				}
				params = sb.toString();
			} else {
				for (String name : parameters.keySet()) {
					sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"))
							.append("&");
				}
				String temp_params = sb.toString();
				params = temp_params.substring(0, temp_params.length() - 1);
			}
			String full_url = url + "?" + params;
//            Logger.getLogger(this.getClass().getName()).info(full_url); 
			// 创建URL对象
			java.net.URL connURL = new java.net.URL(full_url);
			// 打开URL连接
			java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL.openConnection();
			// 设置通用属性
			httpConn.setRequestProperty("Accept", "*/*");
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
			// 建立实际的连接
			httpConn.connect();
			// 定义BufferedReader输入流来读取URL的响应,并设置编码方式
			in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
			String line;
			// 读取返回的内容
			while ((line = in.readLine()) != null) {
				result += line;
			}
			closein(in);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

	private static void closein(BufferedReader in) {
		try {
			if (in != null) {
				in.close();
			}
		} catch (IOException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	/**
	 * 发送POST请求
	 * 
	 * @param url        目的地址
	 * @param parameters 请求参数，Map类型。
	 * @return 远程响应结果
	 */
	public static String sendPost(String url, Map<String, String> parameters) {
		String result = "";// 返回的结果
		BufferedReader in = null;// 读取响应输入流
		PrintWriter out = null;
		StringBuffer sb = new StringBuffer();// 处理请求参数
		String params = "";// 编码之后的参数
		try {
			// 编码请求参数
			if (parameters.size() == 1) {
				for (String name : parameters.keySet()) {
					sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"));
				}
				params = sb.toString();
			} else {
				for (String name : parameters.keySet()) {
					sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"))
							.append("&");
				}
				String temp_params = sb.toString();
				params = temp_params.substring(0, temp_params.length() - 1);
			}
			// 创建URL对象
			java.net.URL connURL = new java.net.URL(url);
			// 打开URL连接
			java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL.openConnection();
			// 设置通用属性
			httpConn.setRequestProperty("Accept", "*/*");
//            httpConn.set
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
			// 设置POST方式
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			// 获取HttpURLConnection对象对应的输出流
			out = new PrintWriter(httpConn.getOutputStream());
			// 发送请求参数
			out.write(params);
//            Logger.getLogger(this.getClass().getName()).info(params);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应，设置编码方式
			in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
			String line;
			// 读取返回的内容
			while ((line = in.readLine()) != null) {
				result += line;
			}
			closeAll(in, out);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

	private static void closeAll(BufferedReader in, PrintWriter out) {
		try {
			closeOut(out);
			if (in != null) {
				in.close();
			}
		} catch (IOException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	/**
	 * 发送POST请求
	 * 
	 * @param url        目的地址
	 * @param parameters 请求参数，Map类型。
	 * @return 远程响应结果
	 */
	public static InputStream sendPost2(String url) {
		PrintWriter out = null;
		String params = "";// 编码之后的参数
		try {
			// 创建URL对象
			java.net.URL connURL = new java.net.URL(url);
			// 打开URL连接
			java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL.openConnection();
			// 设置通用属性
			httpConn.setRequestProperty("Accept", "*/*");
//            httpConn.set
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
			// 设置POST方式
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			// 获取HttpURLConnection对象对应的输出流
			out = new PrintWriter(httpConn.getOutputStream());
			// 发送请求参数
			out.write(params);
//            Logger.getLogger(this.getClass().getName()).info(params);
			// flush输出流的缓冲
			out.flush();
			closeOut(out);
			return httpConn.getInputStream();
			// 定义BufferedReader输入流来读取URL的响应，设置编码方式
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
//		return result;
	}

	private static void closeOut(PrintWriter out) {
		if (out != null) {
			out.close();
		}
	}

	/**
	 * 发送POST请求
	 * 
	 * @param url 目的地址
	 * @param str 请求参数，json类型
	 * @return 远程响应结果
	 */
	public static String sendPost(String url, String str) {
		String result = "";// 返回的结果
		BufferedReader in = null;// 读取响应输入流
		OutputStreamWriter outPut = null;
		try {
			// 创建URL对象
			java.net.URL connURL = new java.net.URL(url);
			// 打开URL连接
			java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL.openConnection();
			// 设置通用属性
			httpConn.setRequestProperty("Accept", "*/*");
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
			httpConn.setRequestProperty("Content-type", "application/json");
			// 设置POST方式
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			// 获取HttpURLConnection对象对应的输出流
			outPut = new OutputStreamWriter(httpConn.getOutputStream(), "UTF-8");
			// 发送请求参数
			outPut.write(str);
			// flush输出流的缓冲
			outPut.flush();
			// 定义BufferedReader输入流来读取URL的响应，设置编码方式
			in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
			String line;
			// 读取返回的内容
			while ((line = in.readLine()) != null) {
				result += line;
			}
			try {
				if (outPut != null) {
					outPut.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				throw new RuntimeException(ex.getMessage());
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

	/**
	 * Author:Jack Time:2017年9月5日下午2:20:26
	 * 
	 * @param url
	 * @param parameters
	 * @return Return:String Description:发送Post请求，并且发送的是Json格式的数据。
	 */
	public static String sendPostJson(String url, Map<String, String> parameters) {
		String result = "";// 返回的结果
		BufferedReader in = null;// 读取响应输入流
		PrintWriter out = null;
		String params = "";// 编码之后的参数
		try {
			if (parameters != null && parameters.size() > 0) {
				params = JSON.toJSONString(parameters);
			}
			// 创建URL对象
			java.net.URL connURL = new java.net.URL(url);
			// 打开URL连接
			java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL.openConnection();
			// 设置通用属性
			httpConn.setRequestProperty("Accept", "*/*");
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
			// 设置POST方式
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			// 获取HttpURLConnection对象对应的输出流
			out = new PrintWriter(httpConn.getOutputStream());
			// 发送请求参数
			out.write(params);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应，设置编码方式
			in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
			String line;
			// 读取返回的内容
			while ((line = in.readLine()) != null) {
				result += line;
			}
			closeAll(in, out);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	public static String sendPost(String url) {
		String result = "";// 返回的结果
		BufferedReader in = null;// 读取响应输入流
		try {
			// 创建URL对象
			java.net.URL connURL = new java.net.URL(url);
			// 打开URL连接
			java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL.openConnection();
			httpConn.setRequestMethod("POST");
			// 设置通用属性
			httpConn.setRequestProperty("Accept", "*/*");
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
			// 设置POST方式
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			// 定义BufferedReader输入流来读取URL的响应，设置编码方式
			in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
			String line;
			// 读取返回的内容
			while ((line = in.readLine()) != null) {
				result += line;
			}
			closein(in);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
}