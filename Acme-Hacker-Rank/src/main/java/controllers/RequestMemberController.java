
package controllers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BrotherhoodService;
import services.MemberService;
import services.RequestService;
import domain.Company;
import domain.Hacker;
import domain.Request;

@Controller
@RequestMapping("/request/member")
public class RequestMemberController extends AbstractController {

	@Autowired
	private RequestService		requestService;
	@Autowired
	private BrotherhoodService	brotherhoodService;
	@Autowired
	private MemberService		memberService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Request> requests;
		requests = this.requestService.getAllMyRequest(this.memberService.getMemberByUserAccount(LoginService.getPrincipal().getId()).getId());
		result = new ModelAndView("request/list");
		result.addObject("requests", requests);
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Request request;

		request = this.requestService.create();
		final Integer a = LoginService.getPrincipal().getId();
		final Hacker member = this.memberService.getMemberByUserAccount(a);
		final Collection<Company> brotherhoods = this.brotherhoodService.getBrotherhoodsByMember(member.getId());
		final Set<Company> brotherhoodsWithOutDuplicates = new HashSet<Company>(brotherhoods);
		result = new ModelAndView("request/edit");
		result.addObject("request", request);
		result.addObject("brotherhoods", brotherhoodsWithOutDuplicates);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(Request newRequest, final BindingResult binding) {
		ModelAndView result;
		final int userAccountId = LoginService.getPrincipal().getId();
		final Hacker member = this.memberService.getMemberByUserAccount(userAccountId);
		final Collection<Company> brotherhoods = this.brotherhoodService.getBrotherhoodsByMember(member.getId());
		final Set<Company> brotherhoodsWithOutDuplicates = new HashSet<Company>(brotherhoods);
		try {
			newRequest = this.requestService.reconstruct(newRequest, binding);
			if (!binding.hasErrors()) {
				this.requestService.save(newRequest);
				result = new ModelAndView("redirect:list.do");
			} else {
				result = new ModelAndView("request/edit");
				result.addObject("brotherhoods", brotherhoodsWithOutDuplicates);
				result.addObject("request", newRequest);
			}
		} catch (final Exception e) {
			result = new ModelAndView("request/edit");
			result.addObject("exception", "e");
			result.addObject("brotherhoods", brotherhoodsWithOutDuplicates);
			result.addObject("request", newRequest);
		}
		return result;

	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int requestId) {
		ModelAndView result;

		try {
			final Request request = this.requestService.findOne(requestId);
			Assert.notNull(request);
			this.requestService.delete(request);
			result = new ModelAndView("redirect:list.do");
			return result;
		} catch (final Exception e) {
			result = new ModelAndView("request/list");
			result.addObject("exception", "e");
			result.addObject("requests", this.requestService.getAllMyRequest(this.memberService.getMemberByUserAccount(LoginService.getPrincipal().getId()).getId()));
			return result;
		}
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int requestId) {
		ModelAndView result;
		Request request;
		try {
			request = this.requestService.findOne(requestId);
			Assert.notNull(request);
			final Hacker m = this.memberService.getMemberByUserAccount(LoginService.getPrincipal().getId());
			Assert.isTrue(m.getRequests().contains(request));
			result = new ModelAndView("request/show");
			result.addObject("request", request);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do");
		}
		return result;
	}
}
