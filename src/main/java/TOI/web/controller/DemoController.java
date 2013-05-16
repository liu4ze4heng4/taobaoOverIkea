/**
 * 
 */
package TOI.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.tools.config.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zehengliu
 * 
 */
@Controller
public class DemoController {
	@RequestMapping(value = "/vm/demo.do", params = "m=hello")
	public String newsListPreview(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("name", "my name is ikea over taobao system!! ");
		model.addAttribute("date", new Data());
		return "demo";
	}
}
