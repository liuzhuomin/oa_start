package cn;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * 消息提示工具类
 * 
 * @author liuzhuomin
 *
 */
public class MessageUtils {

	private final static Image ERROR = new Image(Display.getCurrent(),
			MessageUtils.class.getResourceAsStream("/icon/ava_error.png"));

	/**
	 * 进行消息提示
	 * 
	 * @param e
	 */
	public static void messgeByException(SWTException e) {
		MyMessageBox loginDialog = e.getBox() == null ? new MyMessageBox() : e.getBox();
		boolean codeIsNull = e.getE() == null;
		Integer code = codeIsNull ? 100 : e.getE().getCode();
		loginDialog.setTitle(code <= 100 ? WindowMessage.INFO : WindowMessage.ERRROR);
		loginDialog.setMessage(codeIsNull ? e.getMessage() : e.getE().getMessage());
		if (code > 100) {
			loginDialog.setImage(ERROR);
		}
		int open = loginDialog.open();
		if (open == SWT.OK) {
			if (e.getDoSomeThingClass() != null) {
				e.getDoSomeThingClass().doSmoeThing();
			}
		} else {
			DoSomethingAfterDown cancelDoSomeThing = e.getCancelDoSomeThing();
			if (cancelDoSomeThing != null) {
				cancelDoSomeThing.doSmoeThing();
			}
		}
	}
}
