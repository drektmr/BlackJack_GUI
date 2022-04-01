package blackjack.model;

public class MyException extends Exception{
	private String code;
	
	public MyException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public MyException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MyException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MyException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	public MyException(String message, String pCode) {
		super(message);
		this.code=pCode;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return code+": "+this.getMessage();
	}
	
	

}
