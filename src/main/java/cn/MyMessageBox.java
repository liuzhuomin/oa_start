package cn;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


/**
 * 自定义消息框
 * 
 * @author liuzhuomin
 *
 */
public class MyMessageBox {

	private Shell mainShell;

	private Composite mainComposite;

	private Image image;

	private String title = "";

	private String message = "";

	private static Font defaultFont = SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL);

	private List<Button> buttons = new ArrayList<>();

	private static final Image DEFAULT_IMAGE = new Image(Display.getCurrent(),
			MyMessageBox.class.getResourceAsStream("/icon/提示(3).png"));

	private static Integer result;

	private Composite buttonComposite;

	private boolean canClose;

	private CLabel titleLabel;

	private Text messageText;

	public MyMessageBox() {
		initAnyThings();
	}

	public MyMessageBox(String title) {
		super();
		this.title = title;
		initAnyThings();
	}

	public MyMessageBox(Image image, String message) {
		super();
		this.image = image;
		this.message = message;
	}

	public MyMessageBox(String title, String message) {
		super();
		this.title = title;
		this.message = message;
	}

	public MyMessageBox(Image image) {
		super();
		this.image = image;
	}

	private void initAnyThings() {
		init();
		createTop();
		createMiddle();
		createBotoom();
	}

	public MyMessageBox(Image image, String title, String message) {
		super();
		this.image = image;
		this.title = title;
		this.message = message;
		initAnyThings();
	}

	public MyMessageBox init() {
		synchronized (MyMessageBox.class) {
			if (mainShell == null) {
				mainShell = new Shell(Display.getCurrent(), SWT.APPLICATION_MODAL | SWT.BORDER);
				int[] xy = WindowUtils.getXYWH(WindowStatics.MESSAGE_WIDTH, WindowStatics.MESSAGE_HEIGHT);
				mainShell.setBounds(xy[0], xy[1], WindowStatics.MESSAGE_WIDTH, WindowStatics.MESSAGE_HEIGHT);
				mainShell.setLayout(new FillLayout());
				mainShell.setBackground(WindowStatics.WHITE);
				mainShell.setBackgroundMode(SWT.INDETERMINATE);
				mainShell.addListener(SWT.Close, e -> {
					e.doit = canClose;
				});
				createMainCompo();
			}
			return this;
		}
	}

	private void createMainCompo() {
		mainComposite = new Composite(mainShell, SWT.None);
		GridLayout gridLayout = new GridLayout(10, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		mainComposite.setLayout(gridLayout);
	}

	private void createTop() {
		titleLabel = new CLabel(mainComposite, SWT.NONE);
		Image systemImage = this.image == null ? DEFAULT_IMAGE : this.image;
		titleLabel.setImage(systemImage);
		titleLabel.setText(this.title);
		GridData gridData = new GridData(SWT.LEFT, SWT.TOP, false, false, 10, 1);
		titleLabel.setLayoutData(gridData);
		// 这个是上面的水平线
		new Label(mainComposite, SWT.SEPARATOR | SWT.HORIZONTAL)
				.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 10, 1));
	}

	private void createMiddle() {
		CLabel messageLabel = new CLabel(mainComposite, SWT.NONE);
//		messageLabel.setImage(this.image == null ? DEFAULT_IMAGE : this.image);
		messageLabel.setImage(Display.getCurrent().getSystemImage(SWT.ICON_WARNING));

		messageLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, true, 2, 5));

		messageText = new Text(mainComposite, SWT.WRAP);
		messageText.setEditable(false);
		messageText.setDoubleClickEnabled(false);
		messageText.setTouchEnabled(false);
		messageText.setFont(defaultFont);
		messageText.setText(getMessage());
		messageText.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 8, 5));
	}

	private void createBotoom() {
		// 这个是下面的水平线
		new Label(mainComposite, SWT.SEPARATOR | SWT.HORIZONTAL)
				.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, true, 10, 1));

		buttonComposite = new Composite(mainComposite, SWT.None);
		buttonComposite.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false, 10, 1));

		RowLayout layout = new RowLayout(SWT.HORIZONTAL);
		layout.wrap = true;
		layout.fill = false;
		layout.justify = true;
		buttonComposite.setLayout(layout);

		if (buttons.size() == 0) {
			Button shure = new Button(buttonComposite, SWT.NONE);
			shure.setText("确认");
			Cursor cursor = SWTResourceManager.getCursor(SWT.CURSOR_HAND);
			shure.setCursor(cursor);
			Button cancle = new Button(buttonComposite, SWT.NONE);
			cancle.setText("取消");
			cancle.setCursor(cursor);
			shure.addListener(SWT.Selection, e -> {
				result = SWT.OK;
				mainShell.dispose();
			});
			cancle.addListener(SWT.Selection, e -> {
				result = SWT.NO;
				mainShell.dispose();
			});
		}
		mainShell.addListener(SWT.KeyDown, e -> {
			if (e.character == SWT.CR) {
				result = SWT.OK;
				mainShell.dispose();
			}
		});
	}

	public Integer open() {
		Display.getCurrent().beep();
		messageText.setText(this.message);
		titleLabel.setText(this.title);
		if (this.image != null) {
			titleLabel.setImage(this.image);
			mainShell.setImage(this.image);
		}
		mainComposite.getChildren();
		mainShell.setFocus();
		this.mainShell.open();
		this.mainShell.layout();
		while (!mainShell.isDisposed()) {
			if (!Display.getCurrent().readAndDispatch()) {
				Display.getCurrent().sleep();
			}
		}
		return result;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public static Font getDefaultFont() {
		return defaultFont;
	}

	public static void setDefaultFont(Font defaultFont) {
		MyMessageBox.defaultFont = defaultFont;
	}

	public List<Button> getButtons() {
		return buttons;
	}

	public void setButtons(List<Button> buttons) {
		this.buttons = buttons;
	}

	public Composite getButtonComposite() {
		return buttonComposite;
	}
}