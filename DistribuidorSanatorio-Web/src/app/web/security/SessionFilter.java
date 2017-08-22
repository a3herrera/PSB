package app.web.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.client.utilities.CollectionsUtiliy;
import app.client.utilities.Constants;
import app.security.Menu;

@WebFilter(filterName = "SessionFilter", urlPatterns = "/*")
public class SessionFilter implements Filter {

	private static final String IMG = "/img";
	private static final String CSS = "/css";
	private static final String JS = "/js";

	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			if (request instanceof HttpServletRequest) {
				HttpServletRequest req = (HttpServletRequest) request;

				String uri = req.getRequestURI();
				final String contexPath = req.getContextPath();

				if (allwaysAllow(uri)) {
					chain.doFilter(request, response);
				}

				if (!isAllowed(req)) {
					if (!defaultAllowed(uri)) {
						((HttpServletResponse) response).sendRedirect(contexPath + Constants.LOGIN_PAGE);
					} else {
						chain.doFilter(request, response);
					}
				} else {
					if (!defaultAllowed(uri)) {
						List<Menu> options = (List<Menu>) req.getSession().getAttribute(Constants.USER_OPTIONS);

						if (uri.indexOf(CSS) != -1 || uri.indexOf(IMG) != -1 || uri.indexOf(JS) != -1
								|| uri.indexOf(Constants.MAIN_PAGE) != -1
								|| uri.indexOf(Constants.PROFILE_PAGE) != -1) {
							chain.doFilter(request, response);
							return;
						}

						if (!CollectionsUtiliy.isEmptyList(options)) {
							for (Menu option : options) {
								String tempURL = removeFinally(option.getURL());
								if ((uri.indexOf(tempURL) != -1)) {
									chain.doFilter(request, response);
									req.getSession().setAttribute(Constants.USER_CURRENT_OPTION, option);
									return;
								}

							}

						}

						((HttpServletResponse) response).sendRedirect(contexPath + Constants.MAIN_PAGE);
					} else {
						((HttpServletResponse) response).sendRedirect(contexPath + Constants.MAIN_PAGE);
					}
				}
			}
		} catch (Exception e) {
			// Meter al logger
		}
	}

	private String removeFinally(String uri) {
		if (uri.endsWith(".view")) {
			return uri.replace(".view", "");
		}
		if (uri.endsWith(".xhtml")) {
			return uri.replace(".xhtml", "");
		}
		return uri;
	}

	private boolean isAllowed(HttpServletRequest request) {
		if (request.getSession().getAttribute(Constants.LOGGED) == null
				|| (Boolean) request.getSession().getAttribute(Constants.LOGGED) == false) {
			return false;
		}
		return true;
	}

	public boolean defaultAllowed(String uri) {
		return uri.indexOf(Constants.LOGIN_PAGE) > 0;
	}

	public boolean allwaysAllow(String uri) {
		if (uri.endsWith(".css.view") || uri.endsWith(".js.view")) {
			return true;
		}
		return false;
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
