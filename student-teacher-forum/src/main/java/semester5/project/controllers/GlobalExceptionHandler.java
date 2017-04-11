package semester5.project.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

	@Value("${message.exception.error}")
	private String exceptionMessage;

	@Value("${message.duplicate.user}")
	private String duplicateUserMessage;

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) {
		ModelAndView mav = new ModelAndView();
		mav.getModel().put("message", exceptionMessage);
		mav.getModel().put("url", req.getRequestURI());
		mav.getModel().put("exception", e);
		mav.setViewName("app.exception");
		return mav;
	}

	@ExceptionHandler(value = DataIntegrityViolationException.class)
	public ModelAndView duplicateUserHandler(HttpServletRequest req, Exception e) {
		ModelAndView mav = new ModelAndView();
		mav.getModel().put("message", duplicateUserMessage);
		mav.getModel().put("url", req.getRequestURI());
		mav.getModel().put("exception", e);
		mav.setViewName("app.exception");
		return mav;
	}

	@ExceptionHandler(MultipartException.class)
	@ResponseBody
	public String fileUploadHandler(Exception e) {
		e.printStackTrace();
		return "Error Occurred";
	}
}
