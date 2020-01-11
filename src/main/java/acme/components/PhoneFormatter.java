
package acme.components;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;

import acme.datatypes.Phone;
import acme.framework.helpers.MessageHelper;
import acme.framework.helpers.StringHelper;

public class PhoneFormatter implements Formatter<Phone> {

	@Override
	public String print(final Phone object, final Locale locate) {
		assert object != null;
		assert locate != null;

		String result;
		String countryCodeText, areaCodeText, numberText;

		countryCodeText = String.format("+%d", object.getCountryCode());
		areaCodeText = object.getAreaCode() == null ? "" : String.format(" (%d) ", object.getAreaCode());
		numberText = String.format("%s", object.getNumber());

		result = String.format("+%d%s%1d", countryCodeText, areaCodeText, numberText);

		return result.toString();
	}

	@Override
	public Phone parse(final String text, final Locale locate) throws ParseException {
		assert !StringHelper.isBlank(text);
		assert locate != null;

		Phone result;
		String countryCodeRegex, areaCodeRegex, numberRegex, phoneRegex;
		Pattern pattern;
		Matcher matcher;
		String errorMessage;
		String countryCodeText;
		int countryCode;
		String areaCode, number;

		countryCodeRegex = "\\+\\d{1,3}";
		areaCodeRegex = "\\d{1,6}";
		numberRegex = "\\d{1,9}([\\s-]\\d{1,9}){0,5}";
		phoneRegex = String.format("^\\s*(?<CC>%1$s)(\\s+\\((?<AC>%2$s)\\)\\s+)(?<N>%3$s)\\s*$", countryCodeRegex, areaCodeRegex, numberRegex);

		pattern = Pattern.compile(phoneRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		matcher = pattern.matcher(text);

		if (!matcher.find()) {
			errorMessage = MessageHelper.getMessage("default.error.conversion", null, "Invalid value", locate);
			throw new ParseException(0, errorMessage);
		} else {
			countryCodeText = matcher.group("CC");
			countryCode = Integer.valueOf(countryCodeText);
			areaCode = matcher.group("AC");
			number = matcher.group("N");

			result = new Phone();
			result.setCountryCode(countryCode);
			result.setAreaCode(areaCode);
			result.setNumber(number);
		}

		return result;
	}
}
