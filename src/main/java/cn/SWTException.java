package cn;

/**
 * <li>自定义异常类
 * 
 * @author liuzhuomin
 *
 */
public class SWTException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private DoSomethingAfterDown doSomeThingClass;
	private DoSomethingAfterDown cancelDoSomeThing;
	private SWTEnum e;
	private Integer code = null;
	private MyMessageBox box;

	public SWTException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 基于父类的构造方法
	 * 
	 * @param message
	 * 
	 */
	public SWTException(String message) {
		super(message);
	}

	/**
	 * 预期一个{@link SWTEnum}对象
	 * 
	 * @param e {@link SWTEnum} 标识此异常的code码和message消息
	 */
	public SWTException(SWTEnum e) {
		this.e = e;
	}

	/**
	 * @param e                {@link SWTEnum} 标识此异常的code码和message消息
	 * @param doSomeThingClass {@link DoSomethingAfterDown}
	 *                         标识此异常被aop检测到后需要运行的类，其中的{@link DoSomethingAfterDown#doSmoeThing()}方法会被執行
	 */
	public SWTException(SWTEnum e, DoSomethingAfterDown doSomeThingClass) {
		super();
		this.doSomeThingClass = doSomeThingClass;
		this.e = e;
	}

	public SWTException(DoSomethingAfterDown doSomeThingClass, SWTEnum e, Integer code, MyMessageBox box) {
		super();
		this.doSomeThingClass = doSomeThingClass;
		this.e = e;
		this.code = code;
		this.box = box;
	}

	public SWTException(SWTEnum e, MyMessageBox box, DoSomethingAfterDown doSomeThingClass) {
		super();
		this.doSomeThingClass = doSomeThingClass;
		this.e = e;
		this.box = box;
	}

	public SWTException(SWTEnum e, MyMessageBox box) {
		super();
		this.e = e;
		this.box = box;
	}

	public SWTException(SWTEnum e, DoSomethingAfterDown doSomeThingClass, DoSomethingAfterDown cancelDoSomeThing) {
		super();
		this.doSomeThingClass = doSomeThingClass;
		this.cancelDoSomeThing = cancelDoSomeThing;
		this.e = e;
	}

	public SWTException(DoSomethingAfterDown doSomeThingClass, DoSomethingAfterDown cancelDoSomeThing, SWTEnum e,
			Integer code, MyMessageBox box) {
		super();
		this.doSomeThingClass = doSomeThingClass;
		this.cancelDoSomeThing = cancelDoSomeThing;
		this.e = e;
		this.code = code;
		this.box = box;
	}

	public SWTEnum getE() {
		return e;
	}

	public DoSomethingAfterDown getDoSomeThingClass() {
		return doSomeThingClass;
	}

	public SWTException(Integer code, String message) {
		super(message);
		this.code = code;
	}

	public SWTException(String msg, DoSomethingAfterDown doSomethingAfterDown) {
	}

	public Integer getCode() {
		return code;
	}

	@Override
	public String toString() {
		return "SWTException [doSomeThingClass=" + doSomeThingClass + ", e=" + e + ", code=" + code + "]";
	}

	public MyMessageBox getBox() {
		return box;
	}

	public void setBox(MyMessageBox box) {
		this.box = box;
	}

	public DoSomethingAfterDown getCancelDoSomeThing() {
		return cancelDoSomeThing;
	}

}
