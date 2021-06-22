package com.airtel.urlshortener.enums;

import com.airtel.online.boot.exception.error.AirtelError;

/**
 * 
 * @author deepanshunagpal
 * @since 22-06-2021
 *
 */
public enum UrlError implements AirtelError{
	BASE_URL_NOT_FOUND_ERROR,
	INVALID_LONG_URL_REQUEST,
	INVALID_SHORT_URL_REQUEST,
	EXCESSIVE_TRIES_TO_CREATE_SHORTCODE_ERROR;

	@Override
	public String getErrorCode() {
		return this.toString();
	}

}
