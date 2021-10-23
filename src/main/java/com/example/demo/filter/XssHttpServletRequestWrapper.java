package com.example.demo.filter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Joel 防止XSS攻擊
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
	private HttpServletRequest request;

	boolean isUpData = false;// 判斷是否是上傳 上傳忽略

	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		String contentType = request.getContentType();
		if (null != contentType)
			isUpData = contentType.startsWith("multipart");
	}

	@Override
	public String getParameter(String name) {
		String value = request.getParameter(name);
		if (!StringUtils.isEmpty(value)) {
//			value = StringEscapeUtils.escapeHtml4(value);
			value = cleanXSS(value);
		}
		return cleanXSS(value);
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] parameterValues = super.getParameterValues(name);
		if (parameterValues == null) {
			return null;
		}
		for (int i = 0; i < parameterValues.length; i++) {
			String value = parameterValues[i];
//			parameterValues[i] = cleanXSS(StringEscapeUtils.escapeHtml4(value));
			parameterValues[i] = cleanXSS(value);
		}
		return parameterValues;
	}

	/**
	 * 獲取request的屬性時，做xss過濾
	 */
	@Override
	public Object getAttribute(String name) {
		Object value = super.getAttribute(name);
		if (null != value && value instanceof String) {
			value = cleanXSS((String) value);
		}
		return value;
	}

	@Override
	public String getHeader(String name) {

		String value = super.getHeader(name);
		if (value == null)
			return null;
		return cleanXSS(value);
	}

	private static String cleanXSS(String value) {
		value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		value = value.replaceAll("%3C", "&lt;").replaceAll("%3E", "&gt;");
		value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
		value = value.replaceAll("%28", "&#40;").replaceAll("%29", "&#41;");
		value = value.replaceAll("'", "&#39;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replaceAll("script", "");
		return value;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (isUpData) {
			return super.getInputStream();
		} else {

			final ByteArrayInputStream bais = new ByteArrayInputStream(
					inputHandlers(super.getInputStream()).getBytes());

			return new ServletInputStream() {

				@Override
				public int read() throws IOException {
					return bais.read();
				}

				@Override
				public boolean isFinished() {
					return false;
				}

				@Override
				public boolean isReady() {
					return false;
				}

				@Override
				public void setReadListener(ReadListener readListener) {
				}
			};
		}

	}

	public String inputHandlers(ServletInputStream servletInputStream) {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(servletInputStream, Charset.forName("UTF-8")));
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (servletInputStream != null) {
				try {
					servletInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return cleanXSS(sb.toString());
	}
}
